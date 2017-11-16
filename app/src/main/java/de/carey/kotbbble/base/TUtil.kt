package de.carey.kotbbble.base

import java.lang.reflect.ParameterizedType


class TUtil {

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun <T> getT(any: Any, i: Int): T {
            val type = any.javaClass.genericSuperclass as ParameterizedType
            val typeArguments = type.actualTypeArguments
            if (typeArguments.size <= i) {
                throw IndexOutOfBoundsException("size is ${typeArguments.size}, index is $i")
            }
            val clazz = typeArguments[i] as Class<T>
            return clazz.newInstance()
        }
    }

}