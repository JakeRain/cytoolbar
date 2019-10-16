package com.rxhttp

import retrofit2.Call
import retrofit2.http.*

interface HttpPath {

    @Headers("Content-Type: application/json;charset=UTF-8",
        "ct:1",
        "Origin:http://192.168.1.249:8080" ,
        "Referer:http://192.168.1.249:8080/business/scheme_v2/html/scheme_add.html?args_key=1570849489542",
        "t:wAR7dSr+Sn7ZYhZbJtBOc7k8mt4iZb2Wr7HRrsQdrbrdmtHOZte6BbS6Bh0iZA8GoHmp6vId6I2=",
        "User-Agent:Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36",
        "v:3"
    )
//    @FormUrlEncoded
    @POST("testliu/common/dataDictionary/getDicListByParentId")
    fun getDicListByParentId(@Body bean: CommonBean):Call<MyResponse<List<CommonBean>>>





}