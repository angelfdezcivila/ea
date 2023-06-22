package es.uma.lcc.caesium.ea.operator.variation.recombination.discrete;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.operator.variation.recombination.RecombinationOperator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Single-Point Crossover (SPX)
 * @author ccottap
 * @version 1.2
 *
 */
public class SinglePointCrossover extends RecombinationOperator {
	
	/**
	 * Generates the operator
	 * @param pars probability of application
	 */
	public SinglePointCrossover(List<String> pars) {
		super(pars);
	}

	@Override
	protected Individual _apply(List<Individual> parents) {
		Genotype father = parents.get(0).getGenome();
		Genotype mother = parents.get(1).getGenome();
		int l = father.length();
		Genotype offspring = new Genotype(l);
		int c = EAUtil.random(l-1);
		for (int i=0; i<=c; i++)
			offspring.setGene(i, father.getGene(i));
		for (int i=c+1; i<l; i++)
			offspring.setGene(i, mother.getGene(i));

		Individual ind = new Individual();
		ind.setGenome(offspring);
		return ind;
	}

	@Override
	public String toString() {
		return "SPX(" + prob + ")";
	}

	@Override
	public int getArity() {
		return 2;
	}


}
