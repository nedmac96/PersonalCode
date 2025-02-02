'''
0 is green
1 is yellow
2 is gray

'''


def setAnswerList():
    answerList = []
    # inFile = open('WordleUnusedAnswers.txt', 'r')
    inFile = open('WordleUpdatedUnusedWords.txt', 'r')
    for line in inFile:
        answerList.append(line.rstrip('\n'))
    inFile.close()
    return answerList


def setOriginalWordsList():
    answerList = []
    inFile = open('WordleAnswersOriginal.txt', 'r')
    for line in inFile:
        answerList.append(line.rstrip('\n'))
    inFile.close()
    return answerList


def setAllWordsList():
    answerList = []
    inFile = open('WordleAllWords.txt', 'r')
    for line in inFile:
        answerList.append(line.rstrip('\n'))
    inFile.close()
    return answerList


def adjustAnswerList(answerList, guess, guessResult):
    for word in list(answerList):
        for i in range(5):
            if (guessResult[i] == '0' and word[i] != guess[i]):
                answerList.remove(word)
                break
            elif (guessResult[i] == '1') and (word[i] == guess[i] or word.count(guess[i]) == 0):
                answerList.remove(word)
                break
            elif (guessResult[i] == '2' and word.count(guess[i]) != 0):
                answerList.remove(word)
                break
    return answerList


def printWordList(wordList):
    for word in wordList:
        print(word)
    print(f'Total words: {len(wordList)}')
    return


def removeWordsWithDuplicates(answerList):
    for word in list(answerList):
        if word[2] == word[3] or word[0] == word[2] or word[3] == word[4] or word[1] == word[2] or word[2] == word[4] or \
                word[0] == word[4] or word[1] == word[3] or word[1] == word[4] or word[0] == word[3] or word[0] == word[
            1]:
            answerList.remove(word)
    return answerList


def getPosStartingWords():
    answerList = setAnswerList()
    answerList = adjustAnswerList(answerList, 'QWYUM', '22222')
    answerList = adjustAnswerList(answerList, 'DFGHJ', '22222')
    answerList = adjustAnswerList(answerList, 'KZXVB', '22222')
    answerList = removeWordsWithDuplicates(answerList)
    return answerList


def playAsComputer():
    answerList = setAnswerList()
    print('Choices:')
    print('Enter a word to play or')
    print('C - Calculate a suggestion')
    print('P - Print possible words')
    print('R - Remove duplicates from possible words')
    print('F - Print letter frequencies')
    print('X - End program')
    inputString = input('Enter your choice: ')
    while len(inputString) <= 0 or inputString[0].upper() != 'X':
        if len(inputString) == 5:
            testWord = inputString.upper()
            inputString = input('Enter the result (0=green 1=yellow 2=gray): ')
            while not (len(inputString) == 5 and inputString.isnumeric()):
                print('Invalid input. Try again.')
                inputString = input('Enter the result (0=green 1=yellow 2=gray): ')
            adjustAnswerList(answerList, testWord, inputString)
        elif len(inputString) > 0 and inputString[0].upper() == 'C':
            print(calculate(answerList))
        elif len(inputString) > 0 and inputString[0].upper() == 'P':
            printWordList(answerList)
        elif len(inputString) > 0 and inputString[0].upper() == 'R':
            removeWordsWithDuplicates(answerList)
        elif len(inputString) > 0 and inputString[0].upper() == 'F':
            frequencies = getLetterFrequencies(answerList)
            for i in range(26):
                letter = chr(65 + i)
                print(letter + ': ' + str(frequencies[i]) + ' words')
        else:
            print('Invalid input. Try again.')
        print('Choices:')
        print('Enter a word to play or')
        print('C - Calculate a suggestion')
        print('P - Print possible words')
        print('R - Remove duplicates from possible words')
        print('F - Print letter frequencies')
        print('X - End program')
        inputString = input('Enter your choice: ')
    return


def calculate(answerList):
    fullList = setOriginalWordsList()
    if not len(answerList) == 1:
        if not len(answerList) == 2:
            highestWordScore = 3000
            bestWord = ''
            for guess in fullList:
                highestScore = findMaxWordsLeft(answerList, guess, highestWordScore)
                if highestScore < highestWordScore:
                    bestWord = guess
                    highestWordScore = highestScore
            return bestWord + ' ' + str(round(highestWordScore))
        else:
            return answerList[0] + ' 1'
    else:
        return answerList[0] + ' 0'


# Value is the number of words remaining over which word1, word2, or word3 would be used
def testWord(word1, word2, word3, value):
    fullList = setAnswerList()
    words = []
    testWordScores = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    loops = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    wordsTried = [word1, '', '', '', '', '', '', '', '', '']
    guessScores = []
    guesses = []
    wordToGuess = word1 + ' 99'
    for loops4 in range(243):
        guessesUsed = 0
        wordToGuess = word1 + ' 99'
        guesses.append(word1)
        loops5 = 0
        highestScorePos = 243 - loops4
        highestScorePos -= 1
        l5 = highestScorePos % 3
        highestScorePos = (highestScorePos - l5) // 3
        l4 = highestScorePos % 3
        highestScorePos = (highestScorePos - l4) // 3
        l3 = highestScorePos % 3
        highestScorePos = (highestScorePos - l3) // 3
        l2 = highestScorePos % 3
        highestScorePos = (highestScorePos - l2) // 3
        l1 = highestScorePos
        guessScores.append(str(l1) + str(l2) + str(l3) + str(l4) + str(l5))
        answerList = []
        for listItem in fullList:
            answerList.append(listItem)
        guess = word1
        adjustAnswerList(answerList, word1, guessScores[0])
        myVariable = 0
        for score in testWordScores:
            myVariable += score
        if not myVariable == len(words):
            print('Error: Length of "words" list does not equal values in "testWordScores"')
            print(testWordScores)
            print(words)
            return
        guesses = []
        guessScores = []
        wordsLeftCopy = []
        for answer in answerList:
            wordsLeftCopy.append(answer)
            words.append(answer)
        if len(answerList) > 0:
            runCalculation(243 - loops4, 1, guesses, guessScores, wordsTried, guessesUsed, wordsLeftCopy, answerList,
                           testWordScores, word1, word2, word3, loops, value)
    print(word1 + ' ' + word2 + ' ' + word3 + ' ' + str(value))
    printTestWordResults(testWordScores)
    return


def runCalculation(loops, currentDepth, guesses, guessScores, wordsTried, guessesUsed, wordsLeftCopy, answerList,
                   testWordScores, word1, word2, word3, loopsList, value):
    guesses.append(wordsTried[currentDepth - 1])
    highestScorePos = loops - 1
    l5 = highestScorePos % 3
    highestScorePos = (highestScorePos - l5) // 3
    l4 = highestScorePos % 3
    highestScorePos = (highestScorePos - l4) // 3
    l3 = highestScorePos % 3
    highestScorePos = (highestScorePos - l3) // 3
    l2 = highestScorePos % 3
    highestScorePos = (highestScorePos - l2) // 3
    l1 = highestScorePos
    guessScores.append(str(l1) + str(l2) + str(l3) + str(l4) + str(l5))
    answerList = []
    for word in wordsLeftCopy:
        answerList.append(word)
    for (guess, guessScore) in zip(guesses, guessScores):
        adjustAnswerList(answerList, guess, guessScore)
    if len(answerList) > 0:
        if len(answerList) == 1:
            if guesses[len(guesses) - 1] == answerList[0]:
                testWordScores[guessesUsed] += 1
            else:
                testWordScores[guessesUsed + 1] += 1
        else:
            if len(answerList) < value:
                wordToGuess = calculate(answerList)
            else:
                if len(word1) == 5 and currentDepth == 0:
                    wordToGuess = word1 + ' ' + str(round(findMaxWordsLeft(answerList, word1, 2)))
                elif len(word2) == 5 and currentDepth == 1:
                    wordToGuess = word2 + ' ' + str(round(findMaxWordsLeft(answerList, word2, 2)))
                elif len(word3) == 5 and currentDepth == 2:
                    wordToGuess = word3 + ' ' + str(round(findMaxWordsLeft(answerList, word3, 2)))
                    '''elif currentDepth == 1 and guessScores[len(guessScores)-1] == '22222':
                        wordToGuess = 'OCTAL' +  ' ' + str(round(findMaxWordsLeft(answerList, 'OCTAL', 2)))
                    elif guessScores[len(guessScores)-1] == '22102' and currentDepth == 1:
                        wordToGuess = 'ALOFT' +  ' ' + str(round(findMaxWordsLeft(answerList, 'ALOFT', 2)))  '''
                else:
                    wordToGuess = calculate(answerList)
            if len(wordToGuess) == 7 and int(wordToGuess[6]) < 2:
                if wordToGuess[6] == '1':
                    if guessesUsed + 2 == 4:
                        print(answerList)  # Print statement here
                    if answerList.count(wordToGuess[:5]) != 0:
                        testWordScores[guessesUsed + 1] += 1
                        testWordScores[guessesUsed + 2] += len(answerList) - 1
                    else:
                        testWordScores[guessesUsed + 2] += len(answerList)
                else:
                    testWordScores[guessesUsed + 1] += 1
            else:
                wordsTried[currentDepth] = wordToGuess[:5]
                loopsList[currentDepth - 1] = 0
                for i in range(243):
                    loopsList[currentDepth - 1] += 1
                    runCalculation(loopsList[currentDepth - 1], currentDepth + 1, guesses, guessScores, wordsTried,
                                   guessesUsed + 1, wordsLeftCopy, answerList, testWordScores, word1, word2, word3,
                                   loopsList, value)
    guesses.pop()
    guessScores.pop()
    return


def printTestWordResults(testWordScores):
    totalGuesses = 0
    totalWords = 0
    count = 0
    for score in testWordScores:
        count += 1
        totalGuesses += score * count
        totalWords += score
        print(f'Words that took {count} guesses: {score}')
    print(f'Total words: {totalWords}')
    print(f'Total guesses used: {totalGuesses}')
    print(f'Average guesses: {totalGuesses / totalWords}')
    return


# If more or equal to stopValue words left possible then return stopValue. -1 for no stopValue
def findMaxWordsLeft(answerList, guess, stopValue):
    if len(answerList) < 2:
        return len(answerList)
    highestScore = 0
    l1 = 3
    for i in range(3):
        l1 -= 1
        l2 = 3
        for j in range(3):
            l2 -= 1
            l3 = 3
            for k in range(3):
                l3 -= 1
                l4 = 3
                for m in range(3):
                    l4 -= 1
                    l5 = 3
                    for n in range(3):
                        l5 -= 1
                        score = len(answerList) + 0.0
                        for word in answerList:
                            guessResult = '' + str(l1) + str(l2) + str(l3) + str(l4) + str(l5)
                            for i in range(5):
                                if (guessResult[i] == '0' and word[i] != guess[i]):
                                    score -= 1
                                    break
                                elif (guessResult[i] == '1') and (word[i] == guess[i] or word.count(guess[i]) == 0):
                                    score -= 1
                                    break
                                elif (guessResult[i] == '2' and word.count(guess[i]) != 0):
                                    score -= 1
                                    break
                        if answerList.count(guess) != 0:
                            score -= 0.1
                        if stopValue != -1 and score >= stopValue:
                            return stopValue
                        if score > highestScore:
                            highestScore = score
    return highestScore


def getLetterFrequencies(wordList):
    totalWords = len(wordList)
    frequencies = []
    for i in range(26):
        letter = chr(65 + i)
        answerList = []
        for word in wordList:
            answerList.append(word)
        adjustAnswerList(answerList, letter + letter + letter + letter + letter, '22222')
        frequencies.append(totalWords - len(answerList))
    return frequencies


def posGarenteeIn4(startWord):
    print('Starting word:', startWord)
    allWordList = setAllWordsList()
    answerList0 = setAnswerList()
    guessScores = [''] * 4
    guesses = [''] * 4
    guesses[0] = startWord
    for loops1 in range(243):
        highestScorePos = 243 - loops1
        highestScorePos -= 1
        l5 = highestScorePos % 3
        highestScorePos = (highestScorePos - l5) // 3
        l4 = highestScorePos % 3
        highestScorePos = (highestScorePos - l4) // 3
        l3 = highestScorePos % 3
        highestScorePos = (highestScorePos - l3) // 3
        l2 = highestScorePos % 3
        highestScorePos = (highestScorePos - l2) // 3
        l1 = highestScorePos
        guessScores[0] = str(l1) + str(l2) + str(l3) + str(l4) + str(l5)
        answerList1 = []
        for listItem in answerList0:
            answerList1.append(listItem)
        adjustAnswerList(answerList1, guesses[0], guessScores[0])
        if len(answerList1) > 3:
            calc = calculate(answerList1)
            guesses[1] = str(calc[0]) + str(calc[1]) + str(calc[2]) + str(calc[3]) + str(calc[4])
            issue = False
            for loops2 in range(243):
                answerList2 = []
                for listItem in answerList1:
                    answerList2.append(listItem)
                highestScorePos = 243 - loops2
                highestScorePos -= 1
                l5 = highestScorePos % 3
                highestScorePos = (highestScorePos - l5) // 3
                l4 = highestScorePos % 3
                highestScorePos = (highestScorePos - l4) // 3
                l3 = highestScorePos % 3
                highestScorePos = (highestScorePos - l3) // 3
                l2 = highestScorePos % 3
                highestScorePos = (highestScorePos - l2) // 3
                l1 = highestScorePos
                guessScores[1] = str(l1) + str(l2) + str(l3) + str(l4) + str(l5)
                adjustAnswerList(answerList2, guesses[1], guessScores[1])
                if len(answerList2) > 2:
                    calc = calculate(answerList2)
                    if not (len(calc) == 7 and (calc[6] == '0' or calc[6] == '1')):
                        print('Difficulty with', guessScores[0], ' Tried', guesses[1])
                        issue = True
                        break
            if issue:
                for word in answerList0:
                    guesses[1] = word
                    issue = False
                    for loops2 in range(243):
                        answerList2 = []
                        for listItem in answerList1:
                            answerList2.append(listItem)
                        highestScorePos = 243 - loops2
                        highestScorePos -= 1
                        l5 = highestScorePos % 3
                        highestScorePos = (highestScorePos - l5) // 3
                        l4 = highestScorePos % 3
                        highestScorePos = (highestScorePos - l4) // 3
                        l3 = highestScorePos % 3
                        highestScorePos = (highestScorePos - l3) // 3
                        l2 = highestScorePos % 3
                        highestScorePos = (highestScorePos - l2) // 3
                        l1 = highestScorePos
                        guessScores[1] = str(l1) + str(l2) + str(l3) + str(l4) + str(l5)
                        adjustAnswerList(answerList2, guesses[1], guessScores[1])
                        if len(answerList2) > 2:
                            calc = calculate(answerList2)
                            if not (len(calc) == 7 and (calc[6] == '0' or calc[6] == '1')):
                                issue = True
                                break
                    if not issue:
                        print('Found solution with', word)
                        break
                if issue:
                    print('No solution found for', guessScores[0], ' Cannot garentee in 4 with starting word',
                          startWord)
                    return
    print('Can be garenteed in 4 with', startWord)
    return


def updatePosAnswerList():
    usedWords = 'ABACK ABASE ABATE ABBEY ABIDE ABOUT ABOVE ABYSS ACRID ACTOR ACUTE ADAPT ADMIT ADOBE ADOPT ADORE ADULT AFTER AGAIN AGAPE AGATE AGENT AGILE AGING AGLOW AGONY AGREE AHEAD ALBUM ALIEN ALIKE ALIVE ALLOW ALOFT ALONE ALOOF ALOUD ALPHA ALTAR ALTER AMASS AMBER AMISS AMPLE ANGEL ANGER ANGRY ANGST ANODE ANTIC AORTA APART APHID APPLE APPLY APRON APTLY ARBOR ARDOR ARGUE AROMA ASCOT ASIDE ASKEW ASSET ATOLL ATONE AUDIO AUDIT AVAIL AVERT AWAIT AWAKE AWFUL AXIOM AZURE BACON BADGE BADLY BAGEL BAKER BALSA BANAL BARGE BASIC BASIN BATHE BATON BATTY BAYOU BEACH BEADY BEAST BEEFY BEGET BEGIN BEING BELCH BELIE BELLY BELOW BENCH BERET BERTH BESET BEVEL BINGE BIOME BIRCH BIRTH BLACK BLAME BLAND BLEAK BLEED BLEEP BLIMP BLOCK BLOKE BLOND BLOWN BLUFF BLURB BLURT BLUSH BOOBY BOOST BOOZE BOOZY BORAX BOUGH BRAID BRAKE BRASH BRAVE BRAVO BREAD BREAK BREED BRIAR BRIBE BRIDE BRIEF BRINE BRING BRINK BRINY BRISK BROKE BROOK BROOM BROTH BRUSH BUGGY BUILD BUILT BULKY BULLY BUNCH BURLY CABLE CACAO CACHE CANDY CANNY CANOE CAPER CARAT CARGO CAROL CARRY CATCH CATER CAULK CAUSE CEDAR CHAFE CHAIN CHAMP CHANT CHAOS CHARD CHARM CHART CHEAT CHEEK CHEER CHEST CHIEF CHILD CHILL CHIME CHOIR CHOKE CHORD CHUNK CHUTE CIDER CIGAR CINCH CIRCA CIVIC CLASS CLEAN CLEAR CLEFT CLERK CLICK CLIMB CLING CLOCK CLONE CLOSE CLOTH CLOWN CLUCK COACH COAST COCOA COLON COMET COMMA CONDO CONIC CORNY COULD COUNT COURT COVER COVET COWER COYLY CRAFT CRAMP CRANE CRANK CRASS CRATE CRAVE CRAZE CRAZY CREAK CREDO CREPT CRIME CRIMP CROAK CRONE CROSS CROWD CRUMB CRUST CUMIN CURLY CYNIC DADDY DAISY DANCE DANDY DEATH DEBUG DECAY DECAL DELTA DELVE DENIM DEPOT DEPTH DETER DEVIL DIARY DIGIT DINER DINGO DISCO DITTO DODGE DOING DONOR DONUT DOUBT DOWRY DOZEN DRAIN DREAM DRINK DRIVE DROLL DROOP DUCHY DUTCH DUVET DWARF DWELL DWELT EARLY EARTH EBONY EGRET EJECT ELDER ELOPE ELUDE EMAIL EMBER EMPTY ENEMA ENJOY ENNUI ENTER EPOCH EPOXY EQUAL EQUIP ERODE ERROR ERUPT ESSAY ETHER ETHIC ETHOS EVADE EVERY EVOKE EXACT EXALT EXCEL EXERT EXIST EXPEL EXTRA EXULT FACET FARCE FAULT FAVOR FEAST FEIGN FERRY FEWER FIELD FIEND FIFTY FINAL FINCH FINER FIRST FISHY FIXER FJORD FLAIL FLAIR FLAME FLANK FLARE FLASK FLESH FLICK FLING FLIRT FLOAT FLOCK FLOOD FLOOR FLORA FLOSS FLOUT FLUFF FLUME FLYER FOCAL FOCUS FOGGY FOLLY FORAY FORCE FORGE FORGO FORTH FORTY FOUND FOYER FRAME FRANK FRESH FRIED FROCK FROND FRONT FROST FROTH FROZE FUNGI FUNNY GAMER GAMMA GAUDY GAUZE GAWKY GECKO GENRE GHOUL GIANT GIDDY GIRTH GIVEN GLASS GLAZE GLEAM GLEAN GLIDE GLOAT GLOBE GLOOM GLORY GLOVE GLYPH GNASH GOLEM GONER GOOSE GORGE GOUGE GRACE GRADE GRAIL GRAND GRAPH GRASP GRATE GREAT GREEN GREET GRIEF GRIME GRIMY GRIND GRIPE GROIN GROOM GROUP GROUT GROVE GROWL GRUEL GUANO GUARD GUEST GUIDE GUILD GULLY GUMMY GUPPY HAIRY HAPPY HATCH HATER HAVOC HEADY HEARD HEART HEATH HEAVE HEAVY HEIST HELIX HELLO HENCE HERON HINGE HITCH HOARD HOBBY HOMER HORDE HORSE HOTEL HOUND HOUSE HOWDY HUMAN HUMID HUMOR HUMPH HUNCH HUNKY HURRY HUTCH HYPER IGLOO IMAGE IMPEL INANE INDEX INEPT INERT INFER INPUT INTER INTRO IONIC IRATE IRONY ISLET ITCHY IVORY JAUNT JAZZY JERKY JOKER JOLLY JOUST JUDGE KARMA KAYAK KAZOO KEBAB KHAKI KIOSK KNEEL KNELT KNOCK KNOLL KOALA LABEL LABOR LAPEL LAPSE LARGE LARVA LASER LATTE LAYER LEAFY LEAKY LEAPT LEARN LEASH LEAVE LEDGE LEERY LEGGY LEMON LIBEL LIGHT LILAC LIMIT LINEN LINER LINGO LIVER LOCAL LOCUS LOFTY LOGIC LOOPY LOSER LOUSE LOVER LOWLY LOYAL LUCID LUCKY LUNAR LUNCH LUNGE LUSTY LYING MADAM MAGIC MAGMA MAIZE MAJOR MANIA MANGA MANLY MANOR MAPLE MARCH MARRY MARSH MASON MASSE MATCH MATEY MAXIM MAYBE MAYOR MEALY MEANT MEDAL MEDIA MELON MERCY MERGE MERIT MERRY METAL METRO MICRO MIDGE MIDST MIMIC MINCE MINUS MODEL MOIST MOLAR MONEY MONTH MOOSE MOSSY MOTOR MOTTO MOULT MOUNT MOURN MOUSE MOVIE MUCKY MUMMY MURAL MUSIC MUSTY NAIVE NANNY NASTY NATAL NAVAL NEEDY NEVER NICER NIGHT NINJA NINTH NOBLE NOISE NORTH NYMPH OCCUR OCEAN OFFAL OFTEN OLDER OLIVE ONION ONSET OPERA ORGAN OTHER OUGHT OUTDO OUTER OVERT OXIDE PANEL PANIC PAPAL PAPER PARER PARRY PARTY PASTA PATTY PAUSE PEACE PEACH PERCH PERKY PESKY PHASE PHONE PHONY PHOTO PIANO PICKY PIETY PILOT PINCH PINEY PINKY PINTO PIOUS PIPER PIQUE PITHY PIXEL PIXIE PLACE PLAIT PLANK PLANT PLATE PLAZA PLEAT PLUCK PLUNK POINT POISE POKER POLKA POLYP POUND POWER PRICE PRICK PRIDE PRIME PRIMO PRINT PRIOR PRIZE PROBE PROUD PROVE PROWL PROXY PRUNE PSALM PULPY PURGE QUALM QUART QUEEN QUERY QUEST QUEUE QUICK QUIET QUIRK QUOTE RADIO RAINY RAISE RAMEN RANCH RANGE RATIO RAYON REACT REALM REBUS REBUT RECAP REGAL RELIC RENEW REPAY REPEL RESIN RETCH RETRO RETRY REVEL RHINO RHYME RIDGE RIGHT RIPER RISEN RIVAL ROBIN ROBOT ROCKY RODEO ROGUE ROOMY ROUGE ROUND ROUSE ROUTE ROVER ROYAL RUDDY RUDER RUPEE RUSTY SAINT SALAD SALLY SALSA SALTY SASSY SAUTE SCALD SCANT SCARE SCARF SCOLD SCOPE SCORN SCOUR SCOUT SCRAM SCRAP SCRUB SEDAN SEEDY SENSE SERUM SERVE SEVER SHADE SHAKE SHALL SHAME SHANK SHARD SHARP SHAWL SHAVE SHIFT SHINE SHIRE SHIRK SHORN SHOWN SHOWY SHRUB SHRUG SHYLY SIEGE SIGHT SINCE SISSY SKIER SKILL SKIMP SKIRT SKUNK SLATE SLEEK SLEEP SLICE SLOPE SLOSH SLOTH SLUMP SLUNG SMALL SMART SMASH SMEAR SMELT SMILE SMIRK SMITE SMITH SNACK SNAFU SNAIL SNAKE SNAKY SNARE SNARL SNEAK SNORT SNOUT SOGGY SOLAR SOLID SOLVE SONIC SOUND SOWER SPACE SPADE SPEAK SPELL SPELT SPEND SPENT SPICE SPICY SPIEL SPIKE SPILL SPIRE SPLAT SPOKE SPRAY SPURT SQUAD SQUAT STAFF STAGE STAID STAIR STALE STALL STAND STARK START STASH STATE STEAD STEED STEEL STEIN STICK STIFF STILL STING STINK STOCK STOLE STOMP STONE STONY STOOL STORE STORY STOUT STOVE STRAP STRAW STUDY STUNG STYLE SUGAR SULKY SURER SURLY SUSHI SWEAT SWEEP SWEET SWILL SWINE SWIRL SWISH SWUNG SYRUP TABLE TABOO TACIT TAKEN TALON TANGY TAPER TAPIR TARDY TASTE TASTY TAUNT TAWNY TEARY TEASE TEMPO TENTH TEPID THEIR THEME THERE THESE THIEF THING THINK THIRD THORN THOSE THREE THREW THROW THUMB THUMP THYME TIARA TIBIA TIDAL TIGER TILDE TIPSY TITAN TITHE TODAY TONIC TOPAZ TOPIC TORSO TOTEM TOUCH TOUGH TOWEL TOXIC TOXIN TRACE TRACT TRADE TRAIN TRAIT TRASH TRAWL TREAT TREND TRIAD TRICE TRITE TROLL TROPE TROVE TRUSS TRUST TRUTH TRYST TUTOR TWANG TWEAK TWEED TWICE TWINE TWIRL ULCER ULTRA UNCLE UNDER UNDUE UNFED UNFIT UNIFY UNITE UNLIT UNMET UNTIE UNTIL UNZIP UPSET URBAN USAGE USHER USING USUAL USURP UTTER VAGUE VALET VALUE VALID VAPID VAULT VENOM VERGE VERVE VIGOR VIOLA VIRAL VITAL VIVID VODKA VOICE VOILA VOTER VOUCH WACKY WALTZ WASTE WATCH WEARY WEDGE WHACK WHALE WHEEL WHELP WHERE WHICH WHIFF WHILE WHINE WHINY WHIRL WHISK WHOOP WINCE WINDY WOKEN WOMAN WOOER WORDY WORLD WORRY WORSE WORST WOULD WOVEN WRATH WRIST WRITE WRONG WROTE WRUNG YACHT YEARN YIELD YOUNG YOUTH ZESTY'
    wordList = usedWords.split(' ')
    answerList = setOriginalWordsList()
    file = open('WordleUpdatedUnusedWords.txt', 'w')
    for word in answerList:
        if not word in wordList:
            file.write(word + '\n')
    file.close()
    return


# print(str(findMaxWordsLeft(setAnswerList(), 'CAIRN', -1)))
# testWord('SIREN', '', '', 5)
playAsComputer()
# printWordList(getPosStartingWords())
# posGarenteeIn4('ROAST')
'''

# Best Words
Word  Guesses Average PosAns Fives Twos
SALET 4298    3.3189  False  11    89
LEAST 4326    3.3405  True   13    83
ROAST 4353    3.3614  True   9     72
CAIRN 4409    3.4046  True   31    80
LEANT 4382    3.3838  True   25    89
STOLE 4372    3.3761  False  18    63
ARISE 4397    3.3954  True   22    64
LANCE 4356    3.3637  True   23    78
PARSE 4344    3.3544  True   26    89
PLANE 4359    3.3660  True   30    81
RAISE 4354    3.3622  True   17    72
SANER 4348    3.3575  True   17    80
SCALE 4355    3.3629  True   15    78
SIREN 4352    3.3606  True   15    79
SONAR 4356    3.3637  True   9     70
CRONY 4451    3.4706  True   28    47
ROUND 4481    3.4602  True   34    67
CRIED 4400    3.3977  True   43    74
SLANT 4377    3.3799  True   16    74

# Best Word Pairs (value = 40)
Word1 Word2 Guesses Average PosAns Fives Twos
LEAST MINOR 4352    3.3606  True   30    81
LEAST CRONY 4347    3.3568  True   21    81
ROAST DUNCE 4377    3.3799  True   23    70
SONAR EDICT 4388    3.3884  True   23    70
PARSE CLOUT 4378    3.3807  True   37    88
SALET CRONY 4303    3.3228  False  15    86
LEAST ROUND 4348    3.3575  True   23    80
SALET ROUND 4300    3.3205  False  11    85
CRONY SALET 4444    3.4317  True   33    46
ROUND SALET 4424    3.4162  True   23    65
CRIED SLANT 4368    3.3730  True   27    72
SLANT CRIED 4357    3.3645  True   28    71
CAIRN STOLE 4367    3.3722  True   19    59

# Best Word Pairs (value = 70)
Word1 Word2 Guesses Average PosAns Fives Twos
LEAST CRONY 4320    3.3359  True   11    83
LEAST ROUND 4327    3.3413  True   8     82
SALET CRONY 4303    3.3228  False  10    89
SALET ROUND 4302    3.3220  False  11    88

'''

'''
SIREN:
['BAGGY', 'MAMMY']
['KAPPA', 'MAMMA']

['ROGER', 'ROWER']
['MOVER', 'MOWER']
['BOXER', 'HOVER']
['ODDER', 'ORDER']

LEAST:
['FROWN', 'GROWN']
['BOBBY', 'POPPY']

['MOVER', 'MOWER']
['BOXER', 'ROGER', 'ROWER']
['GIVER', 'RIVER']
['PURER', 'UPPER']

['FILLY', 'WILLY']

SCALE:
['FIGHT', 'MIGHT']
['KITTY', 'WITTY']
['FIFTH', 'MIRTH']

['ROGER', 'ROWER']
['MOVER', 'MOWER']
['BOXER', 'HOVER']
['OFFER', 'OWNER']

['HARPY', 'HARRY']
['GAUNT', 'VAUNT']
['AMONG', 'FAUNA']
'''
