package com.example.scorpcase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.scorpcase.base.BaseActivity
import com.example.scorpcase.base.BaseFragment
import com.example.scorpcase.ext.toastShort
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), BaseFragment.FragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun showSnackbar(text: String) {
        this.toastShort(text)
    }

    override fun showDialog(
        titleId: Int,
        msgId: String,
        positiveButtonId: Int,
        positiveOnClick: () -> Unit,
        negativeButtonId: Int?,
        negativeOnClick: (() -> Unit)?,
    ) {
        TODO("Not yet implemented")
    }


}