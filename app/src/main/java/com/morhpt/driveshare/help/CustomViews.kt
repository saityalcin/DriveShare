package com.morhpt.driveshare.help

import android.view.ViewManager
import com.airbnb.lottie.LottieAnimationView
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.airBnb(init: LottieAnimationView.() -> Unit): LottieAnimationView {
    return ankoView({ LottieAnimationView(it) }, theme = 0, init = init)
}