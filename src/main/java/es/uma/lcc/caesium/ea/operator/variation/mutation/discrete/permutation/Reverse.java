package es.uma.lcc.caesium.ea.operator.variation.mutation.discrete.permutation;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.operator.variation.mutation.MutationOperator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Reverse mutation
 * @author ccottap
 * @version 1.0
 *
 */
public class Reverse extends MutationOperator {
	
	/**
	 * Creates the operator. 
	 * @param pars String representation of the mutation probability
	 */
	public Reverse(List<String> pars) {
		super(pars);
	}

	@Override
	protected Individual _apply(List<Individual> parents) {
		Individual ind = parents.get(0).clone();
		Genotype g = ind.getGenome();
		
		int l = g.length();
		int p1 = EAUtil.random(l);
		int p2 = (p1 + EAUtil.random(l-1) + 1) % l;
		if (p2 < p1) {
			int tmp = p1;
			p1 = p2;
			p2 = tmp;
		}
		for (int i=p1, j=p2; i<j; i++, j--) {
			Object tmp = g.getGene(i);
			g.setGene(i, g.getGene(j));
			g.setGene(j, tmp);
		}

		ind.touch();
		return ind;
	}

	@Override
	public String toString() {
		return "Swap(" + prob + ")";
	}

}
