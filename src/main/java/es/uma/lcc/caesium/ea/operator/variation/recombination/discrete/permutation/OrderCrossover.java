package es.uma.lcc.caesium.ea.operator.variation.recombination.discrete.permutation;

import java.util.LinkedList;
import java.util.List;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.operator.variation.recombination.RecombinationOperator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Order crossover operator for permutations. Picks some random positions
 * for one of the parents and copies the remaining positions in the same
 * order they appear in the second parent.
 * @author ccottap
 * @version 1.0
 *
 */
public class OrderCrossover extends RecombinationOperator {

	/**
	 * Generates the operator
	 * @param pars probability of application
	 */
	public OrderCrossover(List<String> pars) {
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
		List<Integer> elements = new LinkedList<Integer>();
		for (int i=0; i<l; i++)
			elements.add((int)mother.getGene(i));
		
		List<Integer> positions = new LinkedList<Integer>();
		Genotype offspring = new Genotype(l);
		for (int i=0; i<l; i++)
			if (EAUtil.random(2)>0) {
				int v = (int)father.getGene(i);
				offspring.setGene(i, v);
				elements.remove(v);
			}
			else
				positions.add(i);
		
		//assert elements.size() == positions.size();
		
		for (int i=0; i<elements.size(); i++)
			offspring.setGene(positions.get(i), elements.get(i));

		//assert checkPermutation(offspring);

		Individual ind = new Individual();
		ind.setGenome(offspring);
			
		return ind;
	}

	@Override
	public String toString() {
		return "OrderCrossover(" + prob + ")";
	}
	
//	private boolean checkPermutation(Genotype g) {
//		int l = g.length();
//		List<Integer> elements = new LinkedList<Integer>();
//		for (int i=0; i<l; i++)
//			elements.add((int)g.getGene(i));
//		for (int i=0; i<l; i++)
//			if (!elements.contains(i))
//				return false;
//		return true;
//	}
		

}
