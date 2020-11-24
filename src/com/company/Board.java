package com.company;

public class Board {

    Snake snake1;
    Snake snake2;
    Snake snake3;
    Snake snake4;
    Apple apple;
    byte[][] tiles;

    public static final int BLOCK = -10;
    public static final int SNAKE1 = 1;
    public static final int SNAKE2 = 2;
    public static final int SNAKE3 = 3;
    public static final int SNAKE4 = 4;
    public static final int SNAKE1HEAD = -1;
    public static final int SNAKE2HEAD = -2;
    public static final int SNAKE3HEAD = -3;
    public static final int SNAKE4HEAD = -4;
    public static final int EMPTY = 0;
    public static final int APPLE = 10;

    public static final int COL_COUNT = 25;

    public static final int ROW_COUNT = 25;

    public Board() {
        snake1 = new Snake(2,0,'r','l','l',1);
        snake2 = new Snake(24,2,'d','u','u',2);
        snake3 = new Snake(22,24,'l','r','r',3);
        snake4 = new Snake(0,22,'u','d','d',4);
        tiles = new byte[ROW_COUNT][COL_COUNT];
        apple = new Apple();
        spawnFruit();
        CreateBlock2();
        CreateBlock();
    }

    public void update() {
        clearBoard();

        if(snake1.isAlive){
            updateForSnake1();
            snake1.lastBodyDirection = snake1.snakeBody.get(snake1.snakeBody.size() - 1);
        }

        if(snake2.isAlive) {
            updateForSnake2();
            snake2.lastBodyDirection = snake2.snakeBody.get(snake2.snakeBody.size() - 1);
        }
        if(snake3.isAlive){
            updateForSnake3();
            snake3.lastBodyDirection = snake3.snakeBody.get(snake3.snakeBody.size() - 1);
        }
        if (snake4.isAlive){
            updateForSnake4();
            snake4.lastBodyDirection = snake4.snakeBody.get(snake4.snakeBody.size() - 1);
        }
    }

    public void clearBoard() {
        for (int i = 0; i < ROW_COUNT; i++) {
            for(int j = 0 ; j < COL_COUNT ; j++){
                if (tiles[i][j] == APPLE) {
                    tiles[i][j] = APPLE;
                }
                else if (tiles[i][j] == BLOCK) {
                    tiles[i][j] = BLOCK;
                }
                else {
                    tiles[i][j] = EMPTY;
                }
            }
        }
    }

    private void updateForSnake1() {
        int lastXPosition = snake1.snakeHeadX;
        int lastYPosition = snake1.snakeHeadY;
        try {
            switch (tiles[lastXPosition][lastYPosition]) {
                case BLOCK:
                    suicide(snake1);
                    return;
                case SNAKE1:
                    suicide(snake1);
                    return;
                case APPLE:
                    snake1.eatApple(apple.score);
                    tiles[lastXPosition][lastYPosition] = SNAKE1HEAD;
                    spawnFruit();
                    break;
                case EMPTY:
                    tiles[lastXPosition][lastYPosition] = SNAKE1HEAD;
                    break;
            }
        }
        catch (ArrayIndexOutOfBoundsException outOfBounds){
            suicide(snake1);
            return;
        }
        for (int i = 0; i < snake1.snakeBody.size(); i++) {
            if (snake1.snakeBody.get(i) == 'r') {
                lastXPosition++;
            }
            else if (snake1.snakeBody.get(i) == 'l') {
                lastXPosition--;
            }
            else if (snake1.snakeBody.get(i) == 'u') {
                lastYPosition--;
            }
            else if (snake1.snakeBody.get(i) == 'd') {
                lastYPosition++;
            }
            if(tiles[lastXPosition][lastYPosition] == SNAKE1){
                suicide(snake1);
                return;
            }
            else {
                tiles[lastXPosition][lastYPosition] = SNAKE1;
            }
        }
    }

    private void updateForSnake2() {
        int lastXPosition = snake2.snakeHeadX;
        int lastYPosition = snake2.snakeHeadY;
        try {
            switch (tiles[lastXPosition][lastYPosition]) {
                case BLOCK:
                    suicide(snake2);
                    return;
                case SNAKE1:
                    battle(snake1, snake2);
                    if(!snake2.isAlive) return;
                    else tiles[lastXPosition][lastYPosition] = SNAKE2HEAD;
                    break;
                case SNAKE1HEAD:
                    battle(snake1, snake2);
                    if(!snake2.isAlive) return;
                    else tiles[lastXPosition][lastYPosition] = SNAKE2HEAD;
                    break;
                case SNAKE2:
                    suicide(snake2);
                    return;
                case APPLE:
                    snake2.eatApple(apple.score);
                    tiles[lastXPosition][lastYPosition] = SNAKE2HEAD;
                    spawnFruit();
                    break;
                case EMPTY:
                    tiles[lastXPosition][lastYPosition] = SNAKE2HEAD;
                    break;
            }
        }
        catch (ArrayIndexOutOfBoundsException outOfBounds){
            suicide(snake2);
            return;
        }
        for (int i = 0; i < snake2.snakeBody.size(); i++) {
            if (snake2.snakeBody.get(i) == 'r') {
                lastXPosition++;
            }
            else if (snake2.snakeBody.get(i) == 'l') {
                lastXPosition--;
            }
            else if (snake2.snakeBody.get(i) == 'u') {
                lastYPosition--;
            }
            else if (snake2.snakeBody.get(i) == 'd') {
                lastYPosition++;
            }
            switch (tiles[lastXPosition][lastYPosition]){
                case SNAKE1HEAD:
                    battle(snake1, snake2);
                    if(!snake2.isAlive) return;
                    else tiles[lastXPosition][lastYPosition] = SNAKE2;
                    break;
                case SNAKE1:
                    tiles[lastXPosition][lastYPosition] = SNAKE2;
                    break;
                case SNAKE2:
                    suicide(snake2);
                    return;
                case EMPTY:
                    tiles[lastXPosition][lastYPosition] = SNAKE2;
                    break;
            }
        }
    }

    private void updateForSnake3() {
        int lastXPosition = snake3.snakeHeadX;
        int lastYPosition = snake3.snakeHeadY;
        try {
            switch (tiles[lastXPosition][lastYPosition]) {
                case BLOCK:
                    suicide(snake3);
                    return;
                case SNAKE1:
                    battle(snake1, snake3);
                    if (!snake3.isAlive) return;
                    else tiles[lastXPosition][lastYPosition] = SNAKE3HEAD;
                    break;
                case SNAKE1HEAD:
                    battle(snake1, snake3);
                    if(!snake3.isAlive) return;
                    else tiles[lastXPosition][lastYPosition] = SNAKE3HEAD;
                    break;
                case SNAKE2:
                    battle(snake2, snake3);
                    if (!snake3.isAlive) return;
                    else tiles[lastXPosition][lastYPosition] = SNAKE3HEAD;
                    break;
                case SNAKE2HEAD:
                    battle(snake2, snake3);
                    if(!snake3.isAlive) return;
                    else tiles[lastXPosition][lastYPosition] = SNAKE3HEAD;
                    break;
                case SNAKE3:
                    suicide(snake3);
                    return;
                case EMPTY:
                    tiles[lastXPosition][lastYPosition] = SNAKE3HEAD;
                    break;
                case APPLE:
                    snake3.eatApple(apple.score);
                    tiles[lastXPosition][lastYPosition] = SNAKE3HEAD;
                    spawnFruit();
                    break;
            }
        }
        catch (ArrayIndexOutOfBoundsException outOfBounds){
            suicide(snake3);
            return;
        }
        for (int i = 0; i < snake3.snakeBody.size(); i++) {
            if (snake3.snakeBody.get(i) == 'r') {
                lastXPosition++;
            }
            else if (snake3.snakeBody.get(i) == 'l') {
                lastXPosition--;
            }
            else if (snake3.snakeBody.get(i) == 'u') {
                lastYPosition--;
            }
            else if (snake3.snakeBody.get(i) == 'd') {
                lastYPosition++;
            }
            switch (tiles[lastXPosition][lastYPosition]){
                case SNAKE1HEAD:
                    battle(snake1, snake3);
                    if(!snake3.isAlive) return;
                    else tiles[lastXPosition][lastYPosition] = SNAKE3;
                    break;
                case SNAKE1:
                    tiles[lastXPosition][lastYPosition] = SNAKE3;
                    break;
                case SNAKE2HEAD:
                    battle(snake3, snake2);
                    if(!snake3.isAlive) return;
                    else tiles[lastXPosition][lastYPosition] = SNAKE3;
                    break;
                case SNAKE2:
                    tiles[lastXPosition][lastYPosition] = SNAKE3;
                    break;
                case SNAKE3:
                    suicide(snake3);
                    return;
                case EMPTY:
                    tiles[lastXPosition][lastYPosition] = SNAKE3;
                    break;
            }
        }
    }

    private void updateForSnake4() {
        int lastXPosition = snake4.snakeHeadX;
        int lastYPosition = snake4.snakeHeadY;
        try {
            switch (tiles[lastXPosition][lastYPosition]) {
                case BLOCK:
                    suicide(snake4);
                    return;
                case SNAKE1:
                    battle(snake1, snake4);
                    if (!snake4.isAlive) return;
                    else tiles[lastXPosition][lastYPosition] = SNAKE4HEAD;
                    break;
                case SNAKE1HEAD:
                    battle(snake1, snake4);
                    if(!snake4.isAlive) return;
                    else tiles[lastXPosition][lastYPosition] = SNAKE4HEAD;
                    break;
                case SNAKE2:
                    battle(snake2, snake4);
                    if (!snake4.isAlive) return;
                    else tiles[lastXPosition][lastYPosition] = SNAKE4HEAD;
                    break;
                case SNAKE2HEAD:
                    battle(snake2, snake4);
                    if(!snake4.isAlive) return;
                    else tiles[lastXPosition][lastYPosition] = SNAKE4HEAD;
                    break;
                case SNAKE3:
                    battle(snake3, snake4);
                    if (!snake4.isAlive) return;
                    else tiles[lastXPosition][lastYPosition] = SNAKE4HEAD;
                    break;
                case SNAKE3HEAD:
                    battle(snake3, snake4);
                    if(!snake4.isAlive) return;
                    else tiles[lastXPosition][lastYPosition] = SNAKE4HEAD;
                    break;
                case SNAKE4:
                    suicide(snake4);
                    return;
                case EMPTY:
                    tiles[lastXPosition][lastYPosition] = SNAKE4HEAD;
                    break;
                case APPLE:
                    snake4.eatApple(apple.score);
                    tiles[lastXPosition][lastYPosition] = SNAKE4HEAD;
                    spawnFruit();
                    break;
            }
        }
        catch (ArrayIndexOutOfBoundsException outOfBounds){
            suicide(snake4);
            return;
        }
        for (int i = 0; i < snake4.snakeBody.size(); i++) {
            if (snake4.snakeBody.get(i) == 'r') {
                lastXPosition++;
            }
            else if (snake4.snakeBody.get(i) == 'l') {
                lastXPosition--;
            }
            else if (snake4.snakeBody.get(i) == 'u') {
                lastYPosition--;
            }
            else if (snake4.snakeBody.get(i) == 'd') {
                lastYPosition++;
            }
            switch (tiles[lastXPosition][lastYPosition]){
                case SNAKE1HEAD:
                    battle(snake1, snake4);
                    if(!snake4.isAlive) return;
                    else tiles[lastXPosition][lastYPosition] = SNAKE4;
                    break;
                case SNAKE2HEAD:
                    battle(snake2, snake4);
                    if(!snake4.isAlive) return;
                    else tiles[lastXPosition][lastYPosition] = SNAKE4;
                    break;
                case SNAKE3HEAD:
                    battle(snake3, snake4);
                    if(!snake4.isAlive) return;
                    else tiles[lastXPosition][lastYPosition] = SNAKE4;
                    break;
                case SNAKE4:
                    suicide(snake4);
                    return;
                default:
                    tiles[lastXPosition][lastYPosition] = SNAKE4;
                    break;
            }
        }
    }



    private void battle(Snake snake1, Snake snake2) {
        if(snake1.score > snake2.score){
            kill(snake1,snake2);
        }
        else if (snake1.score < snake2.score){
            kill(snake2,snake1);
        }
    }

    private void kill(Snake killer,Snake killed) {
        killed.isAlive = false;
        killer.score += killed.score;
        int lastXPosition = killed.snakeHeadX;
        int lastYPosition = killed.snakeHeadY;
        if(tiles[lastXPosition][lastYPosition] != killer.type && tiles[lastXPosition][lastYPosition] != -1*killer.type){
            tiles[lastXPosition][lastYPosition] = EMPTY;
        }
        for (int i = 0; i < killed.snakeBody.size(); i++) {
            if (killed.snakeBody.get(i) == 'r') {
                lastXPosition++;
            }
            else if (killed.snakeBody.get(i) == 'l') {
                lastXPosition--;
            }
            else if (killed.snakeBody.get(i) == 'u') {
                lastYPosition--;
            }
            else if (killed.snakeBody.get(i) == 'd') {
                lastYPosition++;
            }
            if(tiles[lastXPosition][lastYPosition] != killer.type && tiles[lastXPosition][lastYPosition] != -1*killer.type){
                tiles[lastXPosition][lastYPosition] = EMPTY;
            }
        }
    }



    private void suicide(Snake snake) {
        snake.isAlive = false;
        try {
            if (tiles[snake.snakeHeadX][snake.snakeHeadY] != BLOCK) {
                tiles[snake.snakeHeadX][snake.snakeHeadY] = EMPTY;
            }
        }
        catch (ArrayIndexOutOfBoundsException outOfBounds){
            outOfBounds.printStackTrace();
        }
        int lastXPosition = snake.snakeHeadX;
        int lastYPosition = snake.snakeHeadY;
        for (int i = 0; i < snake.snakeBody.size(); i++) {
            if (snake.snakeBody.get(i) == 'r') {
                lastXPosition++;
            }
            else if (snake.snakeBody.get(i) == 'l') {
                lastXPosition--;
            }
            else if (snake.snakeBody.get(i) == 'u') {
                lastYPosition--;
            }
            else if (snake.snakeBody.get(i) == 'd') {
                lastYPosition++;
            }
            tiles[lastXPosition][lastYPosition] = EMPTY;
        }
    }

    public int getTile(int x, int y) {
        return tiles[x][y];
    }

    public void spawnFruit() {
        apple.score = apple.getRandomNumber(1, 5);
        boolean flag = true;
        int temp_col = apple.getRandomNumber(0, 24);
        int temp_row = apple.getRandomNumber(0, 24);
        while (flag) {
            if (tiles[temp_row][temp_col] == EMPTY) {
                tiles[temp_row][temp_col] = APPLE ;
                apple.appleX = temp_row;
                apple.appleY = temp_col;
                flag = false;
            }
            else {
                temp_col = apple.getRandomNumber(0, 24);
                temp_row = apple.getRandomNumber(0, 24);
            }
        }
    }

    public void CreateBlock2() {
        for (int i = 0; i < 5; i++) {
            boolean flag = true;
            int temp_col = getRandomNumber(3, 23);
            int temp_row = getRandomNumber(3, 23);
            while (flag) {
                if (tiles[temp_row][temp_col] == EMPTY && tiles[temp_row + 1][temp_col] == EMPTY && tiles[temp_row][temp_col + 1] == EMPTY) {
                    tiles[temp_row][temp_col] = BLOCK;
                    tiles[temp_row + 1][temp_col] = BLOCK;
                    tiles[temp_row][temp_col + 1] = BLOCK;
                    flag = false;
                }
                else {
                    temp_col = getRandomNumber(3, 23);
                    temp_row = getRandomNumber(3, 23);
                }
            }
        }
    }

    public void CreateBlock() {
        for (int i = 0; i < 5; i++) {
            boolean flag = true;
            int temp_col = getRandomNumber(3, 23);
            int temp_row = getRandomNumber(3, 23);
            while (flag) {
                if (tiles[temp_row][temp_col] == EMPTY && tiles[temp_row + 1][temp_col] == EMPTY) {
                    tiles[temp_row][temp_col] = BLOCK;
                    tiles[temp_row + 1][temp_col] = BLOCK;
                    flag = false;
                }
                else {
                    temp_col = getRandomNumber(3, 23);
                    temp_row = getRandomNumber(3, 23);
                }
            }
        }
    }
    public int getRandomNumber(int minimum, int maximum) {
        return ((int) (Math.random() * (maximum - minimum))) + minimum;
    }
}
