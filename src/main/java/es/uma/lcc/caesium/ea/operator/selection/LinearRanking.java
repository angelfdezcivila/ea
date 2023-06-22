package es.uma.lcc.caesium.ea.operator.selection;

import java.util.HashMap;
import java.util.List;

import es.uma.lcc.caesium.ea.base.Individual;

/**
 * Linear ranking selection
 * @author ccottap
 *
 */
public class LinearRanking extends ProbabilityBasedSelection {
	/**
	 * Default value of the eta+ parameter
	 */
	public static final double ETAPLUS = 2.0;
	/**
	 * parameter to control selective pressure. it has a value between
	 * 1 and 2 and indicates the expected number of copies of the best 
	 * individual in the selected population.
	 */
	private double etaplus;
	
	/**
	 * Creates the operator
	 */
	public LinearRanking() {
		setEtaPlus(ETAPLUS);
	}
	
	/**
	 * Creates the operator
	 * @param pars parameters (value of the eta+ parameter)
	 */
	public LinearRanking(List<String> pars) {
		setEtaPlus(Double.parseDouble(pars.get(0)));
	}
	
	/**
	 * Sets the value of the eta+ parameter (between 1 and 2)
	 * @param eta the value of the eta+ parameter
	 */
	public void setEtaPlus(double eta) {
		if ((eta < 1) || (eta > 2)) {
			System.err.println("ERROR: linear ranking parameter must be between 1 and 2 (" + eta + " received)");
			System.exit(1);
		}
		etaplus = eta;
	}

	@Override
	protected HashMap<Individual, Double> generateProbs(List<Individual> population) {
		double etaminus = 2.0 - etaplus;
		sort(population);
		
		HashMap<Individual,Double> probs = new HashMap<Individual,Double>();
		int mu = population.size();
		double f = (etaplus-etaminus)/(double)(mu-1);
		for (int i=0; i<mu; i++) {
			probs.put(population.get(i), (etaminus + f*(mu-1-i))/mu);
		}
	
		return probs;
	}

	@Override
	public String toString() {
		return "LinearRanking(" + etaplus + ", " + (2-etaplus) + ")";
	}

}
