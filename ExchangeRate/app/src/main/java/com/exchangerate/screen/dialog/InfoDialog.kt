package com.exchangerate.screen.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exchangerate.R
import com.exchangerate.screen.base.dialog.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_info.*

class InfoDialog : BaseDialogFragment() {

    companion object {

        val TAG: String = InfoDialog::class.java.simpleName

        const val ARGUMENT_MESSAGE = "ARGUMENT_MESSAGE"

        fun newInstance(message: String): InfoDialog {

            val args = Bundle()
            args.putString(ARGUMENT_MESSAGE, message)

            val fragment = InfoDialog()
            fragment.arguments = args
            fragment.isCancelable = false
            fragment.setStyle(STYLE_NO_TITLE, R.style.Theme_ExchangeRate_Dialog)
            return fragment
        }
    }

    private val message: String by lazy { arguments?.getString(ARGUMENT_MESSAGE) ?: "" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.dialog_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initStyle()
        text_view_message_DI.text = message
        button_ok_DI.setOnClickListener {
            dismissAllowingStateLoss()
            onCompleteListener?.onComplete()
        }
    }

    private fun initStyle() {
        text_view_title_DI.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_error, 0, 0)
        text_view_title_DI.setText(R.string.di_error)
    }
}