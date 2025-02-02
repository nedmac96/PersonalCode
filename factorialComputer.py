import numpy as np
import pandas as pd

Primes = [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997, 1009, 1013, 1019, 1021, 1031, 1033, 1039, 1049, 1051, 1061, 1063, 1069, 1087, 1091, 1093, 1097, 1103, 1109, 1117, 1123, 1129, 1151, 1153, 1163, 1171, 1181, 1187, 1193, 1201, 1213, 1217, 1223, 1229, 1231, 1237, 1249, 1259, 1277, 1279, 1283, 1289, 1291, 1297, 1301, 1303, 1307, 1319, 1321, 1327, 1361, 1367, 1373, 1381, 1399, 1409, 1423, 1427, 1429, 1433, 1439, 1447, 1451, 1453, 1459, 1471, 1481, 1483, 1487, 1489, 1493, 1499, 1511, 1523, 1531, 1543, 1549, 1553, 1559, 1567, 1571, 1579, 1583, 1597, 1601, 1607, 1609, 1613, 1619, 1621, 1627, 1637, 1657, 1663, 1667, 1669, 1693, 1697, 1699, 1709, 1721, 1723, 1733, 1741, 1747, 1753, 1759, 1777, 1783, 1787, 1789, 1801, 1811, 1823, 1831, 1847, 1861, 1867, 1871, 1873, 1877, 1879, 1889, 1901, 1907, 1913, 1931, 1933, 1949, 1951, 1973, 1979, 1987, 1993, 1997, 1999]
# Demo is for demonimator
def product(factorSet):
    product = 1
    for item in factorSet:
        product = product * item
    return product

# Assumes the result is a positive integer
def divide(numFactorSet, demoFactorSet):
    for i in range(len(numFactorSet) - 1,-1,-1):
        if numFactorSet[i] in demoFactorSet:
            demoFactorSet.remove(numFactorSet[i])
            numFactorSet.pop(i)
    if len(demoFactorSet) != 0:
        print('Error could not divide evenly')
    return numFactorSet

# Returns a list of primes with the same product of factorSet
def spitIntoPrimes(factorSet):
    factors = []
    for item in factorSet:
        num = item
        for prime in Primes:
            if prime > np.sqrt(num):
                break
            while num % prime == 0:
                factors.append(prime)
                num = num // prime
        if num != 1:
            factors.append(num)
    return factors

# Returns a set of primes whose product equals num!
def factorialSet(num):
    factors = []
    for i in range(2, num + 1):
        factors.append(i)
    return spitIntoPrimes(factors)

# Returns a set of primes whose product equals base! / num! where base >= num
def factorialDifferenceSet(base, num):
    factors = []
    for i in range(num + 1, base + 1):
        factors.append(i)
    return spitIntoPrimes(factors)

# Returns the number of arrangements of num elements where none have their own position
def getDerangements(num):
    if num == 2:
        return 1
    if num == 1:
        return 0
    return (num - 1) * (getDerangements(num - 1) + getDerangements(num - 2))

def findPrimes(max_prime):
    primes = [2, 3, 5]
    num = 5
    while num <= max_prime:
        num += 2
        for prime in primes:
            if num % prime == 0:
                break
            if prime > np.sqrt(num):
                primes.append(num)
                break
    return primes
# print(spitIntoPrimes([134] + factorialSet(4) + factorialDifferenceSet(7, 3)))
# print(divide(spitIntoPrimes([134] + factorialSet(4) + factorialDifferenceSet(7, 3)), spitIntoPrimes([14684570])))
print(spitIntoPrimes([39763]))
# df = pd.read_excel(r"C:\Users\Camden\OneDrive\Documents\Polytopia Stats.xlsx", sheet_name='Sheet5')
# const_set = factorialSet(df["Count"].sum())
# for i in range(16):
#     const_set = divide(const_set, factorialSet(df["Count"].iloc[i]))
#
# val = product(const_set)
# probs = [0.04 for i in range(13)] + [0.20, 0.04, 0.24]
# for val, prob in zip(df["Count"], probs):
#     val *= np.power(prob, val)
# print(val)