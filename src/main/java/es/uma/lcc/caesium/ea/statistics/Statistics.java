package es.uma.lcc.caesium.ea.statistics;

import java.util.Comparator;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

import es.uma.lcc.caesium.ea.base.Individual;

/**
 * Abstract class for island-/EA-statistics
 * @author ccottap
 * @version 1.0
 *
 */
public abstract class Statistics {
	/**
	 * whether a run is active or not 
	 */
	protected boolean runActive;
	
	/**
	 * to compare solutions
	 */
	protected Comparator<Individual> comparator;
	
	/**
	 * to compute diversity
	 */
	protected DiversityMeasure diversity;

	/**
	 * Sets the comparator used to compare individuals
	 * @param comparator the comparator
	 */
	public void setComparator(Comparator<Individual> comparator) {
		this.comparator = comparator;
	}
	
	/**
	 * Sets the diversity measure used in the population
	 * @param diversity the diversity measure
	 */
	public void setDiversityMeasure(DiversityMeasure diversity) {
		this.diversity = diversity;
	}

	/**
	 * Clears all statistics
	 */
	public abstract void clear();
	
	/**
	 * Logs the start of a new run
	 */
	public abstract void newRun();
	
	/**
	 * Closes the current run and commits the statistics to the
	 * global record.
	 */
	public abstract void closeRun();
	
	
	/**
	 * Returns the best solution found so far in the current run
	 * @return the best solution found so far in the current run
	 */
	public abstract Individual getCurrentBest();
	
	/**
	 * Returns the best solution of a given run
	 * @param i the index of the run
	 * @return the best individual in the i-th run
	 */
	public abstract Individual getBest(int i);

	/**
	 * Returns the best solution of all runs
	 * @return the best solution of all runs
	 */
	public abstract Individual getBest();
	
	/**
	 * Returns the data of a certain run in JSON format
	 * @param i the run index
	 * @return a JSON object with the data of the i-th run
	 */
	public abstract JsonObject toJSON(int i);
	
	/**
	 * Returns the data of all runs in JSON format. Any active, non-closed run is not recorded.
	 * @return a JSON array with the data of all runs
	 */
	public abstract JsonArray toJSON();

}
