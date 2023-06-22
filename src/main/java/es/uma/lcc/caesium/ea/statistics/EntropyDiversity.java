package es.uma.lcc.caesium.ea.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;

/**
 * Computes diversity in the population by measuring entropy
 * @author ccottap
 * @version 1.0
 *
 */
public class EntropyDiversity implements DiversityMeasure {

	@Override
	public double apply(List<Individual> pop) {
		int n = pop.get(0).getGenome().length();
		List<HashMap<Integer, Integer>> freq = new ArrayList<HashMap<Integer, Integer>> (n);
		
		for (int i=0; i<n; i++)
			freq.add(new HashMap<Integer, Integer>());
		
		for (Individual ind: pop) {
			Genotype g = ind.getGenome();
			for (int i=0; i<n; i++) {
				int v = (Integer)g.getGene(i);
				HashMap<Integer, Integer> map = freq.get(i);
				if (map.containsKey(v))
					map.put(v, map.get(v) + 1);
				else
					map.put(v,  1);
			}	
		}
		double h = 0.0;
		for (int i=0; i<n; i++) {
			double lh = 0.0;
			HashMap<Integer, Integer> map = freq.get(i);
			if (map.keySet().size()>1) {
				for (int v: map.keySet()) {
					double p = (double)map.get(v)/(double)pop.size();
					lh -= p*Math.log(p);
				}
			}
			h += lh;
		}
		return h;
	}

}
