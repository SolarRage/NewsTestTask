package com.myarulin.newstesttask.ui.bookmarks

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.myarulin.newstesttask.ui.adapter.NewsAdapter
import com.myarulin.newstesttask.ui.adapter.VerticalSpaceItemDecoration
import com.myarulin.newstesttask.databinding.BookmarkFragmentBinding
import com.myarulin.newstesttask.model.ArticleModel
import com.myarulin.newstesttask.ui.BaseFragment
import com.myarulin.newstesttask.ui.WebActivity
import com.myarulin.newstesttask.ui.bookmarks.BookmarksContract.BookmarksEffect
import com.myarulin.newstesttask.ui.bookmarks.BookmarksContract.BookmarksViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkFragment : BaseFragment<BookmarksViewState, BookmarksEffect>() {

    override val viewModel: BookmarkViewModel by viewModel()

    private var _binding: BookmarkFragmentBinding? = null
    private val binding get() = _binding!!

    private val newsAdapter: NewsAdapter = NewsAdapter(
        ::onItemClick,
        ::onShareClick,
        ::onBookmarkClick
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BookmarkFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        viewModel.loadNews()
        binding.apply {
            llSearchContainer.setOnClickListener { binding.etSearch.requestFocus() }
            recyclerView.adapter = newsAdapter
            recyclerView.addItemDecoration(VerticalSpaceItemDecoration(requireContext()))
            etSearch.doOnTextChanged { text, _, _, _ -> viewModel.onTextChange(text.toString()) }
            btnCross.setOnClickListener {
                etSearch.text?.clear()
            }
        }
    }

    private fun onItemClick(article: ArticleModel) {
        val intent = Intent(requireContext(), WebActivity::class.java)
        intent.putExtra("url", article.website)
        startActivity(intent)
    }

    private fun onShareClick(article: ArticleModel) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, article.website)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    /**
     * bad code. find another way to delete and update items in rv
     */
    private fun onBookmarkClick(article: ArticleModel) {
        viewModel.deleteBookmark(article)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStateChanged(state: BookmarksViewState) {
        binding.progressBar.visibility = if (state.isLoading) VISIBLE else GONE
        newsAdapter.setArticles(state.articles)
    }

    override fun handleEffect(effect: BookmarksEffect) {
    }
}