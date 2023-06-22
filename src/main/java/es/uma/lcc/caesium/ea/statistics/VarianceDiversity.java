package es.uma.lcc.caesium.ea.statistics;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Individual;

/**
 * Computes diversity in the population by measuring the variance
 * of real-valued variables
 * @author ccottap
 * @version 1.0
 *
 */
public class VarianceDiversity implements DiversityMeasure {

	@Override
	public double apply(List<Individual> pop) {
		int n = pop.get(0).getGenome().length();
		int mu = pop.size();
		
		double sigma = 0;
		for (int i=0; i<n; i++) {
			double mean = 0.0;
			double mean2 = 0.0;
			double K = (double)pop.get(0).getGenome().getGene(i);

			for (Individual ind: pop) {
				mean += (double)ind.getGenome().getGene(i)-K;
				mean2 += Math.pow((double)ind.getGenome().getGene(i)-K, 2);
			}
			sigma += Math.sqrt((mean2 - (mean*mean)/mu)/mu);
		}
		return sigma/n;
	}

}
