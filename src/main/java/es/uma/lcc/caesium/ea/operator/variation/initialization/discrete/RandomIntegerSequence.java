package es.uma.lcc.caesium.ea.operator.variation.initialization.discrete;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.DiscreteObjectiveFunction;
import es.uma.lcc.caesium.ea.operator.variation.initialization.InitializationOperator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Creates random integer sequences
 * @author ccottap
 * @version 1.0
 *
 */
public class RandomIntegerSequence extends InitializationOperator {
	/**
	 * Generates the operator
	 * @param pars parameters (none)
	 */
	public RandomIntegerSequence(List<String> pars) {
		super(pars);
	}

	@Override
	protected Individual _apply(List<Individual> parents) {
		DiscreteObjectiveFunction p = (DiscreteObjectiveFunction)obj;
		int l = p.getNumVars();
		Genotype g = new Genotype(l);
		for (int i=0; i<l; i++) 
			g.setGene(i, EAUtil.random(p.getAlphabetSize(i)));
		
		Individual ind = new Individual();
		ind.setGenome(g);
		return ind;
	}

	@Override
	public String toString() {
		return "RandomIntegerSequence";
	}

}
