package Google;

public class HighwayTollFromCheckpointLogs {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}


/* C10. Highway Toll From Checkpoint Logs | Priority: VERY HIGH
	
	There are ordered highway checkpoints.
	
	Cost of traveling between checkpoint i and i+1 is:
	
	cost[i]
	
	Input records:
	
	(vehicleId, checkpoint, timestamp, ENTER/EXIT)
	
	Compute total toll per vehicle.
	
	Example:
	
	cost = [3,5,2,4]
	
	ABC enters checkpoint 1
	ABC exits checkpoint 4
	
	Cost:
	
	5 + 2 + 4 = 11
	
	Support interleaved vehicle records and multiple trips.
	
	Follow-ups
	
	Records aren't sorted.
	
	Missing records.
	
	Vehicles can travel either direction.
	
	Huge event stream.
	
	Closest:
	
	1396 Design Underground System, plus prefix sums.
 */