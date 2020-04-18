/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver.job.impl;

import appserver.job.Tool;

/**
 * A tool that computes a fibonacci number.
 * @author zane
 */
public class Fibonacci implements Tool
{

    /**
     * The helper that will compute the fibonacci number.
     */
    FibonacciAux helper = null;
    
    /**
     * Compute a fibonacci number, returning the result.
     * @param parameters an Integer fib number to compute.
     * @return fib(n)
     */
    @Override
    public Object go(Object parameters) 
    {
        
        helper = new FibonacciAux( (Integer) parameters );
        return helper.getResult();
    }
    
}
