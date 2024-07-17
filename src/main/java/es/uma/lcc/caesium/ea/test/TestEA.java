package es.uma.lcc.caesium.ea.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Locale;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import es.uma.lcc.caesium.ea.base.EvolutionaryAlgorithm;
import es.uma.lcc.caesium.ea.config.EAConfiguration;
import es.uma.lcc.caesium.ea.fitness.BinaryEncodedContinuousObjectiveFunction;
import es.uma.lcc.caesium.ea.fitness.ObjectiveFunction;
import es.uma.lcc.caesium.ea.problem.continuous.Sphere;
import es.uma.lcc.caesium.ea.problem.discrete.binary.onemax.OneMax;
import es.uma.lcc.caesium.ea.problem.discrete.binary.trap.DeceptiveTrap;
import es.uma.lcc.caesium.ea.problem.discrete.permutational.lop.LOPObjectiveFunction;
import es.uma.lcc.caesium.ea.problem.discrete.permutational.lop.LinearOrderingProblem;
import es.uma.lcc.caesium.ea.problem.discrete.permutational.tsp.ATSP;
import es.uma.lcc.caesium.ea.problem.discrete.permutational.tsp.TSPObjectiveFunction;
import es.uma.lcc.caesium.ea.statistics.DiversityMeasure;
import es.uma.lcc.caesium.ea.statistics.EntropyDiversity;
import es.uma.lcc.caesium.ea.statistics.VarianceDiversity;

/**
 * Class for testing the evolutionary algorithm
 * @author ccottap
 * @version 1.0
 */
public class TestEA {

	/**
	 * Main method
	 * @param args command-line arguments
	 * @throws FileNotFoundException if configuration file cannot be read 
	 * @throws JsonException if the configuration file is not correctly formatted
	 */
	public static void main(String[] args) throws FileNotFoundException, JsonException {
		EAConfiguration conf;
		for(String a : args){ // Para testear
			System.out.println("Argumento : " + a);
		}
		String problem = (args.length < 1) ? "onemax" : args[0]; 
		if (args.length < 2)
			conf = new EAConfiguration();
		else {
			FileReader reader = new FileReader(args[1]);
			conf = new EAConfiguration((JsonObject) Jsoner.deserialize(reader));
		}
		conf.setVariationFactory(new MyVariationFactory());
		int numruns = conf.getNumRuns();
		System.out.println(conf);
		EvolutionaryAlgorithm myEA = new EvolutionaryAlgorithm(conf);
		myEA.setObjectiveFunction(create(problem));
		myEA.getStatistics().setDiversityMeasure(createMeasure(problem));
		for (int i=0; i<numruns; i++) {
			myEA.run();
			System.out.println ("Run " + i + ": " + 
								String.format(Locale.US, "%.2f", myEA.getStatistics().getTime(i)) + "s\t" +
								myEA.getStatistics().getBest(i).getFitness());
		}
		PrintWriter file = new PrintWriter("stats.json");
		file.print(myEA.getStatistics().toJSON().toJson());
		file.close();
	}
	
	
	/**
	 * Creates an objective function with some default parameters
	 * @param problem name of the objective function
	 * @return the objective function indicated
	 */
	private static ObjectiveFunction create(String problem) {
		switch (problem.toUpperCase()) {
		case "ONEMAX": 
			return new OneMax(100);
			
		case "TRAP":
			return new DeceptiveTrap(25,4);
			
		case "TSP":
			ATSP atsp = new ATSP(100);
			return new TSPObjectiveFunction(atsp);
			
		case "LOP":
			LinearOrderingProblem lop = new LinearOrderingProblem(100);
			return new LOPObjectiveFunction(lop);
			
		case "SPHERE":
			return new Sphere(100, 5.12);
			
		case "BIN-SPHERE":
			return new BinaryEncodedContinuousObjectiveFunction(16, new Sphere(100, 5.12));
						
		default:
			return null;
		}
	}
	
	/**
	 * Creates a diversity measure for a given problem
	 * @param problem name of the objective function
	 * @return a diversity measure suited to the objective function indicated
	 */
	private static DiversityMeasure createMeasure(String problem) {
		switch (problem.toUpperCase()) {
		case "ONEMAX": 			
		case "TRAP":			
		case "TSP":
		case "LOP":
		case "BIN-SPHERE":
			return new EntropyDiversity();
			
		case "SPHERE":
			return new VarianceDiversity();
			
		default:
			return null;
		}
	}

}
