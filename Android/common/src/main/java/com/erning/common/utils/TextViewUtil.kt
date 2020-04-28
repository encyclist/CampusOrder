package com.erning.common.utils

import android.text.Html
import android.widget.TextView

fun TextView.setHtml(html:String){
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        this.text = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        this.text = Html.fromHtml(html)
    }
}