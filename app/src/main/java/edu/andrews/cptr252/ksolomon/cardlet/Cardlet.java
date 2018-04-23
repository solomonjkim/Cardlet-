package edu.andrews.cptr252.ksolomon.cardlet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by solomonjkim on 4/12/18.
 */

public class Cardlet {

    private static final String TAG = "Cardlet";
    private Context mAppContext;
    private static Cardlet sOurInstance;
    private SQLiteDatabase mDatabase;

    public void addCard(Card card){
        ContentValues values = getContentValues(card);
        mDatabase.insert(CardDbSchema.CardTable.NAME, null, values);
    }

    public void updateCard(Card card){
        String uuidString = card.getID().toString();
        ContentValues values = getContentValues(card);

        mDatabase.update(CardDbSchema.CardTable.NAME, values, CardDbSchema.CardTable.Cols.UUID + " =? ", new String[]{uuidString});
    }
    private Cardlet(Context appContext){

        mAppContext = appContext.getApplicationContext();
        mDatabase = new CardDbHelper(mAppContext).getWritableDatabase();

    }

    public static Cardlet getInstance(Context c) {
        if (sOurInstance == null){
            sOurInstance = new Cardlet(c.getApplicationContext());
        }
        return sOurInstance;
    }

    public ArrayList<Card> getCards() {
        ArrayList<Card> cards = new ArrayList<>();
        CardCursorWrapper cursor = queryCards(null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                cards.add(cursor.getCards());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return cards;
    }


    private Cardlet() {
    }

    public Card getCard(UUID id){
        CardCursorWrapper cursor = queryCards(CardDbSchema.CardTable.Cols.UUID + " =? ", new String[] { id.toString()});

        try{
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCard();
        } finally {
            cursor.close();
        }
    }

    public void deleteCard(Card card){

        String uuidString = card.getID().toString();
        mDatabase.delete(CardDbSchema.CardTable.NAME, CardDbSchema.CardTable.Cols.UUID + " =? ", new String[] {uuidString});
    }

    public static ContentValues getContentValues(Card card){
        ContentValues values = new ContentValues();
        values.put(CardDbSchema.CardTable.Cols.UUID, card.getID().toString());
        values.put(CardDbSchema.CardTable.Cols.TITLE, card.getQuestion());
        values.put(CardDbSchema.CardTable.Cols.FALSE, card.isNo());
        values.put(CardDbSchema.CardTable.Cols.TRUE, card.isYes());

        return values;
    }

    private CardCursorWrapper queryBugs(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                CardDbSchema.CardTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CardCursorWrapper(cursor);
    }


}
