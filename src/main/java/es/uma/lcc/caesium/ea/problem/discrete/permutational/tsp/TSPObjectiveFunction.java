package es.uma.lcc.caesium.ea.problem.discrete.permutational.tsp;




import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.OptimizationSense;
import es.uma.lcc.caesium.ea.fitness.PermutationalObjectiveFunction;

/**
 * Fitness function for the TSP
 * @author ccottap
 * @version 1.0
 *
 */
public class TSPObjectiveFunction extends PermutationalObjectiveFunction {
	/**
	 * the TSP data
	 */
	protected ATSP data;
	
	
	/**
	 * Creates an objective function for a random instance of the desired size
	 * @param n number of cities
	 */
	public TSPObjectiveFunction(int n) {
		super(n);
		data = new ATSP(n);
	}
	
	/**
	 * Creates an objective function for a given instance
	 * @param data the instance of the ATSP
	 */
	public TSPObjectiveFunction(ATSP data) {
		super(data.getNumCities());
		this.data = data;
	}


	
	@Override
	public OptimizationSense getOptimizationSense() {
		return OptimizationSense.MINIMIZATION;
	}

	@Override
	protected double _evaluate(Individual i) {
		Genotype g = i.getGenome();
				
		int d = data.getDistance((int)g.getGene(numvars-1), (int)g.getGene(0));
		for (int j=1; j<numvars; j++)
			d +=  data.getDistance((int)g.getGene(j-1), (int)g.getGene(j));
		return (double)d;
	}
	
}
