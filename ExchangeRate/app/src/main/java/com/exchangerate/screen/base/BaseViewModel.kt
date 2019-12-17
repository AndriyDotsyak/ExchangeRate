package com.exchangerate.screen.base

import androidx.annotation.MainThread
import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseViewModel : ViewModel() {

    val errorLive = SingleLiveData<Exception>()
    val progressLive = SingleLiveData<Boolean>()

    protected open fun launchDataLoadWithProgress(func: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                progressLive.value = true
                func()
            } catch (ex: Exception) {
                errorLive.value = ex
            } finally {
                progressLive.value = false
            }
        }
    }
}

class SingleLiveData<T> : MutableLiveData<T>() {

    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer<T> { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }

    @MainThread
    fun call() {
        setValue(null)
    }
}