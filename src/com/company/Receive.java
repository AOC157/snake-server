package com.company;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class Receive implements Runnable{
    Socket socket;
    DataInputStream in;
    JsonParser jsonParser;
    Board board;

    public Receive(Socket socket, Board board) throws IOException {
        this.board = board;
        this.socket = socket;
        in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        jsonParser = new JsonParser();
    }

    @Override
    public void run() {
        String jsonCommand = "{}";
        try {
            jsonCommand = in.readUTF();
        } catch (SocketException s){
            try {
                socket.close();
                System.out.println("socket closed in receive");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        jsonParser.parse(jsonCommand,board.snake1,board.snake2,board.snake3,board.snake4);
    }
}
