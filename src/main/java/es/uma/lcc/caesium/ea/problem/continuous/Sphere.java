package es.uma.lcc.caesium.ea.problem.continuous;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.ContinuousObjectiveFunction;
import es.uma.lcc.caesium.ea.fitness.OptimizationSense;

/**
 * Sphere function
 * @author ccottap
 * @version 1.0
 *
 */
public class Sphere extends ContinuousObjectiveFunction {
	/**
	 * Basic constructor
	 * @param i the number of variables and their range
	 * @param v range of variables ([-v, v])
	 */
	public Sphere(int i, double v) {
		super(i, -v, v);
	}
	
	/**
	 * Indicates whether the goal is maximization or minimization
	 * @return the optimization sense
	 */
	public OptimizationSense getOptimizationSense()
	{
		return OptimizationSense.MINIMIZATION;
	}
	
	@Override
	protected double _evaluate(Individual i) {
		double c = 0;
		Genotype g = i.getGenome();
		for (int j=0; j<numvars; j++) {
			double v = (double)g.getGene(j);
			c += v*v;
		}
		return c;
	}

}
