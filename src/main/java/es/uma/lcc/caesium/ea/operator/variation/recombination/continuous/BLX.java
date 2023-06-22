package es.uma.lcc.caesium.ea.operator.variation.recombination.continuous;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.ContinuousObjectiveFunction;
import es.uma.lcc.caesium.ea.operator.variation.recombination.RecombinationOperator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * BLX Crossover 
 * @author ccottap
 * @version 1.1
 *
 */
public class BLX extends RecombinationOperator {
	/**
	 * alpha parameter
	 */
	private double alpha;
	
	/**
	 * Generates the operator
	 * @param pars probability of application (passed upwards) and the alpha value
	 */
	public BLX(List<String> pars) {
		super(pars);
		alpha = Double.parseDouble(pars.get(1));
		
	}

	@Override
	protected Individual _apply(List<Individual> parents) {
		Genotype father = parents.get(0).getGenome();
		Genotype mother = parents.get(1).getGenome();
		ContinuousObjectiveFunction p = (ContinuousObjectiveFunction)obj;
		int l = father.length();
		Genotype offspring = new Genotype(l);
		for (int i=0; i<l; i++) {
			double fg = (double)father.getGene(i);
			double mg = (double)mother.getGene(i);
			double minval = Math.min(fg, mg);
			double maxval = Math.max(fg, mg);
			double interval = maxval-minval;
			double explore = alpha*interval;
			double val = minval-explore + EAUtil.random01()*(interval + 2.0*explore);
			offspring.setGene(i, Math.min(p.getMaxVal(i), Math.max(p.getMinVal(i), val)));
		}
		
		Individual ind = new Individual();
		ind.setGenome(offspring);
		return ind;
	}

	@Override
	public String toString() {
		return "BLX(p=" + prob + ", alpha=" + alpha + ")";
	}

	@Override
	public int getArity() {
		return 2;
	}

}
