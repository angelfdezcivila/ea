package es.uma.lcc.caesium.ea.operator.migration;

import java.util.List;

/**
 * Factory for topologies
 * @author ccottap
 * @version 1.0
 */
public class TopologyFactory {

	/**
	 * Creates a topology given its name and parameters. The topology is expressed 
	 * as a mapping from integers (the id of an island) to sets of integers (the 
	 * ids of islands it is connected to).
	 * @param name name of the topology
	 * @param parameters parameters of the topology. There is at least one parameter (the 1st one): the number of islands
	 * @return the topology created
	 */
	public static Topology create (String name, List<String> parameters) {
		int n = Integer.parseInt(parameters.get(0));
		Topology topology = new Topology(n);
		
		switch (name.toUpperCase()) {
		case "RING": // bidirectional ring
			for (int i=0; i<n; i++) {
				topology.add(i, (i+1)%n);
				topology.add((i+1)%n, i);
			}
			topology.setRegenerable(false);

		case "COMPLETE": // complete graph
			for (int i=0; i<n; i++) 
				for (int j=i+1; j<n; j++) {
					topology.add(i, j);
					topology.add(j, i);
				}
			topology.setRegenerable(false);

		
		case "EMPTY": // default topology: no connections
		default:
			topology.setRegenerable(false);
		
		}
		return topology;
	}
	

}
