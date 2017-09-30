package com.github.tommykw.tagview

import android.support.annotation.IntDef

class Annotations {
    companion object {
        const val SORT_TYPE_NONE = 0L
        const val SORT_TYPE_ASC = 1L
        const val SORT_TYPE_DESC = 2L
    }

    @IntDef(SORT_TYPE_NONE, SORT_TYPE_ASC, SORT_TYPE_DESC)
    annotation class SortType
}