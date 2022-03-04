package com.example.cafeappproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var backButtonPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // 뒤로가기 버튼 2번 클릭시 앱 종료
    override fun onBackPressed() {
        if (System.currentTimeMillis() > backButtonPressedTime + 1500) {
            backButtonPressedTime = System.currentTimeMillis()
            Toast.makeText(applicationContext, R.string.backbutton_pressed, Toast.LENGTH_SHORT).show()
            return
        }
        if (System.currentTimeMillis() <= backButtonPressedTime + 1500) {
            finish()
        }
    }
}