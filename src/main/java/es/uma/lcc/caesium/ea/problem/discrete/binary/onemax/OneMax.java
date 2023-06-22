package es.uma.lcc.caesium.ea.problem.discrete.binary.onemax;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.DiscreteObjectiveFunction;
import es.uma.lcc.caesium.ea.fitness.OptimizationSense;

/**
 * ONEMAX function
 * @author ccottap
 * @version 1.1
 *
 */
public class OneMax extends DiscreteObjectiveFunction {
	/**
	 * Basic constructor
	 * @param i the number of bits
	 */
	public OneMax(int i) {
		super(i, 2);
	}
	
	/**
	 * Indicates whether the goal is maximization or minimization
	 * @return the optimization sense
	 */
	public OptimizationSense getOptimizationSense()
	{
		return OptimizationSense.MAXIMIZATION;
	}
	

	@Override
	protected double _evaluate(Individual i) {
		int c = 0;
		Genotype g = i.getGenome();
		for (int j=0; j<numvars; j++) {
			c += (Integer)g.getGene(j);
		}
		return (double)c;
	}

}
