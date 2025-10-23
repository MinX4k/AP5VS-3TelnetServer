package utb.fai;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;


public class App {

    public static void main(String[] args) {
        int port = 12345;
        int max_threads = 10;    


        if (args.length > 2){
            System.out.println("Must have 2 arguments: <port> <max_threads>.");
            return;
        }

        if (args.length >= 1){
            try{
                port = Integer.parseInt(args[0]);
            } catch(NumberFormatException e){
                System.err.println("Argument must be a number");
            }
        }
        if (args.length == 2){
            try{
                max_threads = Integer.parseInt(args[1]);
            } catch(NumberFormatException e){
                System.err.println("Argument must be a number");
            }
            
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started at port: " + port);
            System.out.println("Max number of threads: " + max_threads + "(0 means unlimited)");

            ExecutorService exec;
            if (max_threads > 0) {
                exec = Executors.newFixedThreadPool(max_threads);
            } else {
                exec = Executors.newCachedThreadPool();
            }


            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New connection from: " + clientSocket.getInetAddress());
                    exec.execute(new ClientThread(clientSocket));
                } catch (IOException e) {
                    System.err.println("Error while connecting: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("Cannot start server at port: " + port + ": " + e.getMessage());
        }


        }
        
    }

