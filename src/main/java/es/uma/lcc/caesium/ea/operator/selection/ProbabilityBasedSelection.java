package es.uma.lcc.caesium.ea.operator.selection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Abstract class for selection operators based on sampling the population
 * with some given probabilities for each individual.
 * @author ccottap
 * @version 1.0
 *
 */
public abstract class ProbabilityBasedSelection extends SelectionOperator {

	@Override
	public List<Individual> apply(List<Individual> population, int num) {
		HashMap<Individual,Double> probs = generateProbs (population);
		List<Individual> selected = new ArrayList<Individual>(num);
		for (int i=0; i<num; i++)
			selected.add(sample (probs));
		return selected;
	}

	/**
	 * Samples an individual of the population according to their corresponding
	 * probabilities.
	 * @param probs a dictionary of individuals and their selection probabilities
	 * @return an individual from the dictionary
	 */
	private Individual sample(HashMap<Individual, Double> probs) {
		return EAUtil.sample(probs).clone();
	}


	/**
	 * Method for assigning selection probabilities to each individual 
	 * @param population a population
	 * @return a dictionary of individuals and their associated selection probability
	 */
	protected abstract HashMap<Individual, Double> generateProbs(List<Individual> population);



}
