/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.*;
import java.io.*;



/**
 *
 * @author kenkl
 */
public class NodeThread extends Thread
{
  private DataInputStream  inputMessage  =  null;
  private DataOutputStream outputMessage = null;
  private Node node;
  private Socket socket;
  public String userName;
  private Boolean flag;

    public NodeThread(Node node, Socket socket, String logicalName) 
    {

      this.node = node;
      this.socket = socket;
      this.userName = logicalName;
      this.flag = true;

      System.out.println("Starting Thread");

  }
    
}
