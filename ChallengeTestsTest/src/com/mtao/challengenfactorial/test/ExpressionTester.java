package com.mtao.challengenfactorial.test;

import java.util.Random;

import com.mtao.challengenfactorial.game.ValueNode;

import junit.framework.TestCase;

public class ExpressionTester extends TestCase {
	
	private Random mRng;
	
	public ExpressionTester() {
		mRng = new Random();
	}

	public void testValueNode() {
		int r = mRng.nextInt();
		ValueNode v = new ValueNode(r);
		assertEquals(v.val, r);
	}

	public void testEval() {
		int r = mRng.nextInt(100);
		ValueNode v = new ValueNode(r);
		assertEquals(v.eval(), (float)r);
	}

	public void testNumLeaves() {
		int r = mRng.nextInt();
		ValueNode v = new ValueNode(r);
		assertEquals(v.numLeaves(), 1);
	}

	public void testEqualsObject() {
		int r = mRng.nextInt();
		ValueNode a = new ValueNode(r);
		ValueNode b = new ValueNode(r);
		assertEquals(a, b);
	}

}
