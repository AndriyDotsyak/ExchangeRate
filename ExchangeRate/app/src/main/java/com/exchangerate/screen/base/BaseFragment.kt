package com.exchangerate.screen.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.exchangerate.di.Injectable
import javax.inject.Inject

abstract class BaseFragment : Fragment(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
}