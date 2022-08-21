package com.myarulin.newstesttask.ui.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.myarulin.newstesttask.adapter.NewsAdapter
import com.myarulin.newstesttask.databinding.BookmarkFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkFragment: Fragment() {

    private var _binding: BookmarkFragmentBinding? = null
    private val binding get() = _binding!!
    private val  viewModel: BookmarkViewModel by viewModel()
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BookmarkFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.llSearchContainer.setOnClickListener { binding.etSearch.requestFocus() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}