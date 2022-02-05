package com.interview.questions;
#%load_ext autoreload
#%autoreload 2
%reload_ext autoreload

import os.path
from datetime import datetime

filename = "/Users/prash/Documents/PolySign/full.log"
users = __builtins__.set()
dailyUserMap = {}

noOfReq   = 0
noOfErr   = 0
noOfDays  = 0
startDate = 0
endDate   = 0

if not os.path.isfile(filename):
    print ('File does not exist.')

with open(filename) as f:    
    lines = f.readlines()
    
    
i = 0    
for line in lines:
    noOfReq += 1
    data = line.rstrip().split("\t")  #using rstrip to remove '/n' from end
  
    if len(line.strip()) < 10:
        i += 1
        continue

    #Daily user map
    logDate = data[0]
    logDate = logDate[0:logDate.index("T")]
    
    # Check for valid date
    year,month,day = logDate.split('-')
    isValidDate = True
    try :
        datetime(int(year),int(month),int(day))
    except ValueError :
        isValidDate = False
    
    if not isValidDate:
        i += 1
        continue 
    
    logDate = datetime.strptime(logDate, "%Y-%m-%d")
    if(logDate not in dailyUserMap):
        dailyUserSet = {data[1]}
        dailyUserMap[logDate] = dailyUserSet        
    else:
        dailyUserSet = dailyUserMap.get(logDate, None)
        dailyUserSet.add(data[1])
    
        
    users.add(data[1])  #adding user into a set
    if data[2] != "200":
        noOfErr += 1
    
    if(i == 0):
        startDate = data[0]
    
    if(i == len(lines)-1):
        endDate = data[0]
        
    i += 1
    
stDate = startDate[0:startDate.index("T")]
enDate = endDate[0:endDate.index("T")]
numOfDays = abs ( ((datetime.strptime(stDate, "%Y-%m-%d")) - (datetime.strptime(enDate, "%Y-%m-%d"))).days ) + 1 #including current date
    
print("Summary Metrics::")
print("\tNo of requests: %d" %noOfReq)
print("\tNo of errors: %d" %noOfErr)
print("\tNo of unique users: %d" %(len(users)))
print("\tNo of days: %d" %numOfDays)

print("Daily Metrics::")
from datetime import timedelta
startDt = datetime.strptime(stDate, "%Y-%m-%d")
endDt = datetime.strptime(enDate, "%Y-%m-%d")
while startDt <= endDt:
    usrSet = dailyUserMap.get(startDt, None)
    if(usrSet != None):
        print("\t%s\t%d" %(startDt.strftime("%Y-%m-%d"), len(usrSet)))          
    else:
        print("\t%s\t%d" %(startDt.strftime("%Y-%m-%d"), 0))
        
    startDt = startDt + timedelta(days=1)
