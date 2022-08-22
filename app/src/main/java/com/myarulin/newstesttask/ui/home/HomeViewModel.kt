package com.myarulin.newstesttask.ui.home

import android.util.Log
import com.myarulin.newstesttask.db.Article
import com.myarulin.newstesttask.model.ArticleModel
import com.myarulin.newstesttask.repo.NewsRepository
import com.myarulin.newstesttask.ui.BaseViewStateViewModel
import com.myarulin.newstesttask.ui.home.HomeContract.HomeViewState
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit.MILLISECONDS

class HomeViewModel(
    private val newsRepository: NewsRepository
) : BaseViewStateViewModel<HomeViewState, HomeContract.HomeEffect>() {

    private val TAG = HomeViewModel::class.simpleName

    private val textChangeSubject: BehaviorSubject<String> = BehaviorSubject.createDefault("")

    private val searchNewsPage = 1

    private val compositeDisposable = CompositeDisposable()
    private var articleDisposable: Disposable? = null

    override fun getInitialState() = HomeViewState()

    fun onTextChange(text: String) {
        textChangeSubject.onNext(text)
    }


    override fun init() {
        subscribeForTestChanges()
    }

    private fun subscribeForTestChanges() {
        compositeDisposable += textChangeSubject
            .debounce(500, MILLISECONDS)
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { handleTextChanged(it) },
                { Log.e(TAG, it.message.toString()) }
            )
    }

    fun loadNews() {
        disposeNewsRequest()
        articleDisposable = newsRepository.getNews("ua", searchNewsPage)
            .flattenAsFlowable { it.articles }
            .map { mapToItemModel(it) }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setState { copy(articles = it) }
            }
            ) { Log.e(TAG, "Error while receiving articles") }
    }

    private fun handleTextChanged(text: String) {
        disposeNewsRequest()
        articleDisposable = newsRepository.searchNews(text, searchNewsPage)
            .flattenAsFlowable { it.articles }
            .map { mapToItemModel(it) }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setState { copy(articles = it) }
            }
            ) { Log.e(TAG, "Error while receiving articles") }
    }

    fun saveBookmark(article: ArticleModel) {
        articleDisposable = newsRepository.upsert(article)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({})
            { Log.e(TAG, "Error while save articles") }
    }


    private fun mapToItemModel(item: Article) = ArticleModel(
        item.id,
        item.title,
        item.description,
        item.url,
        item.urlToImage
    )

    private fun disposeNewsRequest() {
        articleDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }


}