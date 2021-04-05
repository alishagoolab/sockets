/**
 * The class Server sets up a network host for sending chat messages using the UDP protocol. 
 * The class sets up a port to listen for incoming datagram packets which contain the messages.
 * It sends and receives the datagram packets through datagram sockets.
 * @author RNGTAM002
 */

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;

public class Server {
    public static void main(String[] args) throws Exception{
      int listeningport = 5298; //Match with client destination port. 
      DatagramSocket serversocket = new DatagramSocket(listeningport);
      byte[] sendmessage = new byte[1024];
      long sendChecksum;
      byte[] receivemessage = new byte[1024];
      long receiveChecksum;
      int messageNum = 2;

      while(true){ 
            DatagramPacket fromClient = new DatagramPacket(receivemessage, receivemessage.length);
            serversocket.receive(fromClient);

            String input = new String(fromClient.getData());
            InetAddress ip = fromClient.getAddress();
            int port = fromClient.getPort(); 
            System.out.println("Received: "+ input);

            BufferedReader out = new BufferedReader(new InputStreamReader(System.in)); 
            System.out.print("Enter reply: ");
            String output = out.readLine();//Get user input

            sendmessage = output.getBytes();
            System.out.println("Sent message " + messageNum + ": "+output);
            DatagramPacket toClient = new DatagramPacket(sendmessage, sendmessage.length, ip, port);
            serversocket.send(toClient); 
            messageNum+=2;

            //int checkSize = receivemessage.length - 7;
            //byte[] received = new byte[checkSize];
            //System.out.println(receivemessage);
            //for (int i=0;i<checkSize;i++)
              //received[i] = receivemessage[i+7];
            //System.out.println(receivedCheck);
            //ByteArrayInputStream bias = new ByteArrayInputStream(received);
            //CheckedInputStream cis = new CheckedInputStream(bias, new Adler32());
            //byte readBuffer[] = new byte[10];
            //while(cis.read(readBuffer) >= 0) {
              //receiveChecksum = cis.getChecksum().getValue();
              //System.out.println("Checksum value: " + receiveChecksum);
            //}
      }
    }
}
