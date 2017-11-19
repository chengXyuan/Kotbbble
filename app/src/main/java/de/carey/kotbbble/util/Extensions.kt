package de.carey.kotbbble.util

import android.widget.Toast
import de.carey.kotbbble.app.App

fun Any.toast(msg: String?, length: Int = Toast.LENGTH_SHORT) {
    msg?.let { Toast.makeText(App.instance, msg, length).show() }
}

fun Any.toast(msg: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(App.instance, msg, length).show()
}