package com.github.tommykw.tagview

interface DataTransform<T> {
    fun transfer(item: T): String
}