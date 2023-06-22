package es.uma.lcc.caesium.ea.operator.selection;

import java.util.LinkedList;
import java.util.List;

import es.uma.lcc.caesium.ea.base.Individual;

/**
 * Selection of the best individuals
 * @author ccottap
 * @version 1.0
 *
 */
public class BestSelection extends SelectionOperator {
	

	@Override
	public List<Individual> apply(List<Individual> population, int num) {
		List<Individual> result = new LinkedList<Individual>();
		sort(population);
		int mu = population.size();
		for (int i=0; i<num; i++) {
			result.add(population.get(i%mu).clone());
		}
		return result;
	}

	@Override
	public String toString() {
		return "BestSelection";
	}

}
