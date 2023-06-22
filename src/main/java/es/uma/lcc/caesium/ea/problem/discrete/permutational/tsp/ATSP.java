
package es.uma.lcc.caesium.ea.problem.discrete.permutational.tsp;

import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Asymmetric Traveling Salesman Problem
 * 
 * @author ccottap
 * @version 1.0
 *
 */
public class ATSP {
	/**
	 * the number of cities
	 */
	protected int numCities;
	/**
	 * inter-city distances
	 */
	protected int[][] distances;
	/**
	 * class-level random generator
	 */
	protected static Random r = new Random(1);

	/**
	 * Default parameter-less constructor
	 */
	protected ATSP() {
	}

	/**
	 * Creates a random instance of the TSP of the desired number of cities.
	 * Distances are determined by the randomize() method.
	 * 
	 * @see randomize
	 * @param numCities the number of cities
	 */
	public ATSP(int numCities) {
		this.numCities = numCities;
		distances = new int[numCities][numCities];

		randomize();
	}

	/**
	 * Creates a TSP instance reading data from a file
	 * 
	 * @param filename the name of the file
	 * @throws FileNotFoundException if the file is not found
	 */
	public ATSP(String filename) throws FileNotFoundException {
		Scanner inputFile = new Scanner(new File(filename));

		numCities = inputFile.nextInt();
		distances = new int[numCities][numCities];
		for (int i = 0; i < numCities; i++)
			for (int j = 0; j < numCities; j++)
				distances[i][j] = inputFile.nextInt();

		inputFile.close();

	}

	/**
	 * Internal constant to determine the area of the plane on which cities will be
	 * laid out at random
	 */
	protected static final int RANGE = 1000;

	/**
	 * Randomizes the distances in the matrix by placing the cities in the plane and
	 * computing Euclidean distances.
	 * 
	 */
	protected void randomize() {
		for (int i = 0; i < numCities; i++) {
			for (int j = 0; j < numCities; j++) {
				if (i != j)
					distances[i][j] = r.nextInt(RANGE);
				else
					distances[i][j] = Integer.MAX_VALUE;
			}
		}
	}

	/**
	 * Returns a printable version of the TSP instance
	 * 
	 * @return a printable version of the TSP instance
	 */
	@Override
	public String toString() {
		String str = "";

		str += numCities + "\n";
		for (int i = 0; i < numCities; i++) {
			for (int j = 0; j < numCities; j++)
				str += distances[i][j] + "\t";
			str += "\n";
		}

		return str;
	}

	/**
	 * Writes a TSP instance to a file
	 * 
	 * @param filename the name of the file
	 * @throws IOException if the file cannot be opened for writing
	 */
	public void writeToFile(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		out.println(this);
		out.close();
	}

	/**
	 * Gets the number of cities in the instance
	 * 
	 * @return the numCities
	 */
	public int getNumCities() {
		return numCities;
	}

	/**
	 * Gets the distance between two cities
	 * 
	 * @param i a city
	 * @param j another city
	 * @return the distance between i and j
	 */
	public int getDistance(int i, int j) {
		return distances[i][j];
	}

	/**
	 * Sets the distance between two cities
	 * 
	 * @param i a city
	 * @param j another city
	 * @param d the distance
	 */
	public void setDistance(int i, int j, int d) {
		distances[i][j] = d;
	}

	/**
	 * Evaluates the length of a tour
	 * 
	 * @param tour the permutation of the cities
	 * @return the length of the tour
	 */
	public int evaluateTour(List<Integer> tour) {
		int l = distances[tour.get(numCities - 1)][tour.get(0)];

		for (int i = 1; i < numCities; i++)
			l += distances[tour.get(i - 1)][tour.get(i)];

		return l;
	}

}
