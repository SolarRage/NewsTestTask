package com.myarulin.newstesttask.ui.home

import android.util.Log
import com.myarulin.newstesttask.db.Article
import com.myarulin.newstesttask.model.ArticleModel
import com.myarulin.newstesttask.repo.NewsRepository
import com.myarulin.newstesttask.ui.BaseViewStateViewModel
import com.myarulin.newstesttask.ui.home.HomeContract.HomeViewState
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit.MILLISECONDS

class HomeViewModel(//todo: добавить дефолтный диспозабл и чистить все диспосаблы по onStop()
    private val newsRepository: NewsRepository
) : BaseViewStateViewModel<HomeViewState, HomeContract.HomeEffect>() {

    private val TAG = HomeViewModel::class.simpleName

    private val textChangeSubject: BehaviorSubject<String> = BehaviorSubject.createDefault("")

    private val searchNewsPage = 1

    private var articleDisposable: Disposable? = null

    override fun getInitialState() = HomeViewState()

    fun onTextChange(text: String) {
        textChangeSubject.onNext(text)
    }

    override fun init() {
        subscribeForTestChanges()
    }

    private fun subscribeForTestChanges() {
        lifecycleDisposable += textChangeSubject
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
        lifecycleDisposable += newsRepository.getNews("ua", searchNewsPage)
            .flattenAsFlowable { it.articles }
            .flatMapSingle { mapToItemModel(it) }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setState { copy(articles = it) }
            }
            ) {
                Log.e(TAG, "Error while load articles : ${it.message}")
            }
    }

    private fun handleTextChanged(text: String) {
        disposeNewsRequest()
        articleDisposable = newsRepository.searchNews(text, searchNewsPage)
            .flattenAsFlowable { it.articles }
            .flatMapSingle { mapToItemModel(it) }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setState { copy(articles = it) }
            }
            ) {
                Log.e(TAG, "Error while receiving articles : ${it.message}")
            }
    }

    fun onBookmarkClick(article: ArticleModel) {
        lifecycleDisposable += newsRepository.isItemExists(article.website)
            .flatMap { isSaved ->
                if (isSaved) {
                    newsRepository.deleteArticle(article)
                        .toSingleDefault(false)
                } else {
                    newsRepository.upsert(article)
                        .toSingleDefault(true)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { handleArticleBookmarkStateChanged(article.copy(isSaved = it)) }
            ) { Log.e(TAG, "Error while handling on bookmark click : ${it.message}") }
    }

    private fun handleArticleBookmarkStateChanged(article: ArticleModel) {
        val articles = viewStateLiveData.value?.articles.orEmpty().toMutableList().apply {
            val indexOfArticle = indexOfFirst { it.website == article.website }
            set(indexOfArticle, article)
        }
        setState { copy(articles = articles) }
    }

    private fun mapToItemModel(article: Article): Single<ArticleModel> {
        return newsRepository.isItemExists(article.url.orEmpty())
            .map { isSaved ->
                ArticleModel(
                    article.url.orEmpty(),
                    article.title,
                    article.description,
                    article.urlToImage,
                    isSaved
                )
            }
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
