package Facebook;

import java.util.Stack;

public class SimplifyPath_71_Medium {

	public static void main(String[] args) {
		String path = "/home/";
		System.out.println("Expected: /home   Actual: " + simplifyPath(path));
		
		path = "/../";
		System.out.println("Expected: /   Actual: " + simplifyPath(path));
		
		path = "/home//foo/";
		System.out.println("Expected: /home/foo    Actual: " + simplifyPath(path));
	}
	
	/**
	 * Time Complexity: O(N), if there are N characters in the original path. 
	 * First, we spend O(N) trying to split the input path into components and 
	 * then we process each component one by one which is again an O(N) operation.
	 * 
	 * Space Complexity: O(N). Actually, it's 2N because we have the array that 
	 * contains the split components and then we have the stack.
	 */
	
    public static String simplifyPath(String path) {

        // Initialize a stack
        Stack<String> stack = new Stack<String>();
        String[] components = path.split("/");

        // Split the input string on "/" as the delimiter
        // and process each portion one by one
        for (String directory : components) {

            // A no-op for a "." or an empty string
            if (directory.equals(".") || directory.isEmpty()) {
                continue;
            } else if (directory.equals("..")) {

                // If the current component is a "..", then
                // we pop an entry from the stack if it's non-empty
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {

                // Finally, a legitimate directory name, so we add it
                // to our stack
                stack.add(directory);
            }
        }

        // Stich together all the directory names together
        StringBuilder result = new StringBuilder();
        for (String dir : stack) {
            result.append("/");
            result.append(dir);
        }

        return result.length() > 0 ? result.toString() : "/" ;
    }

}
