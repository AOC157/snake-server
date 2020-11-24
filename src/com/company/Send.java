package com.company;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Send implements Runnable{
    Socket socket;
    DataOutputStream out;
    JsonParser jsonParser;
    Board board;
    byte[] tile;
    public Send(Socket socket, Board board) throws IOException {
        tile = new byte[625];
        this.board = board;
        this.socket = socket;
        out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        jsonParser = new JsonParser();
    }


    @Override
    public void run() {
        tile = convert2DArrayToSimple();
        try {
            out.write(tile);
            out.writeInt(board.snake1.score);
            out.writeInt(board.snake2.score);
            out.writeInt(board.snake3.score);
            out.writeInt(board.snake4.score);
            out.writeInt(board.apple.score);
            out.flush();
        } catch (IOException s) {
            try {
                socket.close();
                System.out.println("socket closed");
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("socket opened");
            }
        }
    }

    public byte[] convert2DArrayToSimple() {
        byte[] tile = new byte[625];
        for (int i = 0 ;i < 25; i++){
            for (int j = 0; j < 25; j++){
                tile[i * 25 + j] = board.tiles[i][j];
            }
        }
        return tile;
    }
}
