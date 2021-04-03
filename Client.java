/**
 * The class Client sets up a network client for sending chat messages using the UDP protocol. 
 * A datagram socket is bound to an available port to receive messages (via datagram packets) through. 
 * Another socket is bound to the Server class's listening port to send messages (via datagram packets) to. 
 * @author RNGTAM002, RJKRAH001, GLBALI002
 */

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.zip.CheckedInputStream;
import java.util.zip.Adler32;


public class Client {
  public static void main(String[] args) throws Exception {
    long sentChecksum;
    InetAddress ip = InetAddress.getLocalHost(); // Get IP address, can get from args instead. 
    
    int sendport = 5298; //set destination port

    try {
      DatagramSocket clientsocket = new DatagramSocket(); //Binds DatagramSocket to any available local port.
      while(true){
        byte[] sendmessage = new byte[1024];
        System.out.print("Enter message: "); //match this with GUI
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
        String input = in.readLine();//Get user input
        System.out.println("Sent: "+ input); //match with GUI
        sendmessage = input.getBytes(); //Convert message string to bytes
        ByteArrayInputStream bais = new ByteArrayInputStream(sendmessage);
        CheckedInputStream cis = new CheckedInputStream(bais, new Adler32());
        byte readBuffer[] = new byte[10];
        while (cis.read(readBuffer) >= 0){
          sentChecksum = cis.getChecksum().getValue();
          System.out.println("The value of checksum is " + sentChecksum);
        }
        DatagramPacket sendpacket = new DatagramPacket(sendmessage, sendmessage.length, ip, sendport); 
        clientsocket.send(sendpacket);
 
        byte[] receivemessage = new byte[1024];
        DatagramPacket receivepacket = new DatagramPacket(receivemessage, receivemessage.length);
        clientsocket.receive(receivepacket);
        String message = new String(receivepacket.getData());
        //Checksum result here
        System.out.println("Received Message: "+ message);
        //clientsocket.close();
        Thread.sleep(10000); 
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