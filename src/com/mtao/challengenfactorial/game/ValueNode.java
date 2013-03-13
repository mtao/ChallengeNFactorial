package com.mtao.challengenfactorial.game;

public final class ValueNode implements IExpressionNode {
	
	public final int val;
	
	public ValueNode(int v) {
		val = v;
	}

	@Override
	public float eval() {
		// TODO Auto-generated method stub
		return val;
	}

	@Override
	public int numLeaves() {
		return 1;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		
		if (other instanceof ValueNode) {
			ValueNode that = (ValueNode) other;
			return this.val == that.val;
		}
		
		return false;
	}

}
