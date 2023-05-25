package com.example.lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.util.Log
import android.widget.*
import androidx.core.widget.addTextChangedListener
import com.google.gson.Gson
import java.io.File
import java.io.FileWriter

class ReceivingActivity : AppCompatActivity() {

    private lateinit var checkBox: CheckBox;
    private lateinit var producerText: EditText;
    private lateinit var outText: TextView;

    private lateinit var acceptButton: Button
    private lateinit var returnButton: Button;

    private lateinit var game: Game;
    private lateinit var json: Gson;
    private var gameList = mutableListOf<Game>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiving)

        checkBox = findViewById(R.id.receiving_multiplayer);
        producerText = findViewById(R.id.receiving_producer)
        outText = findViewById(R.id.receiving_info)
        returnButton = findViewById(R.id.receiving_return);
        acceptButton = findViewById(R.id.receiving_accept)

        json = Gson();

        checkBox.setOnClickListener{
            game.isMultiplayer = checkBox.isChecked
            outText.text = game.getGameData();
        }

        producerText.addTextChangedListener{
            game.producer = producerText.text.toString()
            outText.text = game.getGameData();
        }

        game = intent.getSerializableExtra("Game") as Game

        outText.text = game.getGameData();

        returnButton.setOnClickListener {
            finish()
        }

        acceptButton.setOnClickListener {
            val gameCopy = Game(game.name, game.type, game.author, game.price)
            gameCopy.isMultiplayer = game.isMultiplayer
            gameCopy.producer = game.producer
            gameList.add(gameCopy);

            val jsonValue = json.toJson(gameCopy);
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"games.json")
            val writer = FileWriter(file)

            writer.use {
                it.appendLine(jsonValue);
            }

            Toast.makeText(this, "Файл успешно сохранен", Toast.LENGTH_LONG).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("Game", game);
        outState.putBoolean("IsMultiplayer", checkBox.isChecked)
        outState.putString("Info", outText.text.toString())
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
        outText.text = savedInstanceState.getString("Info");
        producerText.setText(savedInstanceState.getString("Producer"));
    }
}