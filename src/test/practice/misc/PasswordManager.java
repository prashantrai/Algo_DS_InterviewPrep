package test.practice.misc;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordManager {

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {

			String pass = PassGenerator.generatePass(10, true, true, true, true);
			System.out.println(">> " + pass);
		}
	}

	static class PassGenerator {
		private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
		private static final String UPPER = LOWER.toUpperCase();
		private static final String NUMBER = "1234567890";
		private static final String OTHER = "!@#$%^&*";
		private static boolean isLowerAllowed = false;
		private static boolean isUpperAllowed = false;
		private static boolean isNumAllowed = false;
		private static boolean isOtherAllowed = false;
		private static String BASE_CASE = LOWER + UPPER + NUMBER + OTHER;
		private static SecureRandom rand = new SecureRandom();
		private static final String PASS_ALLOW_BASE_SHUFFLE = shuffleStr(BASE_CASE);
		private static final String PASS_ALLOW = PASS_ALLOW_BASE_SHUFFLE;

		public static String generatePass(int len, boolean upper, boolean lower, boolean num, boolean other) {

			if (len < 8)
				throw new IllegalArgumentException();

			StringBuilder sb = new StringBuilder();

			int i = 0;
			while (i < len) {

				int randCahrAt = rand.nextInt(PASS_ALLOW.length());
				char randChar = PASS_ALLOW.charAt(randCahrAt);

				if (!isAllowed(randChar, upper, lower, num, other))
					continue;

				// System.out.println("DEBUG :: randChar: "+randChar);

				sb.append(randChar);

				i++;
			}

			return sb.toString();

		}

		public static String shuffleStr(String str) {

			List<String> letters = Arrays.asList(str.split(""));
			Collections.shuffle(letters);

			return letters.stream().collect(Collectors.joining());

		}

		public static boolean isAllowed(char randChar, boolean upper, boolean lower, boolean num, boolean other) {

			if (!upper && UPPER.indexOf("" + randChar) != -1) {
				return false;
			}

			if (!lower && LOWER.indexOf("" + randChar) != -1) {
				return false;
			}

			if (!num && NUMBER.indexOf("" + randChar) != -1) {
				return false;
			}

			if (!other && OTHER.indexOf("" + randChar) != -1) {
				return false;
			}

			return true;

		}

	}
}
