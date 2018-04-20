package edu.andrews.cptr252.ksolomon.cardlet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Created by solomonjkim on 4/19/18.
 */

public class Card {
    private String mQuestion;
    private int mAnswer;
    private UUID mId;

    private static final String JSON_ID = "id";
    private static final String JSON_QUESTION = "Question";
    private static final String JSON_ANSWER = "Answer";

    public Bug(UUID id){
        mId = id;
    }
    public Card(JSONObject json) throws JSONException{
        mQuestion = json.getString(JSON_QUESTION);
        mAnswer = json.getInt(JSON_ANSWER);
        mId = UUID.fromString(json.getString(JSON_ID);
    }

    public Card(){

        this(UUID.randomUUID());
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();

        json.put(JSON_ID, mId.toString());
        json.put(JSON_QUESTION, mQuestion);
        json.put(JSON_ANSWER, mAnswer);

        return json;
    }

    public void setQuestion(int question) {mQuestion = question;}

    public String getQuestion(){
        return mQuestion;
    }

    public UUID getID(){
        return mId;
    }

    public int getAnswer() { return mAnswer;}

    public void setAnswer(int answer) {mAnswer = answer;}
}
