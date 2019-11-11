package com.charter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;

public class RewardCalculator {
	HashMap<String, HashMap<String, Integer>> monthlyRewardPoints;


	public RewardCalculator() {
		super();
		this.monthlyRewardPoints = new HashMap<String, HashMap<String, Integer>>();
	}

	public void collectRewards(String customer, String dollarSpentInString, String transactionDate) {
		if(customer==null || dollarSpentInString==null ||  transactionDate==null) {
			return;
		}

		HashMap<String, Integer> monthlyRewardPointsForMember = 
				Optional.ofNullable(monthlyRewardPoints.get(customer)).orElse(new HashMap<String, Integer>());
		
		int monthOfYear=0;
		int year=0;
		int dollarSpent =0;
		String monthYear =null;
		try {
			monthOfYear=parseCalendar(transactionDate).get(Calendar.MONTH);
			year=parseCalendar(transactionDate).get(Calendar.YEAR);
			dollarSpent = Integer.parseInt(dollarSpentInString);
		}catch(Exception e) {
			e.printStackTrace();
		}
		monthYear= String.format("%s %d", getCalendarMonth(monthOfYear), year);
		
		Integer rewardPointsForMonth = getRewardsForMonthYear(monthYear, monthlyRewardPointsForMember);
		rewardPointsForMonth = Optional.ofNullable(rewardPointsForMonth).orElse(0);
		monthlyRewardPointsForMember.put(monthYear, rewardPointsForMonth+calculateRewards(dollarSpent));
		monthlyRewardPoints.put(customer, monthlyRewardPointsForMember);
	}
	
	private Integer calculateRewards(Integer dollarSpent) {
		Integer rewardPoints = 0;
		rewardPoints+=dollarSpent>50?50:0;
		rewardPoints+=dollarSpent>100?(dollarSpent-100)*2:0;
		return rewardPoints;
	}
	
	public static Calendar parseCalendar(String stringInstanceRepresentingDate) throws ParseException {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal  = Calendar.getInstance();
		cal.setTime(df.parse(stringInstanceRepresentingDate));
		return cal;
	}
	
    public static String getCalendarMonth(Integer month) {
        String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};

        String displayMonth = monthName[month];

        return displayMonth;
    }
    
    public String getRewardsSummary() {
    	StringBuffer summary = new StringBuffer();
    	HashMap<String, Integer> monthlyRewardPointsForMember;
    	Integer memberTotal=0;
    	for ( Entry<String, HashMap<String, Integer>> monthlyPoints:monthlyRewardPoints.entrySet()) {
	    	summary.append(String.format("\nMonthly Rewards Summary on %s:\n\n",monthlyPoints.getKey()));
	    	monthlyRewardPointsForMember = monthlyPoints.getValue();
			for ( Entry<String, Integer> monthlyPointsForMember:monthlyRewardPointsForMember .entrySet()) {
	        	summary.append(String.format("\t%s has %d reward points\n",  
	        			monthlyPointsForMember.getKey(), monthlyPointsForMember.getValue()));
	        	memberTotal+=monthlyPointsForMember.getValue();
	    	}
	    	summary.append(String.format("\n\tRewards Total on %s is %d:\n\n",monthlyPoints.getKey(),memberTotal));
	    	memberTotal=0;
    	}
    	return summary.toString();
    }
    public Integer getRewardsForMonthYearForMember(String monthYear, String customer) {
    	return getRewardsForMonthYear(monthYear, monthlyRewardPoints.get(customer));
    }
    public Integer getRewardsForMonthYear(String monthYear, HashMap<String, Integer> monthlyRewardPointsForMember) {
    	if(monthlyRewardPointsForMember==null) return 0;
    	return Optional.ofNullable(monthlyRewardPointsForMember.get(monthYear)).orElse(0);
    }
}
