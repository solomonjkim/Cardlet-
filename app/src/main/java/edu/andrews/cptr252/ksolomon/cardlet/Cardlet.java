package edu.andrews.cptr252.ksolomon.cardlet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by solomonjkim on 4/12/18.
 */

public class Cardlet {

    private static final String TAG = "Cardlet";
    private Context mContext;

    public void addBug(Card card){
        ContentValues values = getContentValues(card);
        mDatabase.insert(BugTable.NAME, null, values);
    }

    public void updateBug(Card card){
        String uuidString = card.getID().toString();
        ContentValues values = getContentValues(bug);

        mDatabase.update(BugTable.NAME, values, BugTable.Cols.UUID + " =? ", new String[]{uuidString});
    }
    private BugList(Context appContext){

        mAppContext = appContext.getApplicationContext();
        mDatabase = new BugDbHelper(mAppContext).getWritableDatabase();

    }

    public static BugList getInstance(Context c) {
        if (sOurInstance == null){
            sOurInstance = new BugList(c.getApplicationContext());
        }
        return sOurInstance;
    }

    public ArrayList<Bug> getBugs() {
        ArrayList<Bug> bugs = new ArrayList<>();
        BugCursorWrapper cursor = queryBugs(null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                bugs.add(cursor.getBug());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return bugs;
    }


    private BugList() {
    }

    public Bug getBug(UUID id){
        BugCursorWrapper cursor = queryBugs(BugTable.Cols.UUID + " =? ", new String[] { id.toString()});

        try{
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getBug();
        } finally {
            cursor.close();
        }
    }

    public void deleteBug(Bug bug){

        String uuidString = bug.getID().toString();
        mDatabase.delete(BugTable.NAME, BugTable.Cols.UUID + " =? ", new String[] {uuidString});
    }

    public static ContentValues getContentValues(Bug bug){
        ContentValues values = new ContentValues();
        values.put(BugTable.Cols.UUID, bug.getID().toString());
        values.put(BugTable.Cols.TITLE, bug.getTitle());
        values.put(BugTable.Cols.DESCRIPTION, bug.getDescription());
        values.put(BugTable.Cols.DATE, bug.getDate().getTime());
        values.put(BugTable.Cols.SOLVED, bug.isSolved() ? 1: 0);

        return values;
    }

    private BugCursorWrapper queryBugs(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                BugTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new BugCursorWrapper(cursor);
    }

    public File getPhotoFile(Bug bug){
        File externalFilesDir = mAppContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if(externalFilesDir == null) {
            return null;
        }
        return new File(externalFilesDir, bug.getPhotoFilename());
    }


}
