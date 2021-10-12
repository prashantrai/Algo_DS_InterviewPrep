package Dropbox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class WebCrawler_Multithreaded_ConcurrentHashMap_1242_Medium {

	public static void main(String[] args) {
		String startUrl = "http://news.yahoo.com/news/topics/";
		List<String> res = crawl(startUrl, new HtmlParser());
		System.out.println(">> Res:: "+res);
		
	}
	
	/* Very Good: Watch this for Java implementation: https://www.youtube.com/watch?v=dej0rq-9Xjc
	 * 
	 * For Question: https://leetcode.ca/all/1242.html
	 * 
	 * Code and question: https://leetcode.jp/leetcode-1242-web-crawler-multithreaded-%e8%a7%a3%e9%a2%98%e6%80%9d%e8%b7%af%e5%88%86%e6%9e%90/
	 *
	 * Another resource: http://scrumbucket.org/tutorials/neo4j-site-crawler/part-2-create-multi-threaded-crawl-manager/
	 * 
	 * https://www.youtube.com/watch?v=SFWNZxdFUaI
	 */
	
	public static List<String> crawl(String startUrl, HtmlParser htmlParser) {
		String hostName = startUrl.split("/")[2];
		Crawler_ConcurrentHashMap crawler = new Crawler_ConcurrentHashMap();
		List<String> results = crawler.crawl(startUrl, htmlParser);
		
		return results;
	}
	
}

class Crawler_ConcurrentHashMap {
	ConcurrentHashMap<String, Integer> urls;
	String hostName;
	HtmlParser htmlParser;
	
	public List<String> crawl(String startUrl, HtmlParser htmlParser) {
		this.urls = new ConcurrentHashMap<>();
		this.hostName = startUrl.split("/")[2];;
		this.htmlParser = htmlParser;
		crawl(startUrl);
		return new ArrayList<String>(urls.keySet());
	}

	private void crawl(String startUrl) {

		String startUrl_domain = getUrl(startUrl); 
		
		if(!startUrl_domain.equals(hostName) || urls.containsKey(startUrl))
			return;
		
		urls.put(startUrl, 1); // value doesn't matter here. Our purpose to utilize the thread safe feature of ConcurrentHashMap
		
		List<Thread> threads = new ArrayList<>();
		for(String url : htmlParser.getUrls(startUrl)) {
			Thread t = new Thread(() -> crawl(url));  // for lambda syntax refer: https://alvinalexander.com/java/java-8-lambda-thread-runnable-syntax-examples/
			threads.add(t);
			t.start();
		}
		
		for(Thread t : threads) {
			join(t);
		}
		
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

