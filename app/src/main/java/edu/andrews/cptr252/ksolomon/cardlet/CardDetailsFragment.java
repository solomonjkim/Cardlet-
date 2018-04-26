package edu.andrews.cptr252.ksolomon.cardlet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by solomonjkim on 4/19/18.
 */

public class CardDetailsFragment extends Fragment {
    public static final String EXTRA_CARD_ID = "edu.andrews.cptr252.ksolomon.cardlet.card_id";
    public static final String TAG = "CardDetailsFragment";

    private Card mCard;
    private EditText mQuestioninput;
    private CheckBox mTrue;
    private CheckBox mFalse;

    public interface Callbacks {
        void onCardUpdated(Card card);
    }
    private Callbacks mCallbacks;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mCallbacks = (Callbacks)context;
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mCallbacks = null;
    }


    public void updateCard(){
        Cardlet.getInstance(getActivity()).updateCard(mCard);
        mCallbacks.onCardUpdated(mCard);
    }

    @Override
    public void onPause() {
        super.onPause();
        Cardlet.getInstance(getActivity()).updateCard(mCard);
    }

    public static CardDetailsFragment newInstance(UUID CardId){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CARD_ID, CardId);
        CardDetailsFragment fragment = new CardDetailsFragment();

        fragment.setArguments(args);
        return fragment;
    }

    public CardDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        UUID CardId = (UUID)getArguments().getSerializable(EXTRA_CARD_ID);

        mCard = Cardlet.getInstance(getActivity()).getCard(CardId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        PackageManager pm = getActivity().getPackageManager();

        View v = inflater.inflate(R.layout.fragment_card_tracker, container, false);


        mQuestioninput = (EditText) v.findViewById(R.id.card_question);
        mQuestioninput.setText(mCard.getQuestion());
        mQuestioninput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mCard.setQuestion(s.toString());

                Log.d(TAG, "Title changed to " + mCard.getQuestion());

                updateCard();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTrue = v.findViewById(R.id.true_CheckBox);
        mTrue.setChecked(mCard.isYes());
        mTrue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCard.setYes(isChecked);
                Log.d(TAG, "Question is True: " + isChecked);
                updateCard();

                if(isChecked){
                    mFalse.setChecked(false);
                }
            }
        });

        mFalse = v.findViewById(R.id.false_CheckBox);
        mFalse.setChecked(mCard.isNo());
        mFalse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCard.setNo(isChecked);
                Log.d(TAG, "Question is false: " + isChecked);
                updateCard();

                if(isChecked){
                    mTrue.setChecked(false);
                }
            }
        });

        return v;

    }

}
