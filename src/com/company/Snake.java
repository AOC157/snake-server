package com.company;

import java.util.ArrayList;

public class Snake {

    int size;
    int score;
    int snakeHeadX;
    int snakeHeadY;
    char direction;
    boolean isAlive;
    int type;
    ArrayList<Character> snakeBody;
    char lastBodyDirection;
    boolean lose;
    boolean win;

    public Snake(int snakeHeadX, int snakeHeadY, char direction, char body1, char body2,int type) {
        this.snakeHeadX = snakeHeadX;
        this.snakeHeadY = snakeHeadY;
        this.direction = direction;
        size = 3;
        score = 0;
        snakeBody = new ArrayList<>();
        snakeBody.add(body1);
        snakeBody.add(body2);
        lastBodyDirection = body2;
        isAlive = true;
        this.type = type;
        lose = false;
        win = false;
    }

    public void moveRight(){

        if(snakeBody.get(0) != 'r'){
            snakeHeadX++;
            snakeBody.remove(snakeBody.size() - 1);
            snakeBody.add(0,'l');
            direction = 'r';
        }
        else{
            moveLeft();
        }
    }

    public void moveLeft(){
        if(snakeBody.get(0) != 'l'){
            snakeHeadX--;
            snakeBody.remove(snakeBody.size() - 1);
            snakeBody.add(0,'r');
            direction = 'l';
        }
        else{
            moveRight();
        }
    }

    public void moveUp(){
        if(snakeBody.get(0) != 'u'){
            snakeHeadY--;
            snakeBody.remove(snakeBody.size() - 1);
            snakeBody.add(0,'d');
            direction = 'u';
        }
        else{
            moveDown();
        }

    }

    public void moveDown(){
        if(snakeBody.get(0) != 'd'){
            snakeHeadY++;
            snakeBody.remove(snakeBody.size() - 1);
            snakeBody.add(0,'u');
            direction = 'd';
        }
        else{
            moveUp();
        }
    }

    public void eatApple(int score) {
        size++;
        this.score += score;
        snakeBody.add(lastBodyDirection);
    }

}
