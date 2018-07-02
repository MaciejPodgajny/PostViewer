package com.example.maciek.postviewer.AsyncTasks

import android.os.AsyncTask
import com.example.maciek.postviewer.Managers.ApplicationManager
import com.example.maciek.postviewer.Managers.NetworkingManager
import com.example.maciek.postviewer.Models.Post
import com.google.gson.Gson

class GetPostsAsyncTask: AsyncTask<Void, Void, ArrayList<Post>>() {

    override fun doInBackground(vararg p0: Void?): ArrayList<Post> {
        val json = NetworkingManager.makeGetRequest(ApplicationManager.POSTS_DATA_URL)
        return Gson().fromJson(json, Array<Post>::class.java).toList() as ArrayList<Post>
    }
}