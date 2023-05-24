package com.example.lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception

class ReceivingActivity : AppCompatActivity() {

    private val fileName = "games.json"

    private lateinit var pathToFile: File

    private lateinit var checkBox: CheckBox;
    private lateinit var producerText: EditText;

    private lateinit var acceptButton: Button
    private lateinit var returnButton: Button;

    private lateinit var game: Game;
    private lateinit var json: Gson;
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GameAdapter

    private var gameList = mutableListOf<Game>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiving)

        checkBox = findViewById(R.id.receiving_multiplayer);
        producerText = findViewById(R.id.receiving_producer)
        returnButton = findViewById(R.id.receiving_return);
        acceptButton = findViewById(R.id.receiving_accept)
        recyclerView = findViewById(R.id.game_list)

        json = Gson();

        checkBox.setOnClickListener{
            game.isMultiplayer = checkBox.isChecked
        }

        producerText.addTextChangedListener{
            game.producer = producerText.text.toString()
        }



        game = intent.getSerializableExtra("Game") as Game

        returnButton.setOnClickListener {
            finish()
        }

        acceptButton.setOnClickListener {
            val gameCopy = Game(game.name, game.type, game.author, game.price)
            gameCopy.isMultiplayer = game.isMultiplayer
            gameCopy.producer = game.producer
            gameCopy.link = game.link
            gameCopy.telephone = game.telephone
            gameCopy.email = game.email
            gameList.add(gameCopy);
            adapter.notifyDataSetChanged()
            //val jsonValue = json.toJson(gameCopy);
            //val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"games.json")
            //val writer = FileWriter(file)
//
            //writer.use {
            //    it.write(jsonValue);
            //}
            saveGamesToJson(gameList, File(pathToFile, fileName))
            Toast.makeText(this, "Файл успешно сохранен", Toast.LENGTH_LONG).show()
        }

        pathToFile = this.filesDir;
        gameList = getSavedGamesFromJson(File(pathToFile, fileName)) as MutableList<Game>
        recyclerView.layoutManager = LinearLayoutManager(this);
        adapter = GameAdapter(gameList)
        recyclerView.adapter = adapter;
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("Game", game);
        outState.putBoolean("IsMultiplayer", checkBox.isChecked)
        outState.putString("Producer", producerText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val savedGame = savedInstanceState.getSerializable("Game");
        if (savedGame != null)
        {
            game = savedGame as Game;
        }
        checkBox.isChecked = savedInstanceState.getBoolean("IsMultiplayer")
        producerText.setText(savedInstanceState.getString("Producer"));
    }

    fun getSavedGamesFromJson(file: File) : List<Game>
    {
        try {
            FileReader(file).use {
                val gson = Gson()
                val listType = object : TypeToken<List<Game>>() {}.type
                return gson.fromJson(it, listType);
            }
        }
        catch (e: Exception)
        {
            e.printStackTrace()
        }
        return mutableListOf()
    }

    fun saveGamesToJson(games: List<Game>, file: File)
    {
        val gson = Gson()
        val json = gson.toJson(games)

        try {
            FileWriter(file).use { writer ->
                writer.write(json)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}