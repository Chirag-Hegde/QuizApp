package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        val btn_start = findViewById<TextView>(R.id.btn_start)
        btn_start.setOnClickListener{
            val et_name = findViewById<TextView>(R.id.et_name)
            if (et_name.text.toString().isEmpty()){
                Toast.makeText(this, "Please Enter your Name",
                    Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, QuizQuestionActivity::class.java)
                intent.putExtra(Constants.USER_NAME, et_name.text.toString())
                startActivity(intent)
                finish()
            }

        }
    }
}