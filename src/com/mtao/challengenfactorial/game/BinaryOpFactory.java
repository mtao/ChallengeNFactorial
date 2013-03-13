package com.mtao.challengenfactorial.game;

/**
 * An object that can produce {@link BinaryOpNode}, between two {@link IExpressionNode}s.
 * Welcome to Java... ughhh.
 */
public interface BinaryOpFactory {
	public BinaryOpNode link(IExpressionNode l, IExpressionNode r);
}
