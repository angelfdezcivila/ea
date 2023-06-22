package es.uma.lcc.caesium.ea.base;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import es.uma.lcc.caesium.ea.config.EAConfiguration;
import es.uma.lcc.caesium.ea.fitness.ObjectiveFunction;
import es.uma.lcc.caesium.ea.operator.migration.Topology;
import es.uma.lcc.caesium.ea.operator.migration.TopologyFactory;
import es.uma.lcc.caesium.ea.statistics.EAStatistics;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Configuration of individual islands in the EA
 * @author ccottap
 * @version 1.3
 */
public class EvolutionaryAlgorithm {
	/**
	 * islands in the EA
	 */
	protected List<Island> islands;
	
	/**
	 * List of active islands
	 */
	private List<Island> active;
	/**
	 * List of inactive islands
	 */
	private List<Island> inactive;
	
	/**
	 * the objective function
	 */
	private ObjectiveFunction obj;
	
	/**
	 * current seed for the RNG
	 */
	private long seed;
	
	/**
	 * base seed for each batch of runs
	 */
	private long baseSeed;
	
	/**
	 * statistics of the EA
	 */
	private EAStatistics stats;
	
	/**
	 * island interconnection topology
	 */
	Topology topology;
	
	/**
	 * number of runs
	 */
	private int numruns;
	
	/**
	 * configuration used in the EA
	 */
	private EAConfiguration conf;
	
		
	
	/**
	 * Creates an EA given a certain configuration
	 * @param conf configuration of the EA
	 */
	public EvolutionaryAlgorithm(EAConfiguration conf) {
		this.conf = conf;
		obj = null;
		int n = conf.getNumIslands();
		islands = new ArrayList<Island>(n);
		for (int i=0; i<n; i++) {
			islands.add(new Island(i, conf.getIslandConfiguration(i)));
		}
		baseSeed = conf.getSeed();
		seed = baseSeed;
		numruns = conf.getNumRuns();
		stats = new EAStatistics(islands);
		topology = null;
		conf.getTopologyParameters().add(0, Integer.toString(islands.size()));
	}
	
	/**
	 * Returns the EA statistics
	 * @return the EA statistics
	 */
	public EAStatistics getStatistics() {
		return stats;
	}

	/**
	 * Sets the objective function used by the EA
	 * @param obj the objective function used by the EA
	 */
	public void setObjectiveFunction (ObjectiveFunction obj) {
		this.obj = obj;
		for (Island i: islands)
			i.setObjectiveFunction (obj);
		stats.setComparator(obj.getComparator());
	}
	
	/**
	 * Returns the island whose id is given
	 * @param id id of an island
	 * @return the island whose id is given
	 */
	private Island getIslandByID (int id) {
		for (Island i: islands) 
			if (i.getID() == id)
				return i;
		return null;
	}
	
	/**
	 * Initializes all islands and starts statistical recording 
	 */
	public void initEA() {
		EAUtil.setSeed(seed++);
		stats.newRun();
		obj.newRun();
		active = new LinkedList<Island>();
		inactive = new LinkedList<Island>();
		
		if ((topology == null) || (topology.isRegenerable())) {
			topology = TopologyFactory.create(conf.getTopology(), conf.getTopologyParameters());
			for (Island i: islands) {
				i.resetConnections();
				Set<Integer> links = topology.get(i.getID());
				for (int id: links)
					i.connect(getIslandByID(id));
			}
		}
		

		for (Island i: islands) {
			i.initializeIsland();
			if (i.isActive())
				active.add(i);
			else
				inactive.add(i);
		}
	}
	
	/**
	 * Performs an evolutionary iteration on all active islands.
	 * If all islands become inactive, statistics are closed for that run
	 * @return true if some island(s) remain(s) active
	 */
	public boolean stepUp () {
		
		for (int k = 0; k<active.size();) {
			Island i = active.get(k);
			if (!i.stepUp()) {
				active.remove(k);
				inactive.add(i);
			}
			else 
				k++;
		}

		if (active.size() == 0) {
			stats.closeRun();
			return false;
		}
		
		return true;
	}
	
	

	/**
	 * Runs the EA
	 */
	public void run() {
		initEA();
		while (stepUp());
	}
	
	/**
	 * Performs all runs of the EA indicated in the configuration,
	 * starting with the seed indicated in such configuration.
	 */
	public void runAll() {
		seed = baseSeed;
		for (int i=0; i<numruns; i++)
			run();
	}
	

}
