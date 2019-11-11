package com.charter;

import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.Assert;

public class RewardCalculatorTest {

	static RewardCalculator rc;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		rc = new RewardCalculator();
	}

	@Test
	public void testSingleTransaction() {
		rc.collectRewards("120", "01/03/2019");
		Assert.assertEquals((Integer)90, (Integer)rc.getRewardsForMonthYear("January 2019"));
	}
	
	@Test
	public void testMultipleTransactions() {
		rc.collectRewards("120", "02/03/2019");
		rc.collectRewards("220", "02/04/2019");
		Assert.assertEquals((Integer)380, (Integer)rc.getRewardsForMonthYear("February 2019"));
	}

	@Test
	public void testNegativeAndPositiveTransactions() {
		rc.collectRewards("-120", "03/03/2019");
		rc.collectRewards("120", "03/04/2019");
		Assert.assertEquals((Integer)90, (Integer)rc.getRewardsForMonthYear("March 2019"));
	}
	
	@Test
	public void testBadDatedTransactions() {
		rc.collectRewards("120", "04/33/2019");
		Assert.assertEquals((Integer)0, (Integer)rc.getRewardsForMonthYear("April 2019"));
	}
	
	@Test
	public void testFutureMonthsTransactions() {
		rc.collectRewards("120", "17/06/2019");
		Assert.assertEquals((Integer)90, (Integer)rc.getRewardsForMonthYear("May 2020"));
	}
}
