package es.uma.lcc.caesium.ea.operator.migration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/**
 * Interconnection topology among islands
 * @author ccottap
 * @version 1.0
 */
public class Topology {
	/**
	 * Mapping from island ID to the set of islands ID the former is conencted to
	 */
	HashMap<Integer, Set<Integer>> connections;
	/**
	 * Whether the topology has a random component during creation that
	 * might produce a different result if created again.
	 */
	public boolean regenerable;
	
	/**
	 * Basic constructor for an empty topology
	 */
	public Topology() {
		connections = new HashMap<Integer, Set<Integer>>();
		regenerable = false;
	}
	
	/**
	 * basic constructor for an empty topology with n islands
	 * @param n the number of islands
	 */
	public Topology(int n) {
		this();
		for (int i=0; i<n; i++) {
			connections.put(i, new HashSet<Integer>());
		}
	}
	
	
	/**
	 * Indicates whether the topology is regenerable, i.e., 
	 * whether it has a random component during creation that
	 * might produce a different result if created again.
	 * @return whether the topology is regenerable
	 */
	public boolean isRegenerable() {
		return regenerable;
	}


	/**
	 * Sets the regenerable status of the topology
	 * @param regenerable the regenerable status
	 */
	public void setRegenerable(boolean regenerable) {
		this.regenerable = regenerable;
	}


	/**
	 * Adds a connection to the topology
	 * @param origin island sending individuals
	 * @param destination island receiving individuals
	 */
	public void add(int origin, int destination) {
		Set<Integer> links = connections.get(origin);
		if (links == null) {
			links = new HashSet<Integer>();
			connections.put(origin, links);
		}
		links.add(destination);
	}
	
	/**
	 * Removes a connection to the topology
	 * @param origin island sending individuals
	 * @param destination island receiving individuals
	 */
	public void remove(int origin, int destination) {
		Set<Integer> links = connections.get(origin);
		if (links == null) {
			links = new HashSet<Integer>();
			connections.put(origin, links);
		}
		links.remove(destination);
	}
	
	/**
	 * Gets the connections from an island
	 * @param origin the id of the island
	 * @return a list with the ids of the islands that origin can send to.
	 */
	public Set<Integer> get (int origin) {
		Set<Integer> links = connections.get(origin);
		if (links == null) {
			links = new HashSet<Integer>();
			connections.put(origin, links);
		}
		return links;
	}
	
	@Override
	public String toString() {
		return "topology: " + connections;
	}
	

}
