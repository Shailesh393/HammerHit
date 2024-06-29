package com.example.hammerhit;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt10, startNew, targetButton, timerButton;
    private int score = 0;
    private Drawable originalButtonBackground;
    private MediaPlayer mediaPlayer;
    private Random random;
    private int targetNumber;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning; // Flag to track if the timer is running

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        bt3 = findViewById(R.id.bt3);
        bt4 = findViewById(R.id.bt4);
        bt5 = findViewById(R.id.bt5);
        bt6 = findViewById(R.id.bt6);
        bt7 = findViewById(R.id.bt7);
        bt8 = findViewById(R.id.bt8);
        bt9 = findViewById(R.id.bt9);
        bt10 = findViewById(R.id.bt10);
        startNew = findViewById(R.id.startNew);
        targetButton = findViewById(R.id.targetButton);
        timerButton = findViewById(R.id.timerButton);

        // Initialize the score TextView
        final TextView scoreTextView = findViewById(R.id.scoreTextView);

        // Store the original background of the buttons
        originalButtonBackground = bt1.getBackground();

        // Initialize MediaPlayer with the sound file
        // mediaPlayer = MediaPlayer.create(this, R.raw.click_sound);

        // Initialize Random
        random = new Random();

        // Set an initial random target number and shuffle button texts
        setRandomTargetAndShuffleButtons();

        // Start the timer
        startTimer();

        // Create a common OnClickListener for all buttons
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning) {
                    final Button button = (Button) v;

                    // Check if the button text matches the target number
                    if (Integer.parseInt(button.getText().toString()) == targetNumber) {
                        // Increment the score
                        score++;
                        // Update the score TextView
                        scoreTextView.setText("Score: " + score);

                        // Change the target number and shuffle button texts every time the score is increased
                        setRandomTargetAndShuffleButtons();

                        // Play the sound effect
                        if (mediaPlayer != null) {
                            mediaPlayer.start();
                        }

                        // Display a toast message
                        // Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Display a toast message for incorrect selection
                        // Toast.makeText(MainActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    }

                    // Change the button color
                    button.setBackgroundResource(R.drawable.button_background);

                    // Use a Handler to change the color back after 2 seconds
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Revert the button color to the original (default)
                            button.setBackground(originalButtonBackground);
                        }
                    }, 1000); // 1000 milliseconds = 1 second
                }
            }
        };

        // Set the OnClickListener for all buttons
        for (Button button : Arrays.asList(bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt10)) {
            button.setOnClickListener(buttonClickListener);
        }

        // Set the OnClickListener for the reset button
        startNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        // Set the OnClickListener for the target button
        targetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set a new random target number and shuffle button texts
                setRandomTargetAndShuffleButtons();
            }
        });
    }

    // Method to set a random target number and shuffle button texts
    private void setRandomTargetAndShuffleButtons() {
        // Set a random target number
        targetNumber = random.nextInt(10) + 1; // Random number between 1 and 10
        targetButton.setText("Target: " + targetNumber);

        // Create a list of numbers from 1 to 10
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            numbers.add(i);
        }

        // Shuffle the list
        Collections.shuffle(numbers);

        // Set the button texts with the shuffled numbers
        bt1.setText(String.valueOf(numbers.get(0)));
        bt2.setText(String.valueOf(numbers.get(1)));
        bt3.setText(String.valueOf(numbers.get(2)));
        bt4.setText(String.valueOf(numbers.get(3)));
        bt5.setText(String.valueOf(numbers.get(4)));
        bt6.setText(String.valueOf(numbers.get(5)));
        bt7.setText(String.valueOf(numbers.get(6)));
        bt8.setText(String.valueOf(numbers.get(7)));
        bt9.setText(String.valueOf(numbers.get(8)));
        bt10.setText(String.valueOf(numbers.get(9)));
    }

    // Method to start the timer
    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        isTimerRunning = true;
        countDownTimer = new CountDownTimer(60000, 1000) { // 60 seconds timer with 1-second interval
            public void onTick(long millisUntilFinished) {
                timerButton.setText("Time: " + millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                timerButton.setText("Time's up!");
                isTimerRunning = false;
                showTimeUpDialog();
            }
        };

        countDownTimer.start();
    }

    // Method to show a dialog when the time is up
    private void showTimeUpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Time's Up!")
                .setMessage("Start a new game?")
                .setPositiveButton("Yes", (dialog, which) -> resetGame())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // Method to reset the game
    private void resetGame() {
        // Reset the score to 0
        score = 0;
        // Update the score TextView
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("Score: " + score);

        // Set a new random target number and shuffle button texts
        setRandomTargetAndShuffleButtons();

        // Restart the timer
        startTimer();

        // Display a toast message
        // Toast.makeText(MainActivity.this, "Score reset!", Toast.LENGTH_SHORT).show();
    }

    // Release MediaPlayer when the activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
