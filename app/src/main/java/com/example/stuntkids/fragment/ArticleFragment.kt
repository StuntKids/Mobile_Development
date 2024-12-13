package com.example.stuntkids.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stuntkids.R
import com.example.stuntkids.adapter.ArticleAdapter
import com.example.stuntkids.data.model.ArticleModel
import com.example.stuntkids.data.repository.Repository
import com.example.stuntkids.model.Article
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.launch

class ArticleFragment : Fragment() {

    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var loading: CircularProgressIndicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_article, container, false)
        loading = rootView.findViewById(R.id.loading)

        // Initialize RecyclerView and adapter
        recyclerView = rootView.findViewById(R.id.recyclerViewArticles)
        recyclerView.layoutManager = LinearLayoutManager(context)
        articleAdapter = ArticleAdapter(emptyList()) // Initialize with empty list
        recyclerView.adapter = articleAdapter

        // Initialize SearchView
        searchView = rootView.findViewById(R.id.searchView)

        // Fetch articles and update adapter
        val repository = Repository()
        lifecycleScope.launch {
            try {
                // Show loading indicator while fetching data
                loading.visibility = View.VISIBLE
                val articles: List<Article> = repository.getTopHeadlines() // Assuming health-related articles are fetched here
                articleAdapter = ArticleAdapter(articles)
                recyclerView.adapter = articleAdapter
            } catch (e: Exception) {
                // Handle failure (e.g., show error message to the user)
                // You can add a toast or a Snackbar here to notify the user
            } finally {
                // Hide loading indicator after data is fetched
                loading.visibility = View.GONE
            }
        }

        // SearchView listener for filtering articles based on user input
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle submit (optional behavior)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter articles based on query input
                articleAdapter.filterArticles(newText)
                return true
            }
        })

        // Ensure keyboard is triggered when the SearchView is tapped
        searchView.requestFocus()

        return rootView
    }
}