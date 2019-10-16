package com.rxhttp

import android.widget.Toast
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RxHttpUrl {


    companion object{
        suspend fun test ():List<CommonBean>?{
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


            return call.execute().body()?.d

        }
    }
}


