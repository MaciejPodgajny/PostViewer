package com.example.maciek.postviewer.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.maciek.postviewer.Models.Comment
import com.example.maciek.postviewer.R

class CommentAdapter(private val context: Context,
                     private val dataSource: ArrayList<Comment>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View

        if (convertView == null) {
            view = inflater.inflate(R.layout.comment_list_item, parent, false)
        } else {
            view = convertView
        }

        val comment = this.getItem(position) as Comment
        val nameView = view.findViewById<TextView>(R.id.name_view)
        nameView.text = comment.name
        val emailView = view.findViewById<TextView>(R.id.email_view)
        emailView.text = comment.email
        val contentView = view.findViewById<TextView>(R.id.content_view)
        contentView.text = comment.body

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