
package com.example.hammerhit;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    Button bt1,bt2,bt3,bt4,bt5,bt6,bt7;
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

        //Object Creation
        Object b1 = bt1.getBackground();
        Object b2 = bt2.getBackground();
        Object b3 = bt3.getBackground();
        Object b4 = bt4.getBackground();
        Object b5 = bt5.getBackground();
        Object b6 = bt6.getBackground();
        Object b7 = bt7.getBackground();



        // Create a common OnClickListener for all buttons
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Button button = (Button) v;

                // Display a toast message
                Toast.makeText(MainActivity.this, button.getText() + " clicked!", Toast.LENGTH_SHORT).show();

                // Change the button color
                button.setBackgroundColor(Color.YELLOW);

                // Use a Handler to change the color back after 2 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Revert the button color to the original (default)
                        button.setBackground((Drawable) b1);
                        // Replace with your button's original color

                    }
                }, 1000); // 2000 milliseconds = 2 seconds
            }
        };

        // Set the OnClickListener for all buttons
        for (Button button : Arrays.asList(bt1, bt2, bt3, bt4, bt5, bt6, bt7)) {
            button.setOnClickListener(buttonClickListener);
        }


    }
}
