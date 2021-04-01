import java.net.*;
import java.util.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws Exception{
      int liteningport = 5298; //Match with client destination port. 
      DatagramSocket serversocket = new DatagramSocket(listeningport);
      byte[] sendmessage = new byte[1024];
      byte[] receivemessage = new byte[1024];

      while(true){ 
            DatagramPacket fromClient = new DatagramPacket(receivemessage, receivemessage.length);
            serversocket.receive(inFromClient);
            String input = new String(fromClient.getData());
            InetAddress ip = fromClient.getAddress();
            int port = fromClient.getPort(); 
            System.out.println("Server input: "+input);
      }

