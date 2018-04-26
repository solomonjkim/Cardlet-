package edu.andrews.cptr252.ksolomon.cardlet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by solomonjkim on 4/19/18.
 * Used to create the cards
 */

public class Card {
    private String mQuestion; //question that will be used
    private boolean mYes; //answer to the question
    private boolean mNo; //answer to the question
    private UUID mId; //id for storing in database

    private static final String JSON_ID = "id"; //for implementation of the database
    private static final String JSON_TITLE = "Question";
    private static final String JSON_YES = "True";
    private static final String JSON_NO = "False";

    /**
     * Constructor used to get the cards by their Id numbers
     * @param id
     */

    public Card(UUID id){ mId = id;}

    /**
     * Used to create the card objects for the database
     * @param json
     * @throws JSONException
     */

    public Card(JSONObject json) throws JSONException{
        mQuestion = json.optString(JSON_TITLE);
        mYes = json.getBoolean(JSON_YES);
        mNo = json.getBoolean(JSON_NO);
        mId = UUID.fromString(json.getString(JSON_ID));
    }

    /**
     * Assigns a random id for a card
     */

    public Card(){

        this(UUID.randomUUID());
    }

    /**
     * Sends the JSON information from the Card to the database. Returns the json object.
     * @return
     * @throws JSONException
     */

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();

        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mQuestion);
        json.put(JSON_YES, mYes);
        json.put(JSON_NO, mNo);

        return json;
    }

    /**
     * Used to set the questions for the cards
     */
    public void setQuestion(String question) {mQuestion = question;}

    /**
     * Used to get the questions for each card
     * @return
     */
    public String getQuestion(){
        return mQuestion;
    }

    /**
     * Used to get the id for each card
     * @return
     */
    public UUID getId(){
        return mId;
    }

    /**
     * Checks if the answer is true
     * @return
     */
    public boolean isYes() { return mYes;}

    /**
     * Sets the answer to true
     * @param yet
     */
    public void setYes(boolean yet) {mYes = yet; }

    /**
     * Checks if the answer is false
     * @return
     */
    public Boolean isNo(){ return mNo; }

    /**
     * Sets the answer to false
     * @param not
     */
    public void setNo(boolean not){ mNo = not;}
}
