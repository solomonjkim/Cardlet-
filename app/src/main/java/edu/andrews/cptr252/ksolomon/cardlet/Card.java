package edu.andrews.cptr252.ksolomon.cardlet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by solomonjkim on 4/19/18.
 */

public class Card {
    private String mQuestion; //question that will be used
    private boolean mYes;
    private boolean mNo; //answer to the question
    private UUID mId; //id for storing in database

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "Question";
    private static final String JSON_YES = "True";
    private static final String JSON_NO = "False";

    public Card(UUID id){ mId = id;}

    public Card(JSONObject json) throws JSONException{
        mQuestion = json.optString(JSON_TITLE);
        mYes = json.getBoolean(JSON_YES);
        mNo = json.getBoolean(JSON_NO);
        mId = UUID.fromString(json.getString(JSON_ID);
    }

    public Card(){

        this(UUID.randomUUID());
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();

        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mQuestion);
        json.put(JSON_YES, mYes);
        json.put(JSON_NO, mNo);

        return json;
    }

    public void setQuestion(String question) {mQuestion = question;}

    public String getQuestion(){
        return mQuestion;
    }

    public UUID getID(){
        return mId;
    }

    public boolean isYes() { return mYes;}

    public void setYes(boolean yet) {mYes = yet; }

    public Boolean isNo(){ return mNo; }

    public void setNo(boolean not){ mNo = not;}
}
