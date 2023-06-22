package es.uma.lcc.caesium.ea.operator.selection;

import java.util.HashMap;
import java.util.List;

import es.uma.lcc.caesium.ea.base.Individual;

/**
 * Fitness proportionate solution. Note that this only works for
 * maximization problems.
 * @author ccottap
 * @version 1.0
 *
 */
public class RouletteWheelSelection extends ProbabilityBasedSelection {


	@Override
	protected HashMap<Individual, Double> generateProbs(List<Individual> population) {
		double sum = 0.0;
		
		for (Individual ind: population)
			sum += ind.getFitness();
		
		HashMap<Individual,Double> probs = new HashMap<Individual,Double>();
		for (Individual ind: population) {
			probs.put(ind, ind.getFitness()/sum);
		}
		return probs;
	}

	@Override
	public String toString() {
		return "RouletteWheel";
	}

}
