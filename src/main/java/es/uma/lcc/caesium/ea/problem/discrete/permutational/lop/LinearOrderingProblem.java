package es.uma.lcc.caesium.ea.problem.discrete.permutational.lop;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * An instance of the Linear Ordering Problem
 * @author ccottap
 * @version 1.0
 *
 */
public class LinearOrderingProblem {
	/**
	 * class-level random generator
	 */
	protected static Random r = new Random(1);
	/**
	 * instance data
	 */
	protected int[][] matrix;
	/**
	 * size of the instance
	 */
	protected int size;
	
	/**
	 * Creates a random instance of the given size
	 * @param size size of the instance
	 */
	public LinearOrderingProblem (int size) {		
		this.size = size;
		int maxval = size*size;
		matrix = new int[size][size];
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				if (i != j)
					matrix[i][j] = r.nextInt(maxval);
				else
					matrix[i][j] = 0;
			}
		}
	}
	
	/**
	 * Creates an instance by reading it from a file
	 * @param filename name of the file with the data
	 * @throws FileNotFoundException if the file cannot be found
	 */
	public LinearOrderingProblem (String filename) throws FileNotFoundException {
		Scanner inputFile = new Scanner(new File(filename));
		
		size = inputFile.nextInt();
		matrix = new int[size][size];
		for (int i=0; i<size; i++)
			for (int j=0; j<size; j++) {
				matrix[i][j] = inputFile.nextInt();
			}
		inputFile.close();
	}
	
	/**
	 * Returns the size of the instance
	 * @return the size of the instance
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Evaluates the value of a solution
	 * @param order an ordering of the rows/columns
	 * @return the sum of elements above the main diagonal upon reordering
	 */
	public int evaluate(List<Integer> order) {
		int sum = 0;
		int lim = size-1;
		for (int i=0; i<lim; i++) {
			int v1 = order.get(i);
			for (int j=i+1; j<size; j++)
				sum += matrix[v1][order.get(j)];
		}
		return sum;
	}
	
	@Override
	public String toString() {
		String str = size + "\n";
		for (int i=0; i<size; i++) {
			str += matrix[i][0];
			for (int j=1; j<size; j++) {
				str += "\t" + matrix[i][j];
			}
			str += "\n";
		}

		return str;
	}
	
	/**
	 * Main function for testing
	 * @param args command-line arguments
	 * @throws FileNotFoundException if a data instance cannot be found
	 */
	public static void main(String[] args) throws FileNotFoundException {
		LinearOrderingProblem lop = new LinearOrderingProblem ("test.lop");
		System.out.println(lop);
		ArrayList<Integer> sol = new ArrayList<Integer>();
		sol.add(4);
		sol.add(2);
		sol.add(3);
		sol.add(1);
		sol.add(0);
		System.out.println(lop.evaluate(sol));
	}

}
