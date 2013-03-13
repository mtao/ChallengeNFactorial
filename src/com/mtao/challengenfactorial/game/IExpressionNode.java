package com.mtao.challengenfactorial.game;

/**
 * Interface for a Node in an expression tree
 *
 */
public interface IExpressionNode {
	
	/**
	 * @return the value of the evaluated expression tree
	 */
	public int eval() throws ArithmeticException;
	
	public int numLeaves();
	
}
