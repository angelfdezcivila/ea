/**
 * 
 */
package es.uma.lcc.caesium.ea.operator.variation.recombination.discrete.set;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Individual;

/**
 * Fixed-Size
 */
public class FixedSizeSetRecombination extends SetRecombination {

	/**
	 * Generates the operator
	 * @param pars probability of application, arity
	 */	
	public FixedSizeSetRecombination(List<String> pars) {
		super(pars);
	}

	@Override
	protected int cardinality(List<Individual> parents) {
		return parents.get(0).getGenome().length();
	}

	@Override
	public String toString() {
		return "FixedSizeSetRecombination(" + prob + ", " + getArity() + ")";
	}

}
