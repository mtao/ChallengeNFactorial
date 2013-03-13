package com.mtao.challengenfactorial.game;

import java.util.ArrayList;

public class TreeBuilder {
	
	private final ArrayList<BinaryOpFactory> mCommutativeOps;
	private final ArrayList<BinaryOpFactory> mNoncommutativeOps;
	
	public TreeBuilder(ArrayList<BinaryOpFactory> commutativeOps,
						ArrayList<BinaryOpFactory> noncommutativeOps) {
		mCommutativeOps = commutativeOps;
		mNoncommutativeOps = noncommutativeOps;
	}
	
	/**
	 * Builds all possible trees from a list of values
	 */
	public ArrayList<IExpressionNode> buildFrom(int[] values) {
		return buildFrom(values, 0, values.length-1);
	}
	
	/**
	 * Builds all possible trees from a list of values
	 * @param values values to be embedded in a tree
	 * @param first index of first value
	 * @param last index of last value (inclusive)
	 * @return all possible trees built
	 */
	public ArrayList<IExpressionNode> buildFrom(int[] values, int first, int last) {
		ArrayList<IExpressionNode> result = new ArrayList<IExpressionNode>();
		if (last < first) { // no values to put in a tree
			return result;
		}
		else if (first == last) { // one value
			result.add(new ValueNode(values[first]));
			return result;
		}
		
		// now create all possible sub trees and join them with possible ops
		for (int partition = first; first < last; ++first) {
			ArrayList<IExpressionNode> lResult = buildFrom(values, first, partition);
			ArrayList<IExpressionNode> rResult = buildFrom(values, partition+1, last);
			
			for (IExpressionNode lnode : lResult) {
				for (IExpressionNode rnode : rResult) {
					for (BinaryOpFactory op : mCommutativeOps) {
						result.add( op.link(lnode, rnode) );
					}
					
					for (BinaryOpFactory op : mNoncommutativeOps) {
						result.add( op.link(lnode, rnode) );
					}
				}
			}
			
		}
		
		return result;
	}
	
	public static TreeBuilder defaultBuilder() {
		ArrayList<BinaryOpFactory> commutativeOps = new ArrayList<BinaryOpFactory>();
		ArrayList<BinaryOpFactory> noncommutativeOps = new ArrayList<BinaryOpFactory>();
		
		// add
		commutativeOps.add(new BinaryOpFactory() {
			@Override
			public BinaryOpNode link(IExpressionNode l, IExpressionNode r) {
				return new BinaryOpNode(l, r) {
					@Override public String opString() { return "+"; }
					@Override public int eval() { return left.eval() + right.eval(); }
				};
			}
		});
		
		// mul
		commutativeOps.add(new BinaryOpFactory() {
			@Override
			public BinaryOpNode link(IExpressionNode l, IExpressionNode r) {
				return new BinaryOpNode(l, r) {
					@Override public String opString() { return "*"; }
					@Override public int eval() { return left.eval() * right.eval(); }
				};
			}
		});
		
		// sub
		noncommutativeOps.add(new BinaryOpFactory() {
			@Override
			public BinaryOpNode link(IExpressionNode l, IExpressionNode r) {
				return new BinaryOpNode(l, r) {
					@Override public String opString() { return "-"; }
					@Override public int eval() { return left.eval() - right.eval(); }
				};
			}
		});
		
		return new TreeBuilder(commutativeOps, noncommutativeOps);
	}

}
