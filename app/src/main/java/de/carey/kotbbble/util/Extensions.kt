@file:Suppress("unused")

package de.carey.kotbbble.util

import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import de.carey.kotbbble.app.App

fun Any.toast(msg: String?, length: Int = Toast.LENGTH_SHORT) {
    msg?.let { Toast.makeText(App.instance, msg, length).show() }
}

fun Any.toast(msg: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(App.instance, msg, length).show()
}

fun Any.log(msg: String) {
    Log.d(this.javaClass.simpleName, msg)
}

@Suppress("DEPRECATION")
fun String.htmlToStringNoP(textView: TextView) {
    val body = this.replace("<p>", "")
            .replace("</p>", "").trimEnd()
    textView.text = Html.fromHtml(body)
    textView.movementMethod = LinkMovementMethod.getInstance()
}