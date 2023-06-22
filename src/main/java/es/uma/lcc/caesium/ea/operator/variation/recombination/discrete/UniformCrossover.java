package es.uma.lcc.caesium.ea.operator.variation.recombination.discrete;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.operator.variation.recombination.RecombinationOperator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Uniform Crossover (UX)
 * @author ccottap
 * @version 1.1
 *
 */
public class UniformCrossover extends RecombinationOperator {
	
	/**
	 * Generates the operator
	 * @param pars probability of application
	 */
	public UniformCrossover(List<String> pars) {
		super(pars);
	}

	@Override
	protected Individual _apply(List<Individual> parents) {
		Genotype father = parents.get(0).getGenome();
		Genotype mother = parents.get(1).getGenome();
		int l = father.length();
		Genotype offspring = new Genotype(l);

		for (int i=0; i<l; i++)
			if (EAUtil.random(2)>0)
				offspring.setGene(i, father.getGene(i));
			else
				offspring.setGene(i, mother.getGene(i));

		Individual ind = new Individual();
		ind.setGenome(offspring);
		return ind;
	}

	@Override
	public String toString() {
		return "UX(" + prob + ")";
	}

	@Override
	public int getArity() {
		return 2;
	}


}
