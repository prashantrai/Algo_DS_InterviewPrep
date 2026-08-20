package Oracle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class TransformApiOrdersToUniqueList {

	public static void main(String[] args) {
        // Build the sample input from the problem statement.
        User u1 = new User();
        u1.id = "u1";
        u1.name = "Alice";
        Order o1 = new Order(); o1.id = "o1"; o1.amount = 25;
        Order o2a = new Order(); o2a.id = "o2"; o2a.amount = 50;
        Order o2b = new Order(); o2b.id = "o2"; o2b.amount = 50; // duplicate from same user
        u1.orders = Arrays.asList(o1, o2a, o2b);

        User u2 = new User();
        u2.id = "u2";
        u2.name = "Bob";
        Order o2c = new Order(); o2c.id = "o2"; o2c.amount = 999; // should NOT overwrite amount 50
        Order o3 = new Order(); o3.id = "o3"; o3.amount = 10;
        Order missingAmount = new Order(); missingAmount.id = null; missingAmount.amount = 20; // missing id -> ignore
        Order emptyId = new Order(); emptyId.id = ""; emptyId.amount = 5; // empty id -> ignore
        u2.orders = Arrays.asList(o2c, o3, missingAmount, emptyId);

        List<User> users = Arrays.asList(u1, u2);

        List<OrderRecord> result = transform(users);
        System.out.println("Result:");
        for (OrderRecord r : result) System.out.println("  " + r);

        // Edge case: user with null orders.
        User u3 = new User();
        u3.id = "u3";
        u3.orders = null;
        System.out.println("\nEdge case (null orders): " + transform(Arrays.asList(u3)));

        // Edge case: null users list.
        System.out.println("Edge case (null users list): " + transform(null));

        // Edge case: user with missing/empty id.
        User u4 = new User();
        u4.id = "";
        Order o4 = new Order(); o4.id = "o4"; o4.amount = 40;
        u4.orders = Arrays.asList(o4);
        System.out.println("Edge case (empty user id): " + transform(Arrays.asList(u4)));
    }
	
	
	// Solution starts here....
	
	// Time:  Time Complexity: O(N), where N = total number of Order objects across all users combined.
	// Space: O(N), where N is again bounded by the total input size.
	
	static class User {
        String id;          // Added: needed to populate userIds in the output
        String name;
        List<Order> orders;
    }
    static class Order {
        String id;
        int amount;
    }
	
	// Represents one deduplicated order in the final output.
    static class OrderRecord {
        String orderId;
        long amount;
        // LinkedHashSet preserves first-seen insertion order of users
        // AND automatically prevents duplicate user IDs within this order.
        LinkedHashSet<String> userIds = new LinkedHashSet<>();

        OrderRecord(String orderId, long amount) {
            this.orderId = orderId;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "{orderId: " + orderId + ", amount: " + amount + ", userIds: " + userIds + "}";
        }
    }

    static List<OrderRecord> transform(List<User> users) {
        // LinkedHashMap keyed by orderId gives O(1) lookup while preserving
        // the order in which each unique orderId was first encountered.
        LinkedHashMap<String, OrderRecord> result = new LinkedHashMap<>();

        // With typed input, "users" itself can't be malformed like a raw JSON blob could —
        // but the caller could still pass null, so guard against that.
        if (users == null) {
            return new ArrayList<>();
        }

        for (User user : users) {
            if (user == null) continue; // defensive: skip null entries in the list

            // Rule: ignore users where "id" is missing/blank — can't attribute their
            // orders to anyone without a valid user id.
            if (user.id == null || user.id.isEmpty()) {
                continue;
            }

            // Rule: ignore users where "orders" is missing (null) — the type system
            // already guarantees it's a List when present, so we only need a null check.
            if (user.orders == null) {
                continue;
            }

            for (Order order : user.orders) {
                if (order == null) continue; // defensive: skip null entries in the list

                // Rule: ignore orders without a non-empty string id.
                // Type is already enforced as String by the class, so only null/empty remain to check.
                if (order.id == null || order.id.isEmpty()) {
                    continue;
                }

                // First time seeing this orderId: create the record with this amount.
                // Later appearances must NOT overwrite the amount —
                // "retain the first valid amount" per the rules.
                result.computeIfAbsent(order.id, id -> new OrderRecord(id, order.amount));

                // Adding to a LinkedHashSet is safe even if userId was already added —
                // it silently no-ops on duplicates while preserving first-seen order.
                result.get(order.id).userIds.add(user.id);
            }
        }

        return new ArrayList<>(result.values());
    }
    
    
    
    
    // using instance of, - avoid in interview
    static List<OrderRecord> transform2(Map<String, Object> response) {
        // LinkedHashMap keyed by orderId gives O(1) lookup while preserving
        // the order in which each unique orderId was first encountered.
        LinkedHashMap<String, OrderRecord> result = new LinkedHashMap<>();

        Object usersObj = response.get("users");
        // If "users" itself is missing or not a list, there's nothing to process.
        if (!(usersObj instanceof List)) {
            return new ArrayList<>();
        }

        for (Object userObj : (List<?>) usersObj) {
            if (!(userObj instanceof Map)) continue; // malformed user entry, skip
            Map<?, ?> user = (Map<?, ?>) userObj;

            Object userIdObj = user.get("id");
            // A user without a valid string ID can't be attributed to any order,
            // so there's no meaningful way to include their orders.
            if (!(userIdObj instanceof String) || ((String) userIdObj).isEmpty()) {
                continue;
            }
            String userId = (String) userIdObj;

            Object ordersObj = user.get("orders");
            // Rule: ignore users where "orders" is missing or not a list —
            // this must be checked per-user, not globally.
            if (!(ordersObj instanceof List)) {
                continue;
            }

            for (Object orderObj : (List<?>) ordersObj) {
                if (!(orderObj instanceof Map)) continue; // malformed order entry
                Map<?, ?> order = (Map<?, ?>) orderObj;

                Object idObj = order.get("id");
                // Rule: ignore orders without a non-empty string id.
                // This covers missing id, empty string, None/null, and non-string types (e.g. int 123).
                if (!(idObj instanceof String) || ((String) idObj).isEmpty()) {
                    continue;
                }
                String orderId = (String) idObj;

                Object amountObj = order.get("amount");
                long amount = (amountObj instanceof Number) ? ((Number) amountObj).longValue() : 0L;

                // First time seeing this orderId: create the record with this amount.
                // Subsequent appearances must NOT overwrite the amount —
                // "retain the first valid amount" per the rules.
                result.computeIfAbsent(orderId, id -> new OrderRecord(id, amount));

                // Adding to a LinkedHashSet is safe even if userId was already added —
                // it silently no-ops on duplicates while preserving first-seen order.
                result.get(orderId).userIds.add(userId);
            }
        }

        return new ArrayList<>(result.values());
    }
	

}

/*
Here's the problem as it appears in the screenshot:

---

**Transform API Orders to Unique List**

You receive an API response containing users and their orders. Transform it into a 
list of unique orders. Each output order must include:

- `orderId`
- `amount`
- `userIds`: unique users who referenced that order, in first-seen order

**Rules:**

- Ignore users where `orders` is missing or is not a list.
- Ignore orders without a non-empty string `id`.
- Deduplicate orders by `id`.
- Preserve the order in which each unique order first appears.
- If the same order ID appears with different amounts, retain the first valid amount.
- Do not duplicate a user ID within an order's `userIds`.

**Use this version without invalid users:**

```python
response = {
    "users": [
        {
            "id": "u1",
            "name": "Alice",
            "orders": [
                {"id": "o1", "amount": 25},
                {"id": "o2", "amount": 50},
                {"id": "o2", "amount": 50},  # Duplicate from same user
            ],
        },
        {
            "id": "u2",
            "name": "Bob",
            "orders": [
                {"id": "o2", "amount": 999},  # Keep first amount: 50
                {"id": "o3", "amount": 10},
                {"amount": 20},               # Missing ID - ignore
                {"id": ""},                   # Empty ID - ignore
                {"id": None},                 # Invalid ID - ignore
                {"id": 123, "amount": 5},      # Non-string ID - ignore
            ],
        },
    ]
}
```

**Expected result:**

```python
expected = [
    {"orderId": "o1", "amount": 25, "userIds": ["u1"]},
    {"orderId": "o2", "amount": 50, "userIds": ["u1", "u2"]},
    {"orderId": "o3", "amount": 10, "userIds": ["u2"]},
]
```
 
 
 * */
