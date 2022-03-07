package Dropbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class WebCrawler_1236_Medium {

	public static void main(String[] args) {
		
		//edges = [[2,0],[2,1],[3,2],[3,1],[0,4]]
		String startUrl = "http://news.yahoo.com/news/topics/";
		
		/*Expected Output: [
		  "http://news.yahoo.com",
		  "http://news.yahoo.com/news",
		  "http://news.yahoo.com/news/topics/",
		  "http://news.yahoo.com/us"
		]
		*/
		
		
		List<String> res = crawl_BFS(startUrl, new HtmlParser());
		System.out.println("BFS Res:: "+res);
	}
	
	/*
	 * Problem:: https://leetcode.ca/all/1236.html 
	 * 			https://leetcode.jp/problemdetail.php?id=1236#:~:text=1236.-,Web%20Crawler,web%20crawler%20in%20any%20order.
	 * Solution: https://leetcode.jp/leetcode-1236-web-crawler-%e8%a7%a3%e9%a2%98%e6%80%9d%e8%b7%af%e5%88%86%e6%9e%90/
	 * */
	
	static Set <String> res = new HashSet<>() ; // return result
	public static List<String> crawl_DFS(String startUrl, HtmlParser htmlParser) {
	    String host = getUrl ( startUrl ) ; // get domain name
	    res.add(startUrl); // add startUrl to the returned result
	    dfs (startUrl, host, htmlParser) ; // start dfs
	    return new ArrayList<>(res);
	}
	
	// // Complexity: Time O(n), Space O(n)
	private static void dfs(String startUrl, String host, HtmlParser htmlParser) {
		List<String> urls = htmlParser.getUrls(startUrl);
		for(String u : urls) {
    		
    		// If the link has been crawled or does not belong to the current domain name, skip
    		if(res.contains(u) || !getUrl(u).equals(host)) {
    			continue;
    		}
    		res.add(u); // add the link to the return result
    		
    		dfs(u, host, htmlParser);
    	}
	}

	//Runtime :: O(|V| + |E|)	Space:: O(|V|)
	// Complexity: Time O(n), Space O(n)
	
	//another bfs
	private static List<String> crawl_BFS(String startUrl, HtmlParser htmlParser) {
		String host = getUrl ( startUrl ) ; // get domain name
	    
	    Queue<String> q = new LinkedList<>();
	    q.offer(startUrl);	// get domain name
	    res.add(startUrl);
	    
	    while(!q.isEmpty()) {
	    	
	    	String url = q.poll();
	    	List<String> urls = htmlParser.getUrls(url);
	    	for(String u : urls) {
	    		
	    		// If the link has been crawled or does not belong to the current domain name, skip
	    		if(res.contains(u) || !getUrl(u).equals(host)) {
	    			continue;
	    		}
	    		res.add(u); // add the link to the return result
	    		q.offer(u); // add this link to the Queue of bfs
	    	}
	    }
	    return new ArrayList<String>(res);
	}

	private static String getUrl(String url) {
		String[] args = url.split("/");
		return args[2];
	}
	
	//Mocked class
	static class HtmlParser {
		
		public List<String> getUrls(String url) {

			String[] urls = {
				  "http://news.yahoo.com",
				  "http://news.yahoo.com/news",
				  "http://news.yahoo.com/news/topics/",
				  "http://news.google.com",
				  "http://news.yahoo.com/us"
			};
			
			return Arrays.asList(urls);
		}
	}
	
	
	// https://leetcode.com/problems/web-crawler/discuss/604044/Simple-Java-BFS-and-DFS-solutions
	// bfs
	// Time and space: O(N)
	public List<String> crawl(String startUrl, HtmlParser htmlParser) {
        List<String> result = new ArrayList<>();
        Queue<String> queue = new LinkedList<>();
        Set<String> visitedSet = new HashSet<>();

        String split[] = startUrl.split("/");
        String domain = split[0] + "//" + split[2];
        queue.offer(startUrl);
        visitedSet.add(startUrl);

        while (!queue.isEmpty()) {
            String u = queue.poll();
            result.add(u);
            for (String v : htmlParser.getUrls(u)) {
                if (v.startsWith(domain) && !visitedSet.contains(v)) {
                    queue.offer(v);
                    visitedSet.add(v);
                }
            }
        }
        return result;
    }

}
