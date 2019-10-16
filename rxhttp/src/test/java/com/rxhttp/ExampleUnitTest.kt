package com.rxhttp

import androidx.cursoradapter.widget.SimpleCursorAdapter
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import org.junit.Test

import org.junit.Assert.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.lang.reflect.Type
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun rxTest(){
        println("测试一下是否可以运行")
        val API_BASE_URL = "http://lvji.attect.studio:8090/"


        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)

        val httpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
//            .addInterceptor(HttpInterceptor())


        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()


        val client = retrofit.create<HttpPath>(HttpPath::class.java)


        val commonBean = CommonBean()
        commonBean.id = 1
        val call = client.getDicListByParentId(commonBean)


        call.enqueue(object : Callback<MyResponse<List<CommonBean>>>{
            override fun onResponse(
                call: Call<MyResponse<List<CommonBean>>>,
                response: Response<MyResponse<List<CommonBean>>>
            ) {

                println("访问网络成功")

                response.body()?.d?.forEach {
                    println(it.name)
                }
            }

            override fun onFailure(call: Call<MyResponse<List<CommonBean>>>, t: Throwable) {
                println("访问网络出错")
            }
        })



        Thread.sleep(5000)

    }





}


