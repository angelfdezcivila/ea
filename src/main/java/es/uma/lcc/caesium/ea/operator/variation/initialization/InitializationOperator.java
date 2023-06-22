package es.uma.lcc.caesium.ea.operator.variation.initialization;

import java.util.List;

import es.uma.lcc.caesium.ea.operator.variation.VariationOperator;

/**
 * Abstract class for solution generation operators
 * @author ccottap
 * @version 1.0
 *
 */
public abstract class InitializationOperator extends VariationOperator {
	/**
	 * Basic constructor of a mutation operator. Passes the parameters to the parent class
	 * @param pars parameters.
	 */
	public InitializationOperator(List<String> pars) {
		super(pars);
	}

	@Override
	public int getArity() {
		return 0;
	}


}
