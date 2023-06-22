package es.uma.lcc.caesium.ea.operator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.ObjectiveFunction;


/**
 * Abstract class for operators that need to be aware 
 * of the optimization sense of the problem, e.g., selection,
 * replacement, local search, ...
 * @author ccottap
 * @version 1.1
 */
public abstract class Operator {
	/**
	 * to compare individuals
	 */
	protected Comparator<Individual> comp;
	
	/**
	 * the objective function (for potential use)
	 */
	protected ObjectiveFunction obj;
	
	
	/**
	 * Creates the operator
	 */
	public Operator() {
		obj = null;
		comp = null;
	}
	
	
	/**
	 * Sets the comparator to compare individuals
	 * @param comparator the comparator to compare individuals
	 */
	private void setComparator(Comparator<Individual> comparator) {
		comp = comparator;
	}
	
	/**
	 * Sets the objective function for potential use inside some operator
	 * @param obj the objective function
	 */
	public void setObjectiveFunction (ObjectiveFunction obj) {
		this.obj = obj;
		setComparator(obj.getComparator());
	}
	
	/**
	 * Sorts a list of individuals
	 * @param pop a list of individuals
	 */
	public void sort (List<Individual> pop) {
		Collections.sort(pop, comp);
	}
	
	/**
	 * Performs whatever actions are required at the beginning of a run
	 */
	public void newRun() {
	}
	
	@Override
	public abstract String toString();
}
