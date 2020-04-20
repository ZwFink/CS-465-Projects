/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver.server.loadbalancing;

import appserver.comm.ConnectivityInfo;
import appserver.server.SatelliteManager;

/**
 * This class handles the management of a load balancer, 
 * given a list of satellites held by a satellite manager
 * @author zane
 */
public class LoadBalancingManager 
{
    /**
     * The manager containing satellites
     */
    private SatelliteManager satMan;

    /**
     * A strategy for load balancing
     */
    private LoadBalancingStrategy balancer;

    /**
     * Argument constructor
     * @param satMan The manager containing a list of satellites
     * @param balancer The load balancer, chooses the next satellite to 
     *        compute a job
     */
    public LoadBalancingManager( SatelliteManager satMan,
                                LoadBalancingStrategy balancer 
    )
    {
        this.satMan = satMan;
        this.balancer = balancer;
    }

    /**
     * Get the next satellite from the satellite manager's list of 
     * satellites.
     * @return The next satellite, according to some load balancing strategy.
     */
    public ConnectivityInfo nextSatellite()
    {
        return this.balancer.nextSatellite( satMan.getSatellites() );
    }
    
}
