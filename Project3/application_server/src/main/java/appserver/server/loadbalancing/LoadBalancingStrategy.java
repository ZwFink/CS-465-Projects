/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver.server.loadbalancing;

import appserver.comm.ConnectivityInfo;
import java.util.ArrayList;

/**
 * A strategy for picking a satellite from a list of connectivity info
 * @author zane
 */
public interface LoadBalancingStrategy 
{
    /**
     * Retrieve the nextSatellite, according to some load balancing strategy
     * @param satellites The satellites to pick from
     * @return The next satellite.
     */
    public ConnectivityInfo nextSatellite( ArrayList<ConnectivityInfo> satellites );
    
}
