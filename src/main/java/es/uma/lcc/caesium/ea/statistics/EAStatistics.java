package es.uma.lcc.caesium.ea.statistics;

import java.util.*;
import java.util.stream.Collectors;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
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

	public JsonObject toJSONObject() throws JsonException {
		JsonObject jsondata = new JsonObject();

		int n = seeds.size();	// Cada run tiene una seed distinta, por lo que el numero de runs equivale al numero de seeds
		JsonArray[] genomes = new JsonArray[n];
		JsonArray[] fitnesses = new JsonArray[n];
		for (int i=0; i<n; i++)
		{
			JsonArray jsonRundata = (JsonArray) toJSON(i).get("rundata");
			JsonObject rundataObject = (JsonObject) jsonRundata.get(0);	// Suponiendo que solo hay una isla

			genomes[i] = (JsonArray) rundataObject.get("genome");
			fitnesses[i] = (JsonArray) rundataObject.get("fitness");
		}
		JsonArray genomesJson = concatArray(genomes);
		JsonArray fitnessesJson = concatArray(fitnesses);


		// Para ordenar y eliminar repetidos en los conjuntos.
		// Como un map no puede tener dos claves iguales, se eliminan las repetidas.
		Map<JsonArray, Double> m = sortMap(genomesJson, fitnessesJson);
		genomesJson = new JsonArray(m.keySet());
		fitnessesJson = new JsonArray(m.values());


		jsondata.put("genome", genomesJson);
		jsondata.put("fitness", fitnessesJson);

		return jsondata;
	}

	private Map<JsonArray, Double> sortMap(JsonArray genomesJson, JsonArray fitnessesJson){
		Map<JsonArray, Double> m = new HashMap<>();
		for (int i = 0; i < genomesJson.size(); i++){ // genomesJson.size() == fitnessesJson.size()
			m.put((JsonArray) genomesJson.get(i), (Double) fitnessesJson.get(i));
		}

		m = m.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
						(e1, e2) -> e1, LinkedHashMap::new));

		return m;
	}

	private JsonArray concatArray(JsonArray... arrs)
			throws JsonException {
		JsonArray result = new JsonArray();
		for (JsonArray arr : arrs) {
			result.addAll(arr);
		}

//        Set<Object> set = new HashSet<>(result);
//		set.stream().sorted((o1, o2) -> {
//			((JsonObject) o1).get("fitness")
//		});
//		result.clear();
//		result.addAll(set);

		// Hacer que no haya elementos repetidos. Lo suyo sería ordenarlos mediante el fitness e ir comparando a partir de ahí

		return result;
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
	public Individual getCurrentBest() {
		int n = islands.size();
		Individual best = islands.get(0).getStatistics().getCurrentBest();
		for (int j=1; j<n; j++) {
			Individual cand = islands.get(j).getStatistics().getCurrentBest();
			if (comparator.compare(cand, best) < 0)
				best = cand;
		}
		return best;
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
