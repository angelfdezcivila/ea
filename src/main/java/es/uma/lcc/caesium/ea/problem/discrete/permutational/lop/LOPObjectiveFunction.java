/**
 * 
 */
package es.uma.lcc.caesium.ea.problem.discrete.permutational.lop;

import java.util.ArrayList;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.OptimizationSense;
import es.uma.lcc.caesium.ea.fitness.PermutationalObjectiveFunction;

/**
 * Objective function for the LOP
 * @author ccottap
 * @version 1.0
 *
 */
public class LOPObjectiveFunction extends PermutationalObjectiveFunction {
	/**
	 * instance data
	 */
	protected LinearOrderingProblem lop;
	
	/**
	 * Construct an new objective function for a random instance
	 * @param size size of the instance
	 */
	public LOPObjectiveFunction (int size) {
		super(size);
		lop = new LinearOrderingProblem(size);
	}
	
	/**
	 * Construct an new objective function for a given instance
	 * @param data the instance
	 */
	public LOPObjectiveFunction (LinearOrderingProblem data) {
		super(data.size());
		lop = data;
	}

	@Override
	public OptimizationSense getOptimizationSense() {
		return OptimizationSense.MAXIMIZATION;
	}

	@Override
	protected double _evaluate(Individual i) {
		Genotype g = i.getGenome();
		int l = g.length();
		ArrayList<Integer> sol = new ArrayList<Integer>(lop.size());
		for (int j=0; j<l; j++)
			sol.add((int)g.getGene(j));
		return lop.evaluate(sol);
	}

}
