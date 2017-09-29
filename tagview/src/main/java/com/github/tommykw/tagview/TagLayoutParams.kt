package com.github.tommykw.tagview

import android.view.ViewGroup

class TagLayoutParams(
    val horizontalSpacing: Int,
    val verticalSpacing: Int
) : ViewGroup.LayoutParams(
    ViewGroup.LayoutParams.WRAP_CONTENT,
    ViewGroup.LayoutParams.WRAP_CONTENT
)