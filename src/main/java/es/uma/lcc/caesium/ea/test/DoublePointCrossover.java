package es.uma.lcc.caesium.ea.test;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.operator.variation.recombination.RecombinationOperator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Double-Point Crossover (DPX)
 * @author ccottap
 * @version 1.0
 *
 */
public class DoublePointCrossover extends RecombinationOperator {
	
	/**
	 * Generates the operator
	 * @param pars probability of application
	 */
	public DoublePointCrossover(List<String> pars) {
		super(pars);
	}

	@Override
	protected Individual _apply(List<Individual> parents) {
		Genotype father = parents.get(0).getGenome();
		Genotype mother = parents.get(1).getGenome();
		int l = father.length();
		Genotype offspring = new Genotype(l);
		int c1 = EAUtil.random(l-1);
		int c2;
		do {
			c2 = EAUtil.random(l-1);
		} while (c1 == c2);
		if (c1 > c2) {
			int tmp = c1;
			c1 = c2;
			c2 = tmp;
		}
		for (int i=0; i<=c1; i++)
			offspring.setGene(i, father.getGene(i));
		for (int i=c1+1; i<=c2; i++)
			offspring.setGene(i, mother.getGene(i));
		for (int i=c2+1; i<l; i++)
			offspring.setGene(i, father.getGene(i));
		
		Individual ind = new Individual();
		ind.setGenome(offspring);
		return ind;
	}

	@Override
	public String toString() {
		return "DPX(" + prob + ")";
	}

	@Override
	public int getArity() {
		return 2;
	}


}
