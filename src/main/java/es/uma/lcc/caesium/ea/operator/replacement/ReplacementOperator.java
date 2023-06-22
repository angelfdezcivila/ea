package es.uma.lcc.caesium.ea.operator.replacement;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.operator.Operator;

/**
 * Abstract class for replacement operators
 * @author ccottap
 * @version 1.0
 *
 */
public abstract class ReplacementOperator extends Operator {
	
	/**
	 * Applies the replacement operator on a parental population
	 * and an offspring population. Returns the result of the replacement.
	 * @param population parental population
	 * @param offspring offspring population
	 * @return the resulting population after replacement
	 */
	public abstract List<Individual> apply(List<Individual> population, List<Individual> offspring);
}
