package com.rxhttp


class SimpleResponse {

    private val c: Int = 0
    private val m: String? = null

    fun <T> toResponse(): APIResponse<T> {
        val response = APIResponse<T>()
        response.c = c
        response.m = m
        return response
    }
}
