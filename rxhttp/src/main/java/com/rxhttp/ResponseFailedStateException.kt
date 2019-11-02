package com.rxhttp

import java.lang.IllegalStateException

class ResponseFailedStateException(val code:Int , message:String) : IllegalStateException(){


}