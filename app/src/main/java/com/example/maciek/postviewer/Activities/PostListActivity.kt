package com.example.maciek.postviewer.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ListView
import com.example.maciek.postviewer.Adapters.PostAdapter
import com.example.maciek.postviewer.AsyncTasks.GetPostsAsyncTask
import com.example.maciek.postviewer.Models.Post
import com.example.maciek.postviewer.R
import java.util.*

class PostListActivity : AppCompatActivity() {

    private lateinit var postListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_list_activity)

        val listItems = GetPostsAsyncTask().execute().get()
        this.populatePostList(listItems)
    }

    private fun populatePostList(items: ArrayList<Post>) {
        postListView = findViewById(R.id.post_list_view)
        postListView.adapter = PostAdapter(this, items)
        postListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, PostDetailsActivity::class.java)
            intent.putExtra(PostDetailsActivity.POST_PARAMETER_NAME, items[position])
            startActivity(intent)
        }
    }
}
