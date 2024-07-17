package es.uma.lcc.caesium.ea.statistics;

import java.util.ArrayList;
import java.util.List;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;

/**
 * Class for storing the statistics of the functioning of an EA island
 * @author ccottap
 * @version 1.0
 *
 */
public class IslandStatistics extends Statistics {
	/**
	 * statistics of all runs
	 */
	private List<List<StatsEntry>> stats;
	/**
	 * statistics of the current run
	 */
	private List<StatsEntry> current;
	/**
	 * evolution of the best solutions in all runs 
	 */
	private List<List<IndividualRecord>> sols;
	/**
	 * evolution of the best solution in the current run
	 */
	private List<IndividualRecord> currentSols;
	/**
	 * last best solution in the current run
	 */
	private Individual last;
	
	/**
	 * Initializes statistics for a batch of runs
	 */
	public IslandStatistics() {
		clear();
		setDiversityMeasure(null);
	}
	
	@Override
	public void clear() {
		stats = new ArrayList<List<StatsEntry>> ();
		current = null;
		sols = new ArrayList<List<IndividualRecord>> ();
		currentSols = null;
		runActive = false;		
	}
	
	@Override
	public void newRun() {
		if (runActive)
			closeRun();
		current = new ArrayList<StatsEntry> ();
		currentSols = new ArrayList<IndividualRecord>();	
		runActive = true;
	}
	
	@Override
	public void closeRun() {
		if (runActive) {
			stats.add(current);
			sols.add(currentSols);
		}
		current = null;
		currentSols = null;
		runActive = false;
	}
	
	/**
	 * Takes statistics of the population at a given time
	 * @param evals number of evaluations so far
	 * @param pop the population
	 */
	public void takeStats(long evals, List<Individual> pop) {
		Individual best = pop.get(0);
		double mean = best.getFitness();
		int l = pop.size();
		
		for (int i=1; i<l; i++) {
			Individual ind = pop.get(i);
			if (comparator.compare(ind, best) < 0) {
				best = ind;
			}
			mean += ind.getFitness();
		}
		mean /= l;
		
		double h = 0.0;
		if (diversity != null)
			h = diversity.apply(pop);
		
		current.add(new StatsEntry(evals, best, mean, h));

		if ((currentSols.size()==0) || (comparator.compare(best, last) < 0)) {
			currentSols.add(new IndividualRecord(evals, best));
			last = best.clone();
		}
	}

	
	
	/**
	 * Returns the data of a certain run in JSON format
	 * @param i the run index
	 * @return a JSON object with the data of the i-th run
	 */
	public JsonObject toJSON(int i) {
		JsonObject json = new JsonObject();
		
		JsonObject jsonstats = new JsonObject();
		JsonArray jsonevals = new JsonArray();
		JsonArray jsonbest = new JsonArray();
		JsonArray jsonmean = new JsonArray();
		JsonArray jsondiv = new JsonArray();
		JsonArray jsonIndividual = new JsonArray();
		List<StatsEntry> data = stats.get(i);
		for (StatsEntry s: data) {
			jsonevals.add(s.evals());
//			jsonbest.add(s.best());
			jsonbest.add(s.best().getFitness());
			jsonmean.add(s.mean());
			jsondiv.add(s.diversity());

			JsonArray jsongenome = new JsonArray();
			Genotype g = s.best().getGenome();
			int n = g.length();
			for (int j=0; j<n; j++)
				jsongenome.add(g.getGene(j));
			jsonIndividual.add(jsongenome);
		}
		jsonstats.put("evals", jsonevals);
//		jsonstats.put("best", jsonbest);
		jsonstats.put("best fitness", jsonbest);
		jsonstats.put("mean", jsonmean);
		jsonstats.put("diversity", jsondiv);
		jsonstats.put("genome", jsonIndividual);
//		json.put("idata", jsonstats);
		
		JsonObject jsonsols = new JsonObject();		
		JsonArray jsonsolsevals = new JsonArray();
		JsonArray jsonsolsfitness = new JsonArray();
		JsonArray jsonsolsgenome = new JsonArray();
		List<IndividualRecord> soldata = sols.get(i);
		for (IndividualRecord p: soldata) {
			jsonsolsevals.add(p.evals());
			jsonsolsfitness.add(p.individual().getFitness());
			Genotype g = p.individual().getGenome();
			JsonArray jsongenome = new JsonArray();
			int n = g.length();
			for (int j=0; j<n; j++) 
				jsongenome.add(g.getGene(j));
			jsonsolsgenome.add(jsongenome);
		}		
		jsonsols.put("evals", jsonsolsevals);
		jsonsols.put("fitness", jsonsolsfitness);
		jsonsols.put("genome", jsonsolsgenome);
//		json.put("isols", jsonsols);

		json.put("genome", jsonIndividual);
		json.put("fitness", jsonbest);
		return json;
	}

	@Override
	public JsonArray toJSON() {
		JsonArray jsondata = new JsonArray();
		int n = stats.size();
		for (int i=0; i<n; i++)
			jsondata.add(toJSON(i));
		return jsondata;
	}
	
	@Override
	public Individual getCurrentBest() {
		return currentSols.get(currentSols.size()-1).individual();
	}
	
	@Override
	public Individual getBest(int i) {
		List<IndividualRecord> data = sols.get(i);
		int n = data.size();
		return data.get(n-1).individual();
	}
	
	@Override
	public Individual getBest() {
		int numruns = stats.size();
		Individual best = getBest(0);
		for (int j=1; j<numruns; j++) {
			Individual cand = getBest(j);
			if (comparator.compare(cand, best) < 0)
				best = cand;
		}
		return best;
	}

	
	@Override
	public String toString() {
		String str = "";
		int runs = stats.size();
		for (int i=0; i<runs; i++) {
			List<StatsEntry> runstats = stats.get(i);
			str += "Run " + i + "\n=======\n";
			str += "#evals\tbest\tmean\n------\t----\t----\n";
			for (StatsEntry s: runstats) {
				str += s.evals() + "\t" + s.best() + "\t" + s.mean() + "\n";
			}
		}
		return str;
	}
	
}
