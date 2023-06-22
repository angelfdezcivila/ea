package es.uma.lcc.caesium.ea.config;

import java.util.List;


/**
 * Configuration of an operator (name and parameters)
 * @author ccottap
 * @param name name of the operator
 * @param parameters parameters of the operator
 * @version 1.0
 *
 */
public record OperatorConfiguration (String name, List<String> parameters) {
}
