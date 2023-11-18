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
 * @version 1.3
 *
 */
public class GaussianMutation extends MutationOperator {
	/**
	 * to control the amplitude of the mutation. It indicates the standard deviation of the Gaussian mutation
	 * as a percentage of the value of the variable.
	 */
	private double stepsize;
	/**
	 * This flag controls whether in case of exceeding the upper/lower limit of a variable,
	 * its value is saturated at that value or wraps around to the other end of the range.
	 */
	private boolean wrap = false;
	
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
		if (pars.size()>2)
			wrap = Boolean.parseBoolean(pars.get(2));
	}

	@Override
	protected Individual _apply(List<Individual> parents) {
		ContinuousObjectiveFunction p = (ContinuousObjectiveFunction)obj;
		Individual ind = parents.get(0).clone();
		Genotype g = ind.getGenome();
		
		int pos = EAUtil.random(g.length());
		double v = (double)g.getGene(pos);
		double val = v*(1.0+stepsize*EAUtil.nrandom());
		if (!wrap)
			val = Math.min(p.getMaxVal(pos), Math.max(p.getMinVal(pos), val));
		else {
			double minV = p.getMinVal(pos);
			double maxV = p.getMaxVal(pos);
			while (val < minV) {
				val = maxV - (minV - val);
			}
			while (val > maxV) {
				val = minV + (val - maxV);
			}
		}
		g.setGene(pos, val);
		ind.touch();
		return ind;
	}

	@Override
	public String toString() {
		return "GaussianMutation(p=" + prob + ", " + wrap + ")";
	}

}
