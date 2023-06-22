package es.uma.lcc.caesium.ea.operator.migration;

import java.util.LinkedList;
import java.util.List;

import es.uma.lcc.caesium.ea.base.Individual;

/**
 * Input buffer into which migrating individuals sent are stored and retrieved.
 * It behaves as a FIFO structure.
 * @author ccottap
 * @version 1.0
 *
 */
public class IslandBuffer {
	/**
	 * Internal list of individuals
	 */
	private List<Individual> buffer;

	/**
	 * Constructs an empty buffer
	 */
	public IslandBuffer() {
		buffer = new LinkedList<Individual>();
	}
	
	/**
	 * Clears the buffer
	 */
	public void newRun() {
		buffer.clear();
	}
	
	/**
	 * Returns the size of the buffer
	 * @return the size of the buffer
	 */
	public int size() {
		return buffer.size();
	}
	
	/**
	 * Inserts an individual in the buffer. Returns true iff 
	 * the individual could be inserted. By default it can be 
	 * inserted, but a derived class might implement a fixed-size
	 * buffer.
	 * @param ind the individual to be inserted
	 * @return true iff the individual could be inserted
	 */
	public boolean add (Individual ind) {
		buffer.add(ind);
		return true;
	}
	
	/**
	 * Inserts a collection of individuals in the buffer. Returns true iff 
	 * the individuals could be inserted. By default they can be 
	 * inserted, but a derived class might implement a fixed-size
	 * buffer.
	 * @param pop the individuals to be inserted
	 * @return true iff the individuals could be inserted
	 */
	public boolean add (List<Individual> pop) {
		boolean ok = true;
		
		for (Individual ind: pop) {
			ok &= add(ind);
		}
		return ok;
	}
	
	/**
	 * Extracts the oldest individual in the buffer.
	 * Subsequently, there will be one individual less 
	 * in the buffer. 
	 * @return the oldest individual in the buffer
	 */
	public Individual get() {
		Individual ind = buffer.get(0);
		buffer.remove(0);
		return ind;
	}
	
	/**
	 * Returns all individuals in the buffer. Individuals are extracted and therefore
	 * the buffer is subsequently empty afterwards.
	 * @return a list of all individuals in the buffer
	 */
	public List<Individual> getAll() {
		List<Individual> migrants = new LinkedList<Individual>(buffer);
		buffer.clear();
		return migrants;
	}
	
	@Override
	public String toString() {
		String str = "Buffer : " + buffer;
		return str;
	}
}
