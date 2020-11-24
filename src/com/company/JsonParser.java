package com.company;

import com.company.models.JSONObject;
import com.company.parser.JSONParser;

public class JsonParser {

    public  void parse(String jsonCommand , Snake s1 , Snake s2 , Snake s3 ,Snake s4 )  {
        JSONObject parseJson = (JSONObject)(new JSONParser()).parse(jsonCommand);
        String action = parseJson.getString("action");

        if(action.equals("move")){
            Move(parseJson , s1 , s2 , s3 , s4 );
        }
    }

    public void Move(JSONObject parseJson , Snake snake1 , Snake snake2 , Snake snake3 , Snake snake4)  {
        String snakeName = parseJson.getString("snakeName");
        String direction = parseJson.getString("direction");
        direction = capitalize(direction);


        switch (snakeName){
            case "snake1":
                switch (direction) {
                    case "Left":
                        snake1.moveLeft();
                        break;
                    case "Right":
                        snake1.moveRight();
                        break;
                    case "Up":
                        snake1.moveUp();
                        break;
                    case "Down":
                        snake1.moveDown();
                        break;
                }
                break;
            case "snake2":
                switch (direction) {
                    case "Left":
                        snake2.moveLeft();
                        break;
                    case "Right":
                        snake2.moveRight();
                        break;
                    case "Up":
                        snake2.moveUp();
                        break;
                    case "Down":
                        snake2.moveDown();
                        break;
                }
                break;
            case "snake3":
                switch (direction) {
                    case "Left":
                        snake3.moveLeft();
                        break;
                    case "Right":
                        snake3.moveRight();
                        break;
                    case "Up":
                        snake3.moveUp();
                        break;
                    case "Down":
                        snake3.moveDown();
                        break;
                }
                break;
            case "snake4":
                switch (direction) {
                    case "Left":
                        snake4.moveLeft();
                        break;
                    case "Right":
                        snake4.moveRight();
                        break;
                    case "Up":
                        snake4.moveUp();
                        break;
                    case "Down":
                        snake4.moveDown();
                        break;
                }
                break;
        }
    }




    public  String capitalize(String str){
        if(str == null || str.isEmpty()) {
            return str;
        }
        str = str.toLowerCase();
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
