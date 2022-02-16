package Vmware;

import java.util.*;
import java.util.concurrent.*;
import java.util.Scanner;

public class VmWare_PhoneScreen {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

// For Question see screen shot at location: 
// /Users/prashantrai/Google Drive/LeetCode_Premium/Online Assessments/vmware_phone screen.png	

/*
 * Create the StringsCollection class here.
 */
 class StringsCollection  {
    private List<String> stringCollection;
    
    
    public StringsCollection() {
        stringCollection = new ArrayList<>();
        
    }
    public void addString(String s) {
        stringCollection.add(s);
    }
    public List<String> getStringsCollection() {
        return stringCollection;
    }
 }
 
 
class StringsCollectionRunnable implements Runnable {
    private final StringsCollection stringsCollection;
    private final int stringsCount;
    private final String threadName;
    
    public StringsCollectionRunnable(StringsCollection stringsCollection, int stringsCount, String threadName) {
        this.stringsCollection = stringsCollection;
        this.stringsCount = stringsCount;
        this.threadName = threadName;
    }
    
    @Override
    public void run() {        
        synchronized(stringsCollection) {
            for (int j = 0; j < stringsCount; j++) {
                this.stringsCollection.addString(threadName + String.valueOf(j + 1));
            }
        }
    }
}

class Solution {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final StringsCollection STRINGS_COLLECTION = new StringsCollection();
    
    public static void main(String[] args) {
        int threadsCount = Integer.parseInt(SCANNER.nextLine());
        Thread[] threads = new Thread[threadsCount];
        
        for (int i = 0; i < threadsCount; i++) {
            int stringsCount = Integer.parseInt(SCANNER.nextLine());
            
            threads[i] = new Thread(new StringsCollectionRunnable(STRINGS_COLLECTION, stringsCount, String.valueOf(i + 1)));
            threads[i].start();
        }
        
        for (int i = 0; i < threadsCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ex) {
                System.err.println(ex);
            }
        }
        
        List<String> stringsCollection = STRINGS_COLLECTION.getStringsCollection();
        System.out.println(stringsCollection.size());
        
        int nonNullStrings = 0;
        for (String string: stringsCollection) {
            if (string != null) {
                nonNullStrings++;
            }
        }
        
        System.out.println(nonNullStrings);
    }
}
