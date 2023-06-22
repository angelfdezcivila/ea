package es.uma.lcc.caesium.ea.operator.selection;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.operator.Operator;

/**
 * Abstract class for selection operators
 * @author ccottap
 * @version 1.1
 *
 */
public abstract class SelectionOperator extends Operator {

	/**
	 * Applies the selection operator on a parental population. 
	 * Returns the number of individuals requested.
	 * @param population parental population
	 * @param num the number of individuals to be selected
	 * @return the populations of selected individuals
	 */
	public abstract List<Individual> apply(List<Individual> population, int num);
	

}
