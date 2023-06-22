package es.uma.lcc.caesium.ea.operator.variation.initialization.discrete;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.operator.variation.initialization.InitializationOperator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Generates a permutational individual
 * @author ccottap
 * @version 1.1
 */
public class RandomPermutation extends InitializationOperator {

	/**
	 * Generates the operator
	 * @param pars parameters (none)
	 */
	public RandomPermutation(List<String> pars) {
		super(pars);
	}

	@Override
	protected Individual _apply(List<Individual> parents) {
		int l = obj.getNumVars();
		Genotype g = new Genotype(l);
		List<Integer> perm = EAUtil.randomPermutation(l);
		for (int i=0; i<l; i++) 
			g.setGene(i, perm.get(i));
		
		Individual ind = new Individual();
		ind.setGenome(g);
		return ind;
	}

	@Override
	public String toString() {
		return "RandomPermutation";
	}

}
