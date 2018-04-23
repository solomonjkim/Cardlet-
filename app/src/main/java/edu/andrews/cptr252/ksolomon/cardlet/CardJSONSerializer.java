package edu.andrews.cptr252.ksolomon.cardlet;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by solomonjkim on 4/22/18.
 */

public class CardJSONSerializer {
    private Context mContext;
    private String mFilename;

    public CardJSONSerializer(Context c, String f){
        mContext = c;
        mFilename = f;
    }

    public ArrayList<Card> loadCards() throws IOException, JSONException {
        ArrayList<Card> cards = new ArrayList<>();
        BufferedReader reader = null;
        try{
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null){
                jsonString.append(line);
            }
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            for(int i = 0; i<array.length(); i++){
                cards.add(new Card(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e){

        } finally {
            if(reader != null) reader.close();
        }
        return cards;
    }

    public void saveCards(ArrayList<Card> cards) throws JSONException, IOException{

        JSONArray array = new JSONArray();
        for (Card card : cards) array.put(card.toJSON());

        Writer writer = null;
        try{
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if(writer != null) writer.close();
        }
    }
}
