/**
 * The class Client1 sets up a network client for sending chat messages using the UDP protocol. 
 * The class sets up a datagram socket to send and receive datagram packets through. 
 * It calculates a checksum on the output message and displays it for data integrity validation.
 * @author RNGTAM002, GLBALI002, RJKRAH001.
 * @date 1st April 2021. 
 */

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.zip.CheckedInputStream;
import java.util.zip.Adler32;

public class Client1 {
  
  public static void main(String[] args) throws Exception {
    long sendChecksum;
    InetAddress ip = InetAddress.getLocalHost(); // Get IP address of host. 
    int messageNum = 1;
    int sendport = 5298; //set destination port
    boolean running = true;

    try {
      DatagramSocket clientsocket1 = new DatagramSocket(4444); 
      while(running){
        byte[] sendmessage = new byte[1024];
        System.out.print("Enter Message: "); //match this with GUI
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
        String input = in.readLine();//Get user input
        System.out.println("Sent message " + messageNum + ": "+ input); //match with GUI
        sendmessage = input.getBytes(); //Convert message string to bytes
        DatagramPacket sendpacket = new DatagramPacket(sendmessage, sendmessage.length, ip, sendport); 
        clientsocket1.send(sendpacket);

        if (input.equals("end")){
          running = false;
          clientsocket1.close();
          continue;
        }

        //Checksum output calculation.
        Server server = new Server();
        ByteArrayInputStream bias = new ByteArrayInputStream(sendmessage);
        CheckedInputStream cis = new CheckedInputStream(bias, new Adler32());
        byte readBuffer[] = new byte[10];
        while(cis.read(readBuffer) >= 0) {
          sendChecksum = cis.getChecksum().getValue();
          //System.out.println("Checksum value: " + sendChecksum);
          server.setSendChecksum(sendChecksum);
        }
        

        byte[] receivemessage = new byte[1024];
        DatagramPacket receivepacket = new DatagramPacket(receivemessage, receivemessage.length);
        clientsocket1.receive(receivepacket);
        String inputText = new String(receivepacket.getData(), 0, receivepacket.getLength());

        if (inputText.equals("end")){
          running = false;
          clientsocket1.close();
          continue;
        }
        
        System.out.println("Received: "+ inputText); 
        messageNum += 2;

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