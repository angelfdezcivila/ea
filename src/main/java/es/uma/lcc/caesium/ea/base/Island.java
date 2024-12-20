package es.uma.lcc.caesium.ea.base;

import java.util.ArrayList;
import java.util.List;

import es.uma.lcc.caesium.ea.config.IslandConfiguration;
import es.uma.lcc.caesium.ea.fitness.ObjectiveFunction;
import es.uma.lcc.caesium.ea.operator.migration.MigrationOperator;
import es.uma.lcc.caesium.ea.operator.replacement.ReplacementFactory;
import es.uma.lcc.caesium.ea.operator.replacement.ReplacementOperator;
import es.uma.lcc.caesium.ea.operator.selection.SelectionFactory;
import es.uma.lcc.caesium.ea.operator.selection.SelectionOperator;
import es.uma.lcc.caesium.ea.operator.variation.VariationOperator;
import es.uma.lcc.caesium.ea.operator.variation.VariationFactory;
import es.uma.lcc.caesium.ea.statistics.IslandStatistics;

/**
 * An island within an EA
 * @author ccottap
 * @version 1.3
 *
 */
public class Island {
	/**
	 * ID of the island
	 */
	private int id;
	/**
	 * population size
	 */
	private int mu;
	/**
	 * number of offspring
	 */
	private int lambda;
	/**
	 * The objective function being optimized
	 */
	private ObjectiveFunction obj;
	/**
	 * number of evaluations so far in the current island
	 */
	private long numEvals;
	/**
	 * maximum number of evaluations
	 */
	private long maxEvals;
	/**
	 * the population
	 */
	private List<Individual> population;
	/**
	 * the replacement operator
	 */
	private ReplacementOperator replace;
	/**
	 * selection operator used
	 */
	private SelectionOperator selection;
	/**
	 * Initialization operator
	 */
	private VariationOperator initialization;
	// technically, it is an InitializationOperator (which is a derived
	// subclass from VariationOperator), but for simplicity it is defined like that.
	/**
	 * variation operators used
	 */
	private List<VariationOperator> variationOps;
	/**
	 * number of individuals to be initially selected to create the offspring
	 */
	private int poolSize;
	/**
	 * statistics
	 */
	private IslandStatistics stats;
	/**
	 * Migration operator
	 */
	private MigrationOperator migrate;

	
	/**
	 * Creates an island with a given configuration
	 * @param id the island ID 
	 * @param ic the configuration of the island
	 */
	public Island(int id, IslandConfiguration ic) {
		this.id = id;
		maxEvals = ic.getMaxEvaluations();
		mu = ic.getPopulationSize();
		population = new ArrayList<Individual> (mu);
		lambda = ic.getNumOffspring();
		// create operators
		SelectionFactory   sf = ic.getSelectionFactory();
		VariationFactory   vf = ic.getVariationFactory();
		ReplacementFactory rf = ic.getReplacementFactory();
		
		selection = sf.create(ic.getSelectionOperator(), ic.getSelectionOperatorParameters());
		replace = rf.create(ic.getReplacementOperator(), ic.getReplacementOperatorParameters());
		int numOps = ic.getNumVariationOperators();
		variationOps = new ArrayList<VariationOperator> (numOps);
		initialization = vf.create(ic.getVariationOperator(0), ic.getVariationOperatorParameters(0)); 
		poolSize = lambda;
		for (int i=1; i<numOps; i++) {
			VariationOperator op = vf.create(ic.getVariationOperator(i), ic.getVariationOperatorParameters(i)); 
			variationOps.add(op);	
			poolSize *= op.getArity();
		}
		migrate = new MigrationOperator( 
							(ic.getOutgoingOperator() != null)? sf.create(ic.getOutgoingOperator(), ic.getOutgoingParameters()) : null, 
							(ic.getIncomingOperator() != null)? rf.create(ic.getIncomingOperator(), ic.getIncomingParameters()) : null,
							ic.getMigrationFrequency(), 
							ic.getMigrationIndividuals());
		stats = new IslandStatistics();
	}
	
	
	/**
	 * Returns the island ID
	 * @return the island ID
	 */
	public int getID () {
		return id;
	}

	/**
	 * Sets the objective function to be optimized
	 * @param obj the objective function to be optimized
	 */
	public void setObjectiveFunction(ObjectiveFunction obj) {
		this.obj = obj;
		initialization.setObjectiveFunction(obj);
		selection.setObjectiveFunction(obj);
		for (VariationOperator op: variationOps) {
			op.setObjectiveFunction(obj);
		}
		replace.setObjectiveFunction(obj);
		migrate.setObjectiveFunction(obj);
	}
	
	/**
	 * Returns the number of evaluations so far
	 * @return the number of evaluations so far
	 */
	public long getNumEvaluations ()
	{
		return numEvals;
	}
	
	
	/**
	 * Returns the population size
	 * @return the population size
	 */
	public int getPopulationSize() {
		return population.size();
	}
	
	/**
	 * Adds a unidirectional link from self to the island 
	 * passed as a parameter.	 
	 * @param island the island to connect to
	 */
	public void connect (Island island) {
		migrate.connect(island.getID(), island.getMigrationOp());
	}
	
	
	/**
	 * Removes all connections
	 */
	public void resetConnections() {
		migrate.resetConnections();
	}
	
	/**
	 * Returns the migration operator
	 * @return the migration operator
	 */
	private MigrationOperator getMigrationOp() {
		return migrate;
	}

	/**
	 * Creates the initial population of the island
	 */
	public void initializeIsland () {
		initialization.newRun();
		selection.newRun();
		for (VariationOperator op: variationOps) 
			op.newRun();
		replace.newRun();
		migrate.newRun();
		
		population.clear();
		
		List<Individual> placeholder = new ArrayList<Individual>(0);
		for (int i=0; i<mu; i++) {
			Individual ind = initialization.apply(placeholder);
			obj.evaluate(ind);
			population.add(ind);
		}

		numEvals = mu;		
		stats.takeStats(obj.getEvals(), population);
		stats.takeStatsAllEvals(obj.getEvals(), population);
	}

	/**
	 * Returns true iff the island is active, that is,
	 * if the number of calls to the objective function is less than maxEvals;
	 * @return true iff the island is active
	 */
	public boolean isActive() {
		return (obj.getEvals() < maxEvals);
	}

	/**
	 * Performs an evolutionary cycle on the island
	 * @return true if the island remains active
	 */
	public boolean stepUp() {
		if (obj.getEvals() < maxEvals) {
			// immigration ----------------------------------------------------
			population = migrate.receive(population);
			// selection ------------------------------------------------------
			List<Individual> offspring = selection.apply(population, poolSize);
			// reproduction ---------------------------------------------------
			for (VariationOperator op: variationOps) {
				int a = op.getArity();
				int m = offspring.size()/a;
				List<Individual> stage = new ArrayList<Individual>(m);
				List<Individual> parents = new ArrayList<Individual>(a);
				for (int j=0; j<m; j++) {
					parents.clear();
					for (int i=0; i<a; i++) {
						parents.add(offspring.get(0));
						offspring.remove(0);
					}
					stage.add(op.apply(parents));
				}
				offspring = stage;
			}
			// evaluate new individuals ---------------------------------------
			for (Individual i: offspring) {
				if (!i.isEvaluated())
					numEvals++;
				obj.evaluate(i);
			}
			// apply replacement ----------------------------------------------
			population = replace.apply(population, offspring);
			// emigration ----------------------------------------------------
			migrate.send(population);
			// take stats -----------------------------------------------------
			stats.takeStats(obj.getEvals(), population);
			stats.takeStatsAllEvals(obj.getEvals(), population);
		}
		return isActive();
	}
	
	@Override
	public String toString() {
		String str = "{\n\tnumevals: " + numEvals + 
					 ",\n\tpopulation: [\n";
		str += "\t\t" + population.get(0).toString().replace("\n", "\n\t\t");
		for (int i=1; i<mu; i++)
			str += ",\n\t\t" + population.get(i).toString().replace("\n", "\n\t\t");
		str += "\n\t]\n}";
		return str;
	}

	/**
	 * Returns the island statistics
	 * @return the island statistics
	 */
	public IslandStatistics getStatistics() {
		return stats;
	}


//	/**
//	 * Returns the best individual in the population
//	 * @return the best individual in the population
//	 */
//	public Individual getBestSolution() {
//		selection.sort(population);
//		return population.get(0);
//	}
	

	

}
