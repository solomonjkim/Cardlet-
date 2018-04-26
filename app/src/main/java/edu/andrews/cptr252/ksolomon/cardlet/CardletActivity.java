package edu.andrews.cptr252.ksolomon.cardlet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

/**
 * This class creates the list activity that manages the fragment
 * Created by solomonjkim on 4/12/18.
 */

public class CardletActivity extends SingleFragmentActivity
        implements CardletActivityFragment.Callbacks {

    /**
     * When the card is selected the activity initializes the details activity so then the user
     * can see the card details closer
     * @param card
     */
    public void onCardSelected(Card card){
        if(findViewById(R.id.detailFragmentContainer) == null) {
            Intent i = new Intent(this, CardDetailsActivity.class);
            i.putExtra(CardDetailsFragment.EXTRA_CARD_ID, card.getId());
            startActivityForResult(i, 0);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
            Fragment newDetail = CardDetailsFragment.newInstance(card.getId());

            if (oldDetail != null){
                ft.remove(oldDetail);
            }
            ft.add(R.id.detailFragmentContainer, newDetail);
            ft.commit();
        }
    }
    public void onCardUpdated(Card card){
        FragmentManager fm = getSupportFragmentManager();
        CardletActivityFragment listFragment = (CardletActivityFragment) fm.findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }

    @Override
    protected Fragment createFragment(){
        return new CardletActivityFragment();
    }

    /**
     * Getsthe masterdetail activity that allows for the two pane design
     * @return
     */
    @Override
    protected int getLayoutResId(){
        return R.layout.activity_masterdetail;
    }

}