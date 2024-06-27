package CART;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class WorkHoursRegisterM {
	
	
	// Level 1 Tests - all passed
	public static void test_Level_1() {
		WorkHoursRegisterM register = new WorkHoursRegisterM();

		/** Level 1 */
        System.out.println(" ---- Level 1 ---- \n");
        
        System.out.println(register.addWorker("Ashley", "Middle Developer", 150)); // true
        System.out.println(register.addWorker("Ashley", "Junior Developer", 100)); // false

        System.out.println(register.register("Ashley", 10)); // registered
        System.out.println(register.register("Ashley", 25)); // registered
        System.out.println(register.get("Ashley")); // 15

        System.out.println(register.register("Ashley", 40)); // registered
        System.out.println(register.register("Ashley", 67)); // registered
        System.out.println(register.register("Ashley", 100)); // registered
        System.out.println(register.get("Ashley")); // 42

        System.out.println(register.get("Walter")); // null
        System.out.println(register.register("Walter", 120)); // invalid request
        
	}

	// Level 2 Tests - all passed
	public static void test_Level_2() {
		WorkHoursRegisterM register = new WorkHoursRegisterM();

		/** Level 2 */
        System.out.println("\n\n---- Level 2 ---- \n");
        
        System.out.println(register.addWorker("John", "Junior Developer", 120)); // true
        System.out.println(register.addWorker("Jason", "Junior Developer", 120)); // true
        System.out.println(register.addWorker("Ashley", "Junior Developer", 120)); // true

        System.out.println(register.register("John", 100)); // registered
        System.out.println(register.register("John", 150)); // registered
        System.out.println(register.register("Jason", 200)); // registered
        System.out.println(register.register("Jason", 250)); // registered
        System.out.println(register.register("Jason", 275)); // registered

        System.out.println(register.topNWorkers(5, "Junior Developer")); // ["Jason (50)", "John (50)", "Ashley (0)"]

        System.out.println(register.register("Ashley", 400)); // registered
        System.out.println(register.register("Ashley", 500)); // registered
        System.out.println(register.register("Jason", 575)); // registered

        System.out.println(register.topNWorkers(3, "Junior Developer")); // ["Jason (350)", "Ashley (100)", "John (50)"]

        System.out.println(register.topNWorkers(3, "Middle Developer")); // []

        
	}

	// Level 3 Tests - calSalary is failing	
	public static void test_Level_3() {
		WorkHoursRegisterM register = new WorkHoursRegisterM();

		/** Level 3 */
        System.out.println("\n\n---- Level 3 ---- \n");
        System.out.println(register.addWorker("John", "Middle Developer", 200)); // true - pass
        System.out.println(register.register("John", 100)); // registered - pass
        System.out.println(register.register("John", 125)); // registered - pass

        System.out.println(register.promote("John", "Senior Developer", 500, 200)); // Success - pass

        System.out.println(register.register("John", 150)); // registered - pass
        
        
        System.out.println("Expected: invalid request, Actual: " +  
        		register.promote("John", "Senior Developer", 350, 250)); // invalid request -- pass

        System.out.println(register.register("John", 300)); // registered - pass
        System.out.println(register.register("John", 325)); // registered - pass

        System.out.println(register.calcSalary("John", 0, 500)); // 35000 

        System.out.println(register.topNWorkers(3, "Senior Developer")); // ["John (0)"] - pass

        System.out.println(register.register("John", 400)); // registered - pass

        System.out.println(register.get("John")); // 250  - pass

        System.out.println(register.topNWorkers(10, "Senior Developer")); // ["John (75)"] - pass

        System.out.println(register.topNWorkers(10, "Middle Developer")); // [] - pass

        System.out.println(register.calcSalary("John", 110, 350)); // 45500

        System.out.println(register.calcSalary("John", 900, 1400)); // 0
        
	}

	
	public static void main(String[] args) {
		test_Level_1();
		test_Level_2();
		test_Level_3();
    }
	
	
    Map<String, Worker> workers;

    public WorkHoursRegisterM() {
        this.workers = new HashMap<>();
    }

    public boolean addWorker(String workerId, String position, int compensation) {
        if (workers.containsKey(workerId)) {
            return false; // Worker already exists
        }
        workers.put(workerId, new Worker(workerId, position, compensation));
        return true;
    }

    public String register(String workerId, int timestamp) {
        if (!workers.containsKey(workerId)) {
            return "invalid request"; // Worker not found
        }

        Worker worker = workers.get(workerId);
        List<WorkSession> workSessions = worker.getWorkSessions();
        int size = workSessions.size();

        if (size == 0 || workSessions.get(size - 1).getEndTime() != -1) {
            // Worker is entering the office
            workSessions.add(new WorkSession(timestamp, -1));
            return "registered";
        } else {
            // Worker is leaving the office
            workSessions.get(size - 1).endTime = timestamp;
            return "registered";
        }
    }

    /* previous code
    public Integer get(String workerId) {
        if (!workers.containsKey(workerId)) {
            return null; // Worker not found
        }

        Worker worker = workers.get(workerId);
        List<WorkSession> workSessions = worker.getWorkSessions();
        int totalWorkTime = 0;

        for (WorkSession session : workSessions) {
            if (session.getEndTime() != -1) {
                totalWorkTime += session.getEndTime() - session.getStartTime();
            }
        }

        return totalWorkTime;
    }
    */
    
    // looks good, all related tests are passing
    public Integer get(String workerId) {
        if (!workers.containsKey(workerId)) {
            return null; // Worker not found
        }

        Worker worker = workers.get(workerId);
        return worker.getTotalTimeInOffice();
    }
    
    /** Level 2 */
    
    /*
    public List<String> topNWorkers(int n, String position) {
        List<String> result = new ArrayList<>();

        // Collect workers with the given position and calculate their total time in office
        List<Worker> filteredWorkers = new ArrayList<>();
        for (Worker worker : workers.values()) {
            if (worker.getPosition().equals(position)) {
                filteredWorkers.add(worker);
            }
        }

        // Sort workers by total time in office (descending), and by workerId (ascending) in case of tie
        Collections.sort(filteredWorkers, (w1, w2) -> {
            int totalTime1 = w1.getTotalTimeInOffice();
            int totalTime2 = w2.getTotalTimeInOffice();

            if (totalTime1 != totalTime2) {
                return Integer.compare(totalTime2, totalTime1); // descending order by time
            } else {
                return w1.getWorkerId().compareTo(w2.getWorkerId()); // ascending order by workerId
            }
        });

        // Prepare the result list with formatted strings
        for (int i = 0; i < Math.min(n, filteredWorkers.size()); i++) {
            Worker worker = filteredWorkers.get(i);
            int totalTime = worker.getTotalTimeInOffice();
            result.add(worker.getWorkerId() + " (" + totalTime + ")");
        }

        return result;
    }*/

    public List<String> topNWorkers(int n, String position) {
        List<String> result = new ArrayList<>();

        List<Worker> filteredWorkers = new ArrayList<>();
        for (Worker worker : workers.values()) {
            if (worker.getPosition().equals(position)) {
                filteredWorkers.add(worker);
            }
        }

        Collections.sort(filteredWorkers, (w1, w2) -> {
            int totalTime1 = w1.getTotalTimeInOfficeForCurrentPosition();
            int totalTime2 = w2.getTotalTimeInOfficeForCurrentPosition();

            if (totalTime1 != totalTime2) {
                return Integer.compare(totalTime2, totalTime1);
            } else {
                return w1.getWorkerId().compareTo(w2.getWorkerId());
            }
        });

        for (int i = 0; i < Math.min(n, filteredWorkers.size()); i++) {
            Worker worker = filteredWorkers.get(i);
            int totalTime = worker.getTotalTimeInOfficeForCurrentPosition();
            result.add(worker.getWorkerId() + " (" + totalTime + ")");
        }

        return result;
    }

    
    /** Level 3 */
    private void applyPromotionIfNeeded(Worker worker, int timestamp) {
        for (Promotion promotion : worker.getPromotions()) {
            if (promotion.getStartTimestamp() <= timestamp) {
                worker.position = promotion.getNewPosition();
                worker.currentCompensation = promotion.getNewCompensation();
            }
        }
    }

    /** Level 3 */
    public String promote(String workerId, String newPosition, int newCompensation, int startTimestamp) {
        if (!workers.containsKey(workerId)) {
            return "invalid request"; // Worker not found
        }

        Worker worker = workers.get(workerId);

        // Check if there are already sessions registered after or at startTimestamp
        for (Promotion promotion : worker.getPromotions()) {
            if (promotion.getStartTimestamp() <= startTimestamp) {
                return "invalid request"; // There are sessions already registered
            }
        }

        // Register the promotion
        worker.promote(newPosition, newCompensation, startTimestamp);
        return "Success";
    }


    /** Level 3 */
    public Integer calcSalary(String workerId, int startTimestamp, int endTimestamp) {
        if (!workers.containsKey(workerId)) {
            return null; // Worker not found
        }

        Worker worker = workers.get(workerId);
        List<WorkSession> workSessions = worker.getWorkSessions();
        int totalSalary = 0;

        for (WorkSession session : workSessions) {
            int sessionStartTime = session.getStartTime();
            int sessionEndTime = session.getEndTime();

            // Ensure session is within the specified time range
            if (sessionEndTime != -1 && sessionStartTime <= endTimestamp && sessionEndTime >= startTimestamp) {
                int sessionDuration = Math.max(0, Math.min(sessionEndTime, endTimestamp) - Math.max(sessionStartTime, startTimestamp));
                int compensation = getCompensationForPeriod(worker, sessionStartTime);

                totalSalary += sessionDuration * compensation;
            }
        }

        return totalSalary;
    }


    /** Level 3 */
    // Helper method to get compensation for a given period based on promotions
    private int getCompensationForPeriod(Worker worker, int timestamp) {
        for (Promotion promotion : worker.getPromotions()) {
            if (promotion.getStartTimestamp() <= timestamp) {
                return promotion.getNewCompensation();
            }
        }

        // Default compensation if no promotion found
        return worker.getCurrentCompensation();
    }
    
    
}


class Worker {
    String workerId;
    String position;
    int currentCompensation;
    List<WorkSession> workSessions;
    List<Promotion> promotions;  // Level 3


    public Worker(String workerId, String position, int compensation) {
        this.workerId = workerId;
        this.position = position;
        this.currentCompensation = compensation;
        this.workSessions = new ArrayList<>();
        this.promotions = new ArrayList<>();  // Level 3
    }

    public String getWorkerId() {
        return workerId;
    }
    
    public String getPosition() {
        return position;
    }

    public int getCurrentCompensation() {
        return currentCompensation;
    }

    public void addWorkSession(WorkSession session) {
        workSessions.add(session);
    }

    public List<WorkSession> getWorkSessions() {
        return workSessions;
    }
    
    // Level 3
    public List<Promotion> getPromotions() {
        return promotions;
    }
    
    // Level 3
    public void promote(String newPosition, int newCompensation, int startTimestamp) {
        promotions.add(new Promotion(newPosition, newCompensation, startTimestamp));
        position = newPosition;
        currentCompensation = newCompensation;
    }
    
    
    // Level 2
    public int getTotalTimeInOffice() {
        int totalWorkTime = 0;
        for (WorkSession session : workSessions) {
            if (session.getEndTime() != -1) {
                totalWorkTime += session.getEndTime() - session.getStartTime();
            }
        }
        return totalWorkTime;
    }
    
    // Level 3
    // New method to calculate total time in office for a specific position
    public int getTotalTimeInOfficeForCurrentPosition() {
        int totalWorkTime = 0;
        for (WorkSession session : workSessions) {
            if (session.getEndTime() != -1 && isSessionInCurrentPosition(session)) {
                totalWorkTime += session.getEndTime() - session.getStartTime();
            }
        }
        return totalWorkTime;
    }
    
    private boolean isSessionInCurrentPosition(WorkSession session) {
        // Determine if a work session is within the current position based on promotions
        int sessionStart = session.getStartTime();
        int sessionEnd = session.getEndTime();

        for (Promotion promotion : promotions) {
            if (promotion.getNewPosition().equals(this.position) 
            		&& sessionStart >= promotion.getStartTimestamp()) {
                return true;
            }
        }

        // Default to true if there are no promotions affecting the current position
        return promotions.isEmpty();
    }
    
}

class WorkSession {
    int startTime;
    int endTime;

    public WorkSession(int startTime, int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }
}


// Level 3
class Promotion {
    String newPosition;
    int newCompensation;
    int startTimestamp;

    public Promotion(String newPosition, int newCompensation, int startTimestamp) {
        this.newPosition = newPosition;
        this.newCompensation = newCompensation;
        this.startTimestamp = startTimestamp;
    }

    public String getNewPosition() {
        return newPosition;
    }

    public int getNewCompensation() {
        return newCompensation;
    }

    public int getStartTimestamp() {
        return startTimestamp;
    }
}
