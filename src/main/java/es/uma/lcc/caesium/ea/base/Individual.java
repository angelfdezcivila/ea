package es.uma.lcc.caesium.ea.base;


/**
 * Individuals of the EA
 * @author ccottap
 * @version 1.1
 *
 */
public class Individual {
	/**
	 * fitness of the individual
	 */
	protected double fitness;
	/**
	 * boolean flag to determine whether the individual has been evaluated
	 */
	private boolean evaluated;
	/**
	 * genotype of the individual
	 */
	protected Genotype genome;
	
	/**
	 * Creates an empty individual 
	 */
	public Individual() {
		touch();
	}
	
	/**
	 * Creates an individual by cloning an existing one
	 * @param individual the individual to copy
	 */
	public Individual(Individual individual) {
		setFitness(individual.fitness);
		setGenome(individual.genome.clone());
		evaluated = individual.evaluated;
	}

	/**
	 * Indicates the fitness information of the individual 
	 * is no longer valid.
	 */
	public void touch() {
		evaluated = false;
	}
	
	/**
	 * Returns whether the individual has been evaluated
	 * @return true iff the individual has been evaluated
	 */
	public boolean isEvaluated() {
		return evaluated;
	}

	/**
	 * Returns the genome
	 * @return the genome
	 */
	public Genotype getGenome() {
		return genome;
	}

	/**
	 * Sets the genome
	 * @param genome the genome
	 */
	public void setGenome(Genotype genome) {
		this.genome = genome;
		touch();
	}

	/**
	 * Returns the fitness of the individual
	 * @return the fitness of the individual
	 */
	public double getFitness() {
		assert evaluated : "individual not evaluated";
		return fitness;
	}
	
	/**
	 * Sets the fitness of the individual
	 * @param fitness the fitness of the individual
	 */
	public void setFitness (double fitness) {
		this.fitness = fitness;
		evaluated = true;
	}
	
	/**
	 * Returns a copy of the individual
	 * @return a copy of the individual
	 */
	public Individual clone() {
		return new Individual(this);
	}
	
		
	@Override
	public String toString() {
		return "{\n\tfitness: " + (evaluated?fitness:"*") + "\n\tgenome: " + genome + "\n}";
	}
}
