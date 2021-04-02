/**
 * The class Server sets up a network host for sending chat messages using the UDP protocol. 
 * The class sets up a port to listen for incoming datagram packets which contain the messages.
 * It sends and receives the datagram packets through datagram sockets.
 * @author RNGTAM002, RJKRAH001, GLBALI002
 */

import java.net.*;
import java.util.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws Exception{
      int listeningport = 5298; //Match with client destination port. 
      DatagramSocket serversocket = new DatagramSocket(listeningport);
      byte[] sendmessage = new byte[1024];
      byte[] receivemessage = new byte[1024];

      while(true){ 
            DatagramPacket fromClient = new DatagramPacket(receivemessage, receivemessage.length);
            serversocket.receive(fromClient);
            String input = new String(fromClient.getData());
            InetAddress ip = fromClient.getAddress();
            int port = fromClient.getPort(); 
            System.out.println("Server input: "+input);

            String output = ""; 
            
      }
    }
}

