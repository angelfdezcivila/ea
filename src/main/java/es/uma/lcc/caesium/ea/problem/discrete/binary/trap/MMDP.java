
package es.uma.lcc.caesium.ea.problem.discrete.binary.trap;


/**
 * Massively multimodal deceptive problem. It has two global optima 
 * when all bits are set to 1 or to 0, and multiple local optima when 
 * 3 bits are set to 0 and 3 bits are set to 1. 
 * @author ccottap
 * @version 1.0
 *
 */
public class MMDP extends UnitationTrap {
	/**
	 * Constructs a concatenated trap function 
	 * @param numTraps number of concatenated traps
	 */
	public MMDP(int numTraps) {
		super(numTraps, 6);
	}

	@Override
	protected double trapValue(int u) {
		switch(u) {
			case 6:
			case 0: // global optima
				return 1.0;
			case 5:
			case 1:
				return 0;
			case 4:
			case 2:
				return 0.360384;
			
			case 3: // local optima
				return 0.640576;
			
			default:
				System.err.println("Error computing trap: unitation cannot be " + u);
				return 0;
		}
	}


}
