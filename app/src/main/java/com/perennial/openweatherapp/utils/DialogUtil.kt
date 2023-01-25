package com.perennial.openweatherapp.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.provider.Settings
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.perennial.openweatherapp.R
import java.util.*

object DialogUtil {
    fun showAlertDialog(context: Context, message: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        Objects.requireNonNull(dialog.window)?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    context,
                    android.R.color.transparent
                )
            )
        )
        dialog.setContentView(com.perennial.openweatherapp.R.layout.dialog_warning)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.attributes.windowAnimations = com.perennial.openweatherapp.R.style.DialogAnimation_2
        dialog.setCancelable(false)
        val btn_ok = dialog.findViewById<Button>(com.perennial.openweatherapp.R.id.btn_ok)
        val txt_msg = dialog.findViewById<TextView>(com.perennial.openweatherapp.R.id.txt_msg)
        val tvDismiss = dialog.findViewById<ImageView>(com.perennial.openweatherapp.R.id.tvDismiss)
        val ivDialogStatus = dialog.findViewById<ImageView>(com.perennial.openweatherapp.R.id.ivDialogIcon)
        ivDialogStatus.setImageResource(com.perennial.openweatherapp.R.drawable.ic_launcher_background)
        txt_msg.text = message
        tvDismiss.setOnClickListener { dialog.dismiss() }
        btn_ok.setOnClickListener {
            if (message.equals(
                    context.getString(com.perennial.openweatherapp.R.string.enable_gps_service),
                    ignoreCase = true
                )
            ) {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                (context as Activity).startActivityForResult(intent, Constants.GPS_REQUEST_CODE)
            }
            if (message.equals(
                    context.getString(com.perennial.openweatherapp.R.string.check_internet_connection),
                    ignoreCase = true
                )
            ) {
                val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                (context as Activity).startActivityForResult(intent, Constants.GPS_REQUEST_CODE)
            }
        }
        dialog.show()
    }
}