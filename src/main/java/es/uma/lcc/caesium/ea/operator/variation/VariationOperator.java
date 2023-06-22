package es.uma.lcc.caesium.ea.operator.variation;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.operator.Operator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Abstract class for generic variation operators
 * @author ccottap
 * @version 1.0
 *
 */
public abstract class VariationOperator extends Operator {
	/**
	 * operator application probability
	 */
	protected double prob;
	
	/**
	 * Creates the operator. 
	 * @param pars String representation of the application probability. If the 
	 * list provided is empty, it is assumed that the probability is 1.0
	 */
	public VariationOperator (List<String> pars) {
		prob = pars.size()>0 ? Double.parseDouble(pars.get(0)) : 1.0;
	}
 
	/**
	 * Returns the application probability
	 * @return the application probability
	 */
	public double getProbability() {
		return prob;
	}

	/**
	 * Sets the application probability
	 * @param prob the application probability
	 */
	public void setProbability(double prob) {
		this.prob = prob;
	}

	/**
	 * Returns the arity of the operator (how many individual it expects as an input).
	 * @return the arity of the operator
	 */
	public abstract int getArity();
	
	/**
	 * Applies the operator to a collection of parents.
	 * If the number of parents does not match the arity of the operator, 
	 * null is returned.
	 * @param parents the parents
	 * @return an individual
	 */
	public Individual apply (List<Individual> parents) {
		Individual child = null;
		if (parents.size() == getArity()) {
			if (EAUtil.random01() < prob) {
				child = _apply(parents);
			}
			else
				child = parents.get(0).clone();
		}
		
		return child;
	}

	/**
	 * Internal method for applying the operator. Receives a list of individuals, 
	 * and returns a full-fledged individual whose fitness information might be 
	 * potentially valid (that would depend on the operator used; for example, if 
	 * local search is used, the individual will be already typically evaluated). 
	 * @param parents a list of individuals
	 * @return an individual created from those parents
	 */
	protected abstract Individual _apply(List<Individual> parents);

}
