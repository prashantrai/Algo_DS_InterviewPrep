package Amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoadBalancer {
	
	// Main function to test the solution
    public static void main(String[] args) {
        // Test Case 1
        int num_servers1 = 5;
        int[] requests1 = {3, 2, 3, 2, 4};
//        System.out.println("Test Case 1 Output: " + findRequestTarget_Qwen(num_servers1, requests1)); // Expected: [0, 1, 2, 0, 3]

        // Test Case 2
        int num_servers2 = 5;
        int[] requests2 = {0, 1, 2, 3};
//        System.out.println("Test Case 2 Output: " + findRequestTarget_Qwen(num_servers2, requests2)); // Expected: [0, 1, 2, 3]

        // Test Case 3: Edge Case - All requests point to the first server
        int num_servers3 = 5;
        int[] requests3 = {0, 0, 0, 0};
//        System.out.println("Test Case 3 Output: " + findRequestTarget_Qwen(num_servers3, requests3)); // Expected: [0, 0, 0, 0]

        // Test Case 4: Edge Case - Single server
        int num_servers4 = 1;
        int[] requests4 = {0, 0, 0};
//        System.out.println("Test Case 4 Output: " + findRequestTarget_Qwen(num_servers4, requests4)); // Expected: [0, 0, 0]

        // Test Case 5: Edge Case - Large number of servers and requests
        int num_servers5 = 10;
        int[] requests5 = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
//        System.out.println("Test Case 5 Output: " + findRequestTarget_Qwen(num_servers5, requests5)); // Expected: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
        
        ///////////////////////
        
        // Self
        // Returns Array
        System.out.println("\n\n---[self]-----\n");
        System.out.println("\n\n---[Return Array]-----\n");
        System.out.println("Test Case 1: Expected: [0, 1, 2, 0, 3] Actual: " + Arrays.toString(findRequestTarget_2(num_servers1, requests1))); // Expected: [0, 1, 2, 0, 3]
        System.out.println("Test Case 2: Expected: [0, 1, 2, 3] Actual: " + Arrays.toString(findRequestTarget_2(num_servers2, requests2))); // Expected: [0, 1, 2, 3]
        System.out.println("Test Case 3: Expected: [0, 0, 0, 0] Actual: " + Arrays.toString(findRequestTarget_2(num_servers3, requests3))); // Expected: [0, 0, 0, 0]
        System.out.println("Test Case 4: Expected: [0, 0, 0] Actual: " + Arrays.toString(findRequestTarget_2(num_servers4, requests4))); // Expected: [0, 0, 0]

        System.out.println("Test Case 5 [Returns Array]: Expected: [0, 1, 2, 3, 4, 0, 1, 2, 0, 0] Actual: " 
        		+ Arrays.toString(findRequestTarget_2(num_servers5, requests5))); // Expected: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
        
      // Returns List  
      System.out.println("\n\n---[Return List]-----\n");
      System.out.println("Test Case 1: Expected: [0, 1, 2, 0, 3] Actual: " + findRequestTarget_2_List(num_servers1, requests1)); // Expected: [0, 1, 2, 0, 3]
      System.out.println("Test Case 2: Expected: [0, 1, 2, 3] Actual: " + findRequestTarget_2_List(num_servers2, requests2)); // Expected: [0, 1, 2, 3]
      System.out.println("Test Case 3: Expected: [0, 0, 0, 0] Actual: " + findRequestTarget_2_List(num_servers3, requests3)); // Expected: [0, 0, 0, 0]
      System.out.println("Test Case 4: Expected: [0, 0, 0] Actual: " + findRequestTarget_2_List(num_servers4, requests4)); // Expected: [0, 0, 0]
        
      System.out.println("Test Case 5 [Returns Array]: Expected: [0, 1, 2, 3, 4, 0, 1, 2, 0, 0] Actual: " 
    		  + findRequestTarget_2_List(num_servers5, requests5)); // Expected: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
        
        
//        System.out.println("\n\n----[Perplexity]----\n");
//        System.out.println("Test Case 1 Output: " + Arrays.toString(findRequestTarget_Perplexity(num_servers1, requests1))); // Expected: [0, 1, 2, 0, 3]
//        System.out.println("Test Case 2 Output: " + Arrays.toString(findRequestTarget_Perplexity(num_servers2, requests2))); // Expected: [0, 1, 2, 3]
//        System.out.println("Test Case 3 Output: " + Arrays.toString(findRequestTarget_Perplexity(num_servers3, requests3))); // Expected: [0, 0, 0, 0]
//        System.out.println("Test Case 4 Output: " + Arrays.toString(findRequestTarget_Perplexity(num_servers4, requests4))); // Expected: [0, 0, 0]
//        System.out.println("Test Case 5 Output: " + Arrays.toString(findRequestTarget_Perplexity(num_servers5, requests5))); // Expected: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
        
        
        // ChatGPT
//        System.out.println("\n\n--------\n");
//        System.out.println("Test Case 1 Output: " + findRequestTarget_ChatGPT(num_servers1, requests1)); // Expected: [0, 1, 2, 0, 3]
//        System.out.println("Test Case 2 Output: " + findRequestTarget_ChatGPT(num_servers2, requests2)); // Expected: [0, 1, 2, 3]
//        System.out.println("Test Case 3 Output: " + findRequestTarget_ChatGPT(num_servers3, requests3)); // Expected: [0, 0, 0, 0]
//        System.out.println("Test Case 4 Output: " + findRequestTarget_ChatGPT(num_servers4, requests4)); // Expected: [0, 0, 0]
//        System.out.println("Test Case 5: Expected: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9] Actual: " 
//        		+ findRequestTarget_ChatGPT(num_servers5, requests5)); // Expected: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
        
    }

    // working
    public static int[] findRequestTarget_2(int num_servers, int[] requests) {
    	// Step 1: Initialize the serverLoads array to keep track of request counts
        int[] serverLoads = new int[num_servers]; // To track load on each server
        int[] serverAssignments = new int[requests.length]; // To store final assignments, result
        
        // Step 2: Process each request
        for(int i=0; i<requests.length; i++) {
        	int ipHash = requests[i];
        	int minRequests = Integer.MAX_VALUE;
        	int selectedServer = -1;
        	
        	// Step 3: Find the server with the minimum requests in the range [0, request]
        	for(int j=0; j<=ipHash; j++) {
        		if(serverLoads[j] < minRequests) {
        			minRequests = serverLoads[j];	// Update the minimum requests
        			selectedServer = j;	// Update the assigned server
        		}
        	}
        	
        	// Step 4: Assign the request to the chosen server
        	serverLoads[selectedServer]++;
        	serverAssignments[i] = selectedServer;
        	
        }
        
        
    	return serverAssignments;
    }
    
    // working
    // Function to find the target server for each request
    public static List<Integer> findRequestTarget_2_List(int num_servers, int[] requests) {
        // Step 1: Initialize the serverRequests array to keep track of request counts
        int[] serverRequests = new int[num_servers];
        List<Integer> result = new ArrayList<>();

        // Step 2: Process each request
        for (int request : requests) {
            int minRequests = Integer.MAX_VALUE; // To track the minimum number of requests
            int assignedServer = -1; // To store the ID of the server to assign the request

            // Step 3: Find the server with the minimum requests in the range [0, request]
            for (int i = 0; i <= request; i++) { // Strictly limit the range to [0, request]
                if (serverRequests[i] < minRequests || 
                    (serverRequests[i] == minRequests && i < assignedServer)) {
                    minRequests = serverRequests[i]; // Update the minimum requests
                    assignedServer = i; // Update the assigned server
                }
            }

            // Step 4: Assign the request to the chosen server
            serverRequests[assignedServer]++;
            result.add(assignedServer); // Add the assigned server ID to the result
        }

        return result;
    }
    
    
    //////////// Below are additional solutions, almost similar
    
    // Qwen
    // Function to find the target server for each request
    public static List<Integer> findRequestTarget_Qwen(int num_servers, int[] requests) {
        // Step 1: Initialize the serverRequests array to keep track of request counts
        int[] serverRequests = new int[num_servers];
        List<Integer> result = new ArrayList<>();

        // Step 2: Process each request
        for (int request : requests) {
            int minRequests = Integer.MAX_VALUE; // To track the minimum number of requests
            int assignedServer = -1; // To store the ID of the server to assign the request

            // Step 3: Find the server with the minimum requests in the range [0, request]
            for (int i = 0; i <= request; i++) {
                if (serverRequests[i] < minRequests) {
                    minRequests = serverRequests[i]; // Update the minimum requests
                    assignedServer = i; // Update the assigned server
                }
            }

            // Step 4: Assign the request to the chosen server
            serverRequests[assignedServer]++;
            result.add(assignedServer); // Add the assigned server ID to the result
        }

        return result;
    }
    
    // Perplexity 
    /**
     * Implements a load-balancing algorithm for server requests based on IP hash values.
     * 
     * @param num_servers The number of servers available
     * @param requests An array of IP hash values for each request
     * @return An array indicating which server each request is assigned to
     */
    public static int[] findRequestTarget_Perplexity (int num_servers, int[] requests) {
        // Edge case handling
        if (requests == null || requests.length == 0) {
            return new int[0];
        }
        if (num_servers <= 0) {
            return new int[requests.length]; // Return array of zeros for invalid servers
        }
        
        int[] serverAssignments = new int[requests.length]; // To store final assignments
        int[] serverLoads = new int[num_servers];           // To track load on each server
        
        // Process each request
        for (int i = 0; i < requests.length; i++) {
            int ipHash = requests[i];
            int serversToConsider = Math.min(ipHash, num_servers);
            
            // Find server with minimum load among the first 'serversToConsider' servers
            int minLoadServerId = 0;
            int minLoad = serverLoads[0];
            
            for (int j = 1; j < serversToConsider; j++) {
                if (serverLoads[j] < minLoad) {
                    minLoad = serverLoads[j];
                    minLoadServerId = j;
                }
            }
            
            // Assign request to the selected server
            serverAssignments[i] = minLoadServerId;
            serverLoads[minLoadServerId]++;
        }
        
        return serverAssignments;
    }

    // Chat GPT
    public static List<Integer> findRequestTarget_ChatGPT(int numServers, int[] requests) {
        int[] serverRequests = new int[numServers]; // holds count of requests for each server
        List<Integer> result = new ArrayList<>();

        for (int ipHash : requests) {
            int minRequests = Integer.MAX_VALUE;
            int selectedServer = -1;

            // Check servers from 0 to ipHash
            for (int i = 0; i <= ipHash; i++) {
                if (serverRequests[i] < minRequests) {
                    minRequests = serverRequests[i];
                    selectedServer = i;
                }
            }

            // Assign the request
            serverRequests[selectedServer]++;
            result.add(selectedServer);
        }

        return result;
    }
    
    
}


/*

 ### Problem Description

The developers at Amazon are working on a prototype for a simple load-balancing algorithm. 
There are `num_servers` servers numbered from 0 to `num_servers - 1`, and initially, 
the number of requests assigned to each server is 0.

In the i-th second, a request comes from IP hash of `request[i]`. This request must 
be assigned to the server with the minimum number of requests among the servers from 
index 0 to `request[i]`. 

For example, if request[i] = 4, the request must be assigned 
to the server with the minimum number of requests among the servers with IDs 
[0, 1, 2, 3, 4]. If there are multiple servers with the same minimum number of requests, 
choose the one with the minimum ID. When a request is assigned to a server, its number 
of requests increases by 1.

Given num_servers and the array request[], for each request, find the ID of the server it is assigned to.

---

### Examples

#### Example 1
**Input:**
- `num_servers = 5`
- `requests = [3, 2, 3, 2, 4]`

**Output:**
```
[0, 1, 2, 0, 3]

### Table with Example Details

The table provided in the images details how requests are processed step by step. Here is the extracted information:

#### Table: Request Processing Details

|Req| Server          | Req Allocation | Assigned to | **Remarks** |
|---|-----------------|----------------|-------------|-------------|
| 3 | [0, 0, 0, 0, 0] | [0, 0, 0, 0]   | 0           | The request must be assigned to the server with 
														the minimum number of requests among the first 
														3 servers. Since all the first three servers have 
														0 requests assigned, it is assigned to the one 
														with the minimum ID, i.e., the server with ID 0. |
														
| 2 | [1, 0, 0, 0, 0] | [1, 0, 0]      | 1           | The request must be assigned to the server with the 
														minimum number of requests among the first 2 servers. 
														Since server 1 has 0 requests assigned, which is less 
														than server 0 with 1 request, it is assigned to server 1. |
														
| 3 | [1, 1, 0, 0, 0] | [1, 1, 0]      | 2           | Amongst the first 3 servers, the one with the minimum requests is server 2. |
| 2 | [1, 1, 1, 0, 0] | [1, 1, 1]      | 0           | Both of the first two servers have the same number of 
														requests assigned. Hence, the request is assigned to 
														server 0 as it has the minimum ID. |
| 4 | [2, 1, 1, 0, 0] | [2, 1, 1, 0]   | 3           | The request must be assigned to the server with the 
														minimum number of requests among the first 4 servers. 
														The server with the minimum requests is server 3. |

---

This table provides a detailed breakdown of how each request is processed and assigned 
to a server based on the load-balancing algorithm described in the problem. Let me know 
if you need further assistance!
```

**Explanation:**
1. **Request 1 (`request[0] = 3`):**
   - Servers considered: `[0, 1, 2, 3]`
   - Requests assigned: `[0, 0, 0, 0]`
   - Assigned to server 0 (minimum ID).
   - Updated requests: `[1, 0, 0, 0]`

2. **Request 2 (`request[1] = 2`):**
   - Servers considered: `[0, 1, 2]`
   - Requests assigned: `[1, 0, 0]`
   - Assigned to server 1 (minimum ID).
   - Updated requests: `[1, 1, 0]`

3. **Request 3 (`request[2] = 3`):**
   - Servers considered: `[0, 1, 2, 3]`
   - Requests assigned: `[1, 1, 0, 0]`
   - Assigned to server 2 (minimum ID).
   - Updated requests: `[1, 1, 1, 0]`

4. **Request 4 (`request[3] = 2`):**
   - Servers considered: `[0, 1, 2]`
   - Requests assigned: `[1, 1, 1]`
   - Both servers 0 and 1 have the same number of requests. Assigned to server 0 (minimum ID).
   - Updated requests: `[2, 1, 1]`

5. **Request 5 (`request[4] = 4`):**
   - Servers considered: `[0, 1, 2, 3, 4]`
   - Requests assigned: `[2, 1, 1, 0]`
   - Assigned to server 3 (minimum ID).
   - Updated requests: `[2, 1, 1, 1]`

Final output: `[0, 1, 2, 0, 3]`

---

#### Example 2
**Input:**
- `num_servers = 5`
- `requests = [0, 1, 2, 3]`

**Output:**
```
[0, 1, 2, 3]
```

**Explanation:**
1. **Request 1 (`request[0] = 0`):**
   - Servers considered: `[0]`
   - Requests assigned: `[0]`
   - Assigned to server 0.
   - Updated requests: `[1]`

2. **Request 2 (`request[1] = 1`):**
   - Servers considered: `[0, 1]`
   - Requests assigned: `[1, 0]`
   - Assigned to server 1.
   - Updated requests: `[1, 1]`

3. **Request 3 (`request[2] = 2`):**
   - Servers considered: `[0, 1, 2]`
   - Requests assigned: `[1, 1, 0]`
   - Assigned to server 2.
   - Updated requests: `[1, 1, 1]`

4. **Request 4 (`request[3] = 3`):**
   - Servers considered: `[0, 1, 2, 3]`
   - Requests assigned: `[1, 1, 1, 0]`
   - Assigned to server 3.
   - Updated requests: `[1, 1, 1, 1]`

Final output: `[0, 1, 2, 3]`

---

### Function Description

Complete the function `findRequestTarget` in the editor below.

```python
def findRequestTarget(num_servers, requests):
    # Implementation goes here
    pass
```

**Parameters:**
- `int num_servers`: The number of servers.
- `int requests[n]`: The sizes of the requests.

**Returns:**
- `int[n]`: The IDs of the servers each request is assigned to.

---

### Constraints
- \( 1 \leq \text{num\_servers} \leq 10^5 \)
- \( 0 \leq \text{requests}[i] < \text{num\_servers} \)

---

### Sample Input/Output

#### Sample Case 0
**Input:**
```
num_servers = 5
requests = [4, 8, 6, 2]
```

**Output:**
```
[0, 1, 2, 0]
```

#### Sample Case 1
**Input:**
```
num_servers = 5
requests = [0, 1, 2, 3]
```

**Output:**
```
[0, 1, 2, 3]
```

---

This completes the problem description and examples. Let me know if you need further clarification!
 
 
 */
