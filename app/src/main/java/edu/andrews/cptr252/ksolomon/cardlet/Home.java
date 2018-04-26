package edu.andrews.cptr252.ksolomon.cardlet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by solomonjkim on 4/23/18.
 */

public class Home extends AppCompatActivity{
    private Button mListButton;
    private Button mStartButton;
    /**
     * Creates the display for the home page
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get the view
        setContentView(R.layout.activity_home);
        mStartButton = findViewById(R.id.startButton);
        mListButton = findViewById(R.id.listButton);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, QuizActivity.class));
            }
        });
        mListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, CardletActivity.class));
            }
        });
    }
}
