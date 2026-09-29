package Google.extremelyHigh;
import java.util.PriorityQueue;

public class JobScheduler {

	/* Problem: 
	 * Job Processing Schedule with priority. Higher-priority jobs get 
	 * processed first. Asked to implement a class with two APIs, 
	 * addJob and getJob. Follow-up: add a delay to each job — for example, 
	 * after job A is processed, it can't be processed again until at least 
	 * 3 getJob API invocations have happened.
	 
	 * */
	
	/*
	 Thought Process
		Initially, I can keep all jobs in a max-heap ordered by priority.
		The follow-up changes that because the highest-priority job may temporarily be unavailable. Scanning past unavailable jobs would make getJob() potentially O(n).
		Instead, I maintain two heaps. The ready heap answers the priority question, while the cooldown heap answers the time/eligibility question.
		That keeps both concerns separate and gives us logarithmic operations.
		6. Interview Explanation Before Coding
		“I’ll maintain two priority queues.
		The first is a max-heap containing jobs that are currently eligible, ordered by priority.
		The second is a min-heap containing jobs in cooldown, ordered by the getJob invocation when they become eligible again.
		I’ll also maintain a global getJob call counter.
		Every time getJob() is called, I increment that counter and move any jobs whose cooldown has expired from the cooldown heap back into the ready heap.
		Then, if the ready heap is empty, I return null. Otherwise I remove the highest-priority job, return its ID, calculate its next eligible invocation, and move it into the cooldown heap.
		This avoids scanning unavailable jobs and keeps each heap operation logarithmic.”
		
		7. Complexity Analysis Before Coding
		Let:
		n = number of jobs
		
		addJob()
		Time: O(log n)
		Space: O(1) additional per operation
		
		We insert the job into the ready priority queue.
		getJob()
		Normally:
		O(log n)
		
		because we remove one ready job and insert it into the cooldown heap.
		A call may also move several expired jobs from cooldown back to ready.
		Therefore one individual call can technically be:
		O(k log n)
		
		where k jobs become eligible on that call.
		But each transition corresponds to a job previously processed, so operations remain efficient amortized.
		Total Space
		O(n)
		
		Every job exists in exactly one of the two heaps.
		8. Step-by-Step Algorithm
		1. Maintain getCallCount.
		2. Keep ready jobs in a max-heap ordered by priority.
		3. Keep cooling jobs in a min-heap ordered by nextEligibleCall.
		4. addJob() inserts a new job into the ready heap.
		5. On every getJob():
		   1. Increment getCallCount.
		   2. Move expired cooldown jobs into the ready heap.
		   3. If ready heap is empty, return null.
		   4. Remove highest-priority job.
		   5. Calculate when it can next run.
		   6. Put it into the cooldown heap.
		   7. Return its ID.
	 * */
	
	
    static class Job {
        String id;
        int priority;
        int delay;
        long nextEligibleCall;
        long sequence;

        Job(String id, int priority, int delay, long sequence) {
            this.id = id;
            this.priority = priority;
            this.delay = delay;
            this.sequence = sequence;
        }
    }

    // Higher priority comes first.
    // If priorities are equal, preserve insertion order.
    private PriorityQueue<Job> readyJobs =
            new PriorityQueue<>((a, b) -> {
                if (a.priority != b.priority) {
                    return Integer.compare(b.priority, a.priority);
                }

                return Long.compare(a.sequence, b.sequence);
            });

    // Job that becomes eligible first comes first.
    private PriorityQueue<Job> coolingJobs =
            new PriorityQueue<>((a, b) -> {
                if (a.nextEligibleCall != b.nextEligibleCall) {
                    return Long.compare(a.nextEligibleCall,
                                        b.nextEligibleCall);
                }

                return Long.compare(a.sequence, b.sequence);
            });

    private long getCallCount = 0;
    private long sequence = 0;


    /*
     * Interview:
     * Add a job directly into the ready queue because a newly added
     * job has never been processed and therefore has no cooldown.
     */
    public void addJob(String id, int priority, int delay) {

        if (id == null || delay < 0) {
            throw new IllegalArgumentException("Invalid job");
        }

        readyJobs.offer(
                new Job(id, priority, delay, sequence++)
        );
    }


    /*
     * Interview:
     * Every getJob invocation counts toward cooldown expiration,
     * even if there is currently no runnable job.
     */
    public String getJob() {

        getCallCount++;


        /*
         * Interview:
         * First move every job whose cooldown has expired
         * back into the ready priority queue.
         */
        while (!coolingJobs.isEmpty()
                && coolingJobs.peek().nextEligibleCall <= getCallCount) {

            readyJobs.offer(coolingJobs.poll());
        }


        /*
         * Interview:
         * There may be jobs in the system but all of them
         * could currently be cooling down.
         */
        if (readyJobs.isEmpty()) {
            return null;
        }


        /*
         * Interview:
         * Pick the highest-priority currently runnable job.
         */
        Job job = readyJobs.poll();


        /*
         * Interview:
         * If A runs on call 1 with delay = 3,
         *
         * calls 2, 3 and 4 must happen before A can run again.
         *
         * Therefore:
         * nextEligibleCall = currentCall + delay + 1
         *
         * A becomes eligible again on call 5.
         */
        job.nextEligibleCall =
                getCallCount + job.delay + 1;

        coolingJobs.offer(job);

        return job.id;
    }


    public static void main(String[] args) {

        /*
         * Test 1 — Priority ordering
         */
        JobScheduler scheduler1 = new JobScheduler();

        scheduler1.addJob("A", 10, 0);
        scheduler1.addJob("B", 5, 0);

        System.out.println(
                "Test 1: expected A, actual "
                        + scheduler1.getJob()
        );


        /*
         * Test 2 — Basic cooldown
         *
         * A executes on call 1.
         * delay = 3.
         *
         * Calls 2,3,4 must happen before A becomes eligible.
         */
        JobScheduler scheduler2 = new JobScheduler();

        scheduler2.addJob("A", 10, 3);

        System.out.println(
                "Test 2.1: expected A, actual "
                        + scheduler2.getJob()
        );

        System.out.println(
                "Test 2.2: expected null, actual "
                        + scheduler2.getJob()
        );

        System.out.println(
                "Test 2.3: expected null, actual "
                        + scheduler2.getJob()
        );

        System.out.println(
                "Test 2.4: expected null, actual "
                        + scheduler2.getJob()
        );

        System.out.println(
                "Test 2.5: expected A, actual "
                        + scheduler2.getJob()
        );


        /*
         * Test 3 — Lower-priority job runs while
         * higher-priority job is cooling down.
         */
        JobScheduler scheduler3 = new JobScheduler();

        scheduler3.addJob("A", 10, 2);
        scheduler3.addJob("B", 5, 0);

        System.out.println(
                "Test 3.1: expected A, actual "
                        + scheduler3.getJob()
        );

        System.out.println(
                "Test 3.2: expected B, actual "
                        + scheduler3.getJob()
        );


        /*
         * Test 4 — Equal priorities preserve insertion order
         */
        JobScheduler scheduler4 = new JobScheduler();

        scheduler4.addJob("A", 10, 5);
        scheduler4.addJob("B", 10, 5);

        System.out.println(
                "Test 4.1: expected A, actual "
                        + scheduler4.getJob()
        );

        System.out.println(
                "Test 4.2: expected B, actual "
                        + scheduler4.getJob()
        );


        /*
         * Test 5 — Empty scheduler
         */
        JobScheduler scheduler5 = new JobScheduler();

        System.out.println(
                "Test 5: expected null, actual "
                        + scheduler5.getJob()
        );


        /*
         * Test 6 — Invalid input
         */
        try {
            JobScheduler scheduler6 = new JobScheduler();
            scheduler6.addJob(null, 10, 3);

            System.out.println(
                    "Test 6: expected exception"
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Test 6: expected exception, actual exception"
            );
        }
    }
}