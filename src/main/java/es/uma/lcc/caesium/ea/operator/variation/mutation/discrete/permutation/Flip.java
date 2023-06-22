package es.uma.lcc.caesium.ea.operator.variation.mutation.discrete.permutation;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.operator.variation.mutation.MutationOperator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Flip mutation (reverses two adjacent positions - the permutation is considered circular)
 * @author ccottap
 * @version 1.0
 *
 */
public class Flip extends MutationOperator {
	
	/**
	 * Creates the operator. 
	 * @param pars String representation of the mutation probability
	 */
	public Flip(List<String> pars) {
		super(pars);
	}

	@Override
	protected Individual _apply(List<Individual> parents) {
		Individual ind = parents.get(0).clone();
		Genotype g = ind.getGenome();
		int l = g.length();
		int p1 = EAUtil.random(l);
		int p2 = (p1 + 1) % l;

		Object tmp = g.getGene(p1);
		g.setGene(p1, g.getGene(p2));
		g.setGene(p2, tmp);
		
		ind.touch();

		return ind;
	}

	@Override
	public String toString() {
		return "Flip(" + prob + ")";
	}

}
