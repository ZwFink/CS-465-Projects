/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver.job.impl;

/**
 * A class to compute the n'th fibonacci number.
 * @author zane
 */
public class FibonacciAux 
{
    Integer n;

    /**
     * Initialize the class, passing the fibonacci number 
     * to compute.
     * @param n The n'th fibonacci number this class will compute when 
     *       getResult() is called.
     */
    public FibonacciAux( Integer n )
    {
        this.n = n;
    }

    public int getResult()
    {
        return fib( this.n );    
    }

    /**
     * Compute the n'th fibonacci number, recursively.
     * @param n The fibonacci number that should be computed.
     * @return fib( n )
     */
    private Integer fib( Integer n )
    {
        if( n == 0 )
        {
            return 0;
        }
        else if( n == 1 )
        {
            return 1;
        }
        return fib( n - 1 ) + fib( n - 2 );
    }
    
}
