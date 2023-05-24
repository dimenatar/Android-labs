package com.example.lab5_6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import java.io.File

class DetailActivity : AppCompatActivity() {
    private lateinit var task: Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Получаем выбранную задачу из интента
        task = intent.getSerializableExtra("task") as Task

        // Отображение информации о задаче
        findViewById<EditText>(R.id.detail_name).setText(task.name)
        findViewById<EditText>(R.id.detail_description).setText(task.description)
        findViewById<EditText>(R.id.detail_difficulty).setText(task.difficulty)
        findViewById<EditText>(R.id.detail_urgency).setText(task.urgency)
        findViewById<EditText>(R.id.detail_deadline).setText(task.deadline)

        // Загрузка и отображение фото задачи
        if (task.photoPath.isNotEmpty()) {
            val imageView: ImageView = findViewById(R.id.imageView)
            Picasso.get().load(task.photoPath).into(imageView)
        }
    }

    override fun onDestroy() {
        MainActivity.updateList(task)
        super.onDestroy()
    }
}