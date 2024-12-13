package com.example.stuntkids.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stuntkids.R
import com.example.stuntkids.adapter.TodaysInfoAdapter
import com.example.stuntkids.data.repository.Repository
import com.example.stuntkids.model.TodaysInfo
import com.example.stuntkids.view.MainActivity
import com.example.stuntkids.view.NewsArticleActivity
import com.example.stuntkids.view.StuntingCheckerActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var todaysInfoRecyclerView: RecyclerView
    private lateinit var todaysInfoAdapter: TodaysInfoAdapter
    private lateinit var helloUserText: TextView
    private lateinit var trackKidsText: TextView
    private lateinit var stuntingCheckerButton: View
    private lateinit var loading: CircularProgressIndicator
    private lateinit var newsArticleButton: View
    private lateinit var repository: Repository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        repository = Repository()
        // Initialize views
        todaysInfoRecyclerView = rootView.findViewById(R.id.todaysInfoRecyclerView)
        helloUserText = rootView.findViewById(R.id.helloUser)
        trackKidsText = rootView.findViewById(R.id.trackKidsText)
        stuntingCheckerButton = rootView.findViewById(R.id.stuntingCheckerButton)
        newsArticleButton = rootView.findViewById(R.id.newsArticleButton)
        loading = rootView.findViewById(R.id.loading)

        // Set RecyclerView for today's info
        todaysInfoRecyclerView.layoutManager = LinearLayoutManager(context)
        loadArticles()

        // You can add click listeners or actions for other buttons if needed
        stuntingCheckerButton.setOnClickListener {
            // Open StuntingCheckerActivity
            val intent = Intent(activity, StuntingCheckerActivity::class.java)
            startActivity(intent)
        }

        newsArticleButton.setOnClickListener {
            // Open NewsArticleActivity
            (requireActivity() as MainActivity).loadFragment(ArticleFragment())
//            val intent = Intent(activity, NewsArticleActivity::class.java)
//            startActivity(intent)
        }

        return rootView
    }

    private fun loadArticles() {
        // Create a list of articles (this can come from a server or a local database)
        lifecycleScope.launch {
            // Set up RecyclerView with the Adapter
            todaysInfoAdapter = TodaysInfoAdapter(articles = repository.getTopHeadlines())
            todaysInfoRecyclerView.adapter = todaysInfoAdapter
            loading.visibility = View.GONE
        }
    }
}
