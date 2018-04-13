package edu.andrews.cptr252.ksolomon.cardlet;

/**
 * Created by solomonjkim on 4/12/18.
 */

public class Cardlet {

        private int mQuestion;
        private int mAnswer;

    public Cardlet(int Question, int Answer){
        mQuestion = Question;
        mAnswer = Answer;
    }

    public int getQuestion()   {return mQuestion;}
    public void setQuestion(int question) {mQuestion = question;}

    public int getAnswer()   {return mAnswer;}
    public void setAnswer(int answer) {mAnswer = answer;}

}
