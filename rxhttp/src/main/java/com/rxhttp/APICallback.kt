package com.rxhttp

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.rxhttp.tools.multiCatch
import com.rxhttp.tools.multiLet
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import java.io.IOException
import java.lang.Exception
import java.lang.IllegalStateException
import java.lang.NullPointerException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


/**
 * 1.回调接口池
 * 2.下载进度
 */
abstract class APICallback<T> : Callback{

    private val otherSameCallback :ArrayList<APICallback<T>> by lazy { ArrayList<APICallback<T>>() }

    var canBeRemoveListener : CallbackCanBeRemoveListener? = null
    var request : Request? = null

    var ignore = false
    var masterCallback = false


    fun isStarted():Boolean = request != null

    fun isMasterCallback():Boolean = masterCallback



    fun addSame(callback:APICallback<T>){
        otherSameCallback.add(0 , callback)
    }


    override fun onFailure(call: Call, e: IOException) {

    }

    override fun onResponse(call: Call, response: okhttp3.Response) {

    }



    fun convertResponse(response: okhttp3.Response):T?{
        if(ignore)return null
        val genType = javaClass.genericSuperclass
        val params = genType?.run { (this as ParameterizedType).actualTypeArguments }
        val type = params?.get(0)
        if(!(type is ParameterizedType)){
            throw IllegalStateException("API缺少泛型参数")
        }
        val rawType = type.rawType
        val typeArgument = type.actualTypeArguments[0]
        val body = response.body ?: return null
        if(response.code in 300..599)
            throw ResponseFailedStateException(response.code , response.message)

        val gson = Gson()
        var jsonString: String?
        try {
            jsonString = body.string()
        }catch (e:IOException){
            throw e
        }
        //如果回来的json内容格式与设计API基础结构不一致，则直接进行json解析返回
        if (rawType !== APIResponse::class.java) {
            val data = gson.fromJson<T>(jsonString, type)
            response.close()
            return data
        } else {
            if (typeArgument === Void::class.java) {
                //无数据类型，以此种方式传递泛型 new APICallback<APIResponse<Void>>
                val simpleResponse = gson.fromJson(jsonString, SimpleResponse::class.java)
                response.close()
                //下方不需要做检查
                return simpleResponse as T
            } else {
                var apiResponse : APIResponse<T>? = null

                multiCatch({
                  //runBlock

                    apiResponse = gson.fromJson(jsonString , type)

                },{ e->//catchBlock
                    e.printStackTrace()
                    try {
                        val typeToken = object:ikidou.reflect.TypeToken<APIResponse<Any>>(){}
                        apiResponse = gson.fromJson(jsonString , typeToken.type)
                    }catch (ee:Exception){
                        throw ee
                    }

                } , IllegalStateException::class , JsonIOException::class , JsonSyntaxException::class)

                if(apiResponse == null || apiResponse?.invalid()?:true)throw NullPointerException("请检查数据结构")
                response.close()
                if(apiResponse?.code() in 300 .. 599){
                    //todo 计划集中处理非常状态码
                    var message = apiResponse?.message()
                    if(apiResponse?.data() is String){
                        message = apiResponse?.message() as String
                    }
                    throw ResponseFailedStateException(apiResponse?.code()?:-1 , message?:"message:null")
                }else{
                    return apiResponse as T
                }
            }
        }
    }



    interface CallbackCanBeRemoveListener{
        fun remove()
    }
}

