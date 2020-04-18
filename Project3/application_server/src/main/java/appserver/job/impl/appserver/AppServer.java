/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver.job.impl.appserver;

/**
 * Details from BBLearn:
    * Remember that the application server is just passing through a client's
    * request to the satellite server that was selected by the application server's
    * load balancing manager. That means that how a job is processed is really
    * transparent to the client. That said, all it takes to set up a client is to
    * change the properties file to be that of the application server, instead of
    * the satellite server.
    *
    * The value that the application server adds, compared to a satellite server,
    * is distributing jobs across a number of registered satellite servers, i.e.
    * load balancing. In order to achieve this, the application server is supported
    * by two managers:

       * A satellite manager, which keeps track of all the satellites that have
       * registered. For that to work, you need to: Implement functionality in class
       * Satellite that allows a satellite server to register itself with the
       * application server, providing its connectivity information (i.e. It's name,
       * IP and port number). Implement functionality in the application server that
       * responds to satellite registration requests and forwards the information
       * received to its satellite manager. Please note that this is the matching part
       * to the functionality implemented in the satellite discussed in the prior
       * bullet point. 

       * A load balancing manager. This manager can potentially
       * implement any load balancing policy. It responds to requests from the
       * application server asking for the next satellite to do a job. For simplicity,

    * I want you to implement a "round-robin" policy. Whenever a satellite server
    * registers with the client, the load manager is also informed, so that it
    * knows about the existence of the new satellite server.
    * 
 * @author caleb johnson
 */
public class AppServer
{
    
}
