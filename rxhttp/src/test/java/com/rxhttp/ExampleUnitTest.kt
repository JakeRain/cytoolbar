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
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import java.io.BufferedInputStream
import java.io.InputStreamReader
import okhttp3.Headers.Companion.toHeaders
import java.io.IOException
import java.net.ServerSocket
import java.net.Socket
import java.nio.charset.Charset

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




    @Test
    fun okhttpTest(){

        val headers = HashMap<String , String>()

        headers.put("POST", "/gate/user/login HTTP/1.1")
        headers.put("Host", "lvji.attect.studio:8090")
        headers.put("Connection", "keep-alive")
        headers.put("Content-Length", "43")
        headers.put("v", "3")
        headers.put("Origin", "http://192.168.1.249:8080")
        headers.put("t", "wAR4Tnr+Sn7ZY7BwBhJFJL4LmqyjBtBWrhB8rsR8rhnFmqnjJb2QrbJjJbyQrA8Gukvq6vIzQ5B=")
        headers.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.70 Safari/537.36")
        headers.put("ct", "1")
        headers.put("Content-Type", "application/json;charset=UTF-8")
        headers.put("Accept", "*/*")
        headers.put("Referer", "http://192.168.1.249:8080/login.html")
//        headers.put("Accept-Encoding", "gzip, deflate")
        headers.put("Accept-Language", "zh-CN,zh;q=0.9")





        val params : String = "{\"phone\":\"19901419901\",\"password\":\"123456\"}"




        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://lvji.attect.studio:8090/gate/user/login")
            .headers(headers.toHeaders())
            .post(params.toRequestBody())
            .build()

        val response = client.newCall(request)

        response.enqueue(object : okhttp3.Callback{
            override fun onFailure(call: okhttp3.Call, e: IOException) {

            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {

            }

        })

        val result = response.execute()
            .body
            ?.byteStream()





        result?.let {

            val reader = it.bufferedReader(Charsets.UTF_8)
            var strline = reader.readLine()
            while ( strline!= null && !strline.equals("quite")){
                println(strline)
                strline = reader.readLine()
            }
        }





    }



    @Test
    fun socket_test(){
        val socketList = ArrayList<Socket>()
        val serverSocket = ServerSocket(3000)
        while(true){
            val s = serverSocket.accept()

            socketList.add(s)

        }
    }


}


