package com.example.lab5_6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.io.File

class CreateTaskActivity : AppCompatActivity() {
    private lateinit var photoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        // Обработчик нажатия на кнопку сохранения задачи
        val saveButton: Button = findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            val name = findViewById<EditText>(R.id.nameEditText).text.toString()
            val description = findViewById<EditText>(R.id.descriptionEditText).text.toString()
            val difficulty = findViewById<Spinner>(R.id.difficultySpinner).selectedItem.toString()
            val urgency = findViewById<Spinner>(R.id.urgencySpinner).selectedItem.toString()
            val deadline = findViewById<EditText>(R.id.deadlineEditText).text.toString()

            val newTask = Task(name, description, difficulty, urgency, deadline, photoPath)

            // Возвращаем созданную задачу в MainActivity
            val intent = Intent()
            intent.putExtra("task", newTask)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}