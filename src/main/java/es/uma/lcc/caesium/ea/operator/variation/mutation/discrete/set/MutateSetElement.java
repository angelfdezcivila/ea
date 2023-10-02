package es.uma.lcc.caesium.ea.operator.variation.mutation.discrete.set;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.DiscreteObjectiveFunction;
import es.uma.lcc.caesium.ea.operator.variation.mutation.MutationOperator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Bit-flip mutation
 * @author ccottap
 * @version 1.1
 *
 */
public class MutateSetElement extends MutationOperator {
	
	/**
	 * Creates the operator. 
	 * @param pars String representation of the mutation probability
	 */
	public MutateSetElement(List<String> pars) {
		super(pars);
	}

	@Override
	protected Individual _apply(List<Individual> parents) {
		DiscreteObjectiveFunction p = (DiscreteObjectiveFunction)obj;
		Individual ind = parents.get(0).clone();
		Genotype g = ind.getGenome();

		Set<Integer> values = new HashSet<Integer>();
		int l = p.getNumVars();
		for (int i=0; i<l; i++) 
			values.add((int)g.getGene(i));
		
		int n = p.getAlphabetSize(0);
		int pos = EAUtil.random(g.length());
		int v;
		do {
			v = EAUtil.random(n);
		}
		while (values.contains(v));
		g.setGene(pos, v);
		
		ind.touch();
		return ind;
	}

	@Override
	public String toString() {
		return "MutateSetElement(" + prob + ")";
	}

}
