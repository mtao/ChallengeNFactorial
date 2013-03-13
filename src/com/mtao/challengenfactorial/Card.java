package com.mtao.challengenfactorial;

class Card {
	
	public Card(int size) {
		data = new int[size];
	}
	public Card(int val[]) {
		set(val);
	}
	
	public int get(int i) {
		return data[i];
	}
	
	public void set(int i, int val) {
		data[i] = val;
	}
	public void set(int val[]) {
		if(data.length == val.length) {
		data = val.clone();
		}
	}
	
	public boolean hasSolution() {
		Equation e = findSolution();
		
		return (e != null) && e.eval(this) == CardFactory.targetValue;
	
	}
	public Equation findSolution() {
		CardSolver solver = new CardSolver(this);
		if(solver.solutions.size() > 0) {
			return new Equation(solver.solutions.get(0));
		}
		return null;
	}
	
	public Equation findSolution(Equation eqn) {
		return eqn;
	}
	private	int[] data;
	
	@Override
	public String toString() {
		String result = "";
		for (int i : data) {
			result += String.format("%d ", i);
		}
		
		return result;
	}
	public int[] values() {
		return data;

	}

}
