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

public class CardletActivity extends SingleFragmentActivity
        implements CardletActivityFragment.Callbacks, CardletActivityFragment.Callbacks {

    public void onBugSelected(Card card){
        if(findViewById(R.id.detailFragmentContainer) == null) {
            Intent i = new Intent(this, CardDetailsActivity.class);
            i.putExtra(CardDetailsFragment.EXTRA_BUG_ID, card.getID());
            startActivityForResult(i, 0);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
            Fragment newDetail = CardDetailsFragment.newInstance(Card.getID());

            if (oldDetail != null){
                ft.remove(oldDetail);
            }
            ft.add(R.id.detailFragmentContainer, newDetail);
            ft.commit();
        }
    }
    public void onBugUpdated(Card card){
        FragmentManager fm = getSupportFragmentManager();
        CardletActivityFragment listFragment = (CardletActivityFragment) fm.findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }

    @Override
    protected Fragment createFragment(){
        return new CardletActivityFragment();
    }

    @Override
    protected int getLayoutResId(){
        return R.layout.activity_masterdetail;
    }

}