package com.mtao.challengenfactorial.game;

/**
 * Represents a unary op applied at a leaf node (? only leaf nodes allowed)
 * such as square root or cube
 */
public abstract class UnaryOpNode implements IExpressionNode {
	
	public final ValueNode val;
	
	public UnaryOpNode(ValueNode v) {
		val = v;
	}

	@Override
	public abstract float eval();

	@Override
	public int numLeaves() {
		return 1;
	}

	@Override
	public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof UnaryOpNode) {
            UnaryOpNode that = (UnaryOpNode) other;
            result = (this.val == that.val && this.getClass().equals(that.getClass()));
        }
        return result;	
	}
	
}
