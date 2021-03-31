import java.net.*;
import java.util.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws Exception{
      int litenport = 5298; //Match with client destination port. 
      DatagramSocket serversocket = new DatagramSocket(listenport);
      byte[] senddata = new byte[1024];
      byte[] receivedata = new byte[1024];

