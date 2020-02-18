/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs465.distributed_chat;

import java.net.InetAddress;

/**
 *
 * @author zane
 */
public class NodeInfo 
{
	private InetAddress IpAddr;
	private int port;
	String logicalName;

	public NodeInfo( InetAddress ip,
			 int port,
		         String name 
	               )
	{
		this.IpAddr      = ip;
		this.port        = port;
		this.logicalName = name;

	}
	
}
