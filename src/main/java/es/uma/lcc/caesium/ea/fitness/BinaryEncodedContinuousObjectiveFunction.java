package es.uma.lcc.caesium.ea.fitness;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;

/**
 * Shell for continuous functions to be optimized via a binary encoding
 * @author ccottap
 * @version 1.0
 */
public class BinaryEncodedContinuousObjectiveFunction extends DiscreteObjectiveFunction {
	/**
	 * the continuous objective function to be optimized
	 */
	private ContinuousObjectiveFunction func;
	/**
	 * number of bits to encode each continuous variable
	 */
	private int bits;
	/**
	 * number of continuous variables
	 */
	private int numContVars;
	/**
	 * integer value of a string with all bits equal to one
	 */
	private long maxval;
	/**
	 * auxiliary individual to make the translation from binary to continuous
	 */
	private Individual aux;
	
	/**
	 * Creates the shell for a function, indicating the number of bits to be used for encoding each
	 * of the continuous variables
	 * @param bitsPerVar number of bits to be used for encoding each of the continuous variables
	 * @param cof a continuous objective function
	 */
	public BinaryEncodedContinuousObjectiveFunction(int bitsPerVar, ContinuousObjectiveFunction cof) {
		super(bitsPerVar*cof.getNumVars(), 2);
		bits = bitsPerVar;
		func = cof;
		numContVars = cof.getNumVars();
		Genotype g = new Genotype(numContVars);
		aux = new Individual();
		aux.setGenome(g);
		maxval = 0;
		for (int i=0; i<bits; i++)
			maxval = (maxval<<1) + 1;
	}


	@Override
	public OptimizationSense getOptimizationSense() {
		return func.getOptimizationSense();
	}

	@Override
	protected double _evaluate(Individual ind) {
		Genotype g0 = ind.getGenome();
		Genotype gaux = aux.getGenome();
		for (int i=0, j=0; i<numContVars; i++, j+=bits) {
			long val = 0;
			for (int k=0; k<bits; k++) {
				val <<= 1;
				val += (int) g0.getGene(j+k);
			}
			gaux.setGene(i, (double)val/(double)maxval * (func.getMaxVal(i)-func.getMinVal(i)) + func.getMinVal(i));
		}
		aux.touch();
		return func.evaluate(aux);
	}

}
