package Oracle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WebCrawler_Multithreaded_BestSolutionForInterview_1242_Medium {

	public static void main(String[] args) {
		String startUrl = "http://news.yahoo.com/news/topics/";
		List<String> res = crawl(startUrl, new HtmlParser());
		System.out.println("[1] Res:: "+res);
		
		res = crawl_withPool(startUrl, new HtmlParser());
		System.out.println("[2] Res:: "+res);
	}
	
	/* 
	 * Refer video for solution: https://www.youtube.com/watch?v=dej0rq-9Xjc
	 * 
	 * Question: 
	 * https://leetcode.ca/all/1242.html : This one has another solution link as well
	 * https://leetcode.ca/2019-04-25-1242-Web-Crawler-Multithreaded/ : with different solution
	 * https://leetcode.jp/leetcode-1242-web-crawler-multithreaded-%E8%A7%A3%E9%A2%98%E6%80%9D%E8%B7%AF%E5%88%86%E6%9E%90/
	 * 
	 * */

	public static List<String> crawl(String startUrl, HtmlParser htmlParser) {
		String hostName = getHostName(startUrl);
		
		Crawler crawler = new Crawler(startUrl, hostName, htmlParser);
		//ConcurrentHashMap<String, Boolean> visited = new ConcurrentHashMap<>();
		crawler.result = ConcurrentHashMap.newKeySet();  // works because newKeySet is static  
		Thread thread = new Thread(crawler);
		thread.start();
		
		Crawler.joinThread(thread);
		return new ArrayList<>(Crawler.result);
	}
	
	/* Follow-up solution: only change is additional line for ExecutorSerive and 
	 * Future object instead of Thread rest is same as above
	 */
	public static List<String> crawl_withPool(String startUrl, HtmlParser htmlParser) {
		String hostName = getHostName(startUrl);
		
		Crawler_WithThreadPool crawlerWithPool = new Crawler_WithThreadPool(startUrl, hostName, htmlParser);
		//ConcurrentHashMap<String, Boolean> visited = new ConcurrentHashMap<>();
		crawlerWithPool.result = ConcurrentHashMap.newKeySet();  // 
		
		ExecutorService pool = Executors.newFixedThreadPool(500);
		crawlerWithPool.pool = pool;
		
		Future<?> task = pool.submit(crawlerWithPool);
		Crawler_WithThreadPool.joinThread(task);
		pool.shutdown();
		
		return new ArrayList<>(Crawler.result);
	}
	
	public static String getHostName(String startUrl) {
		int idx = startUrl.indexOf('/', 7);
		String hostName = idx == -1 ? startUrl : startUrl.substring(0, idx);
		hostName = idx == -1 ? startUrl : startUrl.substring(0, idx); // http://news.yahoo.com
		//hostName = idx == -1 ? startUrl : startUrl.substring(7, idx); // news.yahoo.com
		
		return hostName;
	}
	
}

/**** Updated Solution (of Main Problem) for Follow-up question ****/

class Crawler_WithThreadPool implements Runnable {
	private String startUrl;
	private String hostName;
	private HtmlParser htmlParser;
	public static Set<String> result; // you may refer this as ConcurrenHashSet (remember there is no class in Java with this name)
	
	// Follow-up: Create a Thread Pool to serve huge number of requests
	public static ExecutorService pool;
	
	public Crawler_WithThreadPool(String startUrl, String hostName, HtmlParser htmlParser) {
		this.startUrl = startUrl;
		this.hostName = hostName;
		this.htmlParser = htmlParser;
	}
	
	@Override
	public void run() {
		if(startUrl.startsWith(hostName) && !result.contains(startUrl)) {
			result.add(startUrl);
			//System.out.println("startUrl: "+startUrl);
			
			//Follow-up: Updates List from Thread to hold Future objects
			List<Future<?>> tasks = new ArrayList<>(); 
			for(String url : htmlParser.getUrls(startUrl)) {
				Crawler_WithThreadPool crawler 
											= new Crawler_WithThreadPool(url, hostName, htmlParser);
				Future<?> task = pool.submit(crawler);
				tasks.add(task);
			}
			
			// Follow-up: Updates to interate tasks
			for(Future<?> task : tasks) {
				joinThread(task);
			}
		}
	}
	
	//Follow-up: Updated signature for Future from Thread (see main solution to compare)
	public static void joinThread(Future<?> task) { 
		try {
			task.get();
		}catch(Exception e) {e.printStackTrace();} 
	}
	
}


/**** Solution of Main Problem (No Thread Pool i.e. no ExecutorService) ****/

class Crawler implements Runnable {
	private String startUrl;
	private String hostName;
	private HtmlParser htmlParser;
	public static Set<String> result; // you may refer this as ConcurrenHashSet (remember there is no class in Java with this name)
	
	public Crawler(String startUrl, String hostName, HtmlParser htmlParser) {
		this.startUrl = startUrl;
		this.hostName = hostName;
		this.htmlParser = htmlParser;
	}
	
	@Override
	public void run() {
		if(startUrl.startsWith(hostName) && !result.contains(startUrl)) {
			result.add(startUrl);
			List<Thread> threads = new ArrayList<>();
			for(String url : htmlParser.getUrls(startUrl)) {
				Crawler crawler = new Crawler(url, hostName, htmlParser);
				Thread thread = new Thread(crawler);
				thread.start();
				threads.add(thread);
			}
			
			// shutdown all the open threads for crawler
			for(Thread thread : threads) {
				joinThread(thread);
			}
		}
	}
	
	public static void joinThread(Thread thread) {
		try {
			thread.join();
		}catch(Exception e) {e.printStackTrace();} 
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
