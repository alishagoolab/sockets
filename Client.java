/**
 * The class Client sets up a network client for sending chat messages using the UDP protocol. 
 * A datagram socket is bound to an available port to receive messages (via datagram packets) through. 
 * Another socket is bound to the Server class's listening port to send messages (via datagram packets) to. 
 * @author RNGTAM002, RJKRAH001, GLBALI002
 */

import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
  public static void main(String[] args) throws Exception {
    InetAddress ip = InetAddress.getLocalHost(); // Get IP address, can get from args instead. 
    
    int sendport = 5298; //set destination port

    try {
      DatagramSocket clientsocket = new DatagramSocket(); //Binds DatagramSocket to any available local port.
      while(true){
        byte[] sendmessage = new byte[1024];
        System.out.print("Enter text: "); //match this with GUI
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
        String input = in.readLine();//Get user input
        System.out.println("Output message: "+input); //match with GUI
        sendmessage = input.getBytes(); //Convert message string to bytes
        //Checksum origin here
        DatagramPacket sendpacket = new DatagramPacket(sendmessage, sendmessage.length, ip, sendport); 
        clientsocket.send(sendpacket);
 
        byte[] receivemessage = new byte[1024];
        DatagramPacket receivepacket = new DatagramPacket(receivemessage, receivemessage.length);
        clientsocket.receive(receivepacket);
        String text = new String(receivepacket.getData());
        //Checksum result here
        System.out.println("Received Message: "+ text);
        clientsocket.close();
        Thread.sleep(10000); //
      }
    }
    catch (SocketTimeoutException ex) {
            System.out.println("Timeout error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
        }
  }
}