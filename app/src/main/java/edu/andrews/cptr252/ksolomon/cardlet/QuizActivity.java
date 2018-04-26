package edu.andrews.cptr252.ksolomon.cardlet;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by solomonjkim on 4/12/18.
 */

public class QuizActivity extends AppCompatActivity{
    private int mCurrentIndex = 0;
    private TextView mCardTextView;
    private Button mNoButton;
    private Button mYesButton;
    private Button mNext;
    private Button mBack;

    private static final String KEY_INDEX ="index";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        Cardlet cardlet = Cardlet.getInstance(this);
        ArrayList<Card> cards = cardlet.getCards();
        mCardTextView = findViewById(R.id.mCardTextView);

        final Card[] mCardVault = cards.toArray(new Card[cards.size()]);
        String card = mCardVault[mCurrentIndex].getQuestion();
        mCardTextView.setText(card);

        mYesButton = findViewById(R.id.yesButton);
        mNoButton = findViewById(R.id.noButton);

        mYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                if(mCardVault[mCurrentIndex].isYes()){

                    Toast.makeText(QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
                }

                else if (mCardVault[mCurrentIndex].isNo()){
                    Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mNoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mCardVault[mCurrentIndex].isNo()) {
                    Toast.makeText(QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();

                } else if (mCardVault[mCurrentIndex].isYes()) {
                    Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                }
            }

        });

        mNext = findViewById(R.id.nextbutton);
        mNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mCardVault.length;
                String question = mCardVault[mCurrentIndex].getQuestion();
                mCardTextView.setText(question);
            }
        });

        mBack = findViewById(R.id.backbutton);
        mBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mCurrentIndex > 0){
                    mCurrentIndex = (mCurrentIndex -1) % mCardVault.length;
                    String question = mCardVault[mCurrentIndex].getQuestion();
                    mCardTextView.setText(question);
                } else if(mCurrentIndex == 0){
                    mCurrentIndex = (mCardVault.length -1) % mCardVault.length;
                    String question = mCardVault[mCurrentIndex].getQuestion();
                    mCardTextView.setText(question);
                }
            }
        });

        }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

}
