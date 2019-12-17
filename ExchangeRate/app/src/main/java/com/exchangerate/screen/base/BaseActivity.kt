package com.exchangerate.screen.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.exchangerate.data.model.Time
import com.exchangerate.di.Injectable
import com.exchangerate.screen.base.dialog.BaseDialogFragment
import com.exchangerate.screen.dialog.InfoDialog
import com.exchangerate.screen.dialog.ProgressDialog
import io.realm.Realm
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), Injectable {

    companion object {
        const val DATE_FORMAT_PATTERN = "yyyy-MM-dd"
        const val DAY_FORMAT_PATTERN = "dd"
    }

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        realm = Realm.getDefaultInstance()
    }

    override fun onDestroy() {
        super.onDestroy()

        realm.close()
    }

    open fun handleProgress(isShown: Boolean) {
        if (isShown) showProgress() else hideProgress()
    }

    fun showProgress() {
        if (supportFragmentManager.findFragmentByTag(ProgressDialog.TAG) == null) {
            ProgressDialog.newInstance()
                .showWithAllowingStateLoss(supportFragmentManager, ProgressDialog.TAG)
        }
    }

    fun hideProgress() {
        supportFragmentManager.findFragmentByTag(ProgressDialog.TAG)?.let {
            (it as BaseDialogFragment).dismissAllowingStateLoss()
        }
    }

    fun showErrorDialog(message: String): BaseDialogFragment =
        InfoDialog.newInstance(message)
            .showWithAllowingStateLoss(supportFragmentManager, InfoDialog.TAG)

    fun getCurrentTime(): Time = Time(
        getSimpleDateFormat("HH", Calendar.getInstance().time).toInt(),
        getSimpleDateFormat("mm", Calendar.getInstance().time).toInt()
    )

    fun getCurrentDate(): String = getSimpleDateFormat(DATE_FORMAT_PATTERN, Calendar.getInstance().time)

    fun getDateCalculator(day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, day)

        return getSimpleDateFormat(DATE_FORMAT_PATTERN, Date(calendar.timeInMillis))
    }

    fun getSimpleDateFormat(formatPattern: String, date: Any) =
        SimpleDateFormat(formatPattern, Locale.getDefault()).format(date)

    fun getSimpleDateParse(formatPattern: String, date: String) =
        SimpleDateFormat(formatPattern, Locale.getDefault()).parse(date)
}