package mtao.challengenfactorial;

import java.util.ArrayList;

public class CardSolver {
	CardSolver(Card c) {
		equations.add(new ArrayList<Node>());

		for(int i=0; i < CardFactory.size; ++i) {
			Node n = new Node(i);
			equations.get(0).add(n);
			n.eval(c);
		}

		for(int level=1; level < CardFactory.size; ++level) {
			equations.add(new ArrayList<Node>());
			for(int sublev = 0; sublev < level; ++sublev) {

				for(Node l: equations.get(sublev)) {  
					for(Node r: equations.get(level-sublev-1)) {
						if(sublev >= level-sublev-1) {
						Node add = new Node(OperatorType.ADDITION,l,r);
						add.eval(c);
						if(add.valid())
							equations.get(level).add(add);
						}
						Node sub = new Node(OperatorType.SUBTRACTION,l,r);
						if(sub.valid())
							sub.eval(c);
						equations.get(level).add(sub);
						if(sublev >= level-sublev-1) {
						Node mul = new Node(OperatorType.MULTIPLICATION,l,r);
						if(mul.valid())
							mul.eval(c);
						equations.get(level).add(mul);
						}
						Node div = new Node(OperatorType.DIVISION,l,r);
						try{
							div.eval(c);
							if(div.valid())
								equations.get(level).add(div);
						}
						catch (BadIntegerDivisionError e) {
							continue;
						}
					}

				}
			}
		}
		ArrayList<Node> top = equations.get(CardFactory.size-1);
		for(Node n: top) {

			if(n.value == CardFactory.targetValue) {
				solutions.add(n);
			}
		}
	}


	ArrayList<ArrayList<Node>> equations = new ArrayList<ArrayList<Node>>();
	ArrayList<Node> solutions = new ArrayList<Node>();


}
