package ctci.ch6.math.and.puzzles;

import java.util.Random;

public class ApocalypseDemo {

	public static void main(String[] args) {

		
		System.out.println(">>> "+runNFamilies(50));
		
	}

	public static double runNFamilies(int n) {
		
		int boys = 0;
		int girls = 0;
		
		for(int i=0; i<n; i++) {
			int[] genders = runOnlyFamiliy();
			girls += genders[0];
			boys += genders[1];
		}
		
		System.out.println("boys="+boys+", girls="+girls);
		
		return girls/(double) (boys+girls);
		
	}
	
	public static int[] runOnlyFamiliy () {
		
		Random random = new Random();
		int boys = 0;
		int girls = 0;
		
		while(girls == 0) {
			if(random.nextBoolean()) {
				girls++;
			} else {
				boys++;
			}
		} 
		
		return new int[] {girls, boys};
		
	}
}
