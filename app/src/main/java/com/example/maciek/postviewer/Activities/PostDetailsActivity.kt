package com.example.maciek.postviewer.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.example.maciek.postviewer.Adapters.CommentAdapter
import com.example.maciek.postviewer.AsyncTasks.GetPostCommentsAsyncTask
import com.example.maciek.postviewer.AsyncTasks.GetUserAsyncTask
import com.example.maciek.postviewer.Models.Comment
import com.example.maciek.postviewer.Models.Post
import com.example.maciek.postviewer.Models.User
import com.example.maciek.postviewer.R


class PostDetailsActivity : AppCompatActivity(), GetUserAsyncTask.GetUserAsyncTaskListener, GetPostCommentsAsyncTask.GetPostCommentsAsyncTaskListener {

    companion object {
        const val POST_PARAMETER_NAME = "post"
    }

    private lateinit var commentListView: ListView

    override fun onGetCommentsFinished(comments: ArrayList<Comment>?) {
        if (comments != null) {
            this.populateCommentsList(comments)
        }
    }

    override fun onGetUserFinished(user: User?) {
        if (user != null) {
            val authorView = findViewById<TextView>(R.id.author_view)
            authorView.text = user.name
            val emails = arrayOf(user.email)
            authorView.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "message/rfc822"
                intent.putExtra(Intent.EXTRA_EMAIL, emails)
                intent.putExtra(Intent.EXTRA_SUBJECT, "About your post...")
                intent.putExtra(Intent.EXTRA_TEXT, "Hello")
                startActivity(Intent.createChooser(intent, "Select mailbox app:"))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_details_activity)

        val post = intent.getParcelableExtra<Post>(POST_PARAMETER_NAME)

        val titleView = findViewById<TextView>(R.id.title_view)
        titleView.text = post.title

        val contentView = findViewById<TextView>(R.id.content_view)
        contentView.text = post.body

        GetUserAsyncTask(this).execute(post.userId)

        GetPostCommentsAsyncTask(this).execute(post.id)
    }

    private fun populateCommentsList(items: ArrayList<Comment>) {
        commentListView = findViewById(R.id.comment_list_view)
        commentListView.adapter = CommentAdapter(this, items)
        setListViewHeightBasedOnChildren(commentListView);
    }

    /****
     * https://stackoverflow.com/questions/18367522/android-list-view-inside-a-scroll-view
     * Method for Setting the Height of the ListView dynamically.
     * Hack to fix the issue of not showing all the items of the ListView
     * when placed inside a ScrollView   */
    private fun setListViewHeightBasedOnChildren(listView: ListView) {
        val listAdapter = listView.adapter ?: return

        val desiredWidth = MeasureSpec.makeMeasureSpec(listView.width, MeasureSpec.UNSPECIFIED)
        var totalHeight = 0
        var view: View? = null
        for (i in 0 until listAdapter.count) {
            view = listAdapter.getView(i, view, listView)
            if (i == 0)
                view!!.layoutParams = ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT)

            view!!.measure(desiredWidth, MeasureSpec.UNSPECIFIED)
            totalHeight += view.measuredHeight
        }
        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
        listView.layoutParams = params
    }
}
