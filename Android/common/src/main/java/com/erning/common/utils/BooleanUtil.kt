package com.erning.common.utils

fun Boolean.toStr(): String {
    return if (this) "true" else "false"
}

fun Boolean.toInt(): Int {
    return if (this) 1 else 0
}