package com.myarulin.newstesttask.ui.bookmarks

import android.util.Log
import com.myarulin.newstesttask.model.ArticleModel
import com.myarulin.newstesttask.model.toDomainModel
import com.myarulin.newstesttask.repo.NewsRepository
import com.myarulin.newstesttask.ui.BaseViewStateViewModel
import com.myarulin.newstesttask.ui.bookmarks.BookmarksContract.BookmarksEffect
import com.myarulin.newstesttask.ui.bookmarks.BookmarksContract.BookmarksViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class BookmarkViewModel(
    private val newsRepository: NewsRepository
) : BaseViewStateViewModel<BookmarksViewState, BookmarksEffect>() {

    private val TAG = BookmarkViewModel::class.simpleName
    private val textChangeSubject: BehaviorSubject<String> = BehaviorSubject.createDefault("")
    private var articleDisposable: Disposable? = null

    override fun getInitialState() = BookmarksViewState()

    override fun init() {
        subscribeForTestChanges()
    }

    fun onTextChange(text: String) {
        textChangeSubject.onNext(text)
    }

    private fun subscribeForTestChanges() {
        lifecycleDisposable += textChangeSubject
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { handleTextChanged(it) },
                { Log.e(TAG, it.message.toString()) }
            )
    }

    private fun handleTextChanged(text: String) {
        disposeNewsRequest()
        articleDisposable = newsRepository.searchSavedNews(text)
            .flattenAsFlowable { it }
            .map { it.toDomainModel() }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setState { copy(articles = it) }
            }
            ) {
                Log.e(TAG, "Error while receiving articles : ${it.message}")
            }
    }

    fun loadNews() {
        lifecycleDisposable += newsRepository.getSavedNews()
            .flattenAsFlowable { it }
            .map { it.toDomainModel() }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setState { copy(articles = it) }
            }
            ) {
                Log.e(TAG, "Error while receiving articles : ${it.message}")
            }
    }

    fun deleteBookmark(article: ArticleModel) {
        lifecycleDisposable += newsRepository.deleteArticle(article)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ handleBookmarkDeleted(article) })
            { Log.e(TAG, "Error while delete articles : ${it.message}") }

    }

    private fun handleBookmarkDeleted(article: ArticleModel) {
        val articles = viewStateLiveData.value?.articles.orEmpty().toMutableList().apply {
            remove(article)
        }
        setState { copy(articles = articles) }
    }

    private fun disposeNewsRequest() {
        articleDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }

    override fun onDispose() {
        disposeNewsRequest()
    }
}