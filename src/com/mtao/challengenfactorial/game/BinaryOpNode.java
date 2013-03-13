package com.mtao.challengenfactorial.game;

/**
 * Represents a binary operator node in an expression tree
 */
public abstract class BinaryOpNode implements IExpressionNode {
	
	public final IExpressionNode left;
	public final IExpressionNode right;
	
	public BinaryOpNode(IExpressionNode l, IExpressionNode r) {
		left = l;
		right = r;
	}

	@Override
	public abstract float eval();
	
	public abstract String opString();

	@Override
	public int numLeaves() {
		return left.numLeaves() + right.numLeaves();
	}
	
	@Override
	public String toString() {
		return String.format("(%s%s%s)", left.toString(), opString(), right.toString() );
	}
	
	@Override
	public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof BinaryOpNode) {
            BinaryOpNode that = (BinaryOpNode) other;
            result = (this.left == that.left && this.right == that.right
                    && this.getClass().equals(that.getClass()));
        }
        return result;	
	}

}
