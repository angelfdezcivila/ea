package es.uma.lcc.caesium.ea.config;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

import es.uma.lcc.caesium.ea.operator.replacement.ReplacementFactory;
import es.uma.lcc.caesium.ea.operator.selection.SelectionFactory;
import es.uma.lcc.caesium.ea.operator.variation.VariationFactory;
import es.uma.lcc.caesium.ea.util.JsonUtil;

/**
 * Configuration of an EA
 * @author ccottap
 * @version 1.3
 */
public class EAConfiguration {
	/**
	 * Topology by default
	 */
	public static final String TOPOLOGY = "EMPTY";
	/**
	 * default seed for the RNG
	 */
	public static final long SEED = 1;
	/**
	 * default number of runs
	 */
	public static final int NUMRUNS = 1;
	
	/**
	 * total number of islands in the EA
	 */
	private int numIslands;
	/**
	 * configuration of each island (or subset of islands)
	 */
	private List<IslandConfiguration> iConf;
	/**
	 * island connectivity
	 */
	private OperatorConfiguration topology;
	/**
	 * seed for the RNG
	 */
	private long seed;
	/**
	 * number of runs
	 */
	private int numruns;
	/**
	 * List of additional non-standard configuration settings globally used in the EA
	 */
	private List<OperatorConfiguration> extendedConfiguration;

	
	/**
	 * Generates a default configuration
	 */
	public EAConfiguration() {
		this(new JsonObject());
	}
	

	/**
	 * Generates a configuration given its JSON description
	 * @param conf the configuration in JSON format
	 */
	public EAConfiguration(JsonObject conf) {
		numIslands = 0;
		iConf = new ArrayList<IslandConfiguration> ();
		if (conf.containsKey("islands")) {
			JsonArray jsonIslands = (JsonArray) conf.get("islands");
			for (Object obj: jsonIslands) {
				addIslandConfiguration(new IslandConfiguration((JsonObject)obj));
			}
		}
		else {
			addIslandConfiguration(new IslandConfiguration());
		}
		if (conf.containsKey("topology")) {
			JsonObject obj = (JsonObject) conf.get("topology");
			setTopology(IslandConfiguration.processOperator(obj));
		}
		else
			setTopology(new OperatorConfiguration(TOPOLOGY, new ArrayList<String>(1)));
		if (conf.containsKey("seed"))
			setSeed(JsonUtil.getLong(conf, "seed"));
		else
			setSeed(SEED);
		if (conf.containsKey("numruns"))
			setNumRuns(JsonUtil.getInt(conf, "numruns"));
		else
			setNumRuns(NUMRUNS);
		
		extendedConfiguration = new ArrayList<OperatorConfiguration>();
		if (conf.containsKey("extended")) {
			JsonArray extendedOps = (JsonArray)conf.get("extended");
			for (Object o: extendedOps) {
				extendedConfiguration.add(IslandConfiguration.processOperator((JsonObject)o));	
			}
		}
	}
	

	/**
	 * Sets a new user-defined factory for replacement operators
	 * @param replacementFactory the replacement factory
	 */
	public void setReplacementFactory(ReplacementFactory replacementFactory) {
		for (IslandConfiguration iconf: iConf) {
			iconf.setReplacementFactory(replacementFactory);
		}	
	}


	/**
	 * Sets a new user-defined factory for selection operators
	 * @param selectionFactory the selection factory
	 */
	public void setSelectionFactory(SelectionFactory selectionFactory) {
		for (IslandConfiguration iconf: iConf) {
			iconf.setSelectionFactory(selectionFactory);
		}	
	}


	/**
	 * Sets a new user-defined factory for variation operators
	 * @param variationFactory the variation factory
	 */
	public void setVariationFactory(VariationFactory variationFactory) {
		for (IslandConfiguration iconf: iConf) {
			iconf.setVariationFactory(variationFactory);
		}
	}

	/**
	 * Sets the number of runs
	 * @param n number of runs
	 */
	public void setNumRuns(int n) {
		numruns = n;
	}
	
	/**
	 * Returns the number of run
	 * @return the number of runs
	 */
	public int getNumRuns () {
		return numruns;
	}
	
	/**
	 * Sets the seed for the RNG
	 * @param s seed for the RNG
	 */
	public void setSeed(long s) {
		seed = s;
	}

	/**
	 * Returns the seed of the RNG
	 * @return the seed of the RNG
	 */
	public long getSeed() {
		return seed;
	}
	
	/**
	 * Adds the configuration of an island (or a group thereof)
	 * @param iconf the island configuration
	 */
	public void addIslandConfiguration(IslandConfiguration iconf) {
		numIslands += iconf.getNumIslands();
		iConf.add(iconf);	
	}

	/**
	 * Returns the number of islands
	 * @return the number of islands
	 */
	public int getNumIslands() {
		return numIslands;
	}
	
	
	/**
	 * Recomputes the number of islands (useful if the configuration
	 * of islands has been manually updated).
	 */
	public void refreshNumIslands() {
		numIslands = 0;
		for (IslandConfiguration iconf: iConf) {
			numIslands += iconf.getNumIslands();
		}
	}
	
	
	/**
	 * Gets the configuration of the i-th island. If the index is 
	 * out of range, null is returned.
	 * @param iID index of the island
	 * @return the configuration of the i-th island
	 */
	public IslandConfiguration getIslandConfiguration (int iID) {
		IslandConfiguration ic = null;
		if ((iID >= 0) && (iID < numIslands)) {
			int c = 0;
			do {
				ic = iConf.get(c);
				iID -= ic.getNumIslands();
				c++;
			} while (iID >= 0);
		}
		return ic;
	}

	/**
	 * Returns the interconnection topology
	 * @return the topology
	 */
	public String getTopology() {
		return topology.name();
	}
	
	/**
	 * Returns the parameters of the interconnection topology
	 * @return the parameters of the topology
	 */
	public List<String> getTopologyParameters() {
		return topology.parameters();
	}


	/**
	 * Sets the interconnection topology
	 * @param topology the topology configuration to set
	 */
	public void setTopology(OperatorConfiguration topology) {
		this.topology = topology;
	}
	
	@Override
	public String toString() {
		String str = "numruns: "  + getNumRuns() + "\n" +
					 "seed: "     + getSeed() + "\n" +
					 "#islands: " + getNumIslands() + "\n" +
					 "topology: " + getTopology() + " " + getTopologyParameters() + "\n";
		if (extendedConfiguration.size()>0) {
			str += "extended:\n";
			for (OperatorConfiguration opc: extendedConfiguration) {
				str += "\tname: " + opc.name() + " (" + opc.parameters() + ")\n";
			}
		}
			
		int k = 1;
		for (IslandConfiguration ic: iConf)
			str += "Island configuration #" + (k++) + "\n\t" + ic.toString().replace("\n", "\n\t") + "\n";
		return str;
	}
	
	/**
	 * Returns a list with all the extended configuration keys (if any) 
	 * @return a list with all the extended configuration keys (if any)
	 */
	public List<String> getExtendedConfigurationKeys() {
		LinkedList<String> keys = new LinkedList<String>();
		for (OperatorConfiguration opc: extendedConfiguration) {
			keys.add(opc.name());
		}
		return keys;
	}
	
	/**
	 * Returns the list of parameters associated with a certain extended configuration key
	 * @param name name of the key
	 * @return the list of parameters associated with the key indicated
	 */
	public List<String> getExtendedConfigurationValue(String name) {
		String key = name.toLowerCase();
		for (OperatorConfiguration opc: extendedConfiguration) {
			if (opc.name().equals(key))
				return opc.parameters();
		}
		return null;
	}

}
