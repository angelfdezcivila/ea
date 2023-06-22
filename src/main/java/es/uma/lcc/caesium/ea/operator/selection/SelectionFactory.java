package es.uma.lcc.caesium.ea.operator.selection;

import java.util.List;


/**
 * Factory class for selection operators
 * @author ccottap
 * @version 1.1
 *
 */
public class SelectionFactory {
	/**
	 * Returns a selection operator given its name.
	 * If the name does not correspond to any known operator,
	 * it returns null.
	 * @param name the name of the selection operator
	 * @param pars parameters of the selection operator
	 * @return the selection operator named
	 */
	public SelectionOperator create (String name, List<String> pars) {
		SelectionOperator op = null;
		
		switch (name.toUpperCase()) {
		case "TOURNAMENT": 
			op = new TournamentSelection(pars);
			break;
			
		case "BEST":
			op = new BestSelection();
			break;
			
		case "RANDOM":
			op = new RandomSelection();
			break;
			
		case "ROULETTE":
			op = new RouletteWheelSelection();
			break;
			
		case "RANKING":
			op = new LinearRanking(pars);
			break;
			
		}
		
		return op;
	}
}
