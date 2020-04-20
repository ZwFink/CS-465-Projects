/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver.server.loadbalancing;

import appserver.comm.ConnectivityInfo;
import java.util.ArrayList;

/**
 * A Round Robin Load Balancer implements the round robin load balancing 
 * strategy.
 * @author zane
 */
public class RoundRobinLoadBalancing implements LoadBalancingStrategy
{
    /**
     * The index of the next satellite
     */
    private int idx = 0;

    /**
     * Get the next satellite out of an arraylist of satellites
     * @param satellites The satellites to choose from
     * @return The next satellite, according to the round robin scheme.
     */
    @Override
    public ConnectivityInfo nextSatellite( ArrayList<ConnectivityInfo> satellites )
    {
        int nextSatIdx = idx % satellites.size();
        ++idx;
        return satellites.get( nextSatIdx );
    }
    
}
