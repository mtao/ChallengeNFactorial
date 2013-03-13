package com.mtao.challengenfactorial.game;

/**
 * A helper class to define some common operator nodes
 */
public final class OpFactory {
	
	public static BinaryOpNode add(IExpressionNode l, IExpressionNode r) {
		return new BinaryOpNode(l, r) {
			@Override public String opString() { return "+"; }
			
			@Override public float eval() { return left.eval() + right.eval(); }
		};
	}
	
	public static BinaryOpNode sub(IExpressionNode l, IExpressionNode r) {
		return new BinaryOpNode(l, r) {
			@Override public String opString() { return "-"; }
			
			@Override public float eval() { return left.eval() - right.eval(); }
		};
	}
	
	public static BinaryOpNode mul(IExpressionNode l, IExpressionNode r) {
		return new BinaryOpNode(l, r) {
			@Override public String opString() { return "*"; }
			
			@Override public float eval() { return left.eval() * right.eval(); }
		};
	}
	
	public static BinaryOpNode div(IExpressionNode l, IExpressionNode r) {
		return new BinaryOpNode(l, r) {
			@Override public String opString() { return "/"; }
			
			@Override public float eval() { return left.eval() / right.eval(); }
		};
	}

}
