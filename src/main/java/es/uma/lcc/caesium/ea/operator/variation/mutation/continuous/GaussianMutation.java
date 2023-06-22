package es.uma.lcc.caesium.ea.operator.variation.mutation.continuous;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.ContinuousObjectiveFunction;
import es.uma.lcc.caesium.ea.operator.variation.mutation.MutationOperator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Gaussian mutation
 * @author ccottap
 * @version 1.2
 *
 */
public class GaussianMutation extends MutationOperator {
	/**
	 * to control the amplitude of the mutation. It indicates the standard deviation of the Gaussian mutation
	 * as a percentage of the value of the variable.
	 */
	private double stepsize;
	
	/**
	 * Creates the operator. 
	 * @param pars String representation of the mutation probability
	 */
	public GaussianMutation(List<String> pars) {
		super(pars);
		if (pars.size()>1)
			stepsize = Double.parseDouble(pars.get(1));
		else
			stepsize = 1.0;
	}

	@Override
	protected Individual _apply(List<Individual> parents) {
		ContinuousObjectiveFunction p = (ContinuousObjectiveFunction)obj;
		Individual ind = parents.get(0).clone();
		Genotype g = ind.getGenome();
		
		int pos = EAUtil.random(g.length());
		double v = (double)g.getGene(pos);
		double val = v*(1.0+stepsize*EAUtil.nrandom());
		g.setGene(pos, Math.min(p.getMaxVal(pos), Math.max(p.getMinVal(pos), val)));

		ind.touch();
		return ind;
	}

	@Override
	public String toString() {
		return "GaussianMutation(p=" + prob + ")";
	}

}
