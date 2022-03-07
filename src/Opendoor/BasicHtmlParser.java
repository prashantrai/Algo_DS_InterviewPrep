package Opendoor;

import java.util.HashSet;
import java.util.Set;

public class BasicHtmlParser {

	public static void main(String[] args) {

		Set<String> res = parseAndGetUrls();  // working
		Set<String> res2 = parseAndGetUrls_UsingSplit(); //working
	}
	
	static String html = "<!DOCTYPE html>\n"+
            "<html>\n"+
            " <head>\n"+
            "   <title>Example Input Markup</title>\n"+
            " </head>\n"+
            " <body>\n"+
            "   <p id=\"msg\">\n"+
            "     Hello World!\n"+
            "   </p>\n"+
            "	<a href='http://news.yahoo.com'>anchor1</a>" +
            "	<a href='http://news.yahoo.com/news'>anchor2</a>" +
            "	<a href='http://news.yahoo.com/news/topics/'>anchor3</a>" +
            "	<a href='http://news.google.com'>anchor4</a>" +
            "	<a href='http://news.yahoo.com/us'>anchor5</a>" +
            " </body>\n"+
            "</html>";
	
	//working
	//parse and return HREF URLs
	public static Set<String> parseAndGetUrls() {
		
		Set<String> urls = new HashSet<>();
		int index = 0;
		String token = "href";
		
		while(true) {
			index = html.indexOf(token, index);
			
			if(index != -1) {
				String urlStr = html.substring(index, html.length());
				int endOfHrefIdx = urlStr.indexOf(">")-1; //closing element tag 
				// added +2 to get index after '=' and quote
				int urlStartIdx = urlStr.indexOf("=") + 2; 
				String url =  urlStr.substring(urlStartIdx, endOfHrefIdx);
				
				System.out.println("url: "+url);
				
				urls.add(url);
				index += endOfHrefIdx; 
				
			} else {
				break;
			}
		}
		
		System.out.println(urls);
		return urls;
	}
	
	//working
	public static Set<String> parseAndGetUrls_UsingSplit() {
		
		Set<String> urls = new HashSet<>();
		String token = "<a href=";
		
		String[] arr = html.split(token);
		
		for(int i=1; i<arr.length; i++) {
			String s = arr[i];
			int stIdx = 1; // after quote
			int endIdx = s.indexOf(">");
			
			String url = s.substring(stIdx, endIdx-1);
			System.out.println("url:: "+url);
			urls.add(url);
		}
		return urls;
	}

}
