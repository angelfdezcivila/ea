package es.uma.lcc.caesium.ea.operator.variation.initialization.discrete;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.operator.variation.initialization.InitializationOperator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Creates random bitstrings
 * @author ccottap
 * @version 1.1
 *
 */
public class RandomBitString extends InitializationOperator {
	/**
	 * Generates the operator
	 * @param pars parameters (none)
	 */
	public RandomBitString(List<String> pars) {
		super(pars);
	}

	@Override
	protected Individual _apply(List<Individual> parents) {
		int l = obj.getNumVars();
		Genotype g = new Genotype(l);
		for (int i=0; i<l; i++) 
			g.setGene(i, EAUtil.random(2));
		
		Individual ind = new Individual();
		ind.setGenome(g);
		return ind;
	}

	@Override
	public String toString() {
		return "RandomBitString";
	}

}
