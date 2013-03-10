package com.mtao.challengenfactorial;


class BadIntegerDivisionError extends ArithmeticException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
class Node{
	int index;
	
	Node left;
	Node right;
	int leaves=1;
	int value;
	OperatorType type = OperatorType.VALUE;
	Node(int i){
		index = i;
		varusage = new int[CardFactory.size];
		varusage[i] = 1;
	}
	Node() {
		varusage = new int[CardFactory.size];
	}
	boolean valid(){
		for(int i=0; i < CardFactory.size; ++i) {
			if(varusage[i] > 1) {
				return false;
			}
		}
		return true;
	}
	int varusage[];
	Node(OperatorType t, Node lhs, Node rhs) {
		varusage = new int[CardFactory.size];
		for(int i=0; i < CardFactory.size; ++i) {
			varusage[i] = lhs.varusage[i] + rhs.varusage[i];
		}
		type=t;
		left=lhs;
		right=rhs;
		leaves = left.leaves + right.leaves;
	}
	
	int eval(Card c) {
		switch(type) {
		case VALUE:
			value =  c.get(index);
			break;
		case ADDITION:
			value = left.eval(c) + right.eval(c);
			break;
		case SUBTRACTION:
			value = left.eval(c) - right.eval(c);
			break;
		case MULTIPLICATION:
			value = left.eval(c) * right.eval(c);
			break;
		case DIVISION:
			int l = left.eval(c);
			int r = right.eval(c);
			if(r == 0 || l%r!=0) {
				throw new BadIntegerDivisionError();
			}
			value = l/r;
		}
		return value;
	}
	/*
	void setType(OperatorType t) {
		type = t;
		switch(type) {
		case VALUE:
			return;
		default:
			left = new Node();
			right = new Node();
		}
	}*/
}



public class Equation {


	static String toString(Node n, Card c) {
		switch(n.type) {
		case VALUE:
			return Integer.toString(n.eval(c));
		case ADDITION:
			return "(" + toString(n.left,c) +"+"+ toString(n.right,c) + ")";
		case SUBTRACTION:
			return "(" + toString(n.left,c) +"-"+ toString(n.right,c) + ")";
		case MULTIPLICATION:
			return "(" + toString(n.left,c) +"*"+ toString(n.right,c) + ")";
		case DIVISION:
			return "(" + toString(n.left,c) +"/"+ toString(n.right,c) + ")";
		}
		return null;
	}
	String toString(Card c) {
		return toString(root,c);
	}
	int eval(Card c) {
		return root.eval(c);
	}
	Equation(Node n) {
		root = n;
	}
	Node root = new Node();
}
