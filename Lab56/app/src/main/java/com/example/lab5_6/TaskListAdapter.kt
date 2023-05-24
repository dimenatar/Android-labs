package com.example.lab5_6

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.io.File

class TaskListAdapter(private val context: Context, private val taskList: List<Task>) : BaseAdapter() {

    override fun getCount(): Int {
        return taskList.size
    }

    override fun getItem(position: Int): Any {
        return taskList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val task = taskList[position]

        viewHolder.titleTextView.text = task.name
        viewHolder.descriptionTextView.text = task.description
        viewHolder.difficultyTextView.text = task.difficulty
        viewHolder.deadlineTextView.text = task.deadline

        // Загрузка изображения в ImageView
        if (task.photoPath != null) {
            val imageFile = File(task.photoPath)
            val imageBitmap = BitmapFactory.decodeFile(imageFile.path)
            viewHolder.photoImageView.setImageBitmap(imageBitmap)
        } else {
            viewHolder.photoImageView.setImageResource(R.drawable.ic_launcher_background)
        }

        return view
    }

    private class ViewHolder(view: View) {
        val titleTextView: TextView = view.findViewById(R.id.item_name)
        val descriptionTextView: TextView = view.findViewById(R.id.item_description)
        val difficultyTextView: TextView = view.findViewById(R.id.item_difficult)
        val deadlineTextView: TextView = view.findViewById(R.id.item_deadline)
        val photoImageView: ImageView = view.findViewById(R.id.item_image)
    }

}