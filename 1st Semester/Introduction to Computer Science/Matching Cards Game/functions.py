def cardmaker1():
    """Κατασκευάζει τις κάρτες για το εύκολο επίπεδο του παιχνιδιού(επίπεδο 1)"""
    symbols=['10','J','Q','K']
    series=['♦','♥','♣','♠']
    cards=[x+y for x in symbols for y in series]
    return cards

def cardmaker2():
    """Κατασκευάζει τις κάρτες για το επίπεδο μέτριας δυσκολίας του παιχνιδιού(επίπεδο 2)"""
    symbols=['Α']+[str(x) for x in range(2,11)]
    series=['♦','♥','♣','♠']
    cards=[x+y for x in symbols for y in series]
    return cards

def cardmaker3():
    """Κατασκευάζει τις κάρτες για το δύσκολο επίπεδο του παιχνιδιού(επίπεδο 3)"""
    symbols=['Α']+[str(x) for x in range(2,11)]+['J','Q','K']
    series=['♦','♥','♣','♠']
    cards=[x+y for x in symbols for y in series]
    return cards

def symbol(card):
    """
    Δέχεται μία κάρτα και επιστρέφει τον αριθμό/σύμβολο της κάρτας
    >>> symbol('2♣')
    '2'
    >>> symbol('10♦')
    '10'
    >>> symbol('J♥')
    'J'
    """
    if len(card)==3: #Στην περίπτωση που το σύμβολο είναι 10
        return card[0]+card[1] 
    else:
        return card[0]

def series(card):
    """
    Δέχεται μία κάρτα και επιστρέφει την σειρά της κάρτας
    >>> series('2♣')
    '♣'
    >>> series('10♦')
    '♦'
    >>> series('J♥')
    '♥'
    """
    if len(card)==3: #Στην περίπτωση που το σύμβολο είναι 10 
        return card[2]
    else:
        return card[1]

def value(card):
    """
    Δέχεται μία κάρτα και επιστρέφει την αξία της
    >>> value('Α♥')
    1
    >>> value('5♦')
    5
    >>> value('Q♣')
    10
    >>> value('10♠')
    10
    """
    if symbol(card)=='Α':
        return 1
    elif symbol(card)=='J' or symbol(card)=='Q' or symbol(card)=='K':
        return 10
    else:
        return int(symbol(card))

def cardshuffler(cards):
    """Δέχεται μία λίστα με κάρτες (cards) και την ανακατεύει. Επιστρέφει την ανακατεμένη λίστα"""
    import random
    random.shuffle(cards)
    return cards

def tablemaker(cards,columns):
    """
    Δέχεται την λίστα με τις κάρτες (cards) και τον αριθμό των στηλών (columns) και 
    κατασκευάζει τον πίνακα ως μία λίστα απο λίστες όπου κάθε εσωτερική λίστα αντιστοιχεί σε μία γραμμή. Επιστρέφει τον πίνακα
    >>> cards=['2♣', '10♦', 'J♥', 'Α♥', '5♦', 'Q♣', '10♠', '9♥']
    >>> tablemaker(cards,2)
    [[' ', '1', '2'], ['1', '2♣', '10♦'], ['2', 'J♥', 'Α♥'], ['3', '5♦', 'Q♣'], ['4', '10♠', '9♥']]
    """
    table=[[' ']+[str(x) for x in range(1,columns+1)]] #Είναι η γραμμή που δείχνει τις στήλες
    n=1
    index=0
    while n<=4: #Οι γραμμές είναι πάντα 4
        tablerow=[str(n)] #Το πρώτο στοιχείο κάθε γραμμής δείχνει τον αριθμό της γραμμής 
        while index<columns*n: #Η λίστα cards έχει πάντα length n*columns 
            tablerow=tablerow+[cards[index]] 
            index=index+1
        table.append(tablerow) #Προστίθεται η γραμμή που κατασκευάστηκε στον πίνακα
        n=n+1 
    return table

def print_table(table,columns):
    """
    Εμφανίζει τον πίνακα table που έχει 4 γραμμές και columns στήλες με σωστή στοίχιση
    >>> cards=['2♣', '10♦', 'J♥', 'Α♥', '5♦', 'Q♣', '10♠', '9♥']
    >>> table=tablemaker(cards,2)
    >>> print_table(table,2)
         1    2    
    1    2♣   10♦  
    2    J♥   Α♥   
    3    5♦   Q♣   
    4    10♠  9♥   
    """
    for i in range(5):  
        for j in range(columns+1):
            print(table[i][j], end=(2+3-len(table[i][j]))*' ')    
        print('') #Αλλάζει η γραμμή κατά την εμφάνιση

def displayed_tablemaker(columns):
    """
    Δέχεται τον αριθμό των στηλών (columns) και κατασκευάζει τον πίνακα που εμφανίζεται στον χρήστη όταν όλες οι κάρτες είναι κλειστές('Χ')
    ως μία λίστα απο λίστες όπου κάθε εσωτερική λίστα αντιστοιχεί σε μία γραμμή. Επιστρέφει τον πίνακα
    >>> displayed_tablemaker(3)
    [[' ', '1', '2', '3'], ['1', 'Χ', 'Χ', 'Χ'], ['2', 'Χ', 'Χ', 'Χ'], ['3', 'Χ', 'Χ', 'Χ'], ['4', 'Χ', 'Χ', 'Χ']]
    """
    table=[[' ']+[str(x) for x in range(1,columns+1)]] #Είναι η γραμμή που δείχνει τις στήλες
    n=1
    while n<=4: #Οι γραμμές είναι πάντα 4
        tablerow=[str(n)] #Το πρώτο στοιχείο κάθε γραμμής δείχνει τον αριθμό της γραμμής
        i=1 
        while i<=columns: 
            tablerow=tablerow+['Χ']
            i=i+1
        table.append(tablerow) #Προστίθεται η γραμμή που κατασκευάστηκε στον πίνακα
        n=n+1
    return table

def opened(displayed_table,row,col):
    """
    Δέχεται τον αριθμό μιας γραμμής και μιας στήλης. 
    Ελέγχει αν η κάρτα που αντιστοιχεί σε αυτή τη γραμμή και στήλη είναι κλειστή('Χ') στον πίνακα που εμφανίζεται στον χρήστη.
    Επιστρέφει False αν η κάρτα είναι κλειστή και True αν η κάρτα είναι ανοιχτή.
    >>> displayed_table=[[' ', '1', '2', '3'], ['1', 'Χ', 'Χ', 'Χ'], ['2', 'Χ', 'Χ', 'Χ'], ['3', 'Χ', 'Χ', 'Χ'], ['4', 'Χ', 'Χ', 'Χ']]
    >>> opened(displayed_table,4,1)
    False
    >>> displayed_table[4][1]='2♣'
    >>> opened(displayed_table,4,1)
    True
    """ 
    if displayed_table[row][col]=='Χ':  
        return False
    else:
        return True

def rowcol_maker(columns):
    """
    Βρίσκει όλους τους έγκυρους πιθανούς συνδυασμούς "Γραμμή Στήλη" (πχ.1 4) και τους τοποθετεί σε λίστα. Επιστρέφει τη λίστα  
    >>> rowcol_maker(4)
    ['1 1', '1 2', '1 3', '1 4', '2 1', '2 2', '2 3', '2 4', '3 1', '3 2', '3 3', '3 4', '4 1', '4 2', '4 3', '4 4']
    """
    row=list(range(1,5))
    col=list(range(1,columns+1))
    row=[str(x) for x in row]
    col=[str(y) for y in col]
    rowcol=[x + ' ' + y for x in row for y in col]
    return rowcol

def find_card(columns,rowcol_possible,player,displayed_table,i):
    """
    Υλοποιεί την αλληλεπίδραση με τον παίκτη με έλεγχο εγκυρότητας. 
    Παίρνει ως είσοδο την γραμμή και την στήλη της κάρτας που επιθυμεί ο χρήστης και ελέγχει αν η είσοδος είναι έγκυρη,
    αναζητώντας τη στη λίστα με τους έγκυρους πιθανούς συνδυασμούς (rowcol_possible). 
    Ελέγχει επίσης το αν η κάρτα που επιλέγει να ανοίξει ο χρήστης είναι ήδη ανοιχτή με την συνάρτηση opened.
    """
    position=input('Παίκτης '+str(player)+': Δώσε γραμμή και στήλη '+str(i)+'ης κάρτας (π.χ 1 4)')
    while position not in rowcol_possible:
        position=input('Σφάλμα. Υπάρχει κι ένα όριο...Δώσε έγκυρη γραμμή και στήλη (π.χ 1 4)')
    x=position.split(' ') #Διαχωρίζει τη γραμμή απο τη στήλη
    row,col=int(x[0]),int(x[1])
    while opened(displayed_table,row,col):
        position=input('Σφάλμα. Η κάρτα είναι ήδη ανοιχτή...Δώσε νέα γραμμή και στήλη (π.χ 1 4)')
        while position not in rowcol_possible:
            position=input('Σφάλμα. Υπάρχει κι ένα όριο...Δώσε έγκυρη γραμμή και στήλη (π.χ 1 4)') 
        x=position.split(' ') #Διαχωρίζει τη γραμμή απο τη στήλη
        row,col=int(x[0]),int(x[1])
    return [row,col]

def sum_value(card1,card2):
    """
    Υπολογίζει τους πόντους που λαμβάνει ο παίκτης απο το επιτυχές ταίριασμα των card1 και card2 όταν παίζει 
    με την σπέσιαλ έκδοση του παιχνιδιού.
    >>> sum_value('10♦','3♦')
    13
    """
    return value(card1)+value(card2)

def history_queue(element,history):
    """
    Αντιμετώπιση του ιστορικού ως ουρά 5 στοιχείων. Προσθέτει ένα στοιχείο (element) στην λίστα (ουρά) history.
    >>> history=[]
    >>> history=history_queue(['10♦', 2, 3],history)
    >>> print(history)
    [['10♦', 2, 3]]
    >>> history=[['10♦', 2, 4], ['5♦', 1, 3], ['J♥', 3, 3], ['9♥', 4, 2]]
    >>> history=history_queue(['Α♦',3,2],history)
    >>> print(history)
    [['10♦', 2, 4], ['5♦', 1, 3], ['J♥', 3, 3], ['9♥', 4, 2], ['Α♦', 3, 2]]
    >>> history=history_queue(['2♣', 1, 1],history)
    >>> print(history)
    [['5♦', 1, 3], ['J♥', 3, 3], ['9♥', 4, 2], ['Α♦', 3, 2], ['2♣', 1, 1]]
    """
    if len(history)<5 and (element not in history): #Αν η λίστα δεν είναι γεμάτη και το στοιχείο δεν υπάρχει ήδη στην λίστα 
        history.append(element)
    else: 
        history.remove(history[0]) #FIFO λειτουργία της ουράς
        history.append(element)
    return history

def once_in_history(mode,history,card):
    """
    Ελέγχει αν στο ιστορικό (λίστα history) υπάρχει κάρτα (λίστα της μορφής [κάρτα,γραμμή,στήλη]) 
    που να ταιριάζει με την card με κριτήριο τη συνάρτηση function.
    >>> history=[['10♦', 2, 4], ['5♦', 1, 3], ['J♥', 3, 3], ['9♥', 4, 2]]
    >>> print(once_in_history(1,history,'10♥'))
    [True, ['10♦', 2, 4], 1]
    >>> print(once_in_history(2,history,'10♥'))
    [True, ['10♦', 2, 4], 1]
    >>> print(once_in_history(1,history,'K♦'))
    [False, [], 0]
    >>> print(once_in_history(2,history,'K♦'))
    [True, ['10♦', 2, 4], 2]
    >>> print(once_in_history(1,history,'10♦'))
    [False, [], 0]
    >>> print(once_in_history(1,history,'10♦'))
    [False, [], 0]
    >>> print(once_in_history(2,history,'6♥'))
    [True, ['J♥', 3, 3], 2]
    >>> history=[['10♦', 2, 4], ['3♣', 1, 3], ['J♥', 3, 3], ['5♥', 1, 1], ['9♥', 4, 2]]
    >>> print(once_in_history(2,history,'5♣'))
    [True, ['3♣', 1, 3], 2]
    """
    flag=False
    for x in history:
        z=function(mode,card,x[0])
        if z[0] and card!=x[0]: #Αν ταιριάζουν αλλά δεν ταυτίζονται
            element=x
            flag=True
            break
    if flag:    
        return [True,element,z[1]]
    else:
        return [False,[],0]

def twice_in_history(mode,history):
    """
    Ελέγχει αν στο ιστορικό (λίστα history) υπάρχουν 2 κάρτες (λίστες της μορφής [κάρτα,γραμμή,στήλη])
    που να ταιριάζουν μεταξύ τους με κριτήριο τη συνάρτηση function.
    >>> history=[['10♦', 2, 4], ['5♦', 1, 3], ['J♥', 3, 3], ['9♥', 4, 2]]
    >>> print(twice_in_history(1,history))
    [False, [], [], 0]
    >>> print(twice_in_history(2,history))
    [True, ['10♦', 2, 4], ['5♦', 1, 3], 2]
    >>> history=[['10♦', 2, 4], ['5♦', 1, 3], ['10♠', 1, 1], ['J♥', 3, 3], ['9♥', 4, 2]]
    >>> print(twice_in_history(1,history))
    [True, ['10♦', 2, 4], ['10♠', 1, 1], 1]
    >>> print(twice_in_history(2,history))
    [True, ['10♦', 2, 4], ['5♦', 1, 3], 2]
    >>> history=[['9♥', 4, 2]]
    >>> print(twice_in_history(1,history))
    [False, [], [], 0]
    >>> history=[]
    >>> print(twice_in_history(2,history))  
    [False, [], [], 0]
    """
    flag=False
    i=0
    while i<len(history):
        card1=history[i][0]
        for index in range(i+1,len(history)): 
            z=function(mode,card1,history[index][0])                 
            if z[0]:   
                card2=history[index][0]
                row1,col1,row2,col2=history[i][1],history[i][2],history[index][1],history[index][2]
                flag=True 
                break
        if flag: 
            break 
        i+=1
    if flag:
        return [True, [card1,row1,col1],[card2,row2,col2],z[1]]
    else:
        return [False, [],[],0]

def update_history(history,card,row,col):
    """
    Ανανεώνει το ιστορικό όταν παίζει ο παίκτης. 
    Αν η κάρτα που άνοιξε και ταίριαξε ο παίκτης υπάρχει στο ιστορικό αφαιρείται απο αυτό για να μην είναι διαθέσιμη 
    για τον υπολογιστή όταν έρθει η σειρά του.
    >>> history=[['10♦', 2, 4], ['5♦', 1, 3], ['J♥', 3, 3], ['9♥', 4, 2]]
    >>> history=update_history(history,'10♦',2,4)
    >>> print(history)
    [['5♦', 1, 3], ['J♥', 3, 3], ['9♥', 4, 2]]
    >>> history=update_history(history,'7♦', 3, 2)
    >>> print(history)
    [['5♦', 1, 3], ['J♥', 3, 3], ['9♥', 4, 2]]
    """
    if [card,row,col] in history:
        history.remove([card,row,col])
    return history

def QK_points(match_1_2,carda,cardb,score,player,criterium):
    """
    Αφορά την περίπτωση που έχουν ανοίξει 3 κάρτες και η 3η ταιριάζει με κάποια απο τις 2. 
    Η παράμετρος match_1_2 έχει τιμή True ή False ανάλογα με το αν οι 2 πρώτες κάρτες ταίριαξαν ή όχι. Η συνάρτηση υπολογίζει τους πόντους 
    ανάλογα με την τιμή της match_1_2 και η λίστα score ανανεώνεται κατάλληλα στη θέση που αντιστοιχεί στον παίκτη player.
    >>> score=[50,40,60]
    >>> score=QK_points(True,'K♦','10♦',score,2,2)
    Επιτυχές ταίριασμα +10 πόντοι! Παίκτη 2 έχεις συνολικά 50 πόντους!
    >>> score=QK_points(False,'Q♥','9♥',score,1,2)
    Επιτυχές ταίριασμα +19 πόντοι! Παίκτη 1 έχεις συνολικά 69 πόντους!
    >>> score=QK_points(True,'K♦','K♥',score,1,1)
    Επιτυχές ταίριασμα +10 πόντοι! Παίκτη 1 έχεις συνολικά 79 πόντους!
    >>> score=QK_points(False,'Q♠','Q♥',score,3,1)
    Επιτυχές ταίριασμα +10 πόντοι! Παίκτη 3 έχεις συνολικά 70 πόντους!
    """
    score[player-1]+=points_calcul(criterium,carda,cardb)
    if match_1_2 and criterium==2:
        score[player-1]-=10 
        print('Επιτυχές ταίριασμα +'+str(value(cardb))+' πόντοι! Παίκτη',player,'έχεις συνολικά',score[player-1],'πόντους!')
    else:
        print('Επιτυχές ταίριασμα +'+str(points_calcul(criterium,carda,cardb))+' πόντοι! Παίκτη',player,'έχεις συνολικά',score[player-1],'πόντους!')
    return score

def function(mode,card1,card2):
    """
    Δέχεται 2 κάρτες και το mode  του παιχνιδιού. Ελέγχει αν οι κάρτες ταιριάζουν είτε ως προς το σύμβολο (ανεξάρτητα του mode) είτε 
    ως προς τη σειρά όταν το mode είναι 2. Επιστρέφει μία λίστα 2 στοιχείων με 1ο στοιχείο μία λογική τιμή (δηλώνει το ταίριασμα ή μη)
    και 2ο στοιχείο μια αριθμητική τιμή (δηλώνει το κριτήριο με το οποίο επιτεύχθηκε το ταίριασμα). Αν οι κάρτες δεν ταίριαξαν το 2ο 
    στοιχείο είναι η τιμή 0.
    >>> print(function(2, 'Q♦','Q♥'))
    [True, 1]
    >>> print(function(2, 'Q♦','K♦'))
    [True, 2]
    >>> print(function(1, 'Q♦','K♦'))
    [False, 0]
    """
    if symbol(card1)==symbol(card2):
        return [True,1]
    elif series(card1)==series(card2) and mode==2:
        return [True,2]
    else:
        return [False,0]

def points_calcul(criterium,card1,card2):
    """
    Δέχεται 2 κάρτες που ταιριάζουν ως προς το κριτήριο criterium και υπολογίζει τους πόντους ανάλογα με το κριτήριο.
    Επιστρέφει τους πόντους 
    >>> points_calcul(1,'10♠','10♥')
    10
    >>> points_calcul(2,'5♠','8♠')
    13
    """
    if criterium==1:
        return value(card1)
    else:
        return sum_value(card1,card2) 

def game_over(mode,closed):
    """
    Ελέγχει τον τερματισμό του παιχνιδιού. Αν υπάρχουν 2 τουλάχιστον ταιριαστές κλειστές κάρτες επιστρέφει 
    False (δηλαδή δεν έχει τελειώσει το παιχνίδι), αλλιώς επιστρέφει True (δηλαδή το παιχνίδι έχει τελειώσει).
    Ο έλεγχος για την ύπαρξη ταιριαστών καρτών μεταξύ των κλειστών έχει νόημα στην περίπτωση που το mode=2, αφού αν
    mode=1 δεν είναι πιθανό να περισσέψουν αταίριαστες κάρτες. Ουσιαστικά, όταν το mode=1 το παιχνίδι τελειώνει μόλις 
    αδειάσει η λίστα closed.
    >>> closed=[['10♦',2,4],['5♦',1,3],['J♥',3,3],['9♥',4,2]]
    >>> game_over(2,closed)
    False
    >>> closed=[['10♦',2,4],['5♥',1,3],['J♠',3,3],['9♣',4,2]]
    >>> game_over(2,closed)
    True
    >>> closed=[]
    >>> game_over(1,closed)
    True
    >>> closed=[]
    >>> game_over(2,closed)
    True
    """
    if len(closed)<=4:
        #Αν οι κλειστές κάρτες είναι πάνω απο 4 τότε σίγουρα κάποιες θα ταιριάζουν μεταξύ τους ως προς τη σειρά (γιατί οι σειρές είναι 4)
        flag=True
        i=0
        while i<len(closed):
            card1=closed[i][0]
            for index in range(i+1,len(closed)): 
                z=function(mode,card1,closed[index][0])                 
                if z[0]:   
                    flag=False #Βρέθηκαν 2 ταιριαστές, άρα το παιχνίδι δεν έχει τελειώσει
                    break
            if not flag: 
                break 
            i+=1
        return flag
    return False #Αν υπάρχουν πάνω απο 4

def winner(score,players):
    """
    Εμφανίζει τον/τους νικητή/-ες με βάση τη λίστα score. Σε περίπτωση ισοβαθμίας εμφανίζει κατάλληλο μήνυμα.
    >>> score=[60,60,50]
    >>> winner(score,3)
    Συγχαρητήρια Παίκτη 1! Είσαι νικητής του Matching Game!!!
    Συγχαρητήρια Παίκτη 2! Είσαι νικητής του Matching Game!!!
    Ισοπαλία λοιπόν...
    >>> score=[40,70]
    >>> winner(score,2)
    Συγχαρητήρια Παίκτη 2! Είσαι νικητής του Matching Game!!!
    """
    maximum=max(score)
    n=0
    for i in range(0,players):
        if score[i]==maximum:
            n+=1
            print('Συγχαρητήρια Παίκτη '+str(i+1)+'! Είσαι νικητής του Matching Game!!!')
    if n>1: #Αν βρέθηκαν περισσότεροι απο έναν νικητές
        print('Ισοπαλία λοιπόν...')  