package es.uma.lcc.caesium.ea.statistics;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;

/**
 * Computes diversity in the population by measuring the average distance of
 * individuals, considered as sets of real-valued variables. The distance between 
 * a set A and and set B is sqrt(sum_{x in A} min_{y in B} (x-y)^2).
 * @author ccottap
 * @version 1.0
 *
 */
public class ContinuousSetDiversity implements DiversityMeasure {

	@Override
	public double apply(List<Individual> pop) {
		int n = pop.get(0).getGenome().length();
		int mu = pop.size();
		double[][] matrix = new double[mu][n];
		
		for (int i=0; i<mu; i++) {
			Genotype gi = pop.get(i).getGenome();
			for (int k=0; k<n; k++)
				matrix[i][k] = (double)gi.getGene(k);
		}
		
		double totalDist = 0.0;
		for (int i=0; i<mu; i++ ) {
			for (int j=0; j<mu; j++) {
				if (i != j) {
					for (int ki=0; ki<n; ki++) {
						double best = Math.pow(matrix[i][ki] - matrix[j][0], 2.0);
						for (int kj=1; kj<n; kj++) {
							double cand = Math.pow(matrix[i][ki] - matrix[j][kj], 2.0);
							if (cand < best)
								best = cand;
						}
						totalDist += best;
					}
				}
			}
		}
		
		totalDist /= (mu*(mu-1));
		
		return Math.sqrt(totalDist);
	}

}
