package com.morhpt.driveshare.activities

import android.app.Activity
import android.os.Bundle
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import org.jetbrains.anko.*

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        relativeLayout {
            textView("Drive Share").lparams {
                centerHorizontally()
                centerVertically()
            }
        }

        async(UI){
            delay(200)
            startActivity(intentFor<AuthActivity>())
        }
    }
}
