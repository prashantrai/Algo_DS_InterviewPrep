package Dropbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class WebCrawler_Multithreaded_1242_Medium {

	public static void main(String[] args) {

		String startUrl = "http://news.yahoo.com/news/topics/";
		List<String> res = crawl(startUrl, new HtmlParser());
		System.out.println(">> Res:: "+res);
		
	}
	

	/* Very Good (look at this only. It has answer for follow-up as well): 
	 * 			Watch this for Java implementation: https://www.youtube.com/watch?v=dej0rq-9Xjc
	 * 
	 * For Question:  https://leetcode.ca/all/1242.html
	 * 
	 * Code and question: https://leetcode.jp/leetcode-1242-web-crawler-multithreaded-%e8%a7%a3%e9%a2%98%e6%80%9d%e8%b7%af%e5%88%86%e6%9e%90/
	 *
	 * Another resource: http://scrumbucket.org/tutorials/neo4j-site-crawler/part-2-create-multi-threaded-crawl-manager/
	 * 
	 * https://www.youtube.com/watch?v=h9t7Mz6s9b4
	 */
	
	public static List<String> crawl(String startUrl, HtmlParser htmlParser) {
		String hostName = startUrl.split("/")[2];
		Crawler crawler = new Crawler(startUrl, hostName, htmlParser);
		crawler.results = new HashSet<String>();
		Thread thread = new Thread(crawler);
		thread.start();
		crawler.join(thread);
		
		return new ArrayList<String>(crawler.results);
	}
	
}


class Crawler implements Runnable {
	String startUrl;
	String hostName;
	HtmlParser htmlParser;
	
	/*
	 replace this with Set<String> results = ConcurrentHashMap_obj.newKeySet(); // this will create a Set backed by CuncurrentHashMapp you can think it like ConcurrentHashSet
	 No need to use volatile for ConcurrentHashSet
	*/
	static volatile Set<String> results =  new HashSet<>();
	
	public Crawler(String startUrl, String hostName, HtmlParser htmlParser) {
		this.startUrl = startUrl;
		this.hostName = hostName;
		this.htmlParser = htmlParser;
	}
	
	@Override
	public void run() {
		String startUrl_domain = getUrl(startUrl); 
		
		if(!startUrl_domain.equals(hostName) || results.contains(startUrl))
			return;
		
		addUrl(results, startUrl);
		
		List<Thread> threads = new ArrayList<>();
		
		for(String url : htmlParser.getUrls(startUrl)) {
			Crawler crawler = new Crawler(url, hostName, htmlParser);
			Thread thread = new Thread(crawler);
			thread.start();
			threads.add(thread);
		}
		
		for(Thread t : threads) join(t);
		
	}
	
	public static synchronized void addUrl(Set<String> results, String url) {
		results.add(url);
	}
	
	public void join(Thread t) {
		try {
			t.join();
		} catch(Exception e) {
			//TODO
		}
	}
	
	private static String getUrl(String url) {
		String[] args = url.split("/");
		return args[2];
	}
	
}

class HtmlParser {
	
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
