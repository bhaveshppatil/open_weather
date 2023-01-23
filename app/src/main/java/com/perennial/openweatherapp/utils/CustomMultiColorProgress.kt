package com.perennial.openweatherapp.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.TextView
import com.perennial.openweatherapp.R

class CustomMultiColorProgressBar(context: Context?, dialog_msg: String?) :
    Dialog(context!!) {
    var circle_multicolor_progressbar: CircleProgressBar

    init {
        requestWindowFeature(1)
        setContentView(R.layout.dialog_custom_multicolor_progressbar)
        circle_multicolor_progressbar = findViewById<View>(R.id.bar) as CircleProgressBar
        circle_multicolor_progressbar.setColorSchemeResources(
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_blue_light,
            android.R.color.holo_red_light
        )
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        //        ((TextView) findViewById(R.id.messg)).setTextColor(context.getResources().getColor(R.color.black));
        (findViewById<View>(R.id.messg) as TextView).text = dialog_msg
    }

    fun hideProgressBar() {
        if (this != null) {
            dismiss()
            progressBarObj = null
        }
    }

    fun showProgressBar() {
        show()
    }

    fun updateMessage(dialog_msg: String?) {
        (findViewById<View>(R.id.messg) as TextView).text = dialog_msg
    }

    companion object {
        var progressBarObj: CustomMultiColorProgressBar? = null
        fun getInstance(c: Context?, dialog_msg: String?): CustomMultiColorProgressBar? {
            if (progressBarObj == null) {
                progressBarObj = CustomMultiColorProgressBar(c, dialog_msg)
                progressBarObj!!.show()
            } else {
                progressBarObj!!.updateMessage(dialog_msg)
            }
            return progressBarObj
        }
    }
}