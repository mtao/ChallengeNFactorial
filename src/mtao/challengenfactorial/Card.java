package mtao.challengenfactorial;

public class Card {
	Card(int size) {
		data = new int[size];
	}
	Card(int val[]) {
		set(val);
	}
	void set(int i, int val) {
		data[i] = val;
	}
	void set(int val[]) {
		if(data.length == val.length) {
		data = val.clone();
		}
	}
	boolean hasSolution() {
		Equation e = findSolution();
		
		return (e != null) && e.eval(this) == CardFactory.targetValue;
	
	}
	Equation findSolution() {
		CardSolver solver = new CardSolver(this);
		if(solver.solutions.size() > 0) {
			return new Equation(solver.solutions.get(0));
		}
		return null;
	}
	
	Equation findSolution(Equation eqn) {
		return eqn;
	}
	int[] data;

}
