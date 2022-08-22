package com.myarulin.newstesttask.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.widget.doOnTextChanged
import com.myarulin.newstesttask.ui.adapter.NewsAdapter
import com.myarulin.newstesttask.ui.adapter.VerticalSpaceItemDecoration
import com.myarulin.newstesttask.databinding.HomeFragmentBinding
import com.myarulin.newstesttask.model.ArticleModel
import com.myarulin.newstesttask.ui.BaseFragment
import com.myarulin.newstesttask.ui.WebActivity
import com.myarulin.newstesttask.ui.home.HomeContract.HomeEffect
import com.myarulin.newstesttask.ui.home.HomeContract.HomeViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewState, HomeEffect>() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    override val viewModel: HomeViewModel by viewModel()
    lateinit var newsAdapter: NewsAdapter
    private lateinit var loader: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.loadNews()
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

    private fun onBookmarkClick(article: ArticleModel){
        viewModel.saveBookmark(article)
    }

    private fun initViews() {
        binding.apply {
            llSearchContainer.setOnClickListener { binding.etSearch.requestFocus() }
            loader = progressBar
            newsAdapter = NewsAdapter(::onItemClick, ::onShareClick, ::onBookmarkClick)
            rvNews.adapter = newsAdapter
            rvNews.addItemDecoration(VerticalSpaceItemDecoration(requireContext()))
            etSearch.doOnTextChanged { text, _, _, _ -> viewModel.onTextChange(text.toString()) }
            btnCross.setOnClickListener {
                etSearch.text.clear()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStateChanged(state: HomeViewState) {
        loader.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        newsAdapter.setArticles(state.articles)
    }

    override fun handleEffect(effect: HomeEffect) {
    }
}