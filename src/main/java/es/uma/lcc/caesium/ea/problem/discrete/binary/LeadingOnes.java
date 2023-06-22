package es.uma.lcc.caesium.ea.problem.discrete.binary;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.DiscreteObjectiveFunction;
import es.uma.lcc.caesium.ea.fitness.OptimizationSense;

/**
 * Leading-ones function
 * @author ccottap
 * @version 1.1
 *
 */
public class LeadingOnes extends DiscreteObjectiveFunction {
	/**
	 * Basic constructor
	 * @param i the number of bits
	 */
	public LeadingOnes(int i) {
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
		while ((c<numvars) && ((Integer)g.getGene(c) == 1)) 
			c++;
		return (double)c;
	}

}
