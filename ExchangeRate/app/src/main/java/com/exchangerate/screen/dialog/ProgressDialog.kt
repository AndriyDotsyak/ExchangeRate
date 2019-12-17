package com.exchangerate.screen.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.exchangerate.R
import com.exchangerate.screen.base.dialog.BaseDialogFragment

class ProgressDialog : BaseDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_ExchangeRate_Dialog)
    }

    companion object {

        val TAG: String = ProgressDialog::class.java.simpleName

        fun newInstance() = ProgressDialog()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.dialog_progress, container, false)
}