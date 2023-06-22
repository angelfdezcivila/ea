package es.uma.lcc.caesium.ea.statistics;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Individual;

/**
 * Interface for computing diversity measures on a population
 * @author ccottap
 * @version 1.0
 *
 */
public interface DiversityMeasure {
	/**
	 * Computes a measure of diversity in the population
	 * @param pop the population
	 * @return a numerical value that measures diversity
	 */
	public double apply(List<Individual> pop);
	
}
