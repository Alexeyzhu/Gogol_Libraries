import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ThreadedEchoServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            int i = 1;

            while (true) {
                Socket incoming = serverSocket.accept();
                System.out.println("Spawning " + i);
                Runnable handler = new ThreadedEchoHandler(incoming);
                Thread thread = new Thread(handler);
                thread.start();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

