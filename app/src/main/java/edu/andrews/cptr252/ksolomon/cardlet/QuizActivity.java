package edu.andrews.cptr252.ksolomon.cardlet;

import android.support.v4.app.Fragment;

/**
 * Created by solomonjkim on 4/12/18.
 */

public class QuizActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment(){
        return new QuizActivityFragment();
    }
}
