package es.uma.lcc.caesium.ea.problem.discrete.binary.trap;


import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.DiscreteObjectiveFunction;
import es.uma.lcc.caesium.ea.fitness.OptimizationSense;

/**
 * Unitation trap functions
 * @author ccottap
 * @version 1.0
 *
 */
public abstract class UnitationTrap extends DiscreteObjectiveFunction {
	/**
	 * number of traps
	 */
	protected int numTraps;
	/**
	 * number of bits per trap
	 */
	protected int bitsPerTrap;
	
	/**
	 * Constructs a concatenated trap function 
	 * @param numTraps number of concatenated traps
	 * @param bitsPerTrap number of bits per trap
	 */
	public UnitationTrap(int numTraps, int bitsPerTrap) {
		super(numTraps*bitsPerTrap);
		this.numTraps = numTraps;
		this.bitsPerTrap = bitsPerTrap;
	}
	

	@Override
	public OptimizationSense getOptimizationSense() {
		return OptimizationSense.MAXIMIZATION;
	}

	@Override
	protected double _evaluate(Individual ind) {
		Genotype g = ind.getGenome();
		double f = 0.0;
		for (int i=0, j=0; i<numTraps; i++) {
			int u = 0;
			for (int k=0; k<bitsPerTrap; k++, j++) {
				u+= (int) g.getGene(j);
			}
			f += trapValue (u);
		}
		return f;
	}

	/**
	 * Returns the value of a trap with unitation u
	 * @param u unitation of the trap
	 * @return the value of a trap
	 */
	protected abstract double trapValue(int u);
	
	

}
