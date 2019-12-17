package com.exchangerate.screen.base.dialog

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.exchangerate.R

abstract class BaseDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Theme_ExchangeRate_Dialog)
        isCancelable = false
    }

    var onCompleteListener: OnCompleteListener? = null

    fun showWithAllowingStateLoss(fragmentManager: FragmentManager, tag: String): BaseDialogFragment  {
        fragmentManager.beginTransaction()
            .add(this@BaseDialogFragment, tag)
            .commitAllowingStateLoss()
        return this@BaseDialogFragment
    }

    interface OnCompleteListener {
        fun onComplete()
    }
}

inline fun BaseDialogFragment.onComplete (crossinline f: () -> Unit) {
    val listener = object : BaseDialogFragment.OnCompleteListener {
        override fun onComplete() { f() }
    }
    onCompleteListener = listener
}