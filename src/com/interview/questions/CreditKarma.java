package com.interview.questions;

import java.util.ArrayList;

//--Fuzzy Search

public class CreditKarma {

	/*
	 * Find colors containing input string. Each char of input should be there in result color in order no necessarily continuous
	 * 
	 *    For Example: input is "uqi"
	 *   and result list is [ 'darkturquoise', 'mediumaquamarine', 'mediumturquoise', 'paleturquoise', 'turquoise' ]
	 *   each color in resul is containing "u", "q", "i" in their order (different position though which is accepatable) 
	 * 
	 * */
	
	public static void main(String[] args) {

		String[] colors = { "aliceblue", "antiquewhite", "aqua", "aquamarine", "azure", "beige", "bisque", "black",
				"blanchedalmond", "blue", "blueviolet", "brown", "burlywood", "cadetblue", "chartreuse", "chocolate",
				"coral", "cornflowerblue", "cornsilk", "crimson", "cyan", "darkblue", "darkcyan", "darkgoldenrod",
				"darkgray", "darkgreen", "darkgrey", "darkkhaki", "darkmagenta", "darkolivegreen", "darkorange",
				"darkorchid", "darkred", "darksalmon", "darkseagreen", "darkslateblue", "darkslategray",
				"darkslategrey", "darkturquoise", "darkviolet", "deeppink", "deepskyblue", "dimgray", "dimgrey",
				"dodgerblue", "firebrick", "floralwhite", "forestgreen", "fuchsia", "gainsboro", "ghostwhite", "gold",
				"goldenrod", "gray", "green", "greenyellow", "grey", "honeydew", "hotpink", "indianred", "indigo",
				"ivory", "khaki", "lavender", "lavenderblush", "lawngreen", "lemonchiffon", "lightblue", "lightcoral",
				"lightcyan", "lightgoldenrodyellow", "lightgray", "lightgreen", "lightgrey", "lightpink", "lightsalmon",
				"lightseagreen", "lightskyblue", "lightslategray", "lightslategrey", "lightsteelblue", "lightyellow",
				"lime", "limegreen", "linen", "magenta", "maroon", "mediumaquamarine", "mediumblue", "mediumorchid",
				"mediumpurple", "mediumseagreen", "mediumslateblue", "mediumspringgreen", "mediumturquoise",
				"mediumvioletred", "midnightblue", "mintcream", "mistyrose", "moccasin", "navajowhite", "navy",
				"oldlace", "olive", "olivedrab", "orange", "orangered", "orchid", "palegoldenrod", "palegreen",
				"paleturquoise", "palevioletred", "papayawhip", "peachpuff", "peru", "pink", "plum", "powderblue",
				"purple", "red", "rosybrown", "royalblue", "saddlebrown", "salmon", "sandybrown", "seagreen",
				"seashell", "sienna", "silver", "skyblue", "slateblue", "slategray", "slategrey", "snow", "springgreen",
				"steelblue", "tan", "teal", "thistle", "tomato", "turquoise", "violet", "wheat", "white", "whitesmoke",
				"yellow", "yellowgreen" };

		// String[] colors = {"darkturquoise", "mediumaquamarine",
		// "mediumturquoise", "paleturquoise", "turquoise", "trose"};

		// HashSet<String>

		ArrayList<String> res = new ArrayList<String>();
		String in = "uqi";
		for (String color : colors) {
			if (fuzzySearch(color, in)) {
				res.add(color);
			}
		}

		System.out.println(res);

	}

	static boolean fuzzySearch(String color, String in) {

		//System.out.println("in: " + color);
		
		int i = 0;

		for (int j = 0; j < color.length(); j++) {

			char char_in = in.charAt(i);
			char char_col = color.charAt(j);

			if (char_in == char_col) {
				i++;

				continue;
			}

			if (i == in.length() - 1) {

				return true;
			}
		}

		return i == in.length();

	}// --method closed

}
