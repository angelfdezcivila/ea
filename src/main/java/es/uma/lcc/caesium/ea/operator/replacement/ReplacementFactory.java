package es.uma.lcc.caesium.ea.operator.replacement;

import java.util.List;

/**
 * Factory for replacement methods
 * @author ccottap
 * version 1.4
 *
 */
public class ReplacementFactory {
	/**
	 * Returns a replacement operator given its name.
	 * If the name does not correspond to any known operator,
	 * it returns null.
	 * @param name the name of the replacement operator
	 * @param pars parameters of the replacement operator
	 * @return the replacement operator named
	 */
	public ReplacementOperator create (String name, List<String> pars) {
		ReplacementOperator op = null;
		
		switch (name.toUpperCase()) {
		case "PLUS": 
			op = new PlusReplacement();
			break;
		case "COMMA":
			op = new CommaReplacement();
			break;
		}
		
		return op;
	}
}
