package es.uma.lcc.caesium.ea.operator.variation.mutation.discrete;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.DiscreteObjectiveFunction;
import es.uma.lcc.caesium.ea.operator.variation.mutation.MutationOperator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Random substitution mutation: a position is replaced by a random value from the alphabet for that position
 * @author ccottap
 * @version 1.1
 *
 */
public class RandomSubstitution extends MutationOperator {
	
	/**
	 * Creates the operator. 
	 * @param pars String representation of the mutation probability
	 */
	public RandomSubstitution(List<String> pars) {
		super(pars);
	}

	@Override
	protected Individual _apply(List<Individual> parents) {
		DiscreteObjectiveFunction p = (DiscreteObjectiveFunction)obj;
		Individual ind = parents.get(0).clone();
		Genotype g = ind.getGenome();
		int pos = EAUtil.random(g.length());
		int val = (int)g.getGene(pos);
		do {
			g.setGene(pos, EAUtil.random(p.getAlphabetSize(pos)));
		} while (val == (int)g.getGene(pos));
		ind.touch();
		return ind;
	}

	@Override
	public String toString() {
		return "RandomSubstitution(" + prob + ")";
	}

}
