package com.example.hrkotlin.base.log

import android.util.Log

object Logger {
    private val TAG = "HaVu"

    fun e(msg: String) {
        Log.e(TAG, msg)
    }

    fun d(msg: String) {
        Log.d(TAG, msg)

    }

    fun i(msg: String) {
        Log.i(TAG, msg)

    }

    fun w(msg: String) {
        Log.w(TAG, msg)

    }

    fun v(msg: String) {
        Log.v(TAG, msg)

    }

    fun e(tag: String, msg: String) {
        Log.e(tag, msg)

    }

    fun e(tag: String, msg: String, e: Exception) {
        Log.e(tag, msg, e)
        }


    fun d(tag: String, msg: String) {
        Log.d(tag, msg)

    }

    fun i(tag: String, msg: String) {
        Log.i(tag, msg)

    }

    fun w(tag: String, msg: String) {
        Log.w(tag, msg)

    }


    fun v(tag: String, msg: String) {
        Log.v(tag, msg)

    }

    fun info(e: Exception) {
        Log.e(TAG, e.message.toString())
    }

    fun info(e: String) {
        Log.e(TAG, e)
    }


}