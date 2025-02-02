import random

class Team:
    def __init__(self, win_rate, name, wins):
        self.win_rate = win_rate
        self.name = name
        self.start_wins = wins
        self.end_wins = wins
        self.div1 = 0
        self.div2 = 0
        self.wc1 = 0
        self.wc2 = 0
        self.wc3 = 0
        self.wc4 = 0
        self.wc5 = 0

    def reset(self):
        self.end_wins = self.start_wins
    

def run(com_teams, other_teams, games):
    for game in games:
        randNum = random.random()
        odds = game[0].win_rate*(1-game[1].win_rate) / (game[0].win_rate*(1-game[1].win_rate) + game[1].win_rate*(1-game[0].win_rate))
        if odds > randNum:
            game[0].end_wins += 1
        else:
            game[1].end_wins += 1
    max1 = 0
    max2 = 0
    max1_team = None
    max2_team = None
    for team in com_teams[:5]:
        if team.end_wins > max1:
            max2 = max1
            max2_team = max1_team
            max1 = team.end_wins
            max1_team = team
        elif team.end_wins == max1 or team.end_wins > max2:
            max2 = team.end_wins
            max2_team = team
    max1_team.div1 += 1
    max2_team.div2 += 1
    div_winner = max1_team
    
    max1 = 0
    max2 = 0
    max3 = 0
    max4 = 0
    max5 = 0
    max1_team = None
    max2_team = None
    max3_team = None
    max4_team = None 
    max5_team = None
    for team in com_teams:
        if team != div_winner:
            if team.end_wins > max1:
                max5 = max4
                max5_team = max4_team
                max4 = max3
                max4_team = max3_team
                max3 = max2
                max3_team = max2_team
                max2 = max1
                max2_team = max1_team                
                max1 = team.end_wins
                max1_team = team
            elif team.end_wins == max1 or team.end_wins > max2:
                max5 = max4
                max5_team = max4_team
                max4 = max3
                max4_team = max3_team
                max3 = max2
                max3_team = max2_team                 
                max2 = team.end_wins
                max2_team = team
            elif team.end_wins == max2 or team.end_wins > max3:
                max5 = max4
                max5_team = max4_team
                max4 = max3
                max4_team = max3_team                  
                max3 = team.end_wins
                max3_team = team
            elif team.end_wins == max3 or team.end_wins > max4:
                max5 = max4
                max5_team = max4_team
                max4 = team.end_wins
                max4_team = team
            elif team.end_wins == max4 or team.end_wins > max5:
                max5 = team.end_wins
                max5_team = team
    max1_team.wc1 += 1
    max2_team.wc2 += 1
    max3_team.wc3 += 1
    max4_team.wc4 += 1
    max5_team.wc5 += 1

def run2(com_teams, other_teams, games):
    for team in com_teams:
        for i in range(20):
            if random.random() < team.win_rate:
                team.end_wins += 1
    max1 = 0
    max2 = 0
    max1_team = None
    max2_team = None
    for team in com_teams[:5]:
        if team.end_wins > max1:
            max2 = max1
            max2_team = max1_team
            max1 = team.end_wins
            max1_team = team
        elif team.end_wins == max1 or team.end_wins > max2:
            max2 = team.end_wins
            max2_team = team
    max1_team.div1 += 1
    max2_team.div2 += 1
    div_winner = max1_team
    
    max1 = 0
    max2 = 0
    max3 = 0
    max4 = 0
    max5 = 0
    max1_team = None
    max2_team = None
    max3_team = None
    max4_team = None 
    max5_team = None
    for team in com_teams:
        if team != div_winner:
            if team.end_wins > max1:
                max5 = max4
                max5_team = max4_team
                max4 = max3
                max4_team = max3_team
                max3 = max2
                max3_team = max2_team
                max2 = max1
                max2_team = max1_team                
                max1 = team.end_wins
                max1_team = team
            elif team.end_wins == max1 or team.end_wins > max2:
                max5 = max4
                max5_team = max4_team
                max4 = max3
                max4_team = max3_team
                max3 = max2
                max3_team = max2_team                 
                max2 = team.end_wins
                max2_team = team
            elif team.end_wins == max2 or team.end_wins > max3:
                max5 = max4
                max5_team = max4_team
                max4 = max3
                max4_team = max3_team                  
                max3 = team.end_wins
                max3_team = team
            elif team.end_wins == max3 or team.end_wins > max4:
                max5 = max4
                max5_team = max4_team
                max4 = team.end_wins
                max4_team = team
            elif team.end_wins == max4 or team.end_wins > max5:
                max5 = team.end_wins
                max5_team = team
    max1_team.wc1 += 1
    max2_team.wc2 += 1
    max3_team.wc3 += 1
    max4_team.wc4 += 1
    max5_team.wc5 += 1

def simulate(com_teams, other_teams, games):
    n = 10000
    for i in range(n):
        for team in com_teams:
            team.reset()
        for team in other_teams:
            team.reset()
        run2(com_teams, other_teams, games)
    for team in com_teams:
        print(f"{team.name} div1:{team.div1}, div2:{team.div2}, wc1:{team.wc1}, wc2:{team.wc2}, wc3:{team.wc3}, wc4:{team.wc4}, wc5:{team.wc5}")
#             0              1          2          3            4       5           6        7        8          9        10       11
com_names = ['Mariners', 'Astros', 'Rangers', 'Athletics', 'Angels', 'Yankees', 'Royals', 'Twins', 'Red Sox', 'Tigers', 'Rays', 'Blue Jays']
com_wins = [76, 80, 71, 65, 60, 86, 82, 78, 75, 76, 73, 71]
win_rate_calc = "X"
com_teams = []
for name, wins in zip(com_names, com_wins):
    if win_rate_calc == "RAND":
        com_teams.append(Team(0.5, name, wins))
    else:
        com_teams.append(Team(wins / 148, name, wins))
#                0            1          2                  3            4         5         6          7        8          9        10          11         12       13
other_names = ['Cardinals', 'Padres', 'Diamond Backs', 'Guardians', 'White Sox', 'Cubs', 'Pirates', 'Orioles', 'Reds', 'Marlins', 'Rockies', 'Phillies', 'Braves', 'Mets', 'Giants', 'Nationals']
other_wins = [74, 84, 82, 85, 34, 75, 70, 84, 73, 55, 57, 89, 81, 81, 72, 67]
other_teams = []
for name, wins in zip(other_names, other_wins):
    if win_rate_calc == "RAND":
        other_teams.append(Team(0.5, name, wins))
    else:
        other_teams.append(Team(wins / 148, name, wins))
# override win rates
com_teams[1].win_rate = 0.6
com_teams[0].win_rate = 0.46
print(com_teams[1].win_rate)

games = []
games.append([com_teams[0], com_teams[2]])
games.append([com_teams[9], other_teams[7]])
games.append([com_teams[7], other_teams[8]])
games.append([com_teams[6], other_teams[6]])
games.append([com_teams[5], com_teams[8]])
games.append([com_teams[11], other_teams[0]])
games.append([com_teams[10], other_teams[3]])
games.append([com_teams[3], other_teams[4]])
games.append([com_teams[1], com_teams[4]])

games.append([com_teams[7], other_teams[3]])
games.append([com_teams[6], com_teams[9]])
games.append([com_teams[3], other_teams[5]])
games.append([com_teams[4], other_teams[4]])
games.append([com_teams[1], other_teams[1]])

games.append([com_teams[0], com_teams[5]])
games.append([com_teams[7], other_teams[3]])
games.append([com_teams[8], com_teams[10]])
games.append([com_teams[6], com_teams[9]])
games.append([com_teams[3], other_teams[5]])
games.append([com_teams[2], com_teams[11]])
games.append([com_teams[4], other_teams[4]])
games.append([com_teams[1], other_teams[1]])

games.append([com_teams[0], com_teams[5]])
games.append([com_teams[3], other_teams[5]])
games.append([com_teams[4], other_teams[4]])
games.append([com_teams[1], other_teams[1]])
games.append([com_teams[7], other_teams[3]])
games.append([com_teams[8], com_teams[10]])
games.append([com_teams[6], com_teams[9]])
games.append([com_teams[2], com_teams[11]])

games.append([com_teams[0], com_teams[5]])
games.append([com_teams[7], other_teams[3]])
games.append([com_teams[2], com_teams[11]])
games.append([com_teams[8], com_teams[10]])
games.append([com_teams[1], com_teams[4]])

games.append([com_teams[0], com_teams[2]])
games.append([com_teams[10], com_teams[11]])
games.append([com_teams[9], other_teams[7]])
games.append([com_teams[7], com_teams[8]])
games.append([com_teams[1], com_teams[4]])
games.append([com_teams[6], other_teams[14]])
games.append([com_teams[5], com_teams[3]])

games.append([com_teams[0], com_teams[2]])
games.append([com_teams[9], other_teams[7]])
games.append([com_teams[10], com_teams[11]])
games.append([com_teams[7], com_teams[8]])
games.append([com_teams[1], com_teams[4]])
games.append([com_teams[6], other_teams[14]])
games.append([com_teams[5], com_teams[3]])

games.append([com_teams[0], com_teams[2]])
games.append([com_teams[9], other_teams[7]])
games.append([com_teams[7], com_teams[8]])
games.append([com_teams[10], com_teams[11]])
games.append([com_teams[1], com_teams[4]])
games.append([com_teams[6], other_teams[14]])
games.append([com_teams[5], com_teams[3]])

games.append([com_teams[0], com_teams[1]])
games.append([com_teams[8], com_teams[11]])

games.append([com_teams[0], com_teams[1]])
games.append([com_teams[9], com_teams[10]])
games.append([com_teams[6], other_teams[15]])
games.append([com_teams[5], other_teams[7]])
games.append([com_teams[8], com_teams[11]])
games.append([com_teams[4], other_teams[4]])
games.append([com_teams[7], other_teams[9]])
games.append([com_teams[2], com_teams[3]])

games.append([com_teams[0], com_teams[1]])
games.append([com_teams[9], com_teams[10]])
games.append([com_teams[6], other_teams[15]])
games.append([com_teams[5], other_teams[7]])
games.append([com_teams[8], com_teams[11]])
games.append([com_teams[4], other_teams[4]])
games.append([com_teams[7], other_teams[9]])
games.append([com_teams[2], com_teams[3]])

games.append([com_teams[6], other_teams[15]])
games.append([com_teams[9], com_teams[10]])
games.append([com_teams[4], other_teams[4]])
games.append([com_teams[2], com_teams[3]])
games.append([com_teams[5], other_teams[7]])
games.append([com_teams[7], other_teams[9]])

games.append([com_teams[0], com_teams[3]])
games.append([com_teams[9], other_teams[4]])
games.append([com_teams[5], other_teams[6]])
games.append([com_teams[11], other_teams[9]])
games.append([com_teams[1], other_teams[3]])
games.append([com_teams[8], com_teams[10]])
games.append([com_teams[6], other_teams[12]])
games.append([com_teams[7], other_teams[7]])
games.append([com_teams[2], com_teams[4]])

games.append([com_teams[0], com_teams[3]])
games.append([com_teams[5], other_teams[6]])
games.append([com_teams[9], other_teams[4]])
games.append([com_teams[7], other_teams[7]])
games.append([com_teams[11], other_teams[9]])
games.append([com_teams[8], com_teams[10]])
games.append([com_teams[1], other_teams[3]])
games.append([com_teams[6], other_teams[12]])
games.append([com_teams[2], com_teams[4]])

games.append([com_teams[0], com_teams[3]])
games.append([com_teams[8], com_teams[10]])
games.append([com_teams[5], other_teams[6]])
games.append([com_teams[2], com_teams[4]])
games.append([com_teams[11], other_teams[9]])
games.append([com_teams[1], other_teams[3]])
games.append([com_teams[7], other_teams[7]])
games.append([com_teams[9], other_teams[4]])
games.append([com_teams[6], other_teams[12]])

simulate(com_teams, other_teams, games)


