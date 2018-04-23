package edu.andrews.cptr252.ksolomon.cardlet;

import android.database.Cursor;

import java.util.UUID;

/**
 * Created by solomonjkim on 4/22/18.
 */

public class CardCursorWrapper {
    public CardCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /**
     * Extract the question information from a query.
     * @return Question that resulted from query
     */
    public Card getCard() {
        String uuidString = getString(getColumnIndex(CardDbSchema.CardTable.Cols.UUID));
        String title = getString(getColumnIndex(CardDbSchema.CardTable.Cols.TITLE));
        int isTrue = getInt(getColumnIndex(CardDbSchema.CardTable.Cols.TRUE));
        int isFalse = getInt(getColumnIndex(CardDbSchema.CardTable.Cols.FALSE));

        Card card = new Card(UUID.fromString(uuidString));
        card.setQuestion(title);
        card.setYes(isYes != 0);
        card.setFalse(isFalse != 0);
        return card;
    }
}
