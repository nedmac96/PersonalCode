import pandas as pd
import numpy as np

def test(num_people, in_room):
    count = 0
    found = 0
    while count < 1000000:
        num_list = list(np.random.permutation(np.arange(0, num_people)))
        if all(i != num for i, num in enumerate(num_list)):
            count += 1
            if any(num < in_room for num in num_list[:in_room]):
                found += 1
    print(found)

test(11, 4)