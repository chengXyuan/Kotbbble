package de.carey.kotbbble.util

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.io.File
import java.text.DecimalFormat

object Utils {

    /**
     * 判断网络是否可用
     * @return true为可用
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val cm: ConnectivityManager? = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm?.activeNetworkInfo
        return info != null && info.isAvailable && info.isConnected
    }

    /**
     * sp转px
     */
    fun sp2px(sp: Int, metrics: DisplayMetrics)
            = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), metrics)

    /**
     * dp转px
     */
    fun dp2px(dp: Int, metrics: DisplayMetrics)
            = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), metrics)

    /**
     * dp转px
     */
    fun dp2px(dp: Float, metrics: DisplayMetrics)
            = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics)

    /**
     * 替换电话号码中段4位为 *
     */
    fun enctyptionPhoneNumber(phoneNumber: String): String {
        val buffer = StringBuffer()
        phoneNumber.forEachIndexed { index, c ->
            if (index in 3..6) buffer.append('*')
            else buffer.append(c)
        }
        return buffer.toString()
    }


    /**
     * 判断某个服务是否正在运行的方法
     * @param context 上下文
     * @param serviceName 服务的类全名（例如:net.loonggg.testbackstage.TestService）
     */
    @Suppress("DEPRECATION")
    fun isServiceWork(context: Context, serviceName: String): Boolean {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val list = am.getRunningServices(Int.MAX_VALUE)
        return list.any { it.service.className == serviceName }
    }

    /**
     * 激活receiver  用于对付安全软件的自启控制
     * @param context 上下文
     * @param receiverName    要激活的receiver的名字--要带包名例:com.test.TimeReceiver
     */
    fun decide(context: Context, receiverName: String) {
        val pm = context.packageManager
        val componentName = ComponentName(context, receiverName)
        if (pm.getComponentEnabledSetting(componentName) != PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP)
        }
    }

    /**
     * 获取版本名
     */
    fun getVersionName(context: Context): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            "版本未知"
        }

    }

    /**
     * 获取版本号
     */
    fun getVersionCode(context: Context): Int {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            0
        }
    }

    /**
     * Android获取一个用于打开APK文件的intent
     */
    fun openApk(context: Context, file: File) {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = Intent.ACTION_VIEW
        val uri = Uri.fromFile(file)
        intent.setDataAndType(uri, "application/vnd.android.package-archive")
        context.startActivity(intent)
    }

    /**
     * 判断手机GPS是否可用
     */
    fun isGpsEnable(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return gps || network
    }

    /**
     * 隐藏软键盘
     */
    fun hideKeyboard(editText: EditText) {
        val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            imm.hideSoftInputFromWindow(editText.applicationWindowToken, 0)
        }
    }

    /**
     * 获取设备imei
     */
    @SuppressLint("MissingPermission", "HardwareIds")
    @Suppress("DEPRECATION")
    fun getDeviceImei(context: Context): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tm.imei
        } else {
            tm.deviceId
        }
    }

    /**
     * 获取手机号码
     */
    @SuppressLint("MissingPermission", "HardwareIds")
    fun getPhoneNumber(context: Context): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.line1Number.substring(3..14)
    }

    /**
     * 获取一个View的宽度
     */
    fun getViewWidth(view: View): Int {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        return view.measuredWidth
    }

    /**
     * 获取一个View的高度
     */
    fun getViewHeight(view: View): Int {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        return view.measuredHeight
    }

    /**
     * 保留指定位数的小数
     */
    fun keepOneDecimal(num: Double, limit: Int): String {
        val format = DecimalFormat()
        format.maximumFractionDigits = limit
        return format.format(num)
    }

    /**
     * 获取当前进程名
     */
    fun getProcessName(context: Context): String? {
        var processName: String? = null
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        while (true) {
            am.runningAppProcesses.forEach {
                if (it.pid == android.os.Process.myPid()) {
                    processName = it.processName
                    return@forEach
                }
            }

            for (info in am.runningAppProcesses) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName
                    break
                }
            }

            //go home
            if (processName.isNullOrEmpty()) {
                return processName
            }

            //take a rest and again
            try {
                Thread.sleep(100L)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 判断当前进程是否是主进程
     */
    fun isMainProcess(context: Context): Boolean {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val myPid = android.os.Process.myPid()
        val packageName = context.packageName
        return am.runningAppProcesses.any { packageName == it.processName && myPid == it.pid }
    }


}