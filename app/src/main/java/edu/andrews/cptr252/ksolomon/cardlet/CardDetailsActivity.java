package edu.andrews.cptr252.ksolomon.cardlet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by solomonjkim on 4/19/18.
 */

public class CardDetailsActivity  extends AppCompatActivity implements CardDetailsFragment.Callbacks {
    private ViewPager mViewPager;

    private ArrayList<Card> mCards;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);

        mViewPager.setId(R.id.viewPager);

        setContentView(mViewPager);

        mCards = Cardlet.getInstance(this).getCards();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Card card = mCards.get(position);
                return CardDetailsFragment.newInstance(card.getID());
            }

            @Override
            public int getCount() {
                return mCards.size();
            }
        });

        UUID bugId = (UUID) getIntent().getSerializableExtra(CardDetailsFragment.EXTRA_BUG_ID);


        for (int i = 0; i < mCards.size(); i++){
            if(mCards.get(i).getID().equals(bugId)){
                mViewPager.setCurrentItem(i);
                break;
            }

        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int i, float v, int i2){

            }

            @Override
            public void onPageSelected(int position){
                Card card = mCards.get(position);
                if(card.getQuestion()!= null){
                    setQuestion(card.getQuestion());
                }
            }

            @Override
            public void onPageScrollStateChanged(int i){

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bug_tracker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCardUpdated(Card card){

    }
}
