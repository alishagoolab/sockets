/**
 * The class Client sets up a network client for sending chat messages using the UDP protocol. 
 * The class sets up a datagram socket to send and receive datagram packets through. 
 * It calculates a checksum on the output message and on input data and displays it for data integrity validation.
 * @author RNGTAM002
 */

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.zip.CheckedInputStream;
import java.util.zip.Adler32;

public class Client {
  private long sendChecksum;
  public void setChecksum(long sendChecksum){
    this.sendChecksum = sendChecksum;
  }

  public long getChecksum(){
    this.sendChecksum = sendChecksum;
    return sendChecksum;
  }

  public static void main(String[] args) throws Exception {
    InetAddress ip = InetAddress.getLocalHost(); // Get IP address, can get from args instead. 
    int messageNum = 2;
    int sendport = 5298; //set destination port
    boolean running =  true;
    Client client = new Client();
    long sendChecksum;

    try {
      DatagramSocket clientsocket = new DatagramSocket(4445); 
      while(running){
        byte[] receivemessage = new byte[1024];
        DatagramPacket receivepacket = new DatagramPacket(receivemessage, receivemessage.length);
        clientsocket.receive(receivepacket);
        String inputText = new String(receivepacket.getData(), 0, receivepacket.getLength());

        if (inputText.equals("end")){
          running = false;
          clientsocket.close();
          continue;
        }
        
        System.out.println("Received: "+ inputText); 

        byte[] sendmessage = new byte[1024];
        System.out.print("Enter Message: "); //match this with GUI
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
        String input = in.readLine();//Get user input
        System.out.println("Sent message " + messageNum + ": "+ input); //match with GUI
        sendmessage = input.getBytes(); //Convert message string to bytes
        DatagramPacket sendpacket = new DatagramPacket(sendmessage, sendmessage.length, ip, sendport); 
        clientsocket.send(sendpacket);
        messageNum += 2;

        if (input.equals("end")){
          running = false;
          clientsocket.close();
          continue;
        }

        //Checksum output calculation.
        ByteArrayInputStream bias = new ByteArrayInputStream(sendmessage);
        CheckedInputStream cis = new CheckedInputStream(bias, new Adler32());
        byte readBuffer[] = new byte[10];
        while(cis.read(readBuffer) >= 0) {
          //client.setChecksum(cis.getChecksum().getValue());
          //sendChecksum = client.getChecksum();
          
          sendChecksum = cis.getChecksum().getValue(); 
          System.out.println("Checksum value: " + sendChecksum);
        }
        //sendChecksum = client.getChecksum();--put in other method.
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
