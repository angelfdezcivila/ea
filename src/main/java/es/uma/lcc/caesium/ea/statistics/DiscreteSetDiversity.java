package es.uma.lcc.caesium.ea.statistics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;

/**
 * Computes diversity in the population by measuring the average distance of
 * individuals, considered as sets of discrete variables. The distance between 
 * a set A and and set B is |sd(A,B)|, where sd is the symmetrical difference.
 * @author ccottap
 * @version 1.0
 *
 */
public class DiscreteSetDiversity implements DiversityMeasure {

	@Override
	public double apply(List<Individual> pop) {
		int n = pop.get(0).getGenome().length();
		int mu = pop.size();
		List<Set<Object>> sets = new ArrayList<Set<Object>>(mu);
		
		
		for (int i=0; i<mu; i++ ) {
			Genotype gi = pop.get(i).getGenome();
			var si = new HashSet<Object>();
			for (int k=0; k<n; k++) {
				si.add(gi.getGene(k));
			}
			sets.add(si);
		}
		
		double totalDist = 0.0;
		for (int i=0; i<mu; i++ ) {
			for (int j=i+1; j<mu; j++) {
				var diff = new HashSet<Object>(sets.get(i));
				diff.removeAll(sets.get(j));
				totalDist += diff.size();
			}
		}
		
		totalDist /= (mu*(mu-1)/2);
		
		return Math.sqrt(totalDist);
	}

}
