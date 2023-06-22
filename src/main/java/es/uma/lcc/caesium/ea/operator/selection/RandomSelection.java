package es.uma.lcc.caesium.ea.operator.selection;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Selection of the random individuals
 * @author ccottap
 * @version 1.0
 *
 */
public class RandomSelection extends SelectionOperator {
	

	@Override
	public List<Individual> apply(List<Individual> population, int num) {
		List<Individual> result = new LinkedList<Individual>();
		Collections.sort(population, comp);
		int mu = population.size();
		for (int i=0; i<num; i++) {
			result.add(population.get(EAUtil.random(mu)).clone());
		}
		return result;
	}

	@Override
	public String toString() {
		return "RandomSelection";
	}

}
