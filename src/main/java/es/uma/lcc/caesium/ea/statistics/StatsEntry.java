package es.uma.lcc.caesium.ea.statistics;


import es.uma.lcc.caesium.ea.base.Individual;

/**
 * Statistic snapshot of the population
 * @author ccottap
 * @param evals number of evaluations
 * @param best best fitness
 * @param mean mean fitness
 * @param diversity diversity of the population
 * @version 1.0
 */
//public record StatsEntry(long evals, double best, double mean, double diversity) {
public record StatsEntry(long evals, Individual best, double mean, double diversity) {
}
