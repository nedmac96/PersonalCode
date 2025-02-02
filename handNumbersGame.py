


def getPossibleMoves(pos):
    posMoves = []
    
    # Add hits
    if int(pos[0]) + int(pos[2]) < 5:
        posMoves.append(str(int(pos[0]) + int(pos[2])) + pos[3] + pos[0] + pos[1])
    else:
        posMoves.append(pos[3] + '0' + pos[0] + pos[1])
    
    if pos[3] != '0' and pos[3] != pos[2]:
        if int(pos[0]) + int(pos[3]) < 5:
            if int(pos[0]) + int(pos[3]) < int(pos[2]):
                posMoves.append(pos[2] + str(int(pos[0]) + int(pos[3])) + pos[0] + pos[1])
            else:
                posMoves.append(str(int(pos[0]) + int(pos[3])) + pos[2] + pos[0] + pos[1])
        else:
            posMoves.append(pos[2] + '0' + pos[0] + pos[1])    
        
    if pos[1] != '0' and pos[1] != pos[0]:
        if int(pos[1]) + int(pos[2]) < 5:
            posMoves.append(str(int(pos[1]) + int(pos[2])) + pos[3] + pos[0] + pos[1])
        elif int(pos[0]) + int(pos[2]) < 5:
            posMoves.append(pos[3] + '0' + pos[0] + pos[1])
        
        if pos[3] != '0' and pos[3] != pos[2]:
            if int(pos[1]) + int(pos[3]) < 5:
                if int(pos[1]) + int(pos[3]) < int(pos[2]):
                    posMoves.append(pos[2] + str(int(pos[1]) + int(pos[3])) + pos[0] + pos[1])
                else:
                    posMoves.append(str(int(pos[1]) + int(pos[3])) + pos[2] + pos[0] + pos[1])
            elif int(pos[0]) + int(pos[3]) < 5:
                posMoves.append(pos[2] + '0' + pos[0] + pos[1])         
    # Add swaps
    
    if int(pos[0]) + int(pos[1]) > 2:
        if int(pos[0]) + int(pos[1]) == 3:
            posMoves.append(pos[2] + pos[3] + '21')
        elif int(pos[0]) + int(pos[1]) == 4:
            if pos[0] != '2':
                posMoves.append(pos[2] + pos[3] + '22')
            posMoves.append(pos[2] + pos[3] + '31')
        elif int(pos[0]) + int(pos[1]) == 5:
            posMoves.append(pos[2] + pos[3] + '32')
            posMoves.append(pos[2] + pos[3] + '41')
        elif int(pos[0]) + int(pos[1]) == 6:
            if pos[0] != '3':
                posMoves.append(pos[2] + pos[3] + '33')
            posMoves.append(pos[2] + pos[3] + '42')
        elif int(pos[0]) + int(pos[1]) == 7:
            posMoves.append(pos[2] + pos[3] + '43')      
    elif pos[0] == '2':
        posMoves.append(pos[2] + pos[3] + '11')
    
    '''
    if int(pos[0]) + int(pos[1]) > 2:
        if pos[0] + pos[1] == '30':
            posMoves.append(pos[2] + pos[3] + '21')
        elif int(pos[0]) + int(pos[1]) == 4:
            if pos[0] != '2':
                posMoves.append(pos[2] + pos[3] + '22')
            if pos[0] != '3':
                posMoves.append(pos[2] + pos[3] + '31')
        elif int(pos[0]) + int(pos[1]) == 5:
            if pos[0] != '3':
                posMoves.append(pos[2] + pos[3] + '32')
            if pos[0] != '4':
                posMoves.append(pos[2] + pos[3] + '41')
        elif int(pos[0]) + int(pos[1]) == 6:
            if pos[0] != '3':
                posMoves.append(pos[2] + pos[3] + '33')
            if pos[0] != '4':
                posMoves.append(pos[2] + pos[3] + '42')     
    elif pos[0] == '2':
        posMoves.append(pos[2] + pos[3] + '11')
    '''
    return posMoves

def evaluate(pos, remainingDepth, player1Turn, positionsUsed, path):
    if pos[0] == '0':
        return -1
    if remainingDepth == 0:
        return 0
    #print(pos)
    posMoves = getPossibleMoves(pos)
    #print(posMoves)
    result = -2
    outcomes = []
    for position in posMoves:
        score = 0
        if path.count(position) == 0:
            if positionsUsed.count(position) == 0:
                positionsUsed.append(position)
            if path[len(path) - 1] != position:
                path.append(position)
            score = evaluate(position, remainingDepth - 1, not player1Turn, positionsUsed, path) * -1
            path.remove(position)
        outcomes.append(score)
        if score > result:
            result = score
            if result == 1:
                return result
    
    #print(pos)
    #print(posMoves)
    #print(outcomes)
    #print(path)
    return result

def runStartingPosition():
    pos = '1111'
    positionsUsed = [pos]
    score = evaluate(pos, 15, True, positionsUsed, [pos])
    for position in positionsUsed:
        print(position)
    print('Evaluation of ' + pos + ': ' + str(score))
    print('Length of positionsUsed: ' + str(len(positionsUsed)))
    
    print('Unused positions: ')
    for a in range(5):
        for b in range(5):
            for c in range(1, 5):
                for d in range(5):
                    if a >= b and c >= d:
                        position = str(a) + str(b) + str(c) + str(d)
                        if positionsUsed.count(position) == 0:
                            print(position)
    return

def runAllPositions():
    for a in range(1, 5):
        for b in range(5):
            for c in range(1, 5):
                for d in range(5):
                    if a >= b and c >= d:
                        pos = str(a) + str(b) + str(c) + str(d)
                        positionsUsed = [pos]
                        result = evaluate(pos, 7, True,  positionsUsed, [pos])
                        print(pos + ' ' + str(result))
    return

#runStartingPosition()
#runAllPositions()
