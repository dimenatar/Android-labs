package com.example.lab5_6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader

class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация списка задач и адаптера
        taskList = ArrayList()
        // Настройка ListView
        val listView = findViewById<ListView>(R.id.main_recycler)
        val taskList = getListOfTasks() // Получение списка задач из файла или базы данных
        val adapter = TaskListAdapter(this, taskList)
        listView.adapter = adapter

        // Обработчик нажатия на элемент списка
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedTask = taskList[position]
            // Открываем активити детализации с передачей выбранной задачи
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("task", selectedTask)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_TASK_REQUEST_CODE && resultCode == RESULT_OK) {
            val newTask = data?.getSerializableExtra("task") as Task
            taskList.add(newTask)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        val selectedTask = taskList[position]

        return when (item.itemId) {
            R.id.menu_edit -> {
                editTask(selectedTask)
                true
            }
            R.id.menu_delete -> {
                showDeleteConfirmationDialog(selectedTask)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun getListOfTasks(): List<Task>
    {
        val filename = "tasks.txt"
        val file = File(filesDir, filename)

        try {
            FileReader(file).use { reader ->
                val gson = Gson()
                val listType = object : TypeToken<List<Task>>() {}.type
                val tasks: List<Task> = gson.fromJson(reader, listType)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mutableListOf()
    }

    private fun editTask(task: Task)
    {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("task", task)
    }

    private fun showDeleteConfirmationDialog(task: Task) {
        AlertDialog.Builder(this)
            .setTitle("Delete Task")
            .setMessage("Are you sure you want to delete this task?")
            .setPositiveButton("Delete") { _, _ ->
                // Удаляем задачу из списка и сохраняем обновленный список в файл
                // (applicationContext as? MyApplication)?.taskList?.remove(task)
                deleteTask(task);
                saveTaskListToFile(taskList)
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun saveTaskListToFile(taskList: ArrayList<Task>?) {
        taskList?.let {
            // Создаем JSON-строку из списка задач
            val jsonString = Gson().toJson(taskList)

            // Сохраняем JSON-строку в файл
            val fileName = "tasks.txt"
            val file = File(filesDir, fileName)
            file.writeText(jsonString)
        }
    }

    companion object {
        private const val CREATE_TASK_REQUEST_CODE = 1
        lateinit var taskList: ArrayList<Task>
        private lateinit var adapter: ArrayAdapter<Task>
        public fun deleteTask(task: Task)
        {
            this.taskList.remove(task);
        }

        fun updateList(task: Task)
        {
            val foundTask = taskList.find { t -> t.name == task.name }
            foundTask!!.photoPath = task.photoPath;
            foundTask.deadline = task.deadline
            foundTask.description = task.description
            foundTask.difficulty = task.difficulty
            foundTask.urgency = task.urgency
            adapter.notifyDataSetChanged()
        }
    }
}