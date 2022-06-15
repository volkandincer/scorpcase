package com.example.scorpcase.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    @Inject
    @ApplicationContext
    lateinit var appContext: Context
    open var enablePopUp: Boolean = true
    var listener: FragmentListener? = null
        private set

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement FragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    open fun getFragmentLayout(): Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return if (getFragmentLayout() != 0) inflater.inflate(getFragmentLayout(), container, false)
        else super.onCreateView(inflater, container, savedInstanceState)
    }

    interface FragmentListener {

        fun manageProgressDialogVisibility(show: Boolean)
        fun showSnackbar(text: String)
        fun showDialog(titleId: Int, msgId: String, positiveButtonId: Int,
                       positiveOnClick: () -> Unit, negativeButtonId: Int? = null,
                       negativeOnClick: (() -> Unit)? = null)
    }


}