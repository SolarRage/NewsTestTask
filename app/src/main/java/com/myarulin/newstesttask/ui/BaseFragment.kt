package com.myarulin.newstesttask.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.myarulin.newstesttask.ui.BaseFragment.ViewEffect
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

abstract class BaseFragment<ViewState, Effect : ViewEffect> : Fragment(), LifecycleOwner {

    abstract val viewModel: BaseViewStateViewModel<ViewState, Effect>

    private var effectDisposable: Disposable? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) { onStateChanged(it) }
        effectDisposable = viewModel.effect
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { handleEffect(it) },
                { Log.e(BaseFragment::class.simpleName, it.message.orEmpty()) }
            )
        viewModel.init()
    }

    abstract fun onStateChanged(state: ViewState)

    abstract fun handleEffect(effect: Effect)

    interface ViewEffect
}