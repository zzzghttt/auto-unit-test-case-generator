/*
 * Copyright (C) 2010-2018 Gordon Fraser, Andrea Arcuri and SmartUt
 * contributors
 *
 * This file is part of SmartUt.
 *
 * SmartUt is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.
 *
 * SmartUt is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with SmartUt. If not, see <http://www.gnu.org/licenses/>.
 */
package org.smartut.seeding.factories;

import org.smartut.Properties;
import org.smartut.ga.ChromosomeFactory;
import org.smartut.ga.metaheuristics.GeneticAlgorithm;
import org.smartut.testsuite.TestSuiteChromosome;
import org.smartut.utils.Randomness;

/**
 * @author Thomas White
 */
public class BIAndRITestSuiteChromosomeFactory implements
		ChromosomeFactory<TestSuiteChromosome> {

	private static final long serialVersionUID = 1L;

	private final ChromosomeFactory<TestSuiteChromosome> defaultFactory;
	private final GeneticAlgorithm<TestSuiteChromosome> geneticAlgorithm;
	private boolean seeded = false;

	/**
	 * <p>
	 * Constructor for BestIndividualAndRandomTestSuiteChromosomeFactory.
	 * </p>
	 * 
	 * @param defaultFactory
	 *            a {@link org.smartut.ga.ChromosomeFactory} object.
	 */
	public BIAndRITestSuiteChromosomeFactory(
			ChromosomeFactory<TestSuiteChromosome> defaultFactory,
			GeneticAlgorithm<TestSuiteChromosome> geneticAlgorithm) {
		this.defaultFactory = defaultFactory;
		this.geneticAlgorithm = geneticAlgorithm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.smartut.ga.ChromosomeFactory#getChromosome()
	 */
	/** {@inheritDoc} */
	@Override
	public TestSuiteChromosome getChromosome() {
		/*
		 * double P_delta = 0.1d; double P_clone = 0.1d; int MAX_CHANGES = 10;
		 */
		TestSuiteChromosome chrom = null;
		if (!seeded && geneticAlgorithm != null) {
			seeded = true;
			chrom = geneticAlgorithm.getBestIndividual();
		} else if (geneticAlgorithm != null && Randomness.nextDouble() < Properties.SEED_PROBABILITY) {
			int populationSize = geneticAlgorithm.getPopulation().size();
			chrom =  geneticAlgorithm.getPopulation().get(Randomness.nextInt(populationSize));
		}
		if (chrom == null){
			chrom = defaultFactory.getChromosome();
		}
		return chrom;
	}
}