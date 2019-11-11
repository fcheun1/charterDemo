package com.charter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;

public class RewardCalculator {
	HashMap<String, Integer> monthlyRewardPoints;


	public RewardCalculator() {
		super();
		this.monthlyRewardPoints = new HashMap<String, Integer>();
	}

	public void collectRewards(String dollarSpentInString, String transactionDate) {
		if(dollarSpentInString==null ||  transactionDate==null) {
			return;
		}
		
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
		
		Integer rewardPointsForMonth = getRewardsForMonthYear(monthYear);
		rewardPointsForMonth = Optional.ofNullable(rewardPointsForMonth).orElse(0);
		monthlyRewardPoints.put(monthYear, rewardPointsForMonth+calculateRewards(dollarSpent));
		
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
    	summary.append("\nMonthly Rewards Summary:\n\n");
    	for ( Entry<String, Integer> monthlyPoints:monthlyRewardPoints.entrySet()) {
        	summary.append(String.format("%s has %d reward points\n",  monthlyPoints.getKey(), monthlyPoints.getValue()));
    	}
    	return summary.toString();
    }
    
    public Integer getRewardsForMonthYear(String monthYear) {
    	return Optional.ofNullable(monthlyRewardPoints.get(monthYear)).orElse(0);
    }
}
