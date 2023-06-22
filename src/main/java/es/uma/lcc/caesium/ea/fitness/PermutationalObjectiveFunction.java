package es.uma.lcc.caesium.ea.fitness;


/**
 * Abstract class for objective functions on permutations
 * @author ccottap
 * @version 1.0
 */
public abstract class PermutationalObjectiveFunction extends DiscreteObjectiveFunction {

	/**
	 * Creates the objective function
	 * @param n number of elements
	 */
	public PermutationalObjectiveFunction(int n) {
		super(n, n);
	}
	
}
