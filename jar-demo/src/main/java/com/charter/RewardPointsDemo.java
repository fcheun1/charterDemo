package com.charter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.StringTokenizer;

public class RewardPointsDemo {
	// get file from classpath, resources folder
    private File getFileFromResources(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }

    }
    
	public static void main(String[] args) throws IOException {
		StringTokenizer st;
		RewardCalculator rc = new RewardCalculator();
		BufferedReader reader = null;
		String customer=null;
		String txDate=null;
		String dollarSpent=null;
		System.out.println("Transaction Records from transactions.txt:\n");
		try {
			RewardPointsDemo main = new RewardPointsDemo();
			reader = new BufferedReader(new FileReader(main.getFileFromResources("transactions.txt")));
			String line;
			while ((line=reader.readLine()) != null) {
				
				System.out.println(line);

				st=new StringTokenizer(line);
				customer=st.hasMoreTokens()?st.nextToken():null;
				txDate=st.hasMoreTokens()?st.nextToken():null;
				dollarSpent=st.hasMoreTokens()?st.nextToken():null;
				rc.collectRewards(customer, dollarSpent, txDate);
				txDate=null;
				dollarSpent=null;
			}
			System.out.println(rc.getRewardsSummary());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(reader!=null) reader.close();
		}
	}

}
