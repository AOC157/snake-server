package com.company;

public class Apple  {
    int score ;
    int appleX ;
    int appleY;

    public int getRandomNumber(int minimum, int maximum) {
        return ((int) (Math.random() * (maximum - minimum))) + minimum;
    }

}
