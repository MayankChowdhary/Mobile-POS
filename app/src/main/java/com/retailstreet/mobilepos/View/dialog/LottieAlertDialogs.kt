package com.retailstreet.mobilepos.View.dialog;

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.retailstreet.mobilepos.R

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

class LottieAlertDialogs private constructor(context: Context, type: Int?, title: String?, description: String?, positiveText: String?, negativeText: String?, noneText: String?, positiveListener: ClickListeners?, negativeListener: ClickListeners?, noneListener: ClickListeners?, private var positiveBtnColor: Int?, private var positiveTextColor: Int?, private var negativeBtnColor: Int?, private var negativeTextColor: Int?, private var noneBtnColor: Int?, private var noneTextColor: Int?) : AlertDialog(context)
        {
private var mContext : Context = context
            private var mType:Int?= type
private var mTitle:String? = title
private var mDescription:String? = description
private var mPositiveText:String? = positiveText
private var mNegativeText:String? = negativeText
private var mNoneText:String? = noneText
private var mPositiveListener : ClickListeners? = positiveListener
private var mNegativeListener : ClickListeners? = negativeListener
private var mNoneListener : ClickListeners? = noneListener

private lateinit var lAnimation : LottieAnimationView
private lateinit var tvTitle : TextView
private lateinit var tvDescription : TextView
private lateinit var btnPositive : Button
private lateinit var btnNegative : Button
private lateinit var btnNone : Button
            private var animationFadeIn : Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
            private var animationFadeOut: Animation

            init {
                animationFadeIn.duration = 50
                animationFadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
                animationFadeOut.duration = 50
            }


        data class Builder(
                private val context: Context? = null,
                private val type: Int? = null
        )
{
    private var title:String? = null
    private var description:String? = null
    private var positiveText:String? = null
    private var negativeText:String? = null
    private var noneText:String? = null
    private var positiveListener: ClickListeners? = null
    private var negativeListener: ClickListeners? = null
    private var noneListener: ClickListeners? = null
    private var positiveBtnColor:Int? = null
    private var positiveTextColor:Int? = null
    private var negativeBtnColor:Int? = null
    private var negativeTextColor:Int? = null
    private var noneBtnColor:Int? = null
    private var noneTextColor:Int? = null

    fun setTitle(title: String?) : Builder = apply { this.title = title ; return@apply}
    fun setDescription(description: String?) : Builder = apply { this.description = description ; return@apply}
    fun setPositiveText(positiveText: String?) : Builder = apply { this.positiveText = positiveText ; return@apply}
    fun setNegativeText(negativeText: String?) : Builder = apply { this.negativeText = negativeText ; return@apply}
    fun setNoneText(noneText: String?) : Builder = apply { this.noneText = noneText ; return@apply}
    fun setPositiveListener(positiveListener: ClickListeners?) : Builder = apply { this.positiveListener = positiveListener ; return@apply}
    fun setNegativeListener(negativeListener: ClickListeners?) : Builder = apply { this.negativeListener = negativeListener ; return@apply}
    fun setNoneListener(noneListener: ClickListeners?): Builder = apply { this.noneListener = noneListener ; return@apply}
    fun setPositiveButtonColor(color: Int?) : Builder = apply { this.positiveBtnColor = color ; return@apply}
    fun setPositiveTextColor(color: Int?) : Builder = apply { this.positiveTextColor = color ; return@apply}
    fun setNegativeButtonColor(color: Int?) : Builder = apply { this.negativeBtnColor = color ; return@apply}
    fun setNegativeTextColor(color: Int?) : Builder = apply { this.negativeTextColor = color ; return@apply}
    fun setNoneButtonColor(color: Int?) : Builder = apply { this.noneBtnColor = color ; return@apply}
    fun setNoneTextColor(color: Int?) : Builder = apply { this.noneTextColor = color ; return@apply}
    fun build() = LottieAlertDialogs(context!!, type, title, description, positiveText,
            negativeText, noneText, positiveListener, negativeListener, noneListener,
            positiveBtnColor, positiveTextColor, negativeBtnColor, negativeTextColor, noneBtnColor, noneTextColor)

    fun getContext() : Context? {return context}
    fun getType() : Int? { return  type}
    fun getTitle() : String? { return  title}
    fun getDescription() : String? { return  description}
    fun getPositiveText() : String? { return  positiveText}
    fun getNegativeText() : String? { return  negativeText}
    fun getNoneText() : String? { return  noneText}
    fun getPositiveListener() : ClickListeners? { return  positiveListener}
    fun getNegativeListener() : ClickListeners? { return  negativeListener}
    fun getNoneListener() : ClickListeners? { return  noneListener}
    fun getPositiveButtonColor() : Int? { return  positiveBtnColor}
    fun getPositiveTextColor() : Int? { return  positiveTextColor}
    fun getNegativeButtonColor() : Int? { return  negativeBtnColor}
    fun getNegativeTextColor() : Int? { return  negativeTextColor}
    fun getNoneButtonColor() : Int? { return  noneBtnColor}
    fun getNoneTextColor() : Int? { return  noneTextColor}
}

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_alert_dialog)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        findView()
    }

    private fun findView()
    {
        lAnimation=findViewById(R.id.lAnimation)
        tvTitle=findViewById(R.id.tvTitle)
        tvDescription=findViewById(R.id.tvDescription)
        btnPositive=findViewById(R.id.btnPositive)
        btnNegative=findViewById(R.id.btnNegative)
        btnNone=findViewById(R.id.btnNone)
        setView()
    }

    private fun setView()
    {
        // SET VIEW
        lAnimation.startAnimation(animationFadeIn)
        if (mTitle!=null)
        {
            tvTitle.text = mTitle
            tvTitle.visibility=View.VISIBLE
        }
        else
        {
            tvTitle.visibility=View.GONE
        }
        if (mDescription!=null)
        {
            tvDescription.text = mDescription
            tvDescription.visibility=View.VISIBLE
        }
        else
        {
            tvDescription.visibility=View.GONE
        }
        if (mPositiveText!=null)
        {
            btnPositive.text = mPositiveText
            btnPositive.visibility=View.VISIBLE
            btnPositive.setOnClickListener { mPositiveListener?.onClick(this) }
        }
        else
        {
            btnPositive.visibility=View.GONE
        }
        if (mNegativeText!=null)
        {
            btnNegative.text = mNegativeText
            btnNegative.visibility=View.VISIBLE
            btnNegative.setOnClickListener { mNegativeListener?.onClick(this) }
        }
        else
        {
            btnNegative.visibility=View.GONE
        }
        if (mNoneText!=null)
        {
            btnNone.text = mNoneText
            btnNone.visibility=View.VISIBLE
            btnNone.setOnClickListener { mNoneListener?.onClick(this) }
        }
        else
        {
            btnNone.visibility=View.GONE
        }

        if (positiveBtnColor!=null)
            btnPositive.setBackgroundColor(positiveBtnColor!!)
        else
        btnPositive.background.clearColorFilter()
        if (positiveTextColor!=null)
            btnPositive.setTextColor(positiveTextColor!!)
        else
        btnPositive.setTextColor(Color.parseColor("#000000"))
        if (negativeBtnColor!=null)
            btnNegative.setBackgroundColor(positiveBtnColor!!)
        else
            btnNegative.background.clearColorFilter()
        if (negativeTextColor!=null)
            btnNegative.setTextColor(negativeTextColor!!)
        else
        btnNegative.setTextColor(Color.parseColor("#000000"))
        if (noneBtnColor!=null)
            btnNone.setBackgroundColor(positiveBtnColor!!)
        else
        btnNone.background.clearColorFilter()
        if (noneTextColor!=null)
            btnNone.setTextColor(noneTextColor!!)
        else
        btnNone.setTextColor(Color.parseColor("#000000"))
        // TYPE CONTROL
        when (mType) {
            DialogTypes.TYPE_LOADING -> {
                lAnimation.setAnimation("loading.json")
                lAnimation.repeatCount=LottieDrawable.INFINITE
            }
            DialogTypes.TYPE_SUCCESS -> {
                lAnimation.setAnimation("success.json")
                lAnimation.repeatCount=0
            }
            DialogTypes.TYPE_ERROR -> {
                lAnimation.setAnimation("error.json")
                lAnimation.repeatCount=0
            }
            DialogTypes.TYPE_WARNING -> {
                lAnimation.setAnimation("warning.json")
                lAnimation.repeatCount=0
            }
            DialogTypes.TYPE_QUESTION -> {
                lAnimation.setAnimation("question.json")
                lAnimation.repeatCount=LottieDrawable.INFINITE
            }
        }
        lAnimation.playAnimation()
    }

    fun changeDialog(builder: Builder)
    {
        mContext= builder.getContext()!!
            mType=builder.getType()
        mTitle=builder.getTitle()
        mDescription=builder.getDescription()
        mPositiveText=builder.getPositiveText()
        mNegativeText=builder.getNegativeText()
        mNoneText=builder.getNoneText()
        mPositiveListener=builder.getPositiveListener()
        mNegativeListener=builder.getNegativeListener()
        mNoneListener=builder.getNoneListener()
        positiveBtnColor=builder.getPositiveButtonColor()
        positiveTextColor=builder.getPositiveTextColor()
        negativeBtnColor=builder.getNegativeButtonColor()
        negativeTextColor=builder.getNegativeTextColor()
        noneBtnColor=builder.getNoneButtonColor()
        noneTextColor=builder.getNoneTextColor()
        lAnimation.startAnimation(animationFadeOut)
        Handler(Looper.getMainLooper()).postDelayed({ setView() }, 50)
    }
}
