package es.uma.lcc.caesium.ea.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Genotype of an individual
 * @author ccottap
 * @version 1.0
 *
 */
public class Genotype implements Cloneable {
	/**
	 * The genes in the genome
	 */
	protected List<Object> genes;
	
	/**
	 * Creates a genome of the desired length. Each gene is set to null
	 * @param l the genome length
	 */
	public Genotype(int l) {
		genes = new ArrayList<Object>(l);
		for (int i=0; i<l; i++)
			genes.add(null);
	}
	
	/**
	 * Returns the length of the genotype
	 * @return the length of the genotype
	 */
	public int length() {
		return genes.size();
	}
	
	/**
	 * Returns the value of a gene
	 * @param i the index of the gene
	 * @return the value of the i-th gene
	 */
	public Object getGene(int i) {
		return genes.get(i);
	}
	
	/**
	 * Sets the value of a gene
	 * @param i the index of the gene
	 * @param gen the value to be given to the i-th gene
	 */
	public void setGene(int i, Object gen) {
		genes.set(i, gen);
	}
	
	@Override
	public Genotype clone() {
		int l = length();
		Genotype copy = new Genotype(l);
		for (int i=0; i<l; i++)
			copy.setGene(i, getGene(i));
		return copy;
	}
	
	@Override
	public String toString() {
		String str = "";
		int l = length();
		for (int i=0; i<l; i++) 
			str += " " + genes.get(i);
		return str;
	}
}
