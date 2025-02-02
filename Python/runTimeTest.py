from datetime import datetime
import random

startTime = datetime.now()
testNum = 10000000
total = 0
for i in range(testNum):
    rand = random.randint(0, 9)
    if rand > 4:
        total += rand
print('total =', total)
endTime = datetime.now()
print('startTime =', startTime)
print('endTime =', endTime)
print('timeDiff =', endTime - startTime)