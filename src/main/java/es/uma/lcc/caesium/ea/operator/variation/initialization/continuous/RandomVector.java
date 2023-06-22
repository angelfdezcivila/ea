package es.uma.lcc.caesium.ea.operator.variation.initialization.continuous;

import java.util.List;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.ContinuousObjectiveFunction;
import es.uma.lcc.caesium.ea.operator.variation.initialization.InitializationOperator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Creates random floating-point vectors
 * @author ccottap
 * @version 1.1
 *
 */
public class RandomVector extends InitializationOperator {
	/**
	 * Generates the operator
	 * @param pars parameters (none)
	 */
	public RandomVector(List<String> pars) {
		super(pars);
	}

	@Override
	protected Individual _apply(List<Individual> parents) {
		ContinuousObjectiveFunction p = (ContinuousObjectiveFunction)obj;
		int l = p.getNumVars();
		Genotype g = new Genotype(l);
		for (int i=0; i<l; i++) {
			double minv = p.getMinVal(i);
			double maxv = p.getMaxVal(i);
			g.setGene(i, minv + EAUtil.random01()*(maxv-minv));
		}
		
		Individual ind = new Individual();
		ind.setGenome(g);
		return ind;
	}

	@Override
	public String toString() {
		return "RandomVector";
	}

}
