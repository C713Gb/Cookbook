package com.banerjee.mvvmcookbook.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TestModel (
        val name: String? = null,
        val email: String? = null
): Parcelable