package de.carey.kotbbble.util

import com.google.gson.Gson
import java.lang.reflect.Type

object JsonUtil {

    /**
     * json字符串转对象
     */
    fun <T> fromJson(json: String?, type: Type): T = Gson().fromJson(json, type)

    /**
     * 对象转json
     */
    fun toJson(any: Any): String = Gson().toJson(any)
}