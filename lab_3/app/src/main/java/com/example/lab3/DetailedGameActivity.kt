package com.example.lab3

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class DetailedGameActivity : AppCompatActivity() {

    private lateinit var nameTextView: TextView
    private lateinit var typeTextView: TextView
    private lateinit var authorTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var telephoneTextView: TextView
    private lateinit var linkTextView: TextView
    private lateinit var exitButton: Button
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_game)

        nameTextView = findViewById(R.id.detailed_name)
        typeTextView = findViewById(R.id.detailed_type)
        authorTextView = findViewById(R.id.detailed_author)
        priceTextView = findViewById(R.id.detailed_price)
        emailTextView = findViewById(R.id.detailed_email)
        telephoneTextView = findViewById(R.id.detailed_telephone)
        linkTextView = findViewById(R.id.detailed_link);
        imageView = findViewById(R.id.detailed_photo)
        exitButton = findViewById(R.id.detailed_exit)

        val intent = intent
        val game = intent.extras!!.get("Game") as Game;
        nameTextView.setText(game.name)
        typeTextView.setText(game.type)
        authorTextView.setText(game.author)
        priceTextView.setText(game.price.toString())
        emailTextView.setText(game.email)
        telephoneTextView.setText(game.telephone)
        linkTextView.setText(game.link)

        telephoneTextView.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${telephoneTextView.text.toString()}")
            startActivity(intent)
        }

        emailTextView.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:${emailTextView.text}")
            if (intent.resolveActivity(packageManager) != null)
            {
                startActivity(intent)
            }
        }

        linkTextView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            var link = linkTextView.text.toString()
            if (!link.contains("http://"))
            {
                link = "http://$link";
            }
            intent.data = Uri.parse(link)
            startActivity(intent)
        }

        imageView.setOnClickListener{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intent)
        }

        exitButton.setOnClickListener{
            finish()
        }
    }
}