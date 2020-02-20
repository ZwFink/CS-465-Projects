import java.io.*;
import java.net.*;

/**
 * create a node to initialize base connections on main device.
 * @author kenkl
 */
public class MainClass {
   public static void main(String[] args) throws IOException, InterruptedException 
   {
       Node node = new Node("127.0.0.1", 2080);
   }
   
     
}