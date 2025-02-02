import random
import math

odds = (0.17, 0.24, 0.2625, 0.3275)
mins = (40, 120, 175, 80)
maxs = (70, 210, 650, 150)
dists = (2, 2, 2, 2)
skews = (1.5, 2, 3, 2)

def getPriceProduct():
    for j in range(3):
        numTests = 1000
        priceProduct = 1
        for test in range(numTests):
            # Determine the pattern
            randNum = random.random()
            pattern = 0
            for p in odds:
                if randNum < p:
                    break
                else:
                    randNum -= p
                    pattern += 1
            
            # Determine the value
            randNum = 0
            for i in range(dists[pattern]):
                randNum += random.random()
            randNum /= dists[pattern]
            randNum = randNum**skews[pattern]
            sellPrice = math.floor(mins[pattern] + randNum * (maxs[pattern] - mins[pattern]))
            priceProduct = priceProduct * (sellPrice / 110)
        
        print(priceProduct**(1/numTests))

def getDistCounts():
    counts = [0] * 100
    for i in range(1000):
        pattern = 3
        randNum = 0
        for i in range(dists[pattern]):
            randNum += random.random()
        randNum /= dists[pattern]
        randNum = randNum**skews[pattern]
        sellPrice = math.floor(mins[pattern] + randNum * (maxs[pattern] - mins[pattern]))
        counts[math.floor(sellPrice/10)] += 1
    print(counts)