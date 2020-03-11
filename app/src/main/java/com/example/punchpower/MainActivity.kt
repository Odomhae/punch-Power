package com.example.punchpower

import android.animation.*
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var maxPower = 0.0
    var isStart = false
    var startTime = System.currentTimeMillis()// OL

    // 센서 관리자 객체
    private val senSorManager :SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    // 센서 이벤트 처리하는 리스너
    private val eventListener : SensorEventListener = object  : SensorEventListener{
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }

        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                if(event.sensor.type != Sensor.TYPE_LINEAR_ACCELERATION)
                    return@let

                // 각 좌표값 제곱시켜 음수값 없애고 차이를 극대화
                val power =
                    Math.pow(event.values[0].toDouble(), 2.0) +  Math.pow(event.values[1].toDouble(), 2.0) +
                            Math.pow(event.values[2].toDouble(), 2.0)

                if(power > 20 && !isStart){
                    //측정시작
                    startTime = System.currentTimeMillis()
                    isStart  = true
                }

                // 측정이 시작된 경우
                if(isStart){
                    //애니메이션 변경
                    // imageView.clearAnimation()
                    val animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.rotate)
                    imageView.startAnimation(animation)

                    if(maxPower < power)
                    {
                        maxPower = power
                        Log.d("11",event.values[0].toString() )
                        Log.d("22",event.values[1].toDouble().toString() )
                        Log.d("33",event.values[2].toDouble().toString() )

                        Log.d("MaxPower : ", power.toString())
                    }


                    stateLabel.text = "Calculating..."

                    if(System.currentTimeMillis() - startTime > 3000){
                        isStart = false

                        punchPowerTestComplete(maxPower)
                    }
                }
            }
        }
    }


    // 게임 초기화
    private fun initGame(){
        maxPower = 0.0
        isStart = false
        startTime =  System.currentTimeMillis()// OL
        // as fast as you can
        stateLabel.text = "함 질러보세요\n Punch AFAYC"

        //센서의 변화값을 처리할 리스너
        senSorManager.registerListener(
            eventListener,
            senSorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
            SensorManager.SENSOR_DELAY_NORMAL
        )


        // 애니메이션 시작
        // anim 폴더의 alpha_scale 애니메이션 속성을 적용함
        val animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.alpha_scale)
        imageView.startAnimation(animation)

        animation.setAnimationListener(object :Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {
                //애니메이션이 반복될때
            }

            override fun onAnimationStart(animation: Animation?) {
                //애니메이션이 시작될때
            }

            override fun onAnimationEnd(animation: Animation?) {
                // 애니메이션이 종료될때
            }
        })

        AnimatorInflater.loadAnimator(this@MainActivity, R.animator.property_animation).apply {

            val colorAnimator = this@apply as? ObjectAnimator
            colorAnimator?.apply {
                setEvaluator(ArgbEvaluator())

                target = window.decorView.findViewById(android.R.id.content)

                start()
            }

        }
    }

    // 펀치력 측정 완료시 처리함수
    fun punchPowerTestComplete(power :Double){
        Log.d("main", "측정완료 : power : ${String.format("%.5f",power)}")
        senSorManager.unregisterListener(eventListener)
        val intent = Intent(this@MainActivity, resultActivity::class.java)
        intent.putExtra("power", power)
        startActivity(intent)
    }


    ////////////////////////////////////////
    // 화면 최초 생성시 호출
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // 사라졌다 다시 보일 때 호출
    override fun onStart() {
        super.onStart()
        initGame()
    }

    // 사라질 때 호출
    override fun onStop() {
        super.onStop()
        try{
            senSorManager.unregisterListener(eventListener)
        }catch (e : Exception){}
    }
}