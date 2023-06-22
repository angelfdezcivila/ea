package es.uma.lcc.caesium.ea.operator.migration;

import java.util.HashMap;
import java.util.List;

import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.ObjectiveFunction;
import es.uma.lcc.caesium.ea.operator.Operator;
import es.uma.lcc.caesium.ea.operator.replacement.ReplacementOperator;
import es.uma.lcc.caesium.ea.operator.selection.SelectionOperator;

/**
 * Migration of individuals among islands
 * @author ccottap
 * @version 1.0
 *
 */
public class MigrationOperator extends Operator {
	/**
	 * frequency among migrations
	 */
	private int frequency;
	/**
	 * iterations remaining for nest migration
	 */
	private int counter;
	/**
	 * number of individuals to migrate
	 */
	private int numIndividuals;
	/**
	 * operator to select which individuals to migrate
	 */
	private SelectionOperator emigrate;
	/**
	 * operator to select how to deal with incoming individuals
	 */
	private ReplacementOperator immigrate;
	/**
	 * input buffer of the current island
	 */
	private IslandBuffer buffer;
	/**
	 * list of input buffers from other islands
	 */
	private HashMap<Integer, IslandBuffer> connections;
	
	
	
	/**
	 * Creates the operator given its parameters
	 * @param out selection operator to pick individuals to migrate
	 * @param in replacement operator to determine how to accept incoming individuals
	 * @param freq frequency of migration
	 * @param num number of individuals to migrate
	 */
	public MigrationOperator(SelectionOperator out, ReplacementOperator in, int freq, int num) {
		super();
		emigrate = out;
		immigrate = in;
		frequency = freq;
		numIndividuals = num;
		connections = new HashMap<Integer, IslandBuffer>();
		buffer = new IslandBuffer();
		newRun();
	}
	
	
	/**
	 * Sets the objective function for potential use inside some operator
	 * @param obj the objective function
	 */
	public void setObjectiveFunction (ObjectiveFunction obj) {
		super.setObjectiveFunction(obj);
		if (emigrate != null)
			emigrate.setObjectiveFunction(obj);
		if (immigrate != null)
			immigrate.setObjectiveFunction(obj);
	}
	
	/**
	 * Adds a unidirectional link from self to the island whose
	 * migration operator is passed as a parameter.
	 * @param id the id of the island to connect to
	 * @param op the migration operator of another island
	 */
	public void connect (int id, MigrationOperator op) {
		connections.put(id, op.getBuffer());
	}
	
	/**
	 * Removes a connection
	 * @param id the id of the island whose connection to is removed
	 */
	public void removeConnection(int id) {
		connections.remove(id);
	}
	
	/**
	 * Removes all connections
	 */
	public void resetConnections() {
		connections.clear();
	}
	
	/**
	 * Returns the buffer of the island
	 * @return the buffer of the island
	 */
	private IslandBuffer getBuffer() {
		return buffer;
	}

	/**
	 * Resets the counter to determine when the new migration will take place
	 * and empties the island buffer
	 */
	public void newRun() {
		super.newRun();
		buffer.newRun();
		counter = frequency;
	}

	/**
	 * Checks whether migration has to be performed. If so, individuals are selected 
	 * and sent to the islands connected, and true is returned. Otherwise (the time has
	 * not come or no migrant selection mechanism was specified when creating the 
	 * migration operator), it does nothing and returns false.
	 * @param population the list of individuals from which migrants will be selected
	 * @return true iff migration is performed
	 */
	public boolean send(List<Individual> population) {
		if (emigrate != null) {
			counter--;
			if (counter == 0) {
				counter = frequency;
				for (IslandBuffer b: connections.values()) {
					b.add(emigrate.apply(population, numIndividuals));
				}	
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks whether there are individuals in the input buffer, and 
	 * if so, inserts them into the population. If no insertion mechanism was
	 * specified when creating the migration operator, the input population is
	 * returned.
	 * @param population the list of individuals to which migrants will be inserted
	 * @return the updated population
	 */
	public List<Individual> receive(List<Individual> population) {
		if (immigrate != null) {
			return immigrate.apply(population, buffer.getAll());		
		}
		
		return population;
	}
	
	
	@Override
	public String toString() {
		return "Migration(" + emigrate + "[" + frequency + ", " + numIndividuals + "], " + immigrate + ")";
	}

}
