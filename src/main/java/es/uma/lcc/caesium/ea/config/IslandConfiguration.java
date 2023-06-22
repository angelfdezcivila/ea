package es.uma.lcc.caesium.ea.config;

import java.util.ArrayList;
import java.util.List;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

import es.uma.lcc.caesium.ea.operator.replacement.ReplacementFactory;
import es.uma.lcc.caesium.ea.operator.selection.SelectionFactory;
import es.uma.lcc.caesium.ea.operator.variation.VariationFactory;
import es.uma.lcc.caesium.ea.util.JsonUtil;


/**
 * Configuration of individual islands in the EA
 * @author ccottap
 * @version 1.3
 *
 */
public class IslandConfiguration {
	/**
	 * Number of islands by default
	 */
	public static final int NUMISLANDS = 1;
	/**
	 * population size by default
	 */
	public static final int POPSIZE = 100;
	/**
	 * Maximum number of evaluations by default
	 */
	public static final long MAXEVALS = 10000;
	/**
	 * selection operator by default
	 */
	public static final String INITIALIZATION = "BITSTRING";
	/**
	 * selection operator by default
	 */
	public static final String SELECTION = "TOURNAMENT";
	/**
	 * selection operator parameters by default
	 */
	public static final String SELECTION_PARAMS = "2";
	/**
	 * replacement operator by default
	 */
	public static final String REPLACEMENT = "COMMA";
	/**
	 * number of offspring by default
	 */
	public static final int NUMOFFSPRING = POPSIZE-1;
	/**
	 * recombination operator by default
	 */
	public static final String RECOMBINATION = "SPX";
	/**
	 * recombination parameters by default
	 */
	public static final String RECOMBINATION_PARAMS = "0.9";
	/**
	 * mutation operator by default
	 */
	public static final String MUTATION = "BITFLIP";
	/**
	 * mutation parameters by default
	 */
	public static final String MUTATION_PARAMS = "0.01";
	/**
	 * Default frequency for migration (number of iterations).
	 * A value equal to 0 implies no migration is ever performed.
	 */
	public static final int FREQUENCY = 10; 
	/**
	 * Default number of individuals to migrate
	 * A value equal to 0 implies no migration is ever performed.
	 */
	public static final int INDIVIDUALS = 0;
	
	/**
	 * number of islands with this configuration
	 */
	private int numIslands;
	/**
	 * population size
	 */
	private int popSize;
	/**
	 * maximum number of evaluations
	 */
	private long maxEvals;
	/**
	 * number of offspring generated in each iteration
	 */
	private int numOffspring;
	/**
	 * selection operator used
	 */
	private OperatorConfiguration selection;
	/**
	 * replacement operator used
	 */
	private OperatorConfiguration replacement;
	/**
	 * List of variation operators and their parameters
	 */
	private List<OperatorConfiguration> variationOps; 
	/**
	 * frequency among migrations
	 */
	private int frequency;
	/**
	 * number of individuals to migrate
	 */
	private int numIndividuals;	
	/**
	 * In/out configuration of migration
	 */
	private List<OperatorConfiguration> migrationOps; 

	/**
	 * List of additional non-standard configuration settings used in specific island models
	 */
	private List<OperatorConfiguration> extendedConfiguration; 	
	
	/**
	 * Factory used to create replacement operators
	 */
	private ReplacementFactory replacementFactory;
	/**
	 * Factory used to create migration operators
	 */
	private SelectionFactory selectionFactory;
	/**
	 * Factory used to create replacement operators
	 */
	private VariationFactory variationFactory;
	
	/**
	 * Generates an island configuration by default
	 */
	public IslandConfiguration() {
		this(new JsonObject());
	}
	
	
	/**
	 * Constructs the configuration of the island given its JSON description
	 * @param obj island configuration in JSON format
	 */
	public IslandConfiguration(JsonObject obj) {
		// creates default factories
		replacementFactory = new ReplacementFactory();
		selectionFactory = new SelectionFactory();
		variationFactory = new VariationFactory();
		
		if (obj.containsKey("numislands"))
			setNumIslands(JsonUtil.getInt(obj, "numislands"));
		else
			setNumIslands(NUMISLANDS);
		if (obj.containsKey("popsize"))
			setPopulationSize (JsonUtil.getInt(obj, "popsize"));
		else
			setPopulationSize (POPSIZE);
		if (obj.containsKey("offspring"))
			setNumOffspring(JsonUtil.getInt(obj, "offspring"));	
		else
			setNumOffspring(NUMOFFSPRING);	
		if (obj.containsKey("maxevals"))
			setMaxEvaluations (JsonUtil.getLong(obj, "maxevals"));
		else
			setMaxEvaluations (MAXEVALS);
		variationOps = new ArrayList<OperatorConfiguration>();
		if (obj.containsKey("initialization"))
			variationOps.add(processOperator((JsonObject)obj.get("initialization")));
		else {
			variationOps.add(new OperatorConfiguration (INITIALIZATION, new ArrayList<String>()));
		}
		if (obj.containsKey("selection"))
			setSelectionOperator(processOperator((JsonObject)obj.get("selection")));
		else {
			List<String> pars = new ArrayList<String>();
			pars.add(SELECTION_PARAMS);
			setSelectionOperator(new OperatorConfiguration (SELECTION, pars));
		}
		if (obj.containsKey("replacement"))
			setReplacementOperator(processOperator((JsonObject)obj.get("replacement")));
		else {
			setReplacementOperator(new OperatorConfiguration (REPLACEMENT, new ArrayList<String>()));
		}
		if (obj.containsKey("variation")) {
			JsonArray varops = (JsonArray)obj.get("variation");
			for (Object o: varops) {
				variationOps.add(processOperator((JsonObject)o));			
			}
		} 
		else {
			List<String> pars = new ArrayList<String>();
			pars.add(RECOMBINATION_PARAMS);
			variationOps.add(new OperatorConfiguration (RECOMBINATION, pars));
			pars = new ArrayList<String>();
			pars.add(MUTATION_PARAMS);
			variationOps.add(new OperatorConfiguration (MUTATION, pars));
		}
		migrationOps = new ArrayList<OperatorConfiguration>();
		migrationOps.add(null); //placeholders
		migrationOps.add(null);
		if (obj.containsKey("migration")) {
			JsonObject mig = (JsonObject)obj.get("migration");
			if (mig.containsKey("frequency")) 
				setMigrationFrequency(JsonUtil.getInt(mig, "frequency"));
			else
				setMigrationFrequency(FREQUENCY);
			if (mig.containsKey("individuals")) 
				setMigrationIndividuals(JsonUtil.getInt(mig, "individuals"));
			else
				setMigrationIndividuals(INDIVIDUALS);
			if (mig.containsKey("send")) 
				migrationOps.set(0, processOperator((JsonObject)mig.get("send")));
			if (mig.containsKey("receive")) 
				migrationOps.set(1, processOperator((JsonObject)mig.get("receive")));
		}
		extendedConfiguration = new ArrayList<OperatorConfiguration>();
		if (obj.containsKey("extended")) {
			JsonArray extendedOps = (JsonArray)obj.get("extended");
			for (Object o: extendedOps) {
				extendedConfiguration.add(processOperator((JsonObject)o));			
			}
		}
	}


	/**
	 * Returns the factory for replacement operators
	 * @return the replacement factory
	 */
	public ReplacementFactory getReplacementFactory() {
		return replacementFactory;
	}


	/**
	 * Sets a new user-defined factory for replacement operators
	 * @param replacementFactory the replacement factory
	 */
	public void setReplacementFactory(ReplacementFactory replacementFactory) {
		this.replacementFactory = replacementFactory;
	}


	/**
	 * Returns the factory for selection operators
	 * @return the selection factory
	 */
	public SelectionFactory getSelectionFactory() {
		return selectionFactory;
	}


	/**
	 * Sets a new user-defined factory for selection operators
	 * @param selectionFactory the selection factory
	 */
	public void setSelectionFactory(SelectionFactory selectionFactory) {
		this.selectionFactory = selectionFactory;
	}


	/**
	 * Returns the factory for variation operators
	 * @return the variation factory
	 */
	public VariationFactory getVariationFactory() {
		return variationFactory;
	}


	/**
	 * Sets a new user-defined factory for variation operators
	 * @param variationFactory the variation factory
	 */
	public void setVariationFactory(VariationFactory variationFactory) {
		this.variationFactory = variationFactory;
	}


	/**
	 * Reads the name and parameters of an operator
	 * @param op a JSON object with the operator information
	 * @return a pair with the name and parameters of the operator
	 */
	protected static OperatorConfiguration processOperator(JsonObject op) {
		String name = (String)op.get("name");
		List<String> parameters = new ArrayList<String>();
		if (op.containsKey("parameters")) {
			JsonArray pars = (JsonArray)op.get("parameters");
			for (Object o: pars) 
				parameters.add((String)o);
		}
		return new OperatorConfiguration(name, parameters);
	}


	
	/**
	 * Returns the number of variation operators
	 * @return the number of variation operators
	 */
	public int getNumVariationOperators () {
		return variationOps.size();
	}
	
	/**
	 * Gets the name of the i-th variation operator
	 * @param i the index of the operator
	 * @return the name of the i-th operator
	 */
	public String getVariationOperator (int i) {
		return variationOps.get(i).name();
	}
	
	/**
	 * Gets the parameters of the i-th variation operator
	 * @param i the index of the operator
	 * @return the parameters of the i-th operator
	 */
	public List<String> getVariationOperatorParameters (int i) {
		return variationOps.get(i).parameters();
	}
		
	/**
	 * Returns the selection type used
	 * @return the selection type used
	 */
	public String getSelectionOperator() {
		return selection.name();
	}
	
	/**
	 * Returns the parameters of the selection type used
	 * @return the parameters of the selection type used
	 */
	public List<String> getSelectionOperatorParameters() {
		return selection.parameters();
	}

	/**
	 * Sets the selection type used
	 * @param selection the selection type used
	 */
	public void setSelectionOperator(OperatorConfiguration selection) {
		this.selection = selection;
	}

	/**
	 * Returns the replacement type used
	 * @return the replacement type used
	 */
	public String getReplacementOperator() {
		return replacement.name();
	}
	
	/**
	 * Returns the parameters of the replacement type used
	 * @return the parameters of the replacement type used
	 */
	public List<String> getReplacementOperatorParameters() {
		return replacement.parameters();
	}

	/**
	 * Sets the replacement type used
	 * @param replacement the replacement type used
	 */
	public void setReplacementOperator(OperatorConfiguration replacement) {
		this.replacement = replacement;
	}
	
	/**
	 * Returns the number of islands with this configuration
	 * @return the number of islands with this configuration
	 */
	public int getNumIslands() {
		return numIslands;
	}

	/**
	 * Sets the number of islands
	 * @param numIslands the number of islands
	 */
	public void setNumIslands(int numIslands) {
		this.numIslands = numIslands;
	}

	/**
	 * Returns the population size
	 * @return the population size
	 */
	public int getPopulationSize() {
		return popSize;
	}

	/**
	 * Sets the population size
	 * @param popSize the population size
	 */
	public void setPopulationSize(int popSize) {
		this.popSize = popSize;
	}

	/**
	 * Returns the maximum number of evaluations
	 * @return the maximum number of evaluations
	 */
	public long getMaxEvaluations() {
		return maxEvals;
	}

	/**
	 * Sets the maximum number of evaluations
	 * @param maxEvals the maximum number of evaluations
	 */
	public void setMaxEvaluations(long maxEvals) {
		this.maxEvals = maxEvals;
	}

	/**
	 * Returns the number of offspring
	 * @return the number of offspring
	 */
	public int getNumOffspring() {
		return numOffspring;
	}

	/**
	 * Sets the number of offspring
	 * @param numOffspring the number of offspring
	 */
	public void setNumOffspring(int numOffspring) {
		this.numOffspring = numOffspring;
	}

	
	/**
	 * Returns the migration frequency
	 * @return the migration frequency
	 */
	public int getMigrationFrequency() {
		return frequency;
	}

	/**
	 * Sets the migration frequency
	 * @param frequency the migration frequency
	 */
	public void setMigrationFrequency(int frequency) {
		this.frequency = frequency;
	}

	/**
	 * Returns the number of individuals to migrate
	 * @return the number of individuals to migrate
	 */
	public int getMigrationIndividuals() {
		return numIndividuals;
	}

	/**
	 * Sets the number of individuals to migrate
	 * @param n the number of individuals to migrate
	 */
	public void setMigrationIndividuals(int n) {
		this.numIndividuals = n;
	}

	/**
	 * Returns the list of outgoing parameters
	 * @return the list of outgoing parameters
	 */
	public List<String> getOutgoingParameters() {
		if (migrationOps.get(0) != null)
			return migrationOps.get(0).parameters();
		else 
			return null;
	}

	/**
	 * Returns the name of the outgoing operator
	 * @return the name of the outgoing operator
	 */
	public String getOutgoingOperator() {
		if (migrationOps.get(0) != null)
			return migrationOps.get(0).name();
		else 
			return null;
	}

	/**
	 * Returns the name of the incoming operator
	 * @return the name of the incoming operator
	 */
	public String getIncomingOperator() {
		if (migrationOps.get(1) != null)
			return migrationOps.get(1).name();
		else 
			return null;
	}
	
	/**
	 * Returns the list of incoming parameters
	 * @return the list of incoming parameters
	 */
	public List<String> getIncomingParameters() {
		if (migrationOps.get(1) != null)
			return migrationOps.get(1).parameters();
		else 
			return null;
	}
	
	@Override
	public String toString() {
		String str = "islands: " + numIslands + "\n" + 
					 "popsize: " + popSize + "\n" + 
					 "offspring: " + numOffspring + "\n" + 
					 "maxevals: " + maxEvals + "\n" +
					 "initialization: " + variationOps.get(0).name();
		if (variationOps.get(0).parameters().size()>0)
			str += "(" + variationOps.get(0).parameters() + ")";
		str += "\nselection: " + selection.name();
		if (selection.parameters().size()>0)
			str += " (" + selection.parameters() + ")";
		str += "\n";
		int n = variationOps.size();
		for (int i=1; i<n; i++) {
			str +=  "variation: " + variationOps.get(i).name() + " (" + variationOps.get(i).parameters() + ")\n";
		}
		str += "replacement: " + replacement.name();
		if (replacement.parameters().size()>0)
			str += " (" + replacement.parameters() + ")";
		str += "\n";
		if ((migrationOps.get(0) != null) || (migrationOps.get(1) != null)) {
			str += "migration : ";
			if (migrationOps.get(0) != null) {
				str += migrationOps.get(0).name();
				if (migrationOps.get(0).parameters().size()>0)
					str += "(" + migrationOps.get(0).parameters() + ")";
				str += " [freq=" + getMigrationFrequency() + ", num=" + getMigrationIndividuals() + "] + ";
			}
			else
				str += "null + ";
			if (migrationOps.get(1) != null) {
				str += migrationOps.get(1).name();
				if (migrationOps.get(1).parameters().size()>0)
					str += "(" + migrationOps.get(1).parameters() + ")";
			}
			else
				str += "null";
		}
		return str;
	}


}
