package vcmsa.ci.studyapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    //Declare elements
    private lateinit var txtQuestionsCounter: TextView
    private lateinit var txtQuestionsText: TextView
    private lateinit var TrueBTN: Button
    private lateinit var FalseBTN: Button
    private lateinit var nextBTN: Button
    private lateinit var txtFeedback: TextView

    //Declare variables
    private var currentQuestionIndex = 0
    private var score = 0
    private var answered = false

    //create two arrays for Q and A
    private val questions = arrayOf(
        "Syntax : Rules that govern the word usage and punctuation used in every programming language.",
        "Algorithm : Step-by-step solution used to solve a problem.",
        "Loop structure : Repeats a set of actions while a condition remains true.",
        "Integers include decimals, fractions and percentages.",
        "RAM: (Random access memory) Form of internal, volatile memory."
    )

    // True or False answers for each question
    private val answers = arrayOf(
        true,  // Question 1 - True
        true,  // Question 2 - True
        true,  // Question 3 - True
        false, // Question 4 - False
        true,   // Question 5 - True
    )

    // Explanation for incorrect answers (only for Question 4 in this case)
    private val explanations = arrayOf(
        "",
        "",
        "",
        "An integer is a whole number which can be positive negative or zero. An integer cannot be a fraction a decimal or a percentage.",
        ""
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        // Initialize all UI elements by finding them in our layout
        txtQuestionsCounter = findViewById(R.id.txtQuestionsCounter)
        txtQuestionsText = findViewById(R.id.txtQuestionText)
        TrueBTN = findViewById(R.id.TrueBTN)
        FalseBTN = findViewById(R.id.FalseBTN)
        nextBTN = findViewById(R.id.nextBTN)
        txtFeedback = findViewById(R.id.txtFeedback)

        //setup false and true buttons
        FalseBTN.setOnClickListener {
            checkAnswer(false)
        }
        TrueBTN.setOnClickListener {
            checkAnswer(true)
        }
        nextBTN.setOnClickListener {
            moveToNext()
        }
        // Display the first question
        updateQuestion()


    }

    @SuppressLint("SetTextI18n")
    private fun moveToNext() {
        // Move to the next question
        currentQuestionIndex++

        // Check if we've reached the end of the quiz
        if (currentQuestionIndex < questions.size) {
            updateQuestion()
        } else {
            // End of quiz: show final score and disable buttons
            txtQuestionsText.text = "Quiz Completed! Your score is $score out of ${questions.size}."
            txtQuestionsCounter.text = ""
            TrueBTN.isEnabled = false
            FalseBTN.isEnabled = false
            nextBTN.visibility = View.INVISIBLE
            txtFeedback.visibility = View.INVISIBLE
        }


    }

    // Method to update the question display with the current question
    @SuppressLint("SetTextI18n")
    fun updateQuestion() {
        // Display the current question number out of total
        txtQuestionsCounter.text = "Question ${currentQuestionIndex + 1} of ${questions.size}"
        // Display the current question text
        txtQuestionsText.text = questions[currentQuestionIndex]
        // Reset UI elements for the new question
        txtFeedback.visibility = View.INVISIBLE
        nextBTN.visibility = View.INVISIBLE
        // Enable the answer buttons for the new question

        TrueBTN.isEnabled = true
        FalseBTN.isEnabled = true

        // Reset the answered flag
        answered = false
    }

    // Method to check if the user's answer is correct
    @SuppressLint("SetTextI18n")
    private fun checkAnswer(userAnswer: Boolean) {

        // Show feedback and Next button
        txtFeedback.visibility = View.VISIBLE
        nextBTN.visibility = View.VISIBLE

// Disable answer buttons to prevent multiple clicks
        TrueBTN.isEnabled = false
        FalseBTN.isEnabled = false

        // Only process the answer if the question hasn't been answered yet
        if (!answered) {
            // Mark the question as answered to prevent multiple submissions
            answered = true

            // Get the correct answer for the current question
            val correctAnswer = answers[currentQuestionIndex]

            // Check if the user's answer matches the correct answer
            if (userAnswer == correctAnswer) {
                // If the answer is correct, display positive feedback and increment the score
                txtFeedback.text = "Correct"
                txtFeedback.setTextColor(getColor(android.R.color.holo_green_dark))
                score++
            } else {
                // If the answer is wrong, display negative feedback
                txtFeedback.text = "Incorrect"

                // If there's a specific explanation for this question, add it to the feedback
                if (explanations[currentQuestionIndex].isNotEmpty()) {
                    txtFeedback.text = "${txtFeedback.text}\n${explanations[currentQuestionIndex]}"
                }

            }
        }
    }
}













