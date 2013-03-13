package com.mtao.challengenfactorial.game;

public final class ValueNode implements IExpressionNode {
	
	private final int mVal;
	
	public ValueNode(int v) {
		mVal = v;
	}

	@Override
	public float eval() {
		// TODO Auto-generated method stub
		return mVal;
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
			return this.mVal == that.mVal;
		}
		
		return false;
	}

}
