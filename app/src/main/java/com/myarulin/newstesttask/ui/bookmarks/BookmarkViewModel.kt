package com.myarulin.newstesttask.ui.bookmarks

import android.util.Log
import com.myarulin.newstesttask.db.Article
import com.myarulin.newstesttask.model.ArticleModel
import com.myarulin.newstesttask.repo.NewsRepository
import com.myarulin.newstesttask.ui.BaseViewStateViewModel
import com.myarulin.newstesttask.ui.bookmarks.BookmarksContract.BookmarksEffect
import com.myarulin.newstesttask.ui.bookmarks.BookmarksContract.BookmarksViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class BookmarkViewModel(val newsRepository: NewsRepository)
    : BaseViewStateViewModel<BookmarksViewState, BookmarksEffect>(){

    private val TAG = BookmarkViewModel::class.simpleName
    private val compositeDisposable = CompositeDisposable()
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
        compositeDisposable += textChangeSubject
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .filter{it.isNotBlank()}
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { handleTextChanged(it) },
                { Log.e(TAG, it.message.toString()) }
            )
    }

    fun loadNews(){
        disposeNewsRequest()
        articleDisposable = newsRepository.getSavedNews()
            .flattenAsFlowable { it }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setState { copy(articles = it) }
            }
            ) {
                Log.e(TAG, "Error while receiving articles")
            }
    }

    fun deleteBookmark(article: ArticleModel){
        articleDisposable = newsRepository.deleteArticle(article)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({})
            { Log.e(TAG, "Error while delete articles") }

    }

    private fun handleTextChanged(text: String) {

    }

    private fun disposeNewsRequest() {
        articleDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }

}