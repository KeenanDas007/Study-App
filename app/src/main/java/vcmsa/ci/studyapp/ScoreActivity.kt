package vcmsa.ci.studyapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ScoreActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_score)

        // Get data from intent
        val score = intent.getIntExtra("SCORE", 0)
        val questions = intent.getStringArrayExtra("QUESTIONS") as Array<String>
        val answers = intent.getBooleanArrayExtra("ANSWERS") ?: BooleanArray(5)
        val explanations = intent.getStringArrayExtra("EXPLANATIONS") as Array<String>

        // Set up UI
        val txtScore = findViewById<TextView>(R.id.txtScore)
        val txtFeedback = findViewById<TextView>(R.id.txtFeedback)
        val btnReview = findViewById<Button>(R.id.ReviewBTN)
        val btnExit = findViewById<Button>(R.id.ExitBTN)
        val txtReview = findViewById<TextView>(R.id.txtReview)


        // Display score
        txtScore.text = "Your Score: $score/${questions.size}"

        // Set feedback based on score
        txtFeedback.text = if (score >= 3) {
            "Great job! You've mastered these concepts!"
        } else {
            "Keep practising! You'll get better with time."
        }

        // Create review text
        var reviewText = ""
        for (i in questions.indices) {
            reviewText += "Question ${i+1}:\n"
            reviewText += questions[i] + "\n"
            reviewText += "Correct Answer: ${if (answers[i]) "True" else "False"}\n"
            if (explanations[i].isNotEmpty()) {
                reviewText += "Explanation: ${explanations[i]}\n"
            }
            reviewText += "\n"
        }
        txtReview.text = reviewText

        // Set up review button
        btnReview.setOnClickListener {
            if (txtReview.visibility == View.VISIBLE) {
                txtReview.visibility = View.GONE
                btnReview.text = "Review"
            } else {
                txtReview.visibility = View.VISIBLE
                btnReview.text = "Hide"
            }
        }

        // Set up exit button
        btnExit.setOnClickListener {
            finishAffinity()
}

        val btnRestart = findViewById<Button>(R.id.btnRestart)
        btnRestart.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}