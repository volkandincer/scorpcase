package com.example.scorpcase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scorpcase.base.BaseFragment
import com.example.scorpcase.data.models.Status
import com.example.scorpcase.databinding.FragmentListBinding
import com.example.scorpcase.utils.PaginationScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : BaseFragment() {
    private val viewModel: ListFragmentViewModel by viewModels()
    private lateinit var binding: FragmentListBinding
    private lateinit var listFragmentAdapter: ListFragmentAdapter
    private var isRefresh = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        observeResponseData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun init() {
        viewModel.getDataFetch(false)

        if (!::listFragmentAdapter.isInitialized) {
            listFragmentAdapter = ListFragmentAdapter()
        }

        with(binding) {
            with(recyclerView) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                itemAnimator = null
                adapter = listFragmentAdapter
                addOnScrollListener(object : PaginationScrollListener() {
                    override fun onScrollLimit() {
                        viewModel.nextData()
                    }
                })

                swipeLayout.setOnRefreshListener {
                    isRefresh = true
                    listener?.manageProgressDialogVisibility(true)
                    viewModel.refreshData()
                    if (swipeLayout.isRefreshing) {
                        swipeLayout.isRefreshing = false
                        isRefresh = true
                    }
                }
            }
        }
        isRefresh = false
    }

    private fun observeResponseData() {
        viewModel.person.observe(viewLifecycleOwner)
        {
            when (it.status) {
                Status.LOADING -> {
                    listener?.manageProgressDialogVisibility(isRefresh)
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    listener?.manageProgressDialogVisibility(false)

                    listener?.showSnackbar(it.message.toString())
                }
                Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) {
                        binding.progressBar.visibility = View.GONE
                        listener?.manageProgressDialogVisibility(false)

                        listFragmentAdapter.updateOrderList(viewModel.oldPersonList)
                    }
                }
            }
        }
    }
}