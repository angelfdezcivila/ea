package es.uma.lcc.caesium.ea.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;

import es.uma.lcc.caesium.ea.base.Individual;

/**
 * Utility functions for EAs
 * @author ccottap
 * @version 1.0
 *
 */
public class EAUtil {
	
	/**
	 * comparator for maximization
	 */
	public static final Comparator<Individual> maxComp = 
			new Comparator<Individual> () {
				@Override
				public int compare(Individual o1, Individual o2) {
					return Double.compare(o2.getFitness(), o1.getFitness());
				}
	};
	
	/**
	 * comparator for minimization
	 */
	public static final Comparator<Individual> minComp = 
			new Comparator<Individual> () {
				@Override
				public int compare(Individual o1, Individual o2) {
					return Double.compare(o1.getFitness(), o2.getFitness());
				}
	};
	
	/**
	 * global random number generator
	 */
	private static Random rng = new Random(1);

	/**
	 * Last seed used in the rng (kept for reproducibility)
	 */
	private static long seed;
	
	/**
	 * Sets the seed of the RNG
	 * @param n the seed of the RNG
	 */
	public static void setSeed (long n) {
		seed = n;
		rng.setSeed(n);
	}
	
	/**
	 * Returns the last seed used
	 * @return the last seed used
	 */
	public static long getSeed() {
		return seed;
	}
	
	
	/**
	 * Returns a random integer between 0 and n-1
	 * @param n number of possible values
	 * @return a number between 0 and n-1
	 */
	public static int random(int n) {
		return rng.nextInt(n);
	}
	
	/**
	 * Returns a double number between 0.0 (inc) and 1.0 (exc)
	 * @return a double number between 0.0 (inc) and 1.0 (exc)
	 */
	public static double random01() {
		return rng.nextDouble(1.0);
	}

	/**
	 * Returns a normally distributed random number (mean = 0, var = 1)
	 * @return a normally distributed random number
	 */
	public static double nrandom() {
		return rng.nextGaussian();
	}

	/**
	 * Returns a random permutation of numbers 0,...,l-1
	 * @param l length of the permutation
	 * @return a random permutation of length l
	 */
	public static List<Integer> randomPermutation(int l) {
		List<Integer> perm = new ArrayList<Integer>(l);
		for (int i=0; i<l; i++)
			perm.add(i);
		for (int i=0; i<l; i++) {
			int p = random(l-i) + i;
			int temp = perm.get(p);
			perm.set(p, perm.get(i));
			perm.set(i, temp);
		}
		return perm;
	}
	
	
	/**
	 * Samples an object according to their corresponding
	 * probabilities.
	 * @param probs a dictionary of objects and their selection probabilities
	 * @param <K> class of the objects in the dictionary
	 * @return an object from the dictionary
	 */
	public static<K> K sample(HashMap<K, Double> probs) {
		double p = EAUtil.random01();
		for (Entry<K, Double> e: probs.entrySet()) {
			p -= e.getValue();
			if (p<0)
				return e.getKey();				
		}
		assert false;
		return null;
	}

		
}
