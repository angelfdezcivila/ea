package es.uma.lcc.caesium.ea.operator.variation.mutation;

import java.util.ArrayList;
import java.util.List;

import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.ObjectiveFunction;
import es.uma.lcc.caesium.ea.operator.variation.VariationOperator;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Shell for binomial mutation. It is parameterized by a mutation operator whose application is tried n times, each of them with a certain probability p.
 * @author ccottap
 * @version 1.0
 *
 */
public class BinomialMutation extends MutationOperator {
	/**
	 * the internal mutation operator
	 */
	private MutationOperator op;
	
	/**
	 * Basic constructor of a mutation operator. It has 
	 * the default probability of application = 1.0 because the
	 * actual mutation is performed by the subordinate operator with the
	 * corresponding probability.
	 * @param op internal mutation operator
	 */
	public BinomialMutation(VariationOperator op) {
		super(new ArrayList<String>(0));
		this.op = (MutationOperator)op;
	}
	
	/**
	 * Sets the objective function for potential use inside some operator
	 * @param obj the objective function
	 */
	public void setObjectiveFunction (ObjectiveFunction obj) {
		super.setObjectiveFunction(obj);
		op.setObjectiveFunction(obj);
	}

	@Override
	protected Individual _apply(List<Individual> parents) {
		Individual ind = parents.get(0);
		int l = ind.getGenome().length();
		ArrayList<Individual> aux = new ArrayList<Individual>(1);
		aux.add(ind);
		double p = op.getProbability();
		for (int i=0; i<l; i++) {
			if (EAUtil.random01() < p) {
				ind = op._apply(aux);
				aux.set(0, ind);
			}
		}
		return ind;
	}

	@Override
	public String toString() {
		return "BinomialMutation(" + prob + ", " + op + ")";
	}

}
