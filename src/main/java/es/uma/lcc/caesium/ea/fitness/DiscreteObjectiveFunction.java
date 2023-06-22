package es.uma.lcc.caesium.ea.fitness;


/**
 * Abstract class for objective functions on discrete variables
 * @author ccottap
 * @version 1.0
 */
public abstract class DiscreteObjectiveFunction extends ObjectiveFunction {
	/**
	 * alphabet size by default
	 */
	public static final int NUMVAL = 2;
	
	/**
	 * Number of values of variables (alphabet size for each variable)
	 */
	protected int[] numval;

	
	/**
	 * Creates the objective function
	 * @param i number of variables
	 */
	public DiscreteObjectiveFunction(int i) {
		this(i, NUMVAL);
	}
	
	/**
	 * Creates the objective function
	 * @param n number of variables
	 * @param size alphabet size
	 */
	public DiscreteObjectiveFunction(int n, int size) {
		super(n);
		numval = new int[n];
		for (int j=0; j<n; j++) {
			setAlphabetSize(j, size);
		}
	}
	
	/**
	 * Returns the alphabet size of the i-th variable
	 * @param i index of the variable
	 * @return the alphabet size of the i-th variable
	 */
	public int getAlphabetSize (int i) {
		return numval[i];
	}
	
	/**
	 * Sets the alphabet size of the i-th variable
	 * @param i index of the variable
	 * @param v value of the alphabet size
	 */
	public void setAlphabetSize (int i, int v) {
		numval[i] = v;
	}
	
}
