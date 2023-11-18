package es.uma.lcc.caesium.ea.operator.variation;

import java.util.List;

import es.uma.lcc.caesium.ea.operator.variation.initialization.continuous.RandomVector;
import es.uma.lcc.caesium.ea.operator.variation.initialization.discrete.RandomBitString;
import es.uma.lcc.caesium.ea.operator.variation.initialization.discrete.RandomIntegerSequence;
import es.uma.lcc.caesium.ea.operator.variation.initialization.discrete.RandomPermutation;
import es.uma.lcc.caesium.ea.operator.variation.initialization.discrete.RandomSet;
import es.uma.lcc.caesium.ea.operator.variation.mutation.BinomialMutation;
import es.uma.lcc.caesium.ea.operator.variation.mutation.continuous.GaussianMutation;
import es.uma.lcc.caesium.ea.operator.variation.mutation.discrete.BitFlip;
import es.uma.lcc.caesium.ea.operator.variation.mutation.discrete.RandomSubstitution;
import es.uma.lcc.caesium.ea.operator.variation.mutation.discrete.permutation.Flip;
import es.uma.lcc.caesium.ea.operator.variation.mutation.discrete.permutation.Reverse;
import es.uma.lcc.caesium.ea.operator.variation.mutation.discrete.permutation.Swap;
import es.uma.lcc.caesium.ea.operator.variation.mutation.discrete.set.MutateSetElement;
import es.uma.lcc.caesium.ea.operator.variation.recombination.continuous.BLX;
import es.uma.lcc.caesium.ea.operator.variation.recombination.discrete.SinglePointCrossover;
import es.uma.lcc.caesium.ea.operator.variation.recombination.discrete.UniformCrossover;
import es.uma.lcc.caesium.ea.operator.variation.recombination.discrete.permutation.OrderCrossover;
import es.uma.lcc.caesium.ea.operator.variation.recombination.discrete.permutation.UniformCycleCrossover;
import es.uma.lcc.caesium.ea.operator.variation.recombination.discrete.set.FixedSizeSetRecombination;

/**
 * Factory for variation operators
 * @author ccottap
 * @version 1.3
 *
 */
public class VariationFactory {
	/**
	 * Returns a variation operator given its name.
	 * If the name does not correspond to any known operator,
	 * it returns null.
	 * @param name the name of the variation operator
	 * @param pars the parameters of the variation operator
	 * @return the variation operator named
	 */
	public VariationOperator create (String name, List<String> pars) {
		VariationOperator op = null;
		
		switch (name.toUpperCase()) {

		// Recombination
		case "SPX":
			op = new SinglePointCrossover(pars);
			break;
		case "UX":
			op = new UniformCrossover(pars);
			break;
		case "BLX":
			op = new BLX(pars);
			break;		
		case "OX":
			op = new OrderCrossover(pars);
			break;
		case "UCX":
			op = new UniformCycleCrossover(pars);
			break;
		case "FIXEDSET":
			op = new FixedSizeSetRecombination(pars);
			break;
			
		// Mutation
		case "BINOMIAL":
			VariationOperator subOp = create(pars.get(0), pars.subList(1, pars.size()));
			op = new BinomialMutation(subOp);
			break;
		case "BITFLIP":
			op = new BitFlip(pars);
			break;
		case "RANDOMSUBSTITUTION":
			op = new RandomSubstitution(pars);
			break;
		case "GAUSSIAN":
			op = new GaussianMutation(pars);
			break;
		case "SWAP":
			op = new Swap(pars);
			break;
		case "REVERSE":
			op = new Reverse(pars);
			break;	
		case "FLIP":
			op = new Flip(pars);
			break;	
		case "MUTATESET":
			op = new MutateSetElement(pars);
			break;
			
						
		// Initialization
		case "BITSTRING":
			op = new RandomBitString(pars);
			break;
		case "RANDOMVECTOR":
			op = new RandomVector(pars);
			break;
		case "PERMUTATION":
			op = new RandomPermutation(pars);
			break;
		case "RANDOMSEQ":
			op = new RandomIntegerSequence(pars);
			break;
		case "RANDOMSET":
			op = new RandomSet(pars);
			break;
			
			
		default:
			break;
		}
		
		return op;
	}
}
