package com.rxhttp

/**
 * 服务器返回基础数据格式
 *
 * @param <T>
 * @author Attect
 * @version 1
</T> */
class APIResponse<T> {
    /**
     * code
     */
    internal var c: Int = 0

    /**
     * WebSocketAction
     */
    internal var wa: Int = 0

    /**
     * message
     */
    internal var m: String? = null

    /**
     * data
     */
    private val d: T? = null

    fun code(): Int {
        return c
    }

    fun message(): String = m?:""

    fun data(): T? {
        return d
    }

    fun webSocketAction(): Int {
        return wa
    }

    /**
     * 是否为无效数据
     * 如不符合协议的json解析到此对象上
     *
     * @return
     */
    fun invalid(): Boolean {
        return c == 0 && m == null && wa == 0 && d == null
    }
}
