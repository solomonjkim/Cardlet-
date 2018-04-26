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
 * Used to manage and initialize the fragment
 * Created by solomonjkim on 4/19/18.
 */

public class CardDetailsActivity extends AppCompatActivity implements CardDetailsFragment.Callbacks {

    /**
     * Necessary constructor, but can leave blank
     * @param card
     */
    public void onCardUpdated(Card card) {
    }

    private ViewPager mViewPager; //Used for managing the fragment

    private ArrayList<Card> mCards; //Storage for accessing and saving the created cards

    /**
     * This method is used to manage the fragment call when this activity is first created
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);

        mViewPager.setId(R.id.viewPager);

        setContentView(mViewPager);

        mCards = Cardlet.getInstance(this).getCards();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            /**
             * Transitions the data to the fragments
             * @param position
             * @return
             */
            @Override
            public Fragment getItem(int position) {
                Card card = mCards.get(position);
                return CardDetailsFragment.newInstance(card.getId());
            }

            /**
             * Just gets how many cards are in the array
             * @return
             */
            @Override
            public int getCount() {
                return mCards.size();
            }
        });

        UUID CardId = (UUID) getIntent().getSerializableExtra(CardDetailsFragment.EXTRA_CARD_ID);


        for (int i = 0; i < mCards.size(); i++){
            if(mCards.get(i).getId().equals(CardId)){
                mViewPager.setCurrentItem(i);
                break;
            }

        }

    }


}
