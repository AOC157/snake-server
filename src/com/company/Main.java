package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        final int PORT = 8888;
        ServerSocket server = new ServerSocket(PORT);

        Board board = new Board();

        int loserCounter = 0;

        Socket socket1 = server.accept();
        Socket socket2 = server.accept();
        Socket socket3 = server.accept();
        Socket socket4 = server.accept();

        Send sender1 = null;
        Send sender2 = null;
        Send sender3 = null;
        Send sender4 = null;

        String username1;
        String username2;
        String username3;
        String username4;

        Thread receiveThread1 = null;
        Thread receiveThread2 = null;
        Thread receiveThread3 = null;
        Thread receiveThread4 = null;

        Thread sendThread1 = null;
        Thread sendThread2 = null;
        Thread sendThread3 = null;
        Thread sendThread4 = null;

        try {
            sender1 = new Send(socket1, board);
            Receive receiver1 = new Receive(socket1, board);
            username1 = receiver1.in.readUTF();
            sender1.out.writeUTF("snake1");
            sender1.out.flush();
        }
        catch (SocketException s){
            username1 = "";
            board.snake1.isAlive = false;
            loserCounter++;
            socket1.close();
        }

        try {
            sender2 = new Send(socket2, board);
            Receive receiver2 = new Receive(socket2, board);
            username2 = receiver2.in.readUTF();
            sender2.out.writeUTF("snake2");
            sender2.out.flush();
        }
        catch(SocketException s){
            username2 = "";
            board.snake2.isAlive = false;
            socket2.close();
            loserCounter++;
        }

        try {
            sender3 = new Send(socket3, board);
            Receive receiver3 = new Receive(socket3, board);
            username3 = receiver3.in.readUTF();
            sender3.out.writeUTF("snake3");
            sender3.out.flush();
        }
        catch(SocketException s){
            username3 = "";
            board.snake3.isAlive = false;
            loserCounter++;
            socket3.close();
        }

        try {
            sender4 = new Send(socket4, board);
            Receive receiver4 = new Receive(socket4, board);
            username4 = receiver4.in.readUTF();
            sender4.out.writeUTF("snake4");
            sender4.out.flush();
        }
        catch(SocketException s){
            username4 = "";
            loserCounter++;
            board.snake4.isAlive = false;
            socket4.close();
        }

        sendUserNames(sender1, username1,username2,username3,username4);
        sendUserNames(sender2, username1,username2,username3,username4);
        sendUserNames(sender3, username1,username2,username3,username4);
        sendUserNames(sender4, username1,username2,username3,username4);



        while (loserCounter < 3) {
            if (!socket1.isClosed()) {
                receiveThread1 = new Thread(new Receive(socket1, board));
                receiveThread1.start();
            }
            if (!socket2.isClosed()) {
                receiveThread2 = new Thread(new Receive(socket2, board));
                receiveThread2.start();
            }
            if (!socket3.isClosed()) {
                receiveThread3 = new Thread(new Receive(socket3, board));
                receiveThread3.start();
            }
            if (!socket4.isClosed()) {
                receiveThread4 = new Thread(new Receive(socket4, board));
                receiveThread4.start();
            }
            if(receiveThread1 != null) receiveThread1.join();
            if(receiveThread2 != null) receiveThread2.join();
            if(receiveThread3 != null) receiveThread3.join();
            if(receiveThread4 != null) receiveThread4.join();

            board.update();

            if (!socket1.isClosed() || board.snake1.isAlive) {
                try {
                    sendThread1 = new Thread(new Send(socket1, board));
                    sendThread1.start();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                if (!board.snake1.isAlive || socket1.isClosed()) {
                    sendThread1.join();
                    board.snake1.isAlive = false;
                    socket1.close();
                    loserCounter++;
                }
            }
            if (!socket2.isClosed() || board.snake2.isAlive) {
                try {
                    sendThread2 = new Thread(new Send(socket2, board));
                    sendThread2.start();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                if (!board.snake2.isAlive || socket2.isClosed()) {
                    sendThread2.join();
                    board.snake2.isAlive = false;
                    socket2.close();
                    loserCounter++;
                }
            }
            if (!socket3.isClosed() || board.snake3.isAlive) {
                try {
                    sendThread3 = new Thread(new Send(socket3, board));
                    sendThread3.start();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                if (!board.snake3.isAlive || socket3.isClosed()) {
                    sendThread3.join();
                    board.snake3.isAlive = false;
                    socket3.close();
                    loserCounter++;
                }
            }
            if (!socket4.isClosed() || board.snake4.isAlive) {
                try {
                    sendThread4 = new Thread(new Send(socket4, board));
                    sendThread4.start();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                if (!board.snake4.isAlive || socket4.isClosed()) {
                    sendThread4.join();
                    board.snake4.isAlive = false;
                    socket4.close();
                    loserCounter++;
                }
            }
            if(sendThread1 != null) sendThread1.join();
            if(sendThread2 != null) sendThread2.join();
            if(sendThread3 != null) sendThread3.join();
            if(sendThread4 != null) sendThread4.join();
        }
        socket1.close();
        socket2.close();
        socket3.close();
        socket4.close();
    }

    private static void sendUserNames(Send sender, String username1, String username2, String username3, String username4) {
        try {
            sender.out.writeUTF(username1);
            sender.out.writeUTF(username2);
            sender.out.writeUTF(username3);
            sender.out.writeUTF(username4);
            sender.out.flush();
        }
        catch (IOException io){
            io.printStackTrace();
        }
    }
}