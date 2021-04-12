/**
 * The class Server sets up a network host for sending chat messages using the UDP protocol. 
 * The class sets up a port to listen for incoming datagram packets which contain the messages.
 * It sends and receives the datagram packets through datagram sockets.
* @author RNGTAM002, GLBALI002, RJKRAH001.
 */

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;

public class Server {
    private static long sendChecksum;
    public void setSendChecksum(long sendChecksum){
      this.sendChecksum = sendChecksum;
    }

    public static long getSendChecksum(){
      return sendChecksum;
    }

    public static void main(String[] args) throws Exception{
      int listeningport = 5298; //Match with client destination port. 
      DatagramSocket serversocket = new DatagramSocket(listeningport);
      byte[] sendmessage = new byte[1024];
      byte[] receivemessage = new byte[1024];
      long receiveChecksum;
      boolean running = true;

      while(running){ 
            DatagramPacket fromClient = new DatagramPacket(receivemessage, receivemessage.length);
            serversocket.receive(fromClient);

            String input = new String(fromClient.getData());
            byte[] message = input.getBytes();
            InetAddress ip = fromClient.getAddress();
            int port = fromClient.getPort(); 
            System.out.println("Received: "+ input);

            if (input.equals("end")){
              running = false;
              serversocket.close();
              continue;
            }

            if (port == 4444) {
              DatagramPacket toClient = new DatagramPacket(message, message.length, ip, 4445);
              long checksum = getSendChecksum();
              
              ByteArrayInputStream bias = new ByteArrayInputStream(receivemessage);
              CheckedInputStream cis = new CheckedInputStream(bias, new Adler32());
              byte readBuffer[] = new byte[10];
              while(cis.read(readBuffer) >= 0) {
                receiveChecksum = cis.getChecksum().getValue();
              }
              long setChecksum = getSendChecksum();
              serversocket.send(toClient); 
                 
            }

           if (port == 4445) {
             DatagramPacket toClient = new DatagramPacket(message, message.length, ip, 4444);

             ByteArrayInputStream bias = new ByteArrayInputStream(receivemessage);
             CheckedInputStream cis = new CheckedInputStream(bias, new Adler32());
             byte readBuffer[] = new byte[10];
             while(cis.read(readBuffer) >= 0) {
               receiveChecksum = cis.getChecksum().getValue();
             }
             serversocket.send(toClient); 
                
           }
          
      }
    }
}