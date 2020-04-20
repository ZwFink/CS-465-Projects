/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver.server;

import appserver.comm.ConnectivityInfo;
import java.util.ArrayList;

/**
 * A SatelliteManager manages 
 * information about satellites that are available to 
 * the Application Server.
 * @author zane
 */
public class SatelliteManager 
{
    /**
     * The satellites that are manged by this object.
     */
    ArrayList<ConnectivityInfo> satellites;

    /**
     * Initialize with an empty arraylist
     */
    public SatelliteManager()
    {
        satellites = new ArrayList<>();
    }

    /**
     * Add a satellite to be managed by this object.
     * @param satInfo 
     */
    public void addSatellite( ConnectivityInfo satInfo )
    {
        this.satellites.add( satInfo );
    }

    public ArrayList<ConnectivityInfo> getSatellites()
    {
        return this.satellites;
    }
    
}
