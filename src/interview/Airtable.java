package interview;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Airtable {
	/*
	 * To execute Java, please define "static void main" on a class
	 * named Solution.
	 *
	 * If you need more classes, simply define them inline.
	 */
	/** LC609
	Imagine that we are writing a backup application used to backup files from your laptop to a remote server. 
	To save on network bandwidth, we want to identify duplicate files (i.e., files with the same contents). 
	This way, we only need to upload duplicate files once.

	Write a function that identifies sets of files with identical contents.

	    find_dupes(root_path) → sets/lists of 2 or more file paths that have identical contents

	    find_dupes("/home/airtable") → [
	            [".bashrc", "Backups/2017_bashrc"],
	            ["Photos/Vacation/DSC1234.JPG", "profile.jpeg", ".trash/lej2dp28/87msnlgyr"],
	    ]

	
	Follow-up: For scale, imagine running this on a computer with at most 2 TB of data and at most 1 million files.

	For traversing the filesystem, use these library functions:
	- list_folder(path) → list of names of immediate file and folder children
	- is_folder(path) → boolean


	Algorithm: 
	1. List through all the files
		a. if folder recursively call the same method
	2. For each file create a HashMap of HashCode/MessageDigest of file as and value is list of files
		a. for MessageDigest refer https://howtodoinjava.com/java/io/sha-md5-file-checksum-hash/ 
		    and https://rosettacode.org/wiki/Find_duplicate_files#Java
	3. Now for each file generate a MessageDigest and see if HashMap contains that 
		IF YES then add that file in list against that MessageDigest
		ELSE Add new entry in map
	


	https://www.glassdoor.com/Interview/Find-all-duplicate-files-by-content-in-your-filesystem-QTN_899091.htm
	
	For Follow-up: 
	https://leetcode.com/problems/find-duplicate-file-in-system/discuss/1217800/Python3-Memory-Efficient(beats-97).-Thoughts-on-Follow-Up-questions

	*/

	public static Bytestream read(File file) {
	  return '';
	}

	class Solution {
	  public static void main(String[] args) {
	   
	  }
	  
	  public static List<List<String>> find_dupes(String path) {
	  
	      if(path == null || path.isEmpty()) return new ArrayList<>();
	    
	      List<String> children = list_folder(path);
	    
	      Bytestream b1;
	      
	      for(String child : children) {
	          
	          if(isFolder(child)) {
	        	  find_dupes(child); // recursively call this method for sub-folder
	          } else {
	            b1 = read(new File(child));
	            compareFiles(b1, child);
	          }
	          
	      }
	    
	      return map.values();
	    
	  }
	  
	  
	  
	  
	  
	  Map<Long, List<String>> map = new HashMap<>();
	   public getHashCode(Bytestream b1) {
		   // refer: https://howtodoinjava.com/java/io/sha-md5-file-checksum-hash/
		   // https://rosettacode.org/wiki/Find_duplicate_files#Java
		   
	      b1.hashcode(); // some custom hash functions
	   }
	  
	  public static void compareFiles(Bytestream b, String path) {
	    
	    Long hash = getHashCode(b);
	    if(map.contains(hash)) {
	      map.get(hash).add(path);
	    } else {
	      List<String> lst = new ArrayList<>();
	      lst.add(path);
	      map.put(hash, lst);
	    }
	  }
	  
	}

}
