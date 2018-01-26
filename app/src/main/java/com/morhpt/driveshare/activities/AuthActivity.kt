package com.morhpt.driveshare.activities

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.text.InputType
import com.morhpt.driveshare.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.design.themedTextInputLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import android.R.attr.button
import android.view.Gravity
import android.R.attr.gravity
import android.annotation.SuppressLint
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.morhpt.driveshare.help.airBnb
import com.transitionseverywhere.ArcMotion
import com.transitionseverywhere.ChangeBounds
import com.transitionseverywhere.TransitionManager
import com.transitionseverywhere.TransitionSet
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import android.util.Patterns
import android.text.TextUtils
import android.widget.EditText
import com.morhpt.driveshare.help.isValidEmail


class AuthActivity : _Activity() {

    private lateinit var mainLayout: RelativeLayout
    private lateinit var socialButtons: LinearLayout

    private lateinit var emailInput: TextInputLayout
    private lateinit var passwordInput: TextInputLayout

    private lateinit var email: EditText
    private lateinit var password: EditText

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainLayout = relativeLayout {
            backgroundColor = Color.parseColor("#000000")
            cardView {
                textView("SIGN IN") {
                    textSize = 18f
                    textColor = resources.getColor(R.color.black)
                }.lparams {
                    topMargin = dip(16)
                    leftMargin = dip(16)
                }
                relativeLayout {
                    verticalLayout {
                        emailInput = themedTextInputLayout(theme = R.style.TextLabel) {

                            email = editText {
                                hint = "E-Mail"
                                singleLine = true
                                inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                            }
                        }.lparams {
                            width = matchParent
                            leftMargin = dip(16)
                            rightMargin = dip(16)
                        }
                        passwordInput = themedTextInputLayout(theme = R.style.TextLabel) {
                            password = editText {
                                hint = "Password"
                                singleLine = true
                                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                            }
                        }.lparams {
                            width = matchParent
                            topMargin = dip(0)
                            leftMargin = dip(16)
                            rightMargin = dip(16)
                        }
                        relativeLayout {
                            textView("Forget your password?"){
                                textColor = resources.getColor(R.color.light_blue)
                                textSize = 12f
                                onClick {
                                    // TODO: IMPLEMENT FORGET PASSWORD MAGIC
                                }
                            }.lparams {
                                rightMargin = dip(16)
                                alignParentRight()
                            }
                        }
                    }.lparams {
                        width = matchParent
                        centerHorizontally()
                        centerVertically()
                    }.applyRecursively { view -> when(view){
                        is TextInputLayout -> view
                    } }
                    relativeLayout {
//                        airBnb {
//                            setAnimation("loading.json")
//                            loop(true)
//                            playAnimation()
//                        }.lparams {
//                            bottomMargin = dip(16)
//                            alignParentBottom()
//                        }
                        val loginButton = relativeLayout {
                            id = 11
                            background = resources.getDrawable(R.drawable.round_button)
                            val loginBtn = this

                            val progress = progressBar { visibility = View.GONE }.lparams {
                                margin = dip(5)
                            }
                            val loginText = textView("Login"){
                                textColor = resources.getColor(R.color.light_blue)
                            }.lparams {
                                topMargin = dip(5)
                                bottomMargin = dip(5)
                                leftMargin = dip(32)
                                rightMargin = dip(32)
                                centerVertically()
                                centerHorizontally()
                            }

                            onClick {
                                try {
                                    TransitionManager.beginDelayedTransition(mainLayout,
                                            TransitionSet()
                                                    .addTransition(ChangeBounds().addTarget(socialButtons).setPathMotion(ArcMotion()).setDuration(500))
                                                    .addTransition(ChangeBounds().setPathMotion(ArcMotion()).setDuration(500))
                                            )

                                    val params = loginBtn.layoutParams as RelativeLayout.LayoutParams

                                    params.centerHorizontally()
                                    params.width = dip(30)
                                    params.height = dip(30)

                                    loginBtn.layoutParams = params

                                    val socialParams = socialButtons.layoutParams as RelativeLayout.LayoutParams

                                    socialParams.addRule(RelativeLayout.RIGHT_OF, 0)

                                    socialParams.centerHorizontally()
                                    socialParams.width = dip(30)
                                    socialParams.height = dip(30)

                                    socialButtons.layoutParams = socialParams

                                    async(kotlinx.coroutines.experimental.android.UI){
                                        delay(500)
                                        socialButtons.visibility = View.GONE
                                        //loginBtn.visibility = View.GONE
                                        progress.visibility = View.VISIBLE
                                        loginText.visibility = View.GONE
                                    }

                                    fun setToDefault() {
                                        val params = loginBtn.layoutParams as RelativeLayout.LayoutParams

                                        params.alignParentStart()
                                        params.width = wrapContent
                                        params.height = wrapContent

                                        loginBtn.layoutParams = params

                                        val socialParams = socialButtons.layoutParams as RelativeLayout.LayoutParams


                                        socialParams.rightOf(loginBtn)
                                        socialParams.width = matchParent
                                        socialParams.height = wrapContent

                                        socialButtons.layoutParams = socialParams

                                        socialButtons.visibility = View.VISIBLE
                                        //loginBtn.visibility = View.GONE
                                        progress.visibility = View.GONE
                                        loginText.visibility = View.VISIBLE
                                    }

                                    if (!isValidEmail(email.text.toString())){
                                        emailInput.error = "Email is not valid!"
                                        setToDefault()
                                        return@onClick
                                    }

                                    if (password.text.toString().length < 6){
                                        passwordInput.error = "Password should be at least 6 characters"
                                        setToDefault()
                                        return@onClick
                                    }

                                    if (password.text.toString().length > 17){
                                        passwordInput.error = "Password should be max 16 characters"
                                        setToDefault()
                                        return@onClick
                                    }
                                    // TODO: IMPORT FIREBASE AUTH MODULE
                                    // If success
                                    startActivity(intentFor<MainActivity>())
                                } catch (e: Exception){
                                    wtf("Animation Error", e)
                                }

                            }
                        }.lparams {
                            leftMargin = dip(16)
                            bottomMargin = dip(16)
                            rightMargin = dip(16)
                            height = dip(30)
                        }
                        socialButtons = linearLayout {
                            relativeLayout {
                                relativeLayout {
                                    background = resources.getDrawable(R.drawable.circle_grey)
                                    padding = dip(8)
                                    imageView(R.drawable.ic_facebook)
                                }.lparams(width = dip(30), height = dip(30)) {
                                    centerHorizontally()
                                }
                            }.lparams(weight = 1f)
                            relativeLayout {
                                relativeLayout {
                                    background = resources.getDrawable(R.drawable.circle_grey)
                                    padding = dip(8)
                                    imageView(R.drawable.ic_twitter)
                                }.lparams(width = dip(30), height = dip(30)) {
                                    centerHorizontally()
                                }
                            }.lparams(weight = 1f)
                            relativeLayout {
                                relativeLayout {
                                    background = resources.getDrawable(R.drawable.circle_grey)
                                    padding = dip(8)
                                    imageView(R.drawable.ic_facebook)
                                }.lparams(width = dip(30), height = dip(30)) {
                                    centerHorizontally()
                                }
                            }.lparams(weight = 1f)
                        }.lparams {
                            width = matchParent
                            rightOf(loginButton)
                        }
                    }.lparams {
                        width = matchParent
                        alignParentBottom()
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }.lparams {
                val displayMetrics = context.resources.displayMetrics
                val dpWidth = displayMetrics.widthPixels / displayMetrics.density
                val size = dpWidth - 32
                width = dip(size)
                height = dip(size)
                centerVertically()
                centerHorizontally()
            }
        }
    }
}
