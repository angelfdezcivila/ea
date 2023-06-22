package es.uma.lcc.caesium.ea.operator.variation.recombination;

import java.util.List;

import es.uma.lcc.caesium.ea.operator.variation.VariationOperator;

/**
 * Abstract class for recombination operators
 * @author ccottap
 * @version 1.1
 *
 */
public abstract class RecombinationOperator extends VariationOperator {
	/**
	 * Basic constructor of a recombination operator. Passes the parameters to the parent class
	 * @param pars parameters (application probability)
	 */
	public RecombinationOperator(List<String> pars) {
		super(pars);
	}

}
