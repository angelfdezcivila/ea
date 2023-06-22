package es.uma.lcc.caesium.ea.operator.variation.mutation;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.operator.variation.VariationOperator;

/**
 * Abstract class for mutation operators
 * @author ccottap
 * @version 1.1
 *
 */
public abstract class MutationOperator extends VariationOperator {
	/**
	 * Basic constructor of a mutation operator. Passes the parameters to the parent class
	 * @param pars parameters (application probability)
	 */
	public MutationOperator(List<String> pars) {
		super(pars);
	}

	@Override
	public int getArity() {
		return 1;
	}
	
	@Override
	protected abstract Individual _apply(List<Individual> parents);

}
