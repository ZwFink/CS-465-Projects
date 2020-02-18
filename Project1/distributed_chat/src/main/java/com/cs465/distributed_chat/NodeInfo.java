/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs465.distributed_chat;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * The NodeInfo class tracks information that is necessary to connect to 
 * or display information about any node in the network. A node consists
 * of an IP address, port number, and logical name.
 * @note Once set, the information in this class cannot be changed,
 *       as we assume that information is static during a single chat session. 
 * @author zane
 */
public class NodeInfo implements Serializable
{
	/**
	 * The IP address of the node. 
	 */
	private final InetAddress IpAddr;

	/**
	 * The port this node can be reached on.
	 */
	private final int port;

	/**
	 * The logical name of the node. Will be displayed 
	 * when a human-readable representation is needed.
	 */
	private final String logicalName;

	/**
	 * Parameter-initialize information for a node.
	 * @param ip The IP of the node.
	 * @param port The port number of the node.
	 * @param name A logical name for the node.
	 */
	public NodeInfo( InetAddress ip,
			 int port,
		         String name 
	               )
	{
		this.IpAddr      = ip;
		this.port        = port;
		this.logicalName = name;

	}

	/**
	 * Get the IP address this node can be reached at.
	 * @return The IPaddress of the node.
	 */
	public InetAddress getIPAddress()
	{
		return this.IpAddr;
	}

	/**
	 * Get the port this node uses.
	 * @return the port for this node.
	 */
	public int getPort()
	{
		return this.port;
	}

	/**
	 * Get this node's name.
	 * @return the name of this node.
	 */
	public String getName()
	{
		return	this.logicalName;
	}
	
}
