
public class CloudKitchen {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

	// Line 0: The ID of the item
	// Line 1: The item type, either CATEGORY, ENTREE or OPTION
	// Line 2: The name of the item
	// Line 3: The price of the item for ENTREE and OPTION. Not present for CATEGORY items.

	// Any other line: A list of item IDs that are linked to the current item.


	// 4
	// ENTREE
	// Spaghetti
	// 10.95
	// 2
	// 3
	// 
	// 1
	// CATEGORY
	// Pasta
	// 4
	// 5
	// 
	// 2
	// OPTION
	// Meatballs
	// 1.00
	// 
	// 3
	// OPTION
	// Chicken
	// 2.00
	// 
	// 5
	// ENTREE
	// Lasagna
	// 12.00
	// 
	// 6
	// ENTREE
	// Caesar Salad
	// 9.75
	// 3



	/*
	 * 
	 
	 Store data in DS
	 read the input
	 read and return the item from DS
	 
	 */

	import java.io.*;
	import java.util.*;

	interface MenuStream {
	    String nextLine(); // Returns null when stream is empty
	}


	class MenuParser {

	   private Map<Integer, Items> map;
	  
	  // public void parseStream(MenuStream stream) {
	  //   new Items(stream.nextLine(), 
	  //             stream.nextLine(), 
	  //             stream.nextLine(), 
	  //             stream.nextLine(),
	  //            new ArrayList(stream.nextLine(), stream.nextLine()))
	  // }
	  
	  public MenuParser() {
	    map = new HahsMap<>();
	  }
	  
	  public void readAndStoreData(MenuStream st) {
	    List<Items> it = new ArrayList<>();
	    
	    Item i = new Item();
	    List<Stirng> tempList = new ArrayList<>();
	    while(st.nextLine()) {
	      
	      String s = st.nextLine();
	      if(!" ".equals(s)) {
	        i.setId(tempList.get(0));
	        i.setCat(tempList.get(1));
	        i.setName(tempList.get(2));
	        i.setPrice(Integer.parseDouble(tempList.get(3)));
	        
	        for(int ii=4; ii<tempList.size(); ii++) {
	          i.getItemList().add(tempList.get(ii));
	        }
	        
	        i = new Item();
	        tempList = new Arrayist<>();
	      }
	      
	      
	    }
	    
	  } 
	  
	  
	  public Item fetchMenuItem(String id) {
	    if(!map.containsKey(id)) return null;
	    
	    return map.get(id);
	  }
	  
	  public Map<Integer, Items> storeAndGetItemsMap(List<Items> list) {
	      for(Items i : list) {
	        map.put(i.getId, i);
	      }
	  }
	  

	}


	class Items {
	  private String id;
	  private String cat;
	  private String name;
	  private float price;
	  List<Integer> iList;
	  
	  public Items() {
	  }
	  
	  public Items(String id, String cat, String name, float price, List<Integer> iList) {
	    this.id = id;
	    this.cat = cat;
	    this.name = name;
	    this.price = price;
	    this.iList = iList;
	    
	  }
	  
	  public String getId() {
	    return id;
	  }
	  public String getCat() {
	    return cat;
	  }
	  public String getName() {
	    return name;
	  }
	  public float getPrice() {
	    return price;
	  }
	  public List<Integer> getItemList() {
	    return iList;
	  }
	  
	  
	  //TODO setter getters

	}