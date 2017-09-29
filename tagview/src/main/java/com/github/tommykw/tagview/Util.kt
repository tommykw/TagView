package com.github.tommykw.tagview

import android.content.Context
import android.os.Build
import android.widget.TextView

@Suppress("DEPRECATION")
fun TextView.setTextAppearanceV2(context: Context, resId: Int) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        setTextAppearance(context, resId);
    } else {
        setTextAppearance(resId);
    }
}
