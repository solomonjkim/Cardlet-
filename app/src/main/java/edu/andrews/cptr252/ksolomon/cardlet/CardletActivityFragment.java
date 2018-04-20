package edu.andrews.cptr252.ksolomon.cardlet;

import android.content.Context;
import android.support.v4.app.Fragment;
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
 * A placeholder fragment containing a simple view.
 */
public class CardletActivityFragment extends Fragment {


    private CardAdapter mAdapter;

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

    public void updateUI(){
        Cardlet cardlet = Cardlet.getInstance(getActivity());
        ArrayList<Card> cards = Cardlet.getCards();

        if(mAdapter == null){
            mAdapter = new CardAdapter(cards);
            setListAdapter(mAdapter);
        } else {
            mAdapter.setCards(cards);

            mAdapter.notifyDataSetChanged();
        }
    }

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
                inflater.inflate(R.menu.bug_list_item_context, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_item_delete_bug:

                        Cardlet cardlet = Cardlet.getInstance(getActivity());


                        for(int i = mAdapter.getCount() - 1; i >= 0; i--){
                            if(getListView().isItemChecked(i)){
                                cardlet.deleteBug(mAdapter.getItem(i));
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


    private class CardAdapter extends ArrayAdapter<Card> {

        public void setBugs(ArrayList<Card> cards){
            clear();
            addAll(cards);
        }

        public CardAdapter(ArrayList<Card> cards) {
            super(getActivity(), 0, cards);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            if(null == convertView){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_bug, null);
            }

            Card card = getItem(position);

            TextView QuestionTextView = convertView.findViewById(R.id.bug_list_item_titleTextView);
            titleTextView.setText(card.getQuestion());

            TextView AnswerTextView = convertView.findViewById(R.id.bug_list_item_dateTextView);
           if(card.getAnswer() == 1){ dateTextView.setText("True");} if(card.getAnswer() == 0){ dateTextView.setText("False");}


            return convertView;
        }
    }

    private static final String TAG = "BugListFragment";

    private ArrayList<Card> mCards;

    public CardletActivityFragment() {
        // Required empty public constructor
    }

    private void addBug(){
        Card card = new Card();
        cardlet.getInstance(getActivity()).addCard(card);

        mCallbacks.onCardSelected(card);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_bug_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_item_add_bug: addBug();  return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.bug_list_label);
        mCards = cardlet.getInstance(getActivity()).getCards();

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
