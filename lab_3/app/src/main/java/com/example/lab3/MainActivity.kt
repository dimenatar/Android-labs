package com.example.lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private  lateinit var gameName: EditText;
    private  lateinit var gameType: EditText;
    private  lateinit var gameAuthor: EditText;
    private  lateinit var gamePrice: EditText;
    private lateinit var gameTelephone: EditText
    private lateinit var gamaEmail: EditText
    private lateinit var gameLink: EditText
    private lateinit var nextButton: Button;

    private var game: Game? = null;

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameName = findViewById(R.id.main_name)
        gameType = findViewById(R.id.main_type)
        gameAuthor = findViewById(R.id.main_author)
        gamePrice = findViewById(R.id.main_price)
        nextButton = findViewById(R.id.main_nextButton)
        gameTelephone = findViewById(R.id.main_telephone)
        gamaEmail = findViewById(R.id.main_email)
        gameLink = findViewById(R.id.main_link)

        nextButton.setOnClickListener {
            game = Game(gameName.text.toString(), gameType.text.toString(), gameAuthor.text.toString(), gamePrice.text.toString().toInt());
            game!!.telephone = gameTelephone.text.toString()
            game!!.email = gamaEmail.text.toString()
            game!!.link = gameLink.text.toString()
            val intent = Intent(this, ReceivingActivity::class.java);
            intent.putExtra("Game", game);
            startActivity(intent);
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable("Game", game);
        outState.putString("Name", gameName.text.toString())
        outState.putString("Type", gameType.text.toString())
        outState.putString("Author", gameAuthor.text.toString())
        outState.putInt("Price", gamePrice.text.toString().toInt())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val savedGame = savedInstanceState.getSerializable("Game")
        if (savedGame != null)
        {
            game = savedGame as Game
        }
        gameName.setText(savedInstanceState.getString("Name"))
        gameType.setText(savedInstanceState.getString("Type"))
        gameAuthor.setText(savedInstanceState.getString("Author"))
        gamePrice.setText(savedInstanceState.getInt("Price"))
    }
}