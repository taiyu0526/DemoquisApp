package jp.techacademy.taiyu.demoquisapp

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList
import java.util.Collections
import java.util.Random


class MainActivity : AppCompatActivity() {

    private var countLabel: TextView? = null
    private var questionImage: ImageView? = null
    private var answerBtn1: Button? = null
    private var answerBtn2: Button? = null
    private var answerBtn3: Button? = null
    private var answerBtn4: Button? = null

    private var rightAnswer: String? = null
    private var rightAnswerCount = 0
    private var quizCount = 1

    internal var quizArray = ArrayList<ArrayList<String>>()

    internal var quizData = arrayOf(
            // {"画像名", "正解", "選択肢１", "選択肢２", "選択肢３"}
            arrayOf("image_jyumon", "おじさん", "おばさん", "おじいさん", "おばあさん"),
            arrayOf("image_gantyan", "正解", "回答２", "回答３", "回答４"),
            arrayOf("image_mudok", "正解", "回答２", "回答３", "回答４"),
            arrayOf("image_sejyon", "正解", "回答２", "回答３", "回答４"),
            arrayOf("image_sunsin", "正解", "回答２", "回答３", "回答４")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        countLabel = findViewById(R.id.countLabel)
        questionImage = findViewById(R.id.questionImage)
        answerBtn1 = findViewById(R.id.answerBtn1)
        answerBtn2 = findViewById(R.id.answerBtn2)
        answerBtn3 = findViewById(R.id.answerBtn3)
        answerBtn4 = findViewById(R.id.answerBtn4)


        for (i in 0 .. quizData.lastIndex) {
            // 新しいArrayListを用意
            val tmpArray = ArrayList<String>()

            // クイズデータを追加
            tmpArray.add(quizData[i][0]) // 画像名
            tmpArray.add(quizData[i][1]) // 正解
            tmpArray.add(quizData[i][2]) // 選択肢１
            tmpArray.add(quizData[i][3]) // 選択肢２
            tmpArray.add(quizData[i][4]) // 選択肢３

            // tmpArrayをquizArrayに追加
            quizArray.add(tmpArray)
        }


        showNextQuiz()
    }

    fun checkAnswer(view: View) {

        // どの回答ボタンが押されたか
        val answerBtn = findViewById<Button>(view.id)
        val btnText = answerBtn.text.toString()

        val alertTitle: String
        if (btnText == rightAnswer) {
            // 正解
            alertTitle = "正解！"
            rightAnswerCount++
        } else {
            // 不正解
            alertTitle = "不正解..."
        }

        // ダイアログ作成
        val builder = AlertDialog.Builder(this)
        builder.setTitle(alertTitle)
        builder.setMessage("答え：" + rightAnswer!!)
        builder.setPositiveButton("OK") { dialogInterface, i ->
            if (quizArray.size < 1) {
                // クイズを全て出題したら結果を表示
                showResult()

            } else {
                quizCount++
                // 次の問題を表示
                showNextQuiz()
            }
        }
        builder.setCancelable(false)
        builder.show()
    }

    fun showNextQuiz() {
        // クイズカウントラベルを更新
        countLabel!!.text = "Q$quizCount"

        // ランダムな数字を取得
        val random = Random()
        val randomNum = random.nextInt(quizArray.size)

        // randomNumを使って、quizArrayからクイズを一つ取り出す
        val quiz = quizArray[randomNum]

        // 問題画像をセット
        questionImage!!.setImageResource(
                resources.getIdentifier(quiz[0], "drawable", packageName)
        )

        // 正解をrightAnswerをセット
        rightAnswer = quiz[1]

        // クイズ配列から画像名を削除
        quiz.removeAt(0)

        // 正解と選択肢３つをシャッフル
        Collections.shuffle(quiz)

        // 回答ボタンに正解と選択肢３つをセット
        answerBtn1!!.text = quiz[0]
        answerBtn2!!.text = quiz[1]
        answerBtn3!!.text = quiz[2]
        answerBtn4!!.text = quiz[3]

        // このクイズをquizArrayから削除
        quizArray.removeAt(randomNum)
    }

    fun showResult() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("結果")
        builder.setMessage("$rightAnswerCount / $quizCount")
        builder.setPositiveButton("もう一度") { dialogInterface, i -> recreate() }
        builder.setNegativeButton("終了") { dialogInterface, i -> finish() }
        builder.show()
    }
}
