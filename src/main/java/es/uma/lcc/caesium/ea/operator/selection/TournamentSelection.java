package es.uma.lcc.caesium.ea.operator.selection;

import java.util.LinkedList;
import java.util.List;

import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.util.EAUtil;

/**
 * Tournament selection
 * @author ccottap
 * @version 1.0
 *
 */
public class TournamentSelection extends SelectionOperator {
	/**
	 * binary tournament by default
	 */
	private final int TOURNAMENT_SIZE = 2;
	/**
	 * size of the tournament
	 */
	private int tournamentSize = TOURNAMENT_SIZE;
	

	/**
	 * Creates the tournament selection operator with the default tournament size
	 */
	public TournamentSelection() {
		setTournamentSize(TOURNAMENT_SIZE);
	}
	
	/**
	 * Creates the tournament selection operator for the given tournament size
	 * @param tsize tournament size
	 */
	public TournamentSelection(int tsize) {
		setTournamentSize(tsize);
	}

	/**
	 * Creates the operator given its parameter list
	 * @param pars tournament size
	 */
	public TournamentSelection(List<String> pars) {
		this(Integer.parseInt(pars.get(0)));
	}

	/**
	 * Returns the tournament size
	 * @return the tournament size
	 */
	public int getTournamentSize() {
		return tournamentSize;
	}

	/**
	 * Sets the tournament size
	 * @param tournamentSize the tournament size
	 */
	public void setTournamentSize(int tournamentSize) {
		this.tournamentSize = tournamentSize;
	}

	@Override
	public List<Individual> apply(List<Individual> population, int num) {
		List<Individual> result = new LinkedList<Individual>();
		int mu = population.size();
		for (int i=0; i<num; i++) {
			Individual best = population.get(EAUtil.random(mu));
			for (int j=1; j<tournamentSize; j++) {
				Individual cand = population.get(EAUtil.random(mu));
				if (comp.compare(cand, best) < 0)
					best = cand;
			}
			result.add(best.clone());
		}
		return result;
	}

	@Override
	public String toString() {
		return "Tournament(" + tournamentSize + ")";
	}

}
