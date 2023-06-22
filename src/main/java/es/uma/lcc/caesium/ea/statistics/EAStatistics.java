package es.uma.lcc.caesium.ea.statistics;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.base.Island;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Statistics of an island-based EA
 * @author ccottap
 * @version 1.0
 *
 */
public class EAStatistics extends Statistics {
	/**
	 * statistics of every island
	 */
	private List<Island> islands;
	/**
	 * list of seeds used in each run
	 */
	private List<Long> seeds;
	/**
	 * last seed used
	 */
	private long currentSeed;
	/**
	 * to measure computational times
	 */
	private List<Double> runtime;
	/**
	 * time at the beginning of a run
	 */
	private long tic;
	/**
	 * time at the end of a run
	 */
	private long toc;
	
	


	/**
	 * Creates statistics for a collection of islands
	 * @param islands the islands whose statistics will be collected.
	 */
	public EAStatistics (List<Island> islands) {
		this.islands = islands;
		clear();
	}
	
	
	@Override
	public void clear() {
		for (Island i: islands) {
			i.getStatistics().clear();
		}
		seeds = new LinkedList<Long>();
		runtime = new LinkedList<Double>();
		runActive = false;		
	}
	
	@Override
	public void setComparator(Comparator<Individual> comparator) {
		super.setComparator(comparator);
		for (Island i: islands) {
			i.getStatistics().setComparator(comparator);
		}
	}
	
	@Override
	public void setDiversityMeasure (DiversityMeasure d) {
		super.setDiversityMeasure(d);
		for (Island i: islands) {
			i.getStatistics().setDiversityMeasure(d);
		}	
	}
	
	@Override
	public void closeRun() {
		if (runActive) {
			seeds.add(currentSeed);
			for (Island i: islands)
				i.getStatistics().closeRun();
			toc = System.nanoTime();
			runtime.add((toc-tic)/1e9);
		}
		runActive = false;
	}
	
	@Override
	public void newRun() {
		if (runActive)
			closeRun();
		currentSeed = EAUtil.getSeed();
		for (Island i: islands)
			i.getStatistics().newRun();
		runActive = true;
		tic = System.nanoTime();
	}

	@Override
	public JsonObject toJSON(int i) {
		JsonObject json = new JsonObject();
		json.put("run", i);
		json.put("seed", seeds.get(i));
		json.put("time", runtime.get(i));
		JsonArray jsondata = new JsonArray();
		for (Island island: islands)
			jsondata.add(island.getStatistics().toJSON(i));
		json.put("rundata", jsondata);
		return json;
	}

	@Override
	public JsonArray toJSON() {
		JsonArray jsondata = new JsonArray();
		int n = seeds.size();
		for (int i=0; i<n; i++) 
			jsondata.add(toJSON(i));
		return jsondata;
	}
	
	/**
	 * Returns the CPU time of a certain run
	 * @param i the index of the run
	 * @return the CPU time of the i-th run
	 */
	public double getTime(int i) {
		return runtime.get(i);
	}

	@Override
	public Individual getBest(int i) {
		int n = islands.size();
		Individual best = islands.get(0).getStatistics().getBest(i);
		for (int j=1; j<n; j++) {
			Individual cand = islands.get(j).getStatistics().getBest(i);
			if (comparator.compare(cand, best) < 0)
				best = cand;
		}
		return best;
	}

	@Override
	public Individual getBest() {
		int numruns = seeds.size();
		Individual best = getBest(0);
		for (int j=1; j<numruns; j++) {
			Individual cand = getBest(j);
			if (comparator.compare(cand, best) < 0)
				best = cand;
		}
		return best;
	}
	
}
