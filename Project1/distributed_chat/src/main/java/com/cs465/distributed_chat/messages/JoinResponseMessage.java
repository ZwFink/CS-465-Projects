/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs465.distributed_chat.messages;

import com.cs465.distributed_chat.NodeInfo;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A JoinResponse message contains a list of 
 * NodeInfo for each of the nodes in a network
 * @note When crafting a JoinResponse, nodes should not include 
 *       their own information, as the node that sent the JoinRequest
 *       already has that information.
 * @author zane
 */
public class JoinResponseMessage implements MessageType, Serializable 
{
	/**
	 * List of the other nodes that are in the network.
	 * nodesInNetwork + sendingNode make up the entire network.
	 */
	final LinkedList<NodeInfo> nodesInNetwork;

	/**
	 * The node that is sending the JoinResponse.
	 */
	final NodeInfo sendingNode;

	/**
	 * Get the type of this message.
	 * @return JOIN_RESPONSE message type
	 */
	@Override
	public MessageType.MessageTypes getType()
	{
		return MessageType.MessageTypes.JOIN_RESPONSE;	
	}

	/**
	 * Initialize a JoinResponse message with an ArrayList of 
	 * NodeInfo and the info for the node that will send the response.
	 * @param nodes A list of nodes on the network, NOT including 
	 *        this node and the node that is receiving the JoinRequest 
	 *        message.
	 * @param sendingNodeInfo The information for the node that will 
	 *                        be sending the JoinResponse message.
	 * @throws IllegalArgumentException If copying NodeInfo objects 
	 *         results in an error.
	 */
	public JoinResponseMessage( LinkedList<NodeInfo> nodes,
				    NodeInfo sendingNodeInfo 
	                          ) throws IllegalArgumentException
	{
		this.nodesInNetwork = new LinkedList();

		try
		{
                        int counter = 0;
                        NodeInfo info = (NodeInfo) nodes.get(counter);
			while( info != null )
			{
				this.nodesInNetwork.add( new NodeInfo( info ));
                                counter++;
                                info = (NodeInfo) nodes.get(counter);
			}

			this.sendingNode = new NodeInfo( sendingNodeInfo );
		}
		catch( UnknownHostException e )
		{
			throw new 
	                IllegalArgumentException( "Input NodeInfo threw "
				                  + "an UnknownHostException"
			                        );	
		}
	}

	/**
	 * Retrieve the information about the node that sent the
	 * message.
	 * @return NodeInfo about the node sending a message.
	 */
	public NodeInfo getInfo()
	{
		return this.sendingNode;	
	}
        
        /**
	 * Retrieve the list payload of the message.
	 * @return LinkedList of nodeInfo objects.
	 */
	public LinkedList<NodeInfo> getList()
	{
		return this.nodesInNetwork;	
	}

		
}
