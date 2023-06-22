package es.uma.lcc.caesium.ea.fitness;

import java.util.Comparator;

import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Abstract class for objective functions
 * @author ccottap
 * @version 1.3
 */
public abstract class ObjectiveFunction {
	/**
	 * total number of calls to the objective function
	 */
	protected long evals;
	/**
	 * used to store additional cost representing some work
	 * done by external problem-aware operators that somehow 
	 * use the problem information to take fitness-informed
	 * decisions or compute fitness partially.
	 */
	protected double extracost;
	/**
	 * number of variables
	 */
	protected int numvars;
	
	/**
	 * Basic constructor indicating how many variables there are
	 * @param i number of variables
	 */
	public ObjectiveFunction(int i) {
		numvars = i;
		reset();
	}

	/**
	 * Indicates whether the goal is maximization or minimization
	 * @return the optimization sense
	 */
	public abstract OptimizationSense getOptimizationSense();
	
	
	/**
	 * Returns the comparator to use given the optimization sense
	 * @return the comparator to use given the optimization sense
	 */
	public Comparator<Individual> getComparator() {
		Comparator<Individual> comp;
		switch (getOptimizationSense()) {
		case MAXIMIZATION:
			comp = EAUtil.maxComp;
			break;
		case MINIMIZATION:
			comp = EAUtil.minComp;
			break;
		default:
			comp = null;
			break;
		}
		return comp;
	}
	
	/**
	 * Returns the number of variables required to evaluate the function
	 * @return the number of variables required to evaluate the function
	 */
	public int getNumVars()
	{
		return numvars;
	}
	
	/**
	 * Returns the number of evaluations so far
	 * @return the number of evaluations so far
	 */
	public long getEvals() {
		return evals + (long)extracost;
	}
	
	/**
	 * Adds additional extra cost
	 * @param v the amount of extra cost added
	 */
	public void addExtraCost(double v) {
		extracost += v;
	}
	
	/**
	 * Resets the number of evaluations
	 */
	public void reset() {
		evals = 0;
		extracost = 0.0;
	}
	
	/**
	 * Performs whatever actions are required at the start of a run 
	 * (e.g., when a problem generator is used to create new data for each run).
	 * In particular, resets the number of evaluations 
	 */
	public void newRun() {
		reset();
	}
	
	/**
	 * Evaluates an individual (also sets internally its fitness)
	 * @param i the individual
	 * @return the fitness of the individual
	 */
	public double evaluate (Individual i) {
		if (!i.isEvaluated()) {
			evals ++;
			double f = _evaluate(i);
			i.setFitness(f);
			return f;
		}
		else
			return i.getFitness();
	}

	/**
	 * Internal method to evaluate an individual
	 * @param i the individual
	 * @return the fitness of the individual
	 */
	protected abstract double _evaluate(Individual i);
}
