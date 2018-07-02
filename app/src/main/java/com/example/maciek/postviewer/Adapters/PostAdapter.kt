package com.example.maciek.postviewer.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.maciek.postviewer.Models.Post
import com.example.maciek.postviewer.R

class PostAdapter(private val context: Context,
                  private val dataSource: ArrayList<Post>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View

        if (convertView == null) {
            view = inflater.inflate(R.layout.post_list_item, parent, false)
        } else {
            view = convertView
        }

        val post = this.getItem(position) as Post
        val titleView = view.findViewById<TextView>(R.id.title_view)
        titleView.text = post.title
        val contentPreviewView = view.findViewById<TextView>(R.id.content_preview_view)
        contentPreviewView.text = post.body

        return view
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }
}