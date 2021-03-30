import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
  public static void main(String[] args) throws Exception {
    InetAddress ip = InetAddress.getLocalHost(); // Get IP address, can get from args instead. 
    int port = 5298; //set port for packet
    byte[] senddata = new byte[1024];
    byte[] receivedata = new byte[1024];
    DatagramSocket clientsocket = new DatagramSocket(); //Binds the DatagramSocket to any available local port.
    System.out.print("Enter text: "); //match this with GUI
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
    String input = in.readLine();//Get user input
    System.out.println("Message to be sent: "+input); //match with GUI



  }
}