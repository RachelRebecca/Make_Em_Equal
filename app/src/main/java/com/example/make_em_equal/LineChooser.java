package com.example.make_em_equal;

import com.google.gson.Gson;

import java.util.Random;

public class LineChooser {

    Integer[] num1aList;
    Integer[] num1bList;
    Integer[] num2aList;
    Integer[] num2bList;
    Double[] answerList;
    int lineChoice;

    public LineChooser() {
        num1aList = new Integer[]{1, 3, 7, 2, 1, 2, 1, 7, 9, 5, 8, 5, 8, 8, 6, 6, 9, 7, 5, 2};
        num1bList = new Integer[]{2, 4, 1, 1, 2, 3, 6, 0, 2, 5, 3, 3, 2, 3, 2, 2, 5, 2, 4, 1};
        num2aList = new Integer[]{4, 4, 1, 4, 8, 4, 8, 0, 3, 4, 2, 6, 6, 4, 1, 3, 1, 8, 8, 3};
        num2bList = new Integer[]{2, 3, 6, 3, 9, 6, 2, 7, 4, 4, 4, 9, 4, 6, 3, 1, 4, 9, 1, 7};
        answerList = new Double[]{2.0, 12.0, 7.0, 1.0, -1.0, .6666, 16.0, 7.0, 7.0, 1.0, 24.0,
                15.0, 10.0, 24.0, 3.0, 3.0, 4.0, 72.0, 9.0, 21.0};

        Random rand = new Random();
        lineChoice = rand.nextInt(num1aList.length);
    }

    public int getLineChoice(){
        return this.lineChoice;
    }

    public int getNum1A(){
        return num1aList[getLineChoice()];
    }
    public int getNum1B(){
        return num1bList[getLineChoice()];
    }
    public int getNum2A(){
        return num2aList[getLineChoice()];
    }
    public int getNum2B(){
        return num2bList[getLineChoice()];
    }
    public double getAnswer(){
        return answerList[getLineChoice()];
    }



    //code came from 13 Stones App
    /**
     * Reverses the game object's serialization as a String
     * back to a game object
     *
     * @param json The serialized String of the game object
     * @return The game object
     */
    public static LineChooser getGameFromJSON(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, LineChooser.class);
    }

    /**
     * Serializes the game object to a JSON-formatted String
     *
     * @param obj Game Object to serialize
     * @return JSON-formatted String
     */
    public static String getJSONFromGame(LineChooser obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public String getJSONFromCurrentGame() {
        return getJSONFromGame(this);
    }
}

