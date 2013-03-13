package com.mtao.challengenfactorial.test;

import java.util.Random;

import com.mtao.challengenfactorial.game.BinaryOpNode;
import com.mtao.challengenfactorial.game.OpFactory;
import com.mtao.challengenfactorial.game.ValueNode;

import junit.framework.TestCase;

public class BinaryOpNodeTest extends TestCase {
	
	private final Random mRng = new Random();
	private ValueNode lNode;
	private ValueNode rNode;
	private BinaryOpNode tree;

	protected void setUp() throws Exception {
		super.setUp();
		lNode = new ValueNode(mRng.nextInt());
		rNode = new ValueNode(mRng.nextInt());
		
		tree = OpFactory.add(new ValueNode(42), OpFactory.mul(new ValueNode(69), new ValueNode(7)));
	}

	public void testBinaryOpNode() {
		BinaryOpNode bin = OpFactory.add(lNode, rNode);
		assertEquals(bin.left, lNode);
		assertEquals(bin.right, rNode);
	}

	public void testEval() {
		BinaryOpNode bin = OpFactory.add(lNode, rNode);
		assertEquals(bin.eval(), lNode.val + rNode.val);
	}
	
	public void testEvalTree() {
		assertEquals(tree.eval(), 42+(69*7));
	}

	public void testNumLeaves() {
		BinaryOpNode bin = OpFactory.add(lNode, rNode);
		assertEquals(bin.numLeaves(), 2);
	}
	
	public void testNumLeavesTree() {
		assertEquals(tree.numLeaves(), 3);
	}

	public void testToString() {
		BinaryOpNode bin = OpFactory.add(new ValueNode(42), new ValueNode(69));
		assertEquals(bin.toString(), "(42+69)");
	}
	
	public void testToStringTree() {
		assertEquals(tree.toString(), "(42+(69*7))");
	}

	public void testEqualsObject() {
		BinaryOpNode a = OpFactory.add(lNode, rNode);
		BinaryOpNode b = OpFactory.add(lNode, rNode);
		assertEquals(a, b);
	}

	public void testNotEqualsObject() {
		ValueNode l = new ValueNode(42);
		ValueNode r = new ValueNode(69);
		ValueNode other = new ValueNode(7);
		
		BinaryOpNode a = OpFactory.add(l, r);
		BinaryOpNode b = OpFactory.sub(l, r);
		assertNotSame(a, b);
		
		BinaryOpNode c = OpFactory.add(l, other);
		assertNotSame(a, c);
	}
}
