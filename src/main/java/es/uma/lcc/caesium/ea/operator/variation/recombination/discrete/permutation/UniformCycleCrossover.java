package es.uma.lcc.caesium.ea.operator.variation.recombination.discrete.permutation;

import java.util.HashMap;
import java.util.List;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.operator.variation.recombination.RecombinationOperator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Uniform cycle crossover operator for permutations. It identifies
 * cycles in the permutations and mixes them randomly
 * @author ccottap
 * @version 1.0
 *
 */
public class UniformCycleCrossover extends RecombinationOperator {

	/**
	 * Generates the operator
	 * @param pars probability of application
	 */
	public UniformCycleCrossover(List<String> pars) {
		super(pars);
	}

	@Override
	public int getArity() {
		return 2;
	}

	@Override
	protected Individual _apply(List<Individual> parents) {
		Genotype father = parents.get(0).getGenome();
		Genotype mother = parents.get(1).getGenome();
		int l = father.length();
		HashMap<Integer, Integer> fatherMap = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> motherMap = new HashMap<Integer, Integer>();
		for (int i=0; i<l; i++) {
			fatherMap.put((int)father.getGene(i), i);
			motherMap.put((int)mother.getGene(i), i);
		}
		
		Genotype offspring = new Genotype(l);
	
		for (int i=0; i<l; i++) {
			if (offspring.getGene(i) == null) {
				Genotype parent1;
				Genotype parent2;
				HashMap<Integer, Integer> map;
				if (EAUtil.random(2)>0) {
					parent1 = father;
					parent2 = mother;
					map = fatherMap;
				}
				else {
					parent2 = father;
					parent1 = mother;
					map = motherMap;				
				}
				int j = i;
				do {
					offspring.setGene(j, parent1.getGene(j));
					j = map.get((int)parent2.getGene(j));
				} while (j != i);
			}
		}
		
		Individual ind = new Individual();
		ind.setGenome(offspring);
			
		return ind;
	}

	@Override
	public String toString() {
		return "UniformCycleCrossover(" + prob + ")";
	}
	
}
