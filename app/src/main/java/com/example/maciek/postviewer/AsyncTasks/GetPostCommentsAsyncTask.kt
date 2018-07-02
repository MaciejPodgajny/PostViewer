package com.example.maciek.postviewer.AsyncTasks

import android.os.AsyncTask
import com.example.maciek.postviewer.Managers.ApplicationManager
import com.example.maciek.postviewer.Managers.NetworkingManager
import com.example.maciek.postviewer.Models.Comment
import com.google.gson.Gson

class GetPostCommentsAsyncTask(val listener: GetPostCommentsAsyncTaskListener?) : AsyncTask<Int, Void, ArrayList<Comment>>() {

    interface GetPostCommentsAsyncTaskListener {
        fun onGetCommentsFinished(comments: ArrayList<Comment>?)
    }

    override fun doInBackground(vararg params: Int?): ArrayList<Comment> {
        val postId = params[0]
        val url = String.format(ApplicationManager.POST_COMMENTS_DATA_URL_FORMAT, postId.toString())
        val json = NetworkingManager.makeGetRequest(url)
        return Gson().fromJson(json, Array<Comment>::class.java).toList() as ArrayList<Comment>
    }

    override fun onPostExecute(result: ArrayList<Comment>?) {
        super.onPostExecute(result)
        listener?.onGetCommentsFinished(result)
    }
}