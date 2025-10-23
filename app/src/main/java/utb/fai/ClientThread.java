package utb.fai;

import java.io.*;
import java.net.*;

public class ClientThread extends Thread {

    private Socket clientSocket;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try{
            InputStream inStream = clientSocket.getInputStream();
            OutputStream outStream = clientSocket.getOutputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
            PrintWriter out = new PrintWriter(outStream, true);

            String inputString;

            while ((inputString = in.readLine()) != null){
                System.out.println("Message from client: " + inputString);
                out.println(inputString);

            }
        } catch (IOException e) {
            System.err.println("Error in client communication: " + e.getMessage());
        } finally{
            try{
                clientSocket.close();
                System.out.println("Client: " + clientSocket.getInetAddress() + " disconnected.");

            } catch(IOException e){
                System.err.println("Error while closing socket: " + e.getMessage());
            }
        }
    }
}
