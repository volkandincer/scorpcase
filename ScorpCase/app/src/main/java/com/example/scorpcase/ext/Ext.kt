package com.example.scorpcase.ext

import android.content.Context
import android.widget.Toast
import com.example.scorpcase.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.toastShort(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}