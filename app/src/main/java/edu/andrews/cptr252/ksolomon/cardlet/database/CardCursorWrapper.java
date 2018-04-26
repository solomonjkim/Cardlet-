package edu.andrews.cptr252.ksolomon.cardlet.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import edu.andrews.cptr252.ksolomon.cardlet.Card;
import edu.andrews.cptr252.ksolomon.cardlet.database.CardDbSchema.CardTable;

/**
 * Created by solomonjkim on 4/22/18.
 */

public class CardCursorWrapper extends CursorWrapper{
    public CardCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /**
     * Extract the question information from a query.
     * @return Question that resulted from query
     */
    public Card getCard() {
        String uuidString = getString(getColumnIndex(CardTable.Cols.UUID));
        String title = getString(getColumnIndex(CardTable.Cols.TITLE));
        int isTrue = getInt(getColumnIndex(CardTable.Cols.TRUE));
        int isFalse = getInt(getColumnIndex(CardTable.Cols.FALSE));

        Card card = new Card(UUID.fromString(uuidString));
        card.setQuestion(title);
        card.setYes(isTrue != 0);
        card.setNo(isFalse != 0);
        return card;
    }
}
