package es.uma.lcc.caesium.ea.operator.variation.mutation.discrete.permutation;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.operator.variation.mutation.MutationOperator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * swap mutation
 * @author ccottap
 * @version 1.2
 *
 */
public class Swap extends MutationOperator {
	
	/**
	 * Creates the operator. 
	 * @param pars String representation of the mutation probability
	 */
	public Swap(List<String> pars) {
		super(pars);
	}

	@Override
	protected Individual _apply(List<Individual> parents) {
		Individual ind = parents.get(0).clone();
		Genotype g = ind.getGenome();
		
		int l = g.length();
		int p1 = EAUtil.random(l);
		int p2 = (p1 + EAUtil.random(l-1) + 1) % l;
		
		Object tmp = g.getGene(p1);
		g.setGene(p1, g.getGene(p2));
		g.setGene(p2, tmp);
		
		ind.touch();

		return ind;
	}

	@Override
	public String toString() {
		return "Swap(" + prob + ")";
	}

}
