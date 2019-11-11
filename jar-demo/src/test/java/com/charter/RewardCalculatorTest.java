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
		rc.collectRewards("Peter", "120", "01/03/2019");
		Assert.assertEquals((Integer)90, (Integer)rc.getRewardsForMonthYearForMember("January 2019","Peter"));
	}
	
	@Test
	public void testMultipleTransactions() {
		rc.collectRewards("Peter", "120", "02/03/2019");
		rc.collectRewards("Peter", "220", "02/04/2019");
		Assert.assertEquals((Integer)380, (Integer)rc.getRewardsForMonthYearForMember("February 2019","Peter"));
	}

	@Test
	public void testNegativeAndPositiveTransactions() {
		rc.collectRewards("Peter", "-120", "03/03/2019");
		rc.collectRewards("Peter", "120", "03/04/2019");
		Assert.assertEquals((Integer)90, (Integer)rc.getRewardsForMonthYearForMember("March 2019","Peter"));
	}
	
	@Test
	public void testBadDatedTransactions() {
		rc.collectRewards("Peter", "120", "04/33/2019");
		Assert.assertEquals((Integer)0, (Integer)rc.getRewardsForMonthYearForMember("April 2019","Peter"));
	}
	
	@Test
	public void testFutureMonthsTransactions() {
		rc.collectRewards("Peter", "120", "17/06/2019");
		Assert.assertEquals((Integer)90, (Integer)rc.getRewardsForMonthYearForMember("May 2020","Peter"));
	}
	
	@Test
	public void testSingleTransactionWithAdditionalCustomer() {
		rc.collectRewards("Beth", "120", "05/03/2019");
		rc.collectRewards("Peter", "120", "05/04/2019");
		Assert.assertEquals((Integer)90, (Integer)rc.getRewardsForMonthYearForMember("May 2019","Beth"));
		Assert.assertEquals((Integer)90, (Integer)rc.getRewardsForMonthYearForMember("May 2019","Peter"));
	}
}
