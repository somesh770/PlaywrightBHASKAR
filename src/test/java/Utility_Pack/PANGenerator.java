package Utility_Pack;

import java.util.Random;

public class PANGenerator {

	public static String generatePAN() {
		Random rand = new Random();

		// Step 1: The first three characters should be uppercase letters
		String firstPart = getRandomString(3);

		// Step 2: The fourth character should be 'A', 'C', or 'F'
		String fourthChar = "ACF".charAt(rand.nextInt(3)) + "";

		// Step 3: The fifth character should be an uppercase letter
		String fifthChar = getRandomString(1);

		// Step 4: The next four characters should be digits
		String digits = String.format("%04d", rand.nextInt(10000)); // Ensure it's a 4-digit number

		// Step 5: The last character should be an uppercase letter
		String lastChar = getRandomString(1);

		// Construct the PAN in the required format
		return firstPart + fourthChar + fifthChar + digits + lastChar;
	}

	private static String getRandomString(int length) {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rand = new Random();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(letters.charAt(rand.nextInt(letters.length())));
		}
		return sb.toString();
	}
}
