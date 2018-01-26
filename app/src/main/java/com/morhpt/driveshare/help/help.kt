package com.morhpt.driveshare.help

import android.text.TextUtils
import android.util.Patterns

fun isValidEmail(target: CharSequence): Boolean = !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()