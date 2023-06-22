
package es.uma.lcc.caesium.ea.problem.discrete.binary.trap;

/**
 * Single deceptive trap. It has a single global optimum when all bits are 
 * set to 1, and a single local optimum when all bits are set to 0. 
 * @author ccottap
 * @version 1.0
 *
 */
public class DeceptiveTrap extends UnitationTrap {
	/**
	 * slope of the function on the way to the local optimum
	 */
	private double slope;
	/**
	 * value of the local optimum (&lt; 1.0)
	 */
	private double intercept;
	
	/**
	 * Constructs a concatenated deceptive trap function 
	 * @param numTraps number of concatenated traps
	 * @param bitsPerTrap number of bits per trap
	 */
	public DeceptiveTrap(int numTraps, int bitsPerTrap) {
		super(numTraps, bitsPerTrap);
		intercept = ((double)bitsPerTrap-1)/bitsPerTrap;
		slope = 1.0/(double)bitsPerTrap;
	}

	@Override
	protected double trapValue(int u) {
		if (u==bitsPerTrap)
			return 1.0;
		else
			return intercept - slope*u;
	}

}
