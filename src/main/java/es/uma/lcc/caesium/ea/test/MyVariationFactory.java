package es.uma.lcc.caesium.ea.test;

import java.util.List;

import es.uma.lcc.caesium.ea.operator.variation.VariationFactory;
import es.uma.lcc.caesium.ea.operator.variation.VariationOperator;

/**
 * Example of user-defined factory for variation operators
 * @author ccottap
 * @version 1.0
 */
public class MyVariationFactory extends VariationFactory {

	@Override
	public VariationOperator create (String name, List<String> pars) {
		VariationOperator op = null;
		
		switch (name.toUpperCase()) {
		case "DPX":
			op = new DoublePointCrossover(pars);
			break;
		
		default:
			op = super.create(name, pars);
		}
		
		return op;
	}

}
