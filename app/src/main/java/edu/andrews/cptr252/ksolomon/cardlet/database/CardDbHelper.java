package edu.andrews.cptr252.ksolomon.cardlet.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by solomonjkim on 4/22/18.
 */

public class CardDbHelper extends SQLiteOpenHelper{
    /** Current DB version. Increment as DB structure changes */
    private static final int VERSION = 1;
    /** DB filename */
    private static final String DATABASE_NAME = "cardDb.db";

    public CardDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * Creating the database
     * @param db will contain the newly created database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Execute SQL command to create a new question database
        db.execSQL("create table " + CardDbSchema.CardTable.NAME + "(" +
                " _id integer primary key autoincrement," +
                CardDbSchema.CardTable.Cols.UUID + ", " +
                CardDbSchema.CardTable.Cols.TITLE + ", " +
                CardDbSchema.CardTable.Cols.TRUE + ", " +
                CardDbSchema.CardTable.Cols.FALSE +
                ")");
    }

    /**
     * Previous DB is older and needs to be upgraded to current version.
     * @param db is the database that needs to be upgraded
     * @param oldVersion is the version number of the DB
     * @param newVersion is the version the DB should be upgraded to.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Later put code to update DB
    }

}
