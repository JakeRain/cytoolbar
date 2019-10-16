package com.cystatusbar

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.rxhttp.CommonBean
import com.rxhttp.MyResponse
import com.rxhttp.RxHttpUrl
import kotlinx.android.synthetic.main.activity_okhttp.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Headers.Companion.toHeaders
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.internal.http2.Header
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.net.URL


class OkHttpActivity : AppCompatActivity() {

    val myLiveData = MutableLiveData<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okhttp)
        myLiveData.observe(this, Observer {
            responseContent.text = it
        })
    }

    fun httpPost(view: View) {

//        Thread(Runnable {
//
//            val urlString =
//                "http://lvji.attect.studio:8090/testliu/business/scheme/selectByIdByShare"
//
//            val requestBodyJson =
//                JSONObject("{\"base64Id\":\"KXI/Zi93WyMzQzJWRFdTXw==\",\"userId\":\"MHhjKz8vWyNFUS4gWyNFUQ==\",\"relationId\":166}")
//
//            val typeString = "application/json; charset=utf-8"
//
//            val mediaType = typeString.toMediaType()
//            val okHttpClient = OkHttpClient()
//
//            val requestBody = requestBodyJson.toString().toRequestBody(mediaType)
//
//
//            val request = Request.Builder()
//                .url(urlString)
//                .headers(headers())
//                .post(requestBody)
//                .build()
//            val response = okHttpClient.newCall(request).execute()
//
//            if (response.isSuccessful) {
//                myLiveData.postValue(response.body?.string())
//            } else {
//                myLiveData.postValue("请求失败")
//            }
//        }).start()




//        RxHttpUrl.test(object :retrofit2.Callback<MyResponse<List<CommonBean>>>{
//            override fun onFailure(call: Call<MyResponse<List<CommonBean>>>, t: Throwable) {
//                Toast.makeText(baseContext , "访问网络失败", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onResponse(
//                call: Call<MyResponse<List<CommonBean>>>,
//                response: Response<MyResponse<List<CommonBean>>>
//            ) {
//                Toast.makeText(baseContext , "访问网络成功", Toast.LENGTH_LONG).show()
//            }
//
//        })




    }


    fun headers(): Headers {
        return Headers.Builder()
            .add("ct", "1")
            .add("Origin", "http://192.168.1.249:8080")
            .add(
                "Referer",
                "http://192.168.1.249:8080/business/scheme_v2/html/scheme_preview_pc.html?args_key=1570701973362"
            )
            .add(
                "t",
                "wAR7nnr+Sn7ZY7Yvc7YvrhZdmqrdB7BWr7ZvrsQdc7nwmtB8Zb26Bq26rLZdBf8G1w9x6vI5bOy="
            )
            .add(
                "User-Agent",
                "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36"
            )
            .add("v", "3")
            .build()
    }


    fun <T> requestBody(t: T): RequestBody {
        return Gson().toJson(t).toRequestBody()
    }

    /**
     * .scheme("https")
     * .host("www.google.com")
     * .addPathSegment("search")
     * .addQueryParameter("q", "polar bears")
     */
    fun path(): HttpUrl {

//        "http://lvji.attect.studio:8090/testliu/business/scheme/selectByIdByShare".toHttpUrl()
        return HttpUrl.Builder()
            .scheme("https")
            .host("lvji.attect.studio")
            .port(8090)
            .addPathSegment("testliu")
            .addPathSegment("business")
            .addPathSegment("scheme")
            .addPathSegment("selectByIdByShare")
            .build()

    }

}