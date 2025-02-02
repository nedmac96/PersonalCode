
import random

MAX_VALUE = 100.0
MIN_CHANGE = -1.0
MAX_CHANGE = 1.0
TRIALS = 10000

def main():
    odds = findOddsFloat(0.0000000001, MIN_CHANGE, MAX_CHANGE)
    processOdds(odds)
    odds = findOddsFloat(0.0000000001, MIN_CHANGE, MAX_CHANGE)
    processOdds(odds)
    odds = findOddsFloat(0.0000000001, MIN_CHANGE, MAX_CHANGE)
    processOdds(odds)    
    return

def findOddsFloat(decrease, minChange, maxChange):
    odds = []
    for j in range(3):
        zeros = 0
        for i in range(TRIALS):
            num = decrease
            while num > 0 and num < MAX_VALUE:
                num += random.uniform(minChange, maxChange)
            if num <= 0:
                zeros += 1
        odds.append(zeros)
    return odds

def processOdds(odds):
    print(odds)
    avg = (odds[0] + odds[1] + odds[2]) / (3 * TRIALS)
    diff = max(abs(odds[0] - odds[1]), abs(odds[0] - odds[2]), abs(odds[1] - odds[2])) * 1.5 / TRIALS
    print(f'Odds: {avg:.5f} ({avg - diff:.5f}, {avg + diff:.5f})')
    return

main()