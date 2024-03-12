package es.uma.lcc.caesium.ea.operator.variation.recombination.discrete.set;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.operator.variation.recombination.RecombinationOperator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Abstract superclass of some set-based recombination operators that select 
 * a subset of elements from the union of the parents.
 * @author ccottap
 * @version 1.0
 */
public abstract class SetRecombination extends RecombinationOperator {
	/**
	 * Default arity of the operator
	 */
	private static final int DEFAULT_ARITY = 2;
	/**
	 * arity of the operator
	 */
	private int arity;
	
	/**
	 * Generates the operator
	 * @param pars probability of application, arity
	 */	
	public SetRecombination(List<String> pars) {
		super(pars);
		if (pars.size()<2)
			arity = DEFAULT_ARITY;
		else
			arity = Integer.parseInt(pars.get(1));
	}
	
	@Override
	public int getArity() {
		return arity;
	}

	/**
	 * Returns the number of elements to pick from the union set
	 * @param parents the solutions being recombined
	 * @return the number of elements to pick from the union set
	 */
	protected abstract int cardinality(List<Individual> parents);

	
	@Override
	protected Individual _apply(List<Individual> parents) {
		Set<Object> values = new HashSet<Object>();
		List<Object> genes = new ArrayList<Object>();
		
		for (Individual ind: parents) {
			Genotype g = ind.getGenome();
			int l = g.length();
			for (int i=0; i<l; i++) {
				Object val = g.getGene(i);
				if (!values.contains(val)) {
					values.add(val);
					genes.add(val);
				}
			}
		}
		
		int n = cardinality(parents);
		Genotype offspring = new Genotype(n);
			
		if (n > genes.size() ) {
			Genotype g = parents.get(0).getGenome();
			for (int i=0; i<n; i++) {
				offspring.setGene(i, g.getGene(i));
			}
		}
		else {
			for (int i=0; i<n; i++) {
				int p = EAUtil.random(genes.size());
				offspring.setGene(i, genes.get(p));
				genes.remove(p);
			}
		}
		
		Individual ind = new Individual();
		ind.setGenome(offspring);
		
		return ind;
	}


}
