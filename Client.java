import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
  public static void main(String[] args) throws Exception {
    InetAddress ip = InetAddress.getLocalHost(); // Get IP address, can get from args instead. 
    int port = 5298; //packet port
    byte[] senddata = new byte[1024];
    byte[] receivedata = new byte[1024];
    DatagramSocket clientsocket = new DatagramSocket(); //This constructor binds the DatagramSocket to any available local port.
    System.out.print("Enter text: "); //match this with GUI
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));



  }
}