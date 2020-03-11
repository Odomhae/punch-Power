package com.example.punchpower

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_result.*
import java.text.NumberFormat

class resultActivity : AppCompatActivity() {

    // 파워값 받은거 x100하고
    val power by lazy {
        intent.getDoubleExtra("power", 0.0) *100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // 위에 제목
        title = "Result"
        // 정수로 바꾸고 쉼표 표시해서
        // 점수표시
        scoreLabel.text = NumberFormat.getInstance().format(power.toInt())

        // 다시하기 버튼
        restartButton.setOnClickListener {
            finish()
        }
    }
}