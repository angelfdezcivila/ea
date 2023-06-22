package es.uma.lcc.caesium.ea.operator.variation.mutation.discrete;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.operator.variation.mutation.MutationOperator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Bit-flip mutation
 * @author ccottap
 * @version 1.1
 *
 */
public class BitFlip extends MutationOperator {
	
	/**
	 * Creates the operator. 
	 * @param pars String representation of the mutation probability
	 */
	public BitFlip(List<String> pars) {
		super(pars);
	}

	@Override
	protected Individual _apply(List<Individual> parents) {
		Individual ind = parents.get(0).clone();
		Genotype g = ind.getGenome();
		int pos = EAUtil.random(g.length());
		g.setGene(pos, 1-(Integer)g.getGene(pos));
		ind.touch();
		return ind;
	}

	@Override
	public String toString() {
		return "BitFlip(" + prob + ")";
	}

}
