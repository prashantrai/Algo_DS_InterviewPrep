package Stripe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ParseAcceptLanguages {

	public static void main(String[] args) {
		/*
		testParseValidMatches();
		testParseNoMatchServer();
		testParseNoMatchClient();
		testParseValidMatchesWithLanguageTag();
		testParseValidMatchesWithWildCard();
		*/
		// part 1
		String acceptLangHeader = "en-US, fr-CA, fr-FR";
		String[] supportedLangs = {"fr-FR", "en-US"};
		System.out.println("Expected: [en-US, fr-FR], Actual: " +parse_accept_language(acceptLangHeader, supportedLangs));
		assertEquals(Arrays.asList("en-US", "fr-FR"), 
				parse_accept_language(acceptLangHeader, supportedLangs));
		assertEquals(Arrays.asList("fr-FR"), 
				parse_accept_language("fr-CA, fr-FR", new String[] {"en-US", "fr-FR"}));
		assertEquals(Arrays.asList("en-US"), 
				parse_accept_language("en-US", new String[] {"en-US", "fr-CA"}));

		// part 2
		System.out.println("\n--------Part 2--------\n");
		assertEquals(Arrays.asList("en-US"), 
				parse_accept_language_2("en", new String[] {"en-US", "fr-CA", "fr-FR"}));
		
		assertEquals(Arrays.asList("fr-CA", "fr-FR"), 
				parse_accept_language_2("fr", new String[] {"en-US", "fr-CA", "fr-FR"}));
		
		assertEquals(Arrays.asList("fr-FR", "fr-CA"), 
				parse_accept_language_2("fr-FR, fr", new String[] {"en-US", "fr-CA", "fr-FR"}));
		
		// part 3 -- all passing from 1 to 3
		System.out.println("\n--------Part 3--------\n");
		
		// run part 1 test cases on part 3 method
		assertEquals(Arrays.asList("en-US", "fr-FR"), 
				parse_accept_language_3(acceptLangHeader, supportedLangs));
		assertEquals(Arrays.asList("fr-FR"), 
				parse_accept_language_3("fr-CA, fr-FR", new String[] {"en-US", "fr-FR"}));
		assertEquals(Arrays.asList("en-US"), 
				parse_accept_language_3("en-US", new String[] {"en-US", "fr-CA"}));
		
		
		// run part 2 test cases on part 3 method
		
		assertEquals(Arrays.asList("en-US"), 
				parse_accept_language_3("en", new String[] {"en-US", "fr-CA", "fr-FR"}));
		
		assertEquals(Arrays.asList("fr-CA", "fr-FR"), 
				parse_accept_language_3("fr", new String[] {"en-US", "fr-CA", "fr-FR"}));
		
		assertEquals(Arrays.asList("fr-FR", "fr-CA"), 
				parse_accept_language_3("fr-FR, fr", new String[] {"en-US", "fr-CA", "fr-FR"}));
		
		
		// part 3 test cases
		System.out.println("Expected: [en-US, fr-CA, fr-FR], Actual: " + parse_accept_language_3("en-US, *", new String[] {"en-US", "fr-CA", "fr-FR"}));
		assertEquals(Arrays.asList("en-US", "fr-CA", "fr-FR"), 
				parse_accept_language_3("en-US, *", new String[] {"en-US", "fr-CA", "fr-FR"}));
		
		System.out.println("Expected: [fr-FR, fr-CA, en-US], Actual: " + parse_accept_language_3("fr-FR, fr, *", new String[] {"en-US", "fr-CA", "fr-FR"}));
		assertEquals(Arrays.asList("fr-FR", "fr-CA", "en-US"), 
				parse_accept_language_3("fr-FR, fr, *", new String[] {"en-US", "fr-CA", "fr-FR"}));
		
		
		// part 4
		System.out.println("\n--------Part 4--------\n");
		
		/////
		/*Comparator<Lang> compareByProviderThenCreditLimitInJDK8 
		 = Comparator.comparing((Lang c)->c.)
		            .thenComparing(c->c.creditLimit)
		            .thenComparingInt(c->c.fee); */
		/*
		//first name comparator
		Comparator<Employee> compareByFirstName = Comparator.comparing( Employee::getFirstName );
		 
		//last name comparator
		Comparator<Employee> compareByLastName = Comparator.comparing( Employee::getLastName );
		 
		//Compare by first name and then last name (multiple fields)
		Comparator<Employee> compareByFullName = compareByFirstName.thenComparing(compareByLastName);
		 */

		/*
		Comparator<Lang> cp = (a, b) ->  Double.compare(b.q_factor, a.q_factor);
		cp = cp.thenComparing((a, b) -> b.lang.compareTo(a.lang));
		
		Comparator<Lang> comparebyMultiField 
		 = Comparator.comparing(Lang::getQ_factor)
		            .thenComparing(Lang::getLang);
		
		
			//TreeSet<Lang> test = new TreeSet<>((a,b) -> (Double.compare(b.q_factor, a.q_factor)));
			TreeSet<Lang> test = new TreeSet<>(cp);
			test.add(new Lang("A", 1));
			test.add(new Lang("B", 2));
			test.add(new Lang("C", 1));
			test.add(new Lang("D", 3));
			
			System.out.println("test TreeSet: "+ test);
			////
			 * 
			 */
		
		
		System.out.println("Expected: [fr-FR, fr-BG, fr-CA], Actual: " 
					+ parse_accept_language_4("fr-FR;q=1, fr-CA;q=0, fr;q=0.5", 
												new String[] {"fr-FR", "fr-CA", "fr-BG"}));
		
		assertEquals(Arrays.asList("fr-FR", "fr-BG", "fr-CA"), 
				parse_accept_language_4("fr-FR;q=1, fr-CA;q=0, fr;q=0.5", new String[] {"fr-FR", "fr-CA", "fr-BG"}));
		
		
		//parse_accept_language("fr-FR;q=1, fr-CA;q=0, *;q=0.5", ["fr-FR", "fr-CA", "fr-BG", "en-US"])
		//returns: ["fr-FR", "fr-BG", "en-US", "fr-CA"]
		System.out.println("\n\n");
		System.out.println("Expected: [fr-FR, fr-BG, en-US, fr-CA], Actual: " 
				+ parse_accept_language_4("fr-FR;q=1, fr-CA;q=0, *;q=0.5", 
											new String[] {"fr-FR", "fr-CA", "fr-BG", "en-US"}));		
				
		assertEquals(Arrays.asList("fr-FR;q=1, fr-CA;q=0, *;q=0.5"), 
				parse_accept_language_4("fr-FR;q=1, fr-CA;q=0, *;q=0.5", new String[] {"fr-FR", "fr-CA", "fr-BG", "en-US"}));

		
		//parse_accept_language("fr-FR;q=1, fr-CA;q=0.8, *;q=0.5", ["fr-FR", "fr-CA", "fr-BG", "en-US"])
		
		
		
	}
	
	/*
	 parse_accept_language(
	  "en-US, fr-CA, fr-FR",  # the client's Accept-Language header, a string
	  ["fr-FR", "en-US"]      # the server's supported languages, a set of strings
	)
	returns: ["en-US", "fr-FR"]
	 */
	
	// part 1
	public static List<String> parse_accept_language(String acceptLangHeader, String[] supportedLangs) {
		Set<String> res = new LinkedHashSet<>(); // to avoid duplicate entry and maintain order
		Set<String> set = new HashSet<>(Arrays.asList(supportedLangs));
		String[] langsArr = acceptLangHeader.split(", ");
		
		for(String lang : langsArr) {
			if(set.contains(lang)) {
				res.add(lang);
			}
		}
		return new ArrayList<String>(res);
	}
	
	
	/*
	Part 2
	
	Accept-Language headers will often also include a language tag that is not
	region-specific - for example, a tag of "en" means "any variant of English". Extend
	your function to support these language tags by letting them match all specific
	variants of the language.
	
	Examples:
	
	parse_accept_language("en", ["en-US", "fr-CA", "fr-FR"])
	returns: ["en-US"]
	
	parse_accept_language("fr", ["en-US", "fr-CA", "fr-FR"])
	returns: ["fr-CA", "fr-FR"]
	
	parse_accept_language("fr-FR, fr", ["en-US", "fr-CA", "fr-FR"])
	returns: ["fr-FR", "fr-CA"]
	*/
	
	// part 2
	public static List<String> parse_accept_language_2(String acceptLangHeader, String[] supportedLangs) {
		Set<String> res = new LinkedHashSet<>(); // to avoid duplicate entry
		Map<String, Set<String>> map = groupSupportedLangByLangTag(supportedLangs); 
		
		String[] langsArr = acceptLangHeader.split(", ");
		
		for(String lang : langsArr) {
			
			if(lang.length() == 2) {
				res.addAll(map.get(lang));
				continue;
			}
			
			String tag = lang.substring(0, 2); // en, fr etc.
			if(map.get(tag).contains(lang)) {
				res.add(lang);
			}
			
		}
		return new ArrayList<String>(res);
	}
	
	
	
	/* This method will create map by grouping each lang by region
	 * e.g For ["en-US", "fr-CA", "fr-FR"] the group map will be
	 * 
	 *  {en=["en-US"], fr=["fr-CA", "fr-FR"]}
	 * 
	 */
	private static Map<String, Set<String>> groupSupportedLangByLangTag(String[] supportedLangs) {
		
		Map<String, Set<String>> map = new HashMap<>();
		for(String lang : supportedLangs) {
			String langTag = lang.substring(0, 2);
			
			map.putIfAbsent(langTag, new LinkedHashSet<String>()); // need maintain same order as input supportedLangs
			map.get(langTag).add(lang);
		}
		//System.out.println("Lang group: " + map);
		return map;
	}
	
	
	/** Part 3
	
	Accept-Language headers will sometimes include a "wildcard" entry, represented
	by an asterisk, which means "all other languages". Extend your function to
	support the wildcard entry.
	
	Examples:
	
	parse_accept_language("en-US, *", ["en-US", "fr-CA", "fr-FR"])
	returns: ["en-US", "fr-CA", "fr-FR"]
	
	parse_accept_language("fr-FR, fr, *", ["en-US", "fr-CA", "fr-FR"])
	returns: ["fr-FR", "fr-CA", "en-US"]
	*/
	// part 3
	public static List<String> parse_accept_language_3(String acceptLangHeader, String[] supportedLangs) {
		Set<String> res = new LinkedHashSet<>(); // to avoid duplicate entry
		Map<String, Set<String>> map = groupSupportedLangByLangTag_withAllEntry(supportedLangs); 
		
		String[] langsArr = acceptLangHeader.split(", ");
		
		for(String lang : langsArr) {
			
			if("*".equals(lang)) {
				res.addAll(map.get("ALL"));
				continue;
			}
			
			if(lang.length() == 2) {
				res.addAll(map.get(lang));
				continue;
			}
			
			String tag = lang.substring(0, 2); // en, fr etc.
			if(map.get(tag).contains(lang)) {
				res.add(lang);
			}
			
		}
		return new ArrayList<String>(res);
	}
	
	// same as above method groupSupportedLangByLangTag(), only change is ALL entry  
	private static Map<String, Set<String>> groupSupportedLangByLangTag_withAllEntry(String[] supportedLangs) {
		
		Map<String, Set<String>> map = new HashMap<>();
		for(String lang : supportedLangs) {
			String langTag = lang.substring(0, 2);
			
			map.putIfAbsent(langTag, new LinkedHashSet<String>()); // need maintain same order as input supportedLangs
			map.get(langTag).add(lang);
			
			// adding wild card scenario support by adding entry with key "all" to contain all the languages
			map.putIfAbsent("ALL", new LinkedHashSet<String>());
			map.get("ALL").add(lang);
		}
		//System.out.println("Lang group: " + map);
		return map;
	}
	
	
	/**
	Part 4
	
	Accept-Language headers will sometimes include explicit numeric weights (known as
	q-factors) for their entries, which are used to designate certain language tags
	as specifically undesired. For example:
	
	Accept-Language: fr-FR;q=1, fr;q=0.5, fr-CA;q=0
	
	This means that the reader most prefers French as spoken in France, will take
	any variant of French after that, but specifically wants French as spoken in
	Canada only as a last resort. Extend your function to parse and respect q-factors.
	
	Examples:
	
	parse_accept_language("fr-FR;q=1, fr-CA;q=0, fr;q=0.5", ["fr-FR", "fr-CA", "fr-BG"])
	returns: ["fr-FR", "fr-BG", "fr-CA"]
	
	parse_accept_language("fr-FR;q=1, fr-CA;q=0, *;q=0.5", ["fr-FR", "fr-CA", "fr-BG", "en-US"])
	returns: ["fr-FR", "fr-BG", "en-US", "fr-CA"]
	
	parse_accept_language("fr-FR;q=1, fr-CA;q=0.8, *;q=0.5", ["fr-FR", "fr-CA", "fr-BG", "en-US"])
	
	
	Algo: 
	Now, in this part sorting of result is based on q-factor (In other parts it was based on sequence of accept lang).
	
	To solve this, create Map of {lang, q-fact} and while adding the result in TreeSet
	fecth the q-fact of the lanf from this map and add to TreeSet
	
	
	*/
	
	public static List<String> parse_accept_language_4(String acceptLangHeader, String[] supportedLangs) {
		Set<String> res = new LinkedHashSet<>(); // to avoid duplicate entry
		
		Map<String, Set<String>> map = groupSupportedLangByLangTag_withAllEntry(supportedLangs); 
		Map<String, Double> q_FactMap = getLang_QFactMap(acceptLangHeader);
		
		String[] langsArr = acceptLangHeader.split(", ");
		
		for(String lang : langsArr) {
			
			lang = getLang(lang); // from fr-FR;q=1 extract fr-FR
			
			if("*".equals(lang)) {
				res.addAll(map.get("ALL"));
				continue;
			}
			
			if(lang.length() == 2) {
				res.addAll(map.get(lang));
				continue;
			}
			
			String tag = lang.substring(0, 2); // en, fr etc.
			if(map.get(tag).contains(lang)) {
				res.add(lang);
			}
			
		}
		
		Set<Lang> treeSet = getTreeSetFromRes(res, q_FactMap);
		System.out.println(">>res: " + res);
		//System.out.println("2. treeSet: " + treeSet);
		
		List<String> result = new ArrayList<>();
		for(Lang l : treeSet) {
			result.add(l.lang);
		}
		
		return result;
	}
	
	public static Set<Lang> getTreeSetFromRes(Set<String> res, Map<String, Double> q_FactMap) {
		
		Comparator<Lang> cp = (a, b) ->  Double.compare(b.q_factor, a.q_factor);
		cp = cp.thenComparing((a, b) -> b.lang.compareTo(a.lang));
		
		Set<Lang> treeSet = new TreeSet<>(cp);
		
		for(String lang : res) {
			
			if(q_FactMap.containsKey(lang)) {
				Lang l = new Lang(lang, q_FactMap.get(lang));
				treeSet.add(l);
				continue;
			}
			
			// fr-BG
			String tag = lang.substring(0, 2);
			//if(!q_FactMap.containsKey(lang) && 
			if(!q_FactMap.containsKey(tag)
					&& !q_FactMap.containsKey("*")) 
				continue;
			
			if(q_FactMap.containsKey(tag)) {
				treeSet.add(new Lang(lang, q_FactMap.get(tag))); // fetch tag value from map
				continue;
			}
			
			if(q_FactMap.containsKey("*")) {
				treeSet.add(new Lang(lang, q_FactMap.get("*"))); 
			}
			
		}
		
		return treeSet;
	}
	
	/*
	 "fr-FR;q=1, fr-CA;q=0, fr;q=0.5"
	
	"fr-FR;q=1, fr-CA;q=0, *;q=0.5"
	"fr-FR;q=1, fr-CA;q=0.8, *;q=0.5"
	 */
	public static Map<String, Double> getLang_QFactMap(String acceptLangHeader) {
		Map<String, Double> map = new HashMap<>();
		for(String acptLang : acceptLangHeader.split(", ")) {
			String key = getLang(acptLang);
			double value = getQ_factor(acptLang);
			map.put(key, value);
		}
		
		System.out.println("QFactMap:: " + map);
		
		return map;
	}
	
	public static void addAllToTreeSet(Set<Lang> treeSet, 
			Set<String> langSet, Map<String, Double> q_FactMap) {
		
		for(String supLang : langSet) {
			if(!q_FactMap.containsKey(supLang)) 
				continue;
			
			double q_fact = q_FactMap.get(supLang);
			treeSet.add(new Lang(supLang, q_fact));
		}
	}
	
	public static String getLang(String lang) {
		// for input "fr-FR;q=1" return fr-FR
		return lang.substring(0, lang.indexOf(";"));
	}
	public static double getQ_factor(String lang) {
		// for input "fr-FR;q=0.5" return 0.5
		String q_fact = lang.substring(lang.indexOf("=")+1);
		return Double.parseDouble(q_fact);
	}
	
	static class Lang { //implements Comparable {
		public String lang;
		public double q_factor;
		public Lang(String lang, double q_factor) {
			this.lang = lang;
			this.q_factor = q_factor;
		}
		public String getLang() {
			return lang;
		}
		public void setLang(String lang) {
			this.lang = lang;
		}
		public double getQ_factor() {
			return q_factor;
		}
		public void setQ_factor(double q_factor) {
			this.q_factor = q_factor;
		}
		@Override
		public String toString() {
			return "[lang=" + lang + ", q_factor=" + q_factor + "]";
		}
		
		/*
		@Override
		public int compareTo(Object obj){
			Lang o = (Lang) obj;
		    return Comparator.comparing(Lang::getQ_factor)
		              .thenComparing(Lang::getLang)
		              .compare(this, o);
		} */
		
	}
	
	// Part 4 ends
	
	//---- Working [Taken from leetcode post]-----
	// Reference: https://leetcode.com/discuss/interview-question/1126296/Amazon-Latest-Hack-OA-experienced-SDE/1508390
	
	public static Set<String> parseAcceptLanguages(String userAcceptedLanguages, String[] serverSupportedLanguages) {
        //Disabled in prod by default
        assert userAcceptedLanguages != null;
        assert  serverSupportedLanguages != null;

        String[] userLocales = userAcceptedLanguages.split(",");

        List<AcceptLanguage> result = new ArrayList<>();
        List<AcceptLanguage> wildCardResult = new ArrayList<>();

        for (String userLocale : userLocales) {
            for (String serverSupportedLanguage : serverSupportedLanguages) {
                double weight = 0;
                String[] userLocaleSplitWithWeight = userLocale.split(";");
                if (userLocaleSplitWithWeight.length == 2){
                    userLocale = userLocaleSplitWithWeight[0]; //THake the wight part out
                    weight = Double.parseDouble(userLocaleSplitWithWeight[1].split("=")[1].trim());
                    //Is it possible to have no weight
                }

                String[] userLocaleSplit = userLocale.split("-");
                if (userLocaleSplit.length == 1) { // Wild card or non locale
                    String serverLanguage = userLocaleSplit[0];
                    if(serverLanguage.equals("*")) {
                        wildCardResult.add(new AcceptLanguage(serverSupportedLanguage, weight));
                    }
                    if(serverLanguage.equals(userLocale.trim())) {
                        result.add(new AcceptLanguage(serverSupportedLanguage, weight));
                    }
                } else {
                    if(serverSupportedLanguage.equals(userLocale.trim())) {
                        result.add(new AcceptLanguage(serverSupportedLanguage, weight));
                    }
                }

            }
        }
        result.addAll(wildCardResult);
        Collections.sort(result, Comparator.comparingDouble(AcceptLanguage::getWeight));

        return result.stream()
                .map(r -> r.header)
                .collect(Collectors.toSet());
    }

    static class AcceptLanguage {
        public String header;
        public Double weight;

        public Double getWeight() {
            return weight;
        }

        public AcceptLanguage(String header, double weight) {
            this.header = header;
            this.weight = weight;
        }
    }
    
    // test cases
    //Phase 1
    //How reliable is the user input/Case
    public static void testParseValidMatches() {
        String[] serverSupportedLanguages = new String[] {"fr-FR", "en-US"};
        String userAcceptedLanguages = "en-US, fr-CA, fr-FR";
        Set<String> parsedLocales = ParseAcceptLanguages
                .parseAcceptLanguages(userAcceptedLanguages, serverSupportedLanguages);
        //assertArrayEquals(new String[]{"en-US", "fr-FR"}, userLocales);
        //Prefer this so that you cna actually see the wrong values
        assertEquals(Arrays.toString(new String[]{"en-US", "fr-FR"}), parsedLocales.toString());
    }

    public static void testParseNoMatchServer() {
        String[] serverSupportedLanguages = new String[] {"fr-FR1", "en-US2"};
        String userAcceptedLanguages = "en-US, fr-CA, fr-FR";
        Set<String> parsedLocales = ParseAcceptLanguages
                .parseAcceptLanguages(userAcceptedLanguages, serverSupportedLanguages);
        assertEquals(Arrays.toString(new String[]{}), parsedLocales.toString());
    }

    public static void testParseNoMatchClient() {
        String[] serverSupportedLanguages = new String[] {"fr-FR", "en-US"};
        String userAcceptedLanguages = "en-US1, fr-CA, fr-FR2";
        Set<String> parsedLocales = ParseAcceptLanguages
                .parseAcceptLanguages(userAcceptedLanguages, serverSupportedLanguages);
        assertEquals(Arrays.toString(new String[]{}), parsedLocales.toString());
    }

    //Phase2

    public static void testParseValidMatchesWithLanguageTag() {
        String[] serverSupportedLanguages = new String[] {"fr-FR", "en-US"};
        String userAcceptedLanguages = "en, fr-CA, fr-FR";
        Set<String> parsedLocales = ParseAcceptLanguages
                .parseAcceptLanguages(userAcceptedLanguages, serverSupportedLanguages);
        assertEquals(Arrays.toString(new String[]{"en-US", "fr-FR"}), parsedLocales.toString());
    }
    
    public static void testParseValidMatchesWithWildCard() {
        String[] serverSupportedLanguages = new String[] {"fr-FR", "fr-CA", "fr-BG", "en-US"};
        String userAcceptedLanguages = "fr-FR;q=1, fr-CA;q=0.8, *;q=0.5";
        Set<String> parsedLocales = ParseAcceptLanguages
                .parseAcceptLanguages(userAcceptedLanguages, serverSupportedLanguages);
        assertEquals(Arrays.toString(new String[]{"fr-FR", "fr-CA"}), parsedLocales.toString());
    }
    
    public static <T> void assertEquals(T expected, T actual) {
	    if (expected == null && actual == null || actual != null && actual.equals(expected)) {
	      System.out.println("PASSED");
	    } else {
	      throw new AssertionError("Expected:\n  " + expected + "\nActual:\n  " + actual + "\n");
	    }
	}

}


/**

// https://leetcode.com/discuss/interview-question/1126296/amazon-latest-hack-oa-experienced-sde
	U will have enough time to solve these algorithms, but need to take edge cases.

	Part 1
	
	In an HTTP request, the Accept-Language header describes the list of
	languages that the requester would like content to be returned in. The header
	takes the form of a comma-separated list of language tags. For example:
	
	Accept-Language: en-US, fr-CA, fr-FR
	
	means that the reader would accept:
	
	1. English as spoken in the United States (most preferred)
	2. French as spoken in Canada
	3. French as spoken in France (least preferred)
	
	We're writing a server that needs to return content in an acceptable language
	for the requester, and we want to make use of this header. Our server doesn't
	support every possible language that might be requested (yet!), but there is a
	set of languages that we do support. 
	
	Write a function that receives two arguments:
	an Accept-Language header value as a string and a set of supported languages,
	and returns the list of language tags that will work for the request. The
	language tags should be returned in descending order of preference (the
	same order as they appeared in the header).
	
	In addition to writing this function, you should use tests to demonstrate that it's
	correct, either via an existing testing system or one you create.
	
	Examples:
	
	parse_accept_language(
	  "en-US, fr-CA, fr-FR",  # the client's Accept-Language header, a string
	  ["fr-FR", "en-US"]      # the server's supported languages, a set of strings
	)
	returns: ["en-US", "fr-FR"]
	
	parse_accept_language("fr-CA, fr-FR", ["en-US", "fr-FR"])
	returns: ["fr-FR"]
	
	parse_accept_language("en-US", ["en-US", "fr-CA"])
	returns: ["en-US"]
	
	Part 2
	
	Accept-Language headers will often also include a language tag that is not
	region-specific - for example, a tag of "en" means "any variant of English". Extend
	your function to support these language tags by letting them match all specific
	variants of the language.
	
	Examples:
	
	parse_accept_language("en", ["en-US", "fr-CA", "fr-FR"])
	returns: ["en-US"]
	
	parse_accept_language("fr", ["en-US", "fr-CA", "fr-FR"])
	returns: ["fr-CA", "fr-FR"]
	
	parse_accept_language("fr-FR, fr", ["en-US", "fr-CA", "fr-FR"])
	returns: ["fr-FR", "fr-CA"]
	
	Part 3
	
	Accept-Language headers will sometimes include a "wildcard" entry, represented
	by an asterisk, which means "all other languages". Extend your function to
	support the wildcard entry.
	
	Examples:
	
	parse_accept_language("en-US, *", ["en-US", "fr-CA", "fr-FR"])
	returns: ["en-US", "fr-CA", "fr-FR"]
	
	parse_accept_language("fr-FR, fr, *", ["en-US", "fr-CA", "fr-FR"])
	returns: ["fr-FR", "fr-CA", "en-US"]
	
	Part 4
	
	Accept-Language headers will sometimes include explicit numeric weights (known as
	q-factors) for their entries, which are used to designate certain language tags
	as specifically undesired. For example:
	
	Accept-Language: fr-FR;q=1, fr;q=0.5, fr-CA;q=0
	
	This means that the reader most prefers French as spoken in France, will take
	any variant of French after that, but specifically wants French as spoken in
	Canada only as a last resort. Extend your function to parse and respect q-factors.
	
	Examples:
	
	parse_accept_language("fr-FR;q=1, fr-CA;q=0, fr;q=0.5", ["fr-FR", "fr-CA", "fr-BG"])
	returns: ["fr-FR", "fr-BG", "fr-CA"]
	
	parse_accept_language("fr-FR;q=1, fr-CA;q=0, *;q=0.5", ["fr-FR", "fr-CA", "fr-BG", "en-US"])
	returns: ["fr-FR", "fr-BG", "en-US", "fr-CA"]
	
	parse_accept_language("fr-FR;q=1, fr-CA;q=0.8, *;q=0.5", ["fr-FR", "fr-CA", "fr-BG", "en-US"])
	

*/