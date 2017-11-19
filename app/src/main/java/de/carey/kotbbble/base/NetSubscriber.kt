package de.carey.kotbbble.base

import com.google.gson.JsonParseException
import de.carey.kotbbble.app.App
import de.carey.kotbbble.util.Utils
import io.reactivex.subscribers.DisposableSubscriber
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.ParseException
import javax.net.ssl.SSLHandshakeException


abstract class NetSubscriber<T> : DisposableSubscriber<T>() {

    override fun onStart() {
        super.onStart()
        if (!Utils.isNetworkAvailable(App.instance)) {
            cancel()
            onFailure(ERROR.NETWORK_ERROR, "网络异常，请检查网络连接后重试")
        }
    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onComplete() {

    }

    override fun onError(t: Throwable?) {
        when (t) {
            is HttpException -> when (t.code()) {
                401 -> onFailure(t.code(), "访问被服务器拒绝啦~")
                403 -> onFailure(t.code(), "服务器资源不可用")
                404 -> onFailure(t.code(), "我们好像迷路了，找不到服务器")
                408 -> onFailure(t.code(), "糟糕，我们的请求超时了，请检查网络连接后重试")
                500 -> onFailure(t.code(), "服务器正在开小差，请稍后重试")
                502 -> onFailure(t.code(), "服务器正在开小差，请稍后重试")
                503 -> onFailure(t.code(), "服务器可能正在维护，请稍后重试")
                504 -> onFailure(t.code(), "糟糕，我们的请求超时了，请检查网络连接后重试")
                else -> onFailure(t.code(), "网络异常，请检查网络连接后重试")
            }
            is JSONException -> onFailure(ERROR.PARSE_ERROR, "数据解析错误")
            is JsonParseException -> onFailure(ERROR.PARSE_ERROR, "数据解析错误")
            is ParseException -> onFailure(ERROR.PARSE_ERROR, "数据解析错误")
            is ConnectException -> onFailure(ERROR.NETWORK_ERROR, "连接失败，网络连接可能存在异常，请检查网络后重试")
            is SSLHandshakeException -> onFailure(ERROR.SSL_ERROR, "证书验证失败")
            is UnknownHostException -> onFailure(ERROR.HOST_ERROR, "无法连接到服务器，请检查你的网络或稍后重试")
            else -> onFailure(ERROR.UNKNOWN, "出现了未知的错误")
        }
    }

    abstract fun onSuccess(data: T)

    abstract fun onFailure(code: Int, msg: String)

    /**
     * 约定异常
     */
    internal object ERROR {
        /**
         * 未知错误
         */
        val UNKNOWN = 1000
        /**
         * 解析错误
         */
        val PARSE_ERROR = 1001
        /**
         * 网络错误
         */
        val NETWORK_ERROR = 1002
        /**
         * 协议出错
         */
        val HTTP_ERROR = 1003

        /**
         * 证书出错
         */
        val SSL_ERROR = 1005

        /**
         * host错误
         */
        val HOST_ERROR = 1006
    }
}