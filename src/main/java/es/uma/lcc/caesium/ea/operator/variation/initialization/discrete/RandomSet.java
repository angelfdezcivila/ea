package es.uma.lcc.caesium.ea.operator.variation.initialization.discrete;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.DiscreteObjectiveFunction;
import es.uma.lcc.caesium.ea.operator.variation.initialization.InitializationOperator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Creates random subsets of a given size, taken from a larger alphabet
 * @author ccottap
 * @version 1.0
 *
 */
public class RandomSet extends InitializationOperator {
	/**
	 * Generates the operator
	 * @param pars parameters (none)
	 */
	public RandomSet(List<String> pars) {
		super(pars);
	}

	@Override
	protected Individual _apply(List<Individual> parents) {
		DiscreteObjectiveFunction p = (DiscreteObjectiveFunction)obj;
		int l = p.getNumVars();
		Genotype g = new Genotype(l);
		Set<Integer> values = new HashSet<Integer>();
		int n = p.getAlphabetSize(0);
		for (int i=0; i<l; i++) {
			int v;
			do {
				v = EAUtil.random(n);
			}
			while (values.contains(v));
			values.add(v);
			g.setGene(i, v);
		}
		
		Individual ind = new Individual();
		ind.setGenome(g);
		return ind;
	}

	@Override
	public String toString() {
		return "RandomSet";
	}

}
