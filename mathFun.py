import random
import numpy as np

INCREMENT = 12

def findInvalidEndingsFor31Prob():
    invalidEndings = findInvalidEndings()
    printValidEndings(invalidEndings)
    return

def toBinary(numInt, digits):
    binary = ''
    count = 0
    while count < digits:
        count += 1
        if numInt % 2 == 0:
            binary = '0' + binary
        else:
            binary = '1' + binary
            numInt -= 1
        numInt /= 2
    return binary

def findNextPower(num):
    power = 0
    while 2**power <= num:
        power += 1
    return power

def testEnding(ending):
    startingNum = toBaseTen(ending)
    num = startingNum
    maxCount = findMaxCount(ending)
    count = 0
    while num >= startingNum and count < maxCount:
        count += 1
        num = 3 * num + 1
        while num % 2 == 0:
            num /= 2
    if num < startingNum:
        return True
    return False

def toBaseTen(binary):
    num = 0
    count = 0
    while len(binary) > count:
        num += (2**count)*int(binary[len(binary)-count-1])
        count += 1
    return num

def getNextBinary(startingNum):
    nextNum = startingNum
    if toBaseTen(startingNum)+INCREMENT >= 2**(len(startingNum)):
        nextNum = toBinary(2**findNextPower(toBaseTen(nextNum))+INCREMENT-1, len(nextNum))
        nextNum = '0' + nextNum
        if findMaxCount(nextNum) == findMaxCount(startingNum):
            nextNum = toBinary(toBaseTen(nextNum), len(nextNum)+1)
    else:
        nextNum = toBinary(toBaseTen(nextNum)+INCREMENT, len(nextNum))   
    return nextNum

def findMaxCount(ending):
    maxCount = 0
    while 2**len(ending) > 3**maxCount:
        maxCount += 1
    maxCount -= 1
    return maxCount

def findInvalidEndings():
    pct = 0.25
    startingNum = '11'
    invalidEndings = ['0','01']
    for i in range(1000):
        valid = True
        for ending in invalidEndings:
            if startingNum.endswith(ending):
                valid = False
        if valid:
            if testEnding(startingNum):
                invalidEndings.append(startingNum)
                #print(startingNum)
                pct = pct - 1/2**len(startingNum)
        startingNum = getNextBinary(startingNum)
        if i % 100 == 99:
            print(f'pct = 1 / {1/pct} at i = {i}')
            print(invalidEndings[len(invalidEndings)-1])
    return invalidEndings

def printValidEndings(invalidEndings):
    validEndings = []
    startingNum = '11'
    while len(startingNum) <= len(invalidEndings[len(invalidEndings)-1]):
        valid = True
        for ending in invalidEndings:
            if startingNum.endswith(ending):
                valid = False
        if valid:
            print(startingNum)
        nextNum = startingNum
        if toBaseTen(startingNum)+INCREMENT >= 2**(len(startingNum)):
            nextNum = toBinary(2**findNextPower(toBaseTen(nextNum))+INCREMENT-1, len(nextNum))
            nextNum = '0' + nextNum
    
        else:
            nextNum = toBinary(toBaseTen(nextNum)+INCREMENT, len(nextNum))
        startingNum = nextNum


def doubleGame():
    runs = 10000
    total_amount = 0
    for i in range(runs):
        amount = 2
        while random.random() < 0.5:
            amount *=2
        total_amount += amount
    print(f"{runs} runs, ${total_amount} earnings, average of ${total_amount / runs:.2f}")


#findInvalidEndingsFor31Prob()
doubleGame()
