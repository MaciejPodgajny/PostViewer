package com.example.maciek.postviewer.AsyncTasks

import android.os.AsyncTask
import com.example.maciek.postviewer.Managers.ApplicationManager
import com.example.maciek.postviewer.Managers.NetworkingManager
import com.example.maciek.postviewer.Models.User
import com.google.gson.Gson

class GetUserAsyncTask(val listener: GetUserAsyncTaskListener?) : AsyncTask<Int, Void, User>() {

    interface GetUserAsyncTaskListener {
        fun onGetUserFinished(user: User?)
    }

    override fun doInBackground(vararg params: Int?): User {
        val userId = params[0]
        val url = String.format(ApplicationManager.USERS_DATA_URL_FORMAT, userId.toString())
        val json = NetworkingManager.makeGetRequest(url)
        return Gson().fromJson(json, User::class.java)
    }

    override fun onPostExecute(result: User?) {
        super.onPostExecute(result)
        listener?.onGetUserFinished(result)
    }
}