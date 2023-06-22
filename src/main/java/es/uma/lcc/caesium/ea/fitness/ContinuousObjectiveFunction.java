package es.uma.lcc.caesium.ea.fitness;

/**
 * Abstract class for objective functions on continuous variables
 * @author ccottap
 * @version 1.0
 */
public abstract class ContinuousObjectiveFunction extends ObjectiveFunction {
	/**
	 * minimum value by default
	 */
	public static final double MINVAL = 0.0;
	/**
	 * maximum value by default
	 */
	public static final double MAXVAL = 1.0;
	
	/**
	 * Minimum value of variables
	 */
	protected double[] minval;
	/**
	 * maximum value of variables
	 */
	protected double[] maxval;
	
	/**
	 * Creates the objective function
	 * @param i number of variables
	 */
	public ContinuousObjectiveFunction(int i) {
		this(i, MINVAL, MAXVAL);
	}
	
	/**
	 * Creates the objective function
	 * @param i number of variables
	 * @param minv minimum value of each variable
	 * @param maxv maximum value of each variable
	 */
	public ContinuousObjectiveFunction(int i, double minv, double maxv) {
		super(i);
		minval = new double[i];
		maxval = new double[i];
		for (int j=0; j<i; j++) {
			minval[j] = minv;
			maxval[j] = maxv;
		}
	}
	
	/**
	 * Returns the minimum value of the i-th variable
	 * @param i index of the variable
	 * @return the minimum value of the i-th variable
	 */
	public double getMinVal (int i) {
		return minval[i];
	}
	
	/**
	 * Sets the minimum value of the i-th variable
	 * @param i index of the variable
	 * @param v value of the lower limit
	 */
	public void setMinVal (int i, double v) {
		minval[i] = v;
	}
	
	/**
	 * Returns the maximum value of the i-th variable
	 * @param i index of the variable
	 * @return the maximum value of the i-th variable
	 */
	public double getMaxVal (int i) {
		return maxval[i];
	}
	
	/**
	 * Sets the maximum value of the i-th variable
	 * @param i index of the variable
	 * @param v value of the upper limit
	 */
	public void setMaxVal (int i, double v) {
		maxval[i] = v;
	}
}
