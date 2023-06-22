package es.uma.lcc.caesium.ea.statistics;

import es.uma.lcc.caesium.ea.base.Individual;

/**
 * A pair (evals, individual) to record the creation of an individual 
 * at a specific moment
 * @author ccottap
 * @param evals number of evaluations (used as a timestamp)
 * @param individual an individual
 * @version 1.0
 *
 */
public record IndividualRecord(long evals, Individual individual) {
}
