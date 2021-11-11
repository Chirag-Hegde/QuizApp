package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat


class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionList : ArrayList<Question>? = null
    private var mSelectedOptionPosition : Int = 0
    private var mCorrectAnswers : Int = 0
    private var mUserName : String? = null


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        mQuestionList = Constants.getQuestion()

        setQuestion()
        val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        val tv_option_four = findViewById<TextView>(R.id.tv_option_four)

        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)

        val btn_submit = findViewById<TextView>(R.id.btn_submit)
        btn_submit.setOnClickListener(this)

    }

    private fun setQuestion() {
        //mCurrentPosition = 1 (testing purposes)
        val question  = mQuestionList!![mCurrentPosition-1]

        defaultOptionsView()
        if(mCurrentPosition == mQuestionList!!.size){
            val btn_submit = findViewById<TextView>(R.id.btn_submit)
            btn_submit.text = "Finish"
        }else{
            val btn_submit = findViewById<TextView>(R.id.btn_submit)
            btn_submit.text = "Submit"
        }

        //val progressBar = findViewById<TextView>(R.id.progressBar)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        progressBar.progress = mCurrentPosition
        val tv_progress = findViewById<TextView>(R.id.tv_progress)
        tv_progress.text = "$mCurrentPosition" + "/" + progressBar.max

        val tv_question = findViewById<TextView>(R.id.tv_question)
        tv_question.text = question!!.question

        val iv_image = findViewById<ImageView>(R.id.iv_image)
        iv_image.setImageResource(question.image)

        val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        tv_option_one.text = question.optionone

        val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        tv_option_two.text = question.optiontwo

        val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        tv_option_three.text = question.optionthree

        val tv_option_four = findViewById<TextView>(R.id.tv_option_four)
        tv_option_four.text = question.optionfour
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()

        val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        options.add(0, tv_option_one)

        val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        options.add(1, tv_option_two)

        val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        options.add(2, tv_option_three)

        val tv_option_four = findViewById<TextView>(R.id.tv_option_four)
        options.add(3, tv_option_four)

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg

            )
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_option_one ->{
                val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
                selectedOptionView(tv_option_one,1)
            }
            R.id.tv_option_two ->{
                val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
                selectedOptionView(tv_option_two,2)
            }
            R.id.tv_option_three ->{
                val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
                selectedOptionView(tv_option_three,3)
            }
            R.id.tv_option_four ->{
                val tv_option_four = findViewById<TextView>(R.id.tv_option_four)
                selectedOptionView(tv_option_four,4)
            }
            R.id.btn_submit ->{
                if (mSelectedOptionPosition == 0){
                    mCurrentPosition++

                    when{
                        mCurrentPosition <= mQuestionList!!.size ->{
                            setQuestion()
                        }else ->{
                           val intent = Intent(this, ResultActivity::class.java)
                           intent.putExtra(Constants.USER_NAME,mUserName)
                           intent.putExtra(Constants.CORRECT_ANSWERS,mCorrectAnswers)
                           intent.putExtra(Constants.TOTAL_QUESTION,mQuestionList!!.size)
                           startActivity(intent)
                           finish()
                        }
                    }
                }else{
                    val question = mQuestionList?.get(mCurrentPosition-1)
                    if (question!!.correctanswer != mSelectedOptionPosition){
                        answerview(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }else{
                        mCorrectAnswers++
                    }
                    answerview(question.correctanswer , R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionList!!.size){
                        val btn_submit = findViewById<TextView>(R.id.btn_submit)
                        btn_submit.text = "Finish"
                    }else{
                        val btn_submit = findViewById<TextView>(R.id.btn_submit)
                        btn_submit.text = "Go To Next Question"
                    }
                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerview(answer:Int,drawableview:Int){
        when(answer){
            1 ->{
                val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
                tv_option_one.background = ContextCompat.getDrawable(
                    this, drawableview )
            }
            2 ->{
                val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
                tv_option_two.background = ContextCompat.getDrawable(
                    this, drawableview )
            }
            3 ->{
                val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
                tv_option_three.background = ContextCompat.getDrawable(
                    this, drawableview )
            }
            4 ->{
                val tv_option_four = findViewById<TextView>(R.id.tv_option_four)
                tv_option_four.background = ContextCompat.getDrawable(
                    this, drawableview )
            }
        }

    }

    private fun selectedOptionView(tv : TextView, selectedOptionNum: Int){
        defaultOptionsView()
        mSelectedOptionPosition =  selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg)
    }
}