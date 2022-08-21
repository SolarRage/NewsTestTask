package com.myarulin.newstesttask.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.widget.doOnTextChanged
import com.myarulin.newstesttask.adaptor.NewsAdapter
import com.myarulin.newstesttask.databinding.HomeFragmentBinding
import com.myarulin.newstesttask.ui.BaseFragment
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
    }

    private fun initViews(){
        binding.llSearchContainer.setOnClickListener { binding.etSearch.requestFocus() }
        loader = binding.progressBar
        newsAdapter = NewsAdapter()
        binding.rvNews.adapter = newsAdapter
        binding.etSearch.doOnTextChanged{ text, _, _, _ -> viewModel.onTextChange(text.toString()) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStateChanged(state: HomeViewState) {
        loader.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        newsAdapter.setProducts(state.articles)
    }

    override fun handleEffect(effect: HomeEffect) {
    }
}