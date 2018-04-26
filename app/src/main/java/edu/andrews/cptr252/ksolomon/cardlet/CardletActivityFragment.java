package edu.andrews.cptr252.ksolomon.cardlet;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This fragment manages the list design and individual cards in the list
 * Created by solomonjkim on 4/12/18.
 */
public class CardletActivityFragment extends ListFragment {


    private CardAdapter mAdapter;
    private ArrayList<Card> mCards;
    private CheckBox mYesCheckBox;
    private CheckBox mNoCheckBox;
    private static final String TAG = "CardletActivityFragment";

//all the same as in the CardDetailsFragment
    public interface Callbacks {
        void onCardSelected(Card card);
    }

    private Callbacks mCallbacks;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks)context;
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mCallbacks = null;
    }

    /**
     * Updates the cards based on the ArrayList of cards. The adapter allows for the data
     * to be displayed as a list
     */
    public void updateUI(){
        Cardlet cardlet = Cardlet.getInstance(getActivity());
        ArrayList<Card> cards = cardlet.getCards();

        if(mAdapter == null){
            mAdapter = new CardAdapter(cards);
            setListAdapter(mAdapter);
        } else {
            mAdapter.setCards(cards);

            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * When created, the view creates a new listview and connects the function of adding a new card to the list from
     * the menu
     * @param inflater
     * @param parent
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = super.onCreateView(inflater, parent, savedInstanceState);

        updateUI();

        ListView listView = v.findViewById(android.R.id.list);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }
            public boolean onCreateActionMode(ActionMode mode, Menu menu){
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.card_list_item_context, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            /**
             * When the card is clicked the card can be deleted
             * @param mode
             * @param item
             * @return
             */
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_item_delete_question:

                        Cardlet cardlet = Cardlet.getInstance(getActivity());


                        for(int i = mAdapter.getCount() - 1; i >= 0; i--){
                            if(getListView().isItemChecked(i)){
                                cardlet.deleteCard(mAdapter.getItem(i));
                            }
                        }

                        mode.finish();
                        updateUI();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        return v;
    }

    /**
     * This class allows for the Card ArrayList to be accessed and creates the ArrayList
     */
    private class CardAdapter extends ArrayAdapter<Card> {

        public CardAdapter(ArrayList<Card> cards){
            super(getActivity(), 0, cards);
        }

        public void setCards(ArrayList<Card> cards){
            clear();
            addAll(cards);
        }

        /**
         * This method allows for each card to be displayed on the list
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            if(null == convertView){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_card, null);
            }

            Card card = getItem(position);

            TextView titleTextView = convertView.findViewById(R.id.question_Text);
            titleTextView.setText(card.getQuestion());

           CheckBox YesCheckBox = convertView.findViewById(R.id.yesCheckBox);
            YesCheckBox.setChecked(card.isYes());

            CheckBox NoCheckBox = convertView.findViewById(R.id.noCheckBox);
            NoCheckBox.setChecked(card.isNo());

            return convertView;
        }
    }

    public CardletActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Adds a card to the data set made in Cardlet
     */
    private void addCard(){
        Card card = new Card();
        Cardlet.getInstance(getActivity()).addCard(card);

        mCallbacks.onCardSelected(card);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_cardlet, menu);
    }

    /**
     * Allows for the user to add a card to the list with the add button on the menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_item_add_card: addCard();  return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.card_list_label);
        mCards = Cardlet.getInstance(getActivity()).getCards();

        mAdapter = new CardAdapter(mCards);
        setListAdapter(mAdapter);
    }


    public void onListItemClick(ListView l, View v, int position, long id){
        Card card = (Card)(getListAdapter()).getItem(position);
        Log.d(TAG, card.getQuestion() + "was clicked");

        mCallbacks.onCardSelected(card);
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }
}
