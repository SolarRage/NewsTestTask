package com.myarulin.newstesttask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myarulin.newstesttask.api.NetService
import com.myarulin.newstesttask.ui.BaseFragment.ViewEffect
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

abstract class BaseViewStateViewModel<ViewState, Effect : ViewEffect> : ViewModel() {

    private val _viewStateLiveData: MutableLiveData<ViewState> by lazy {
        MutableLiveData(getInitialState())
    }
    val viewStateLiveData: LiveData<ViewState>
        get() = _viewStateLiveData

    private val _effect: PublishSubject<Effect> = PublishSubject.create()
    val effect: Observable<Effect>
        get() = _effect

    private val viewState: ViewState
        get() = _viewStateLiveData.value as ViewState

    abstract fun getInitialState(): ViewState

    protected fun setState(reducer: ViewState.() -> ViewState) {
        _viewStateLiveData.postValue(reducer(viewState))
    }

    protected fun setEffect(builder: () -> Effect) {
        _effect.onNext(builder())
    }

    abstract fun init()

    protected val netApi by lazy {
        NetService.configureRetrofit()
    }
}