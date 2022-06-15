package com.example.scorpcase.utils

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.scorpcase.data.models.Person

@SuppressLint("SetTextI18n")
@BindingAdapter("setPerson")
fun textPerson(textView: TextView, p: Person) {
    textView.text = "${p.fullName} (${p.id})"
}
