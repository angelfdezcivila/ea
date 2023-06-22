package es.uma.lcc.caesium.ea.operator.replacement;

import java.util.ArrayList;
import java.util.List;

import es.uma.lcc.caesium.ea.base.Individual;

/**
 * Plus replacement
 * @author ccottap
 * @version 1.0
 */
public class PlusReplacement extends ReplacementOperator {

	/**
	 * Performs plus replacement. Let mu = |population|. It keeps 
	 * the best mu individuals out of population U offspring.
	 * @param population parental population
	 * @param offspring offspring population
	 * @return the resulting population after replacement
	 */
	public List<Individual> apply(List<Individual> population, List<Individual> offspring) {
		int mu = population.size();
		int lambda = offspring.size();
		sort(population);
		sort(offspring);
		List<Individual> result = new ArrayList<Individual>(mu);
		int ip = 0;
		int io = 0;
		for (int i=0; i<mu; i++) {
			if (io>=lambda) {
				Individual i1 = population.get(ip);
				result.add(i1.clone());
				ip++;				
			}
			else {
				Individual i1 = population.get(ip);
				Individual i2 = offspring.get(io);
				if (comp.compare(i1, i2) >= 0) {
					result.add(i2.clone());
					io++;
				}
				else {
					result.add(i1.clone());
					ip++;
				}
			}
		}
		
		return result;
	}

	@Override
	public String toString() {
		return "PlusReplacement";
	}

}
