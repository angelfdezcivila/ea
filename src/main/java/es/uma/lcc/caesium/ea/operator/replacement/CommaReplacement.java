package es.uma.lcc.caesium.ea.operator.replacement;

import java.util.ArrayList;
import java.util.List;

import es.uma.lcc.caesium.ea.base.Individual;

/**
 * Comma replacement
 * @author ccottap
 * @version 1.0
 */
public class CommaReplacement extends ReplacementOperator {
	
	/**
	 * Performs comma replacement. Let mu = |population|. If lambda = 
	 * |offspring| is larger than mu, it keeps the best mu individuals 
	 * out of the offspring. Otherwise, it keeps the offspring and the
	 * best mu-lambda individuals in the population.
	 * @param population parental population
	 * @param offspring offspring population
	 * @return the resulting population after replacement
	 */
	public List<Individual> apply(List<Individual> population, List<Individual> offspring) {
		int mu = population.size();
		int lambda = offspring.size();
		sort(offspring);
		List<Individual> result = new ArrayList<Individual>(mu);
		int num = Math.min(lambda, mu);
		for (int i=0; i<num; i++)
			result.add(offspring.get(i).clone());
		if (lambda < mu) {
			sort(population);
			num = mu - lambda;
			for (int i=0; i<num; i++) {
				result.add(population.get(i).clone());
			}
		}		
		return result;

	}

	@Override
	public String toString() {
		return "CommaReplacement";
	}

}
