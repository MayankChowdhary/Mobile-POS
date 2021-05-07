package com.retailstreet.mobilepos.Utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import com.retailstreet.mobilepos.View.ApplicationContextProvider

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */
class Vibration {

    companion object {

     fun vibrate(duration: Long) {
        val vibrator = ApplicationContextProvider.getContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(duration)
        }
    }
}
}