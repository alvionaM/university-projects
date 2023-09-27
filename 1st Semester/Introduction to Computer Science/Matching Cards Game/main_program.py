from functions import *
"""
    Alviona Mancho:             3200098
    Miltiadis Tsolkas:          3200213
    Charalampos Karakostas:     3200065

Απαντήθηκαν όλα τα ερωτήματα της εργασίας (και τα bonus)
Σημείωσεις-Σχόλια:
1. Θεωρούμε προτιμότερο ακόμη και στην περίπτωση που 3 κάρτες ταιριάζουν (ως προς τη σειρά--περίπτωση Q,K) να μην μένουν ανοικτές
και οι 3 κάρτες. Στην υλοποίηση μας κλείνει πάντα η 2η κάρτα. Πάντως ο παίκτης λαμβάνει τους πόντους που 
αναλογούν και στις 3 ταιριαστές κάρτες.

2. Όταν έχει επιλεχθεί το 2ο mode του παιχνιδιού ο έλεγχος στο ιστορικό του υπολογιστή για την ύπαρξη ταιριαστών καρτών 
γίνεται συγχρόνως με κριτήριο το σύμβολο και τη σειρά της κάρτας (χωρίς προτεραιότητα στο σύμβολο ή τη σειρά). Συνεπώς επιστρέφεται 
η κάρτα (συνάρτηση once_in_history) ή οι κάρτες (συνάρτηση twice_in_history) που ικανοποίησαν πρώτες το 1 απο τα 2 κριτήρια.

3.Υπάρχει το ενδεχόμενο στο 2ο mode να περισσέψουν κάρτες στο τέλος, οι οποίες δεν μπορούν να ταιριάξουν μεταξύ τους. 
Για να αντιμετωπιστεί το πρόβλημα της μη λήξης του παιχνιδιού χρησιμοποιείται η συνάρτηση game_over 
με την οποία το παιχνίδι τερματίζεται αν δεν υπάρχουν άλλες κλειστές ταιριαστές κάρτες.
"""
print('The Matching Game')
#Καθορισμός πλήθους παίκτων
players=int(input('Πόσοι παίκτες θα παίξουν;(Δώσε αριθμό)'))
while players<=0:
    players=int(input('Σφάλμα. Δώσε έγκυρο αριθμό παικτών, μην έχεις αρνητική διάθεση'))
if players==1: #Ο παίκτης θα παίξει με αντίπαλο τον υπολογιστή  
    players=2
    computer=True
    print('Μόλις προκάλεσες τον Mr. Bot... Mr. Bot είσαι ο παίκτης 2!') 
else:
    computer=False
#Καθορισμός τρόπου υλοποίησης παιχνιδιού
mode=input('Επίλεξε πώς θες να παίξεις Κλασικό(1), Σπέσιαλ έκδοση(2):')
while mode!='1' and mode!='2':
    mode=input('Σφάλμα. Αυτό το mode δεν υπάρχει...ακόμα. Δώσε έγκυρο mode')
mode=int(mode)
#Καθορισμός επιπέδου δυσκολίας
level=input('Επίλεξε επίπεδο δυσκολίας Εύκολο (1), Μέτριο (2), Δύσκολο (3):')
while level!='1' and level!='2' and level!='3':
    level=input('Σφάλμα. Το έχεις πάει σε άλλο level...Δώσε έγκυρο επίπεδο δυσκολίας')
level=int(level)
if level==1:
    cards=cardmaker1()
elif level==2:
    cards=cardmaker2()
else:
    cards=cardmaker3()
columns=len(cards)//4 #Οι στήλες καθορίζονται με βάση το επίπεδο δυσκολίας
rowcol_possible=rowcol_maker(columns)
cards=cardshuffler(cards) 
table=tablemaker(cards,columns)
if computer:
    history=[]
closed=[[table[i][j],i,j] for i in range(1,5) for j in range(1,columns+1)] #Περιέχει όλες τις κλειστές κάρτες
displayed_table=displayed_tablemaker(columns)
print_table(table,columns)
#Αρχικοποίηση score
#Επειδή ο δείκτης της λίστας score ξεκινά απο 0 αντιστοιχούμε στον παίκτη 1 την θέση 0 της λίστας.
#Έτσι, κάθε φορά στον παίκτη player αντιστοιχεί η θέση player-1 της λίστας score.
i=1
score=[] 
while i<=players:
    score.append(0)
    i+=1
player=1
while not game_over(mode,closed): 
    print_table(displayed_table,columns)
    if computer and player==1 or not computer:
        #Πρώτη κάρτα 
        rowcol=find_card(columns,rowcol_possible,player,displayed_table,1)
        row1,col1=rowcol[0],rowcol[1]
        card1=table[row1][col1]
        displayed_table[row1][col1]=card1
        closed.remove([card1,row1,col1])
        print_table(displayed_table,columns)
        #Δεύτερη κάρτα 
        rowcol=find_card(columns,rowcol_possible,player,displayed_table,2)
        row2,col2=rowcol[0],rowcol[1]
        card2=table[row2][col2]
        displayed_table[row2][col2]=card2
        closed.remove([card2,row2,col2])
        print_table(displayed_table,columns)        
        flag=True #Ελέγχει αν υπάρχει ταίριασμα μεταξύ των καρτών
        z=function(mode,card1,card2)
        if z[0]:
            score[player-1]+=points_calcul(z[1],card1,card2)  
            print('Επιτυχές ταίριασμα +'+str(points_calcul(z[1],card1,card2))+' πόντοι! Παίκτη',player,'έχεις συνολικά',score[player-1],'πόντους!')
            flag=False
            if computer:
                #Αν έχουμε ένα παίκτη και επιτυχές ταίριασμα το ιστορικό του υπολογιστή ενημερώνεται κατάλληλα
                history=update_history(history,card1,row1,col1) 
                history=update_history(history,card2,row2,col2)
        else:
            print('Ανεπιτυχές Ταίριασμα...')
        if (symbol(card1)=='Q' and symbol(card2)=='K' or symbol(card1)=='K' and symbol(card2)=='Q') and len(closed)!=0:
            print('Μόλις απέκτησες την δυνατότητα να ανοίξεις τρίτη κάρτα!')
            print_table(displayed_table,columns)
            #Τρίτη κάρτα
            rowcol=find_card(columns,rowcol_possible,player,displayed_table,3)
            row3,col3=rowcol[0],rowcol[1]
            card3=table[row3][col3]
            displayed_table[row3][col3]=card3
            closed.remove([card3,row3,col3])
            print_table(displayed_table,columns)
            l=function(mode,card1,card3) 
            k=function(mode,card2,card3)
            if l[0]:
                displayed_table[row2][col2]='Χ'     
                closed.append([card2,row2,col2]) 
                score=QK_points(z[0],card1,card3,score,player,l[1])
                flag=False
                if computer:
                    #Αν έχουμε ένα παίκτη και επιτυχές ταίριασμα το ιστορικό του υπολογιστή ενημερώνεται κατάλληλα
                    history=update_history(history,card1,row1,col1)
                    history=update_history(history,card3,row3,col3)
            elif k[0]:
                displayed_table[row1][col1]='Χ'
                closed.append([card1,row1,col1])
                score=QK_points(z[0],card2,card3,score,player,k[1])
                flag=False
                if computer:
                    #Αν έχουμε ένα παίκτη και επιτυχές ταίριασμα το ιστορικό του υπολογιστή ενημερώνεται κατάλληλα
                    history=update_history(history,card2,row2,col2)
                    history=update_history(history,card3,row3,col3)
            else:
                #Η τρίτη κάρτα δεν ταιριάζει με καμία απο τις 2 προηγούμενες. Κλείνει η 3η κάρτα
                displayed_table[row3][col3]='Χ'
                closed.append([card3,row3,col3]) 
                print('Ανεπιτυχές Ταίριασμα...')                           
        if flag: #Δεν υπάρχει ταίριασμα. Κλείνουν οι 2 κάρτες
            displayed_table[row1][col1]='Χ'
            displayed_table[row2][col2]='Χ'
            closed.extend([[card1,row1,col1],[card2,row2,col2]])
    else:
        #Σειρά του υπολογιστή (όταν υπάρχει ένας παίκτης)
        closed=cardshuffler(closed)
        print('Mr. Bot διάλεξε δύο φύλλα!')
        flag=True #Ελέγχει αν υπάρχει ταίριασμα μεταξύ των καρτών
        #Αναζήτηση στο ιστορικό για 1η και 2η κάρτα
        ls=twice_in_history(mode,history)
        if ls[0]==True: #Βρέθηκαν δύο ταιριαστές κάρτες στο ιστορικό 
            #Πρώτη κάρτα
            card1,row1,col1=ls[1][0], ls[1][1], ls[1][2]
            displayed_table[row1][col1]=card1
            print_table(displayed_table,columns)
            print()
            #Δεύτερη κάρτα
            card2,row2,col2=ls[2][0], ls[2][1], ls[2][2]
            displayed_table[row2][col2]=card2 
            print_table(displayed_table,columns)
            history.remove([card1,row1,col1])
            history.remove([card2,row2,col2])
            closed.remove([card2,row2,col2])
            closed.remove([card1,row1,col1])
            score[1]+=points_calcul(ls[2],card2,card1)   
            flag=False
            print('Επιτυχές ταίριασμα +'+str(points_calcul(ls[2],card1,card2))+' πόντοι! Παίκτη',player,'έχεις συνολικά',score[player-1],'πόντους!')
        else:
            #Ανοίγει τυχαία πρώτη κάρτα
            card1=closed[0][0]
            row1,col1=closed[0][1],closed[0][2] 
            closed.remove(closed[0]) 
            displayed_table[row1][col1]=card1
            print_table(displayed_table,columns) 
            print()
            #Αναζήτηση στο ιστορικό για 2η κάρτα
            ls2=once_in_history(mode,history,card1)
            if ls2[0]==True:
                #Δεύτερη κάρτα
                card2,row2,col2=ls2[1][0],ls2[1][1],ls2[1][2] 
                displayed_table[row2][col2]=card2
                print_table(displayed_table,columns)
                history.remove([card2,row2,col2])
                closed.remove([card2,row2,col2])
                score[1]+=points_calcul(ls[2],card2,card1)
                flag=False
                print('Επιτυχές ταίριασμα +'+str(points_calcul(ls[2],card1,card2))+' πόντοι! Παίκτη',player,'έχεις συνολικά',score[player-1],'πόντους!')
            else:
                #Ανοίγει τυχαία δεύτερη κάρτα
                card2=closed[0][0]
                row2,col2=closed[0][1],closed[0][2]
                displayed_table[row2][col2]=card2
                closed.remove([card2,row2,col2]) 
                print_table(displayed_table,columns) 
                z=function(mode,card1,card2)
                if z[0]:
                    displayed_table[row2][col2]=card2
                    score[1]+=points_calcul(z[1],card2,card1)
                    flag=False
                    print('Επιτυχές ταίριασμα +'+str(points_calcul(z[1],card1,card2))+' πόντοι! Παίκτη',player,'έχεις συνολικά',score[player-1],'πόντους!')       
        if (symbol(card1)=='Q' and symbol(card2)=='K' or symbol(card1)=='K' and symbol(card2)=='Q') and len(closed)!=0:
            print('Mr. Bot μόλις απέκτησες την δυνατότητα να ανοίξεις τρίτη κάρτα!')
            #Αναζήτηση στο ιστορικό για 3η κάρτα
            flag1=False 
            for x in history:
                l=function(mode,card1,x[0])
                k=function(mode,card2,x[0])
                if l[0] or k[0]:
                    flag1=True 
                    #Τρίτη κάρτα
                    card3,row3,col3=x[0],x[1],x[2] 
                    displayed_table[row3][col3]=card3
                    print_table(displayed_table,columns)
                    history.remove([card3,row3,col3])
                    closed.remove([card3,row3,col3])
                    break  
            if not flag1:
                #Ανοίγει τυχαία τρίτη κάρτα
                card3=closed[0][0]
                row3,col3=closed[0][1],closed[0][2]
                displayed_table[row3][col3]=card3
                closed.remove([card3,row3,col3])
                print_table(displayed_table,columns)    
            if l[0]:
                displayed_table[row2][col2]='Χ'
                closed.append([card2,row2,col2])
                history_queue([card2,row2,col2],history)  
                score=QK_points(flag,card1,card3,score,player,l[1]) 
                flag=False
            elif k[0]:
                displayed_table[row1][col1]='Χ'
                closed.append([card1,row1,col1])
                history_queue([card1,row1,col1],history)
                score=QK_points(flag,card2,card3,score,player,k[1])
                flag=False
            else:
                #Η τρίτη κάρτα δεν ταιριάζει με καμία απο τις 2 προηγούμενες. Κλείνει η 3η κάρτα
                displayed_table[row3][col3]='Χ'
                closed.append([card3,row3,col3])
                history_queue([card3,row3,col3],history) 
                print('Ανεπιτυχές Ταίριασμα 3ης κάρτας...')
        if flag: #Δεν υπάρχει ταίριασμα. Κλείνουν οι 2 κάρτες
            displayed_table[row1][col1]='Χ'
            displayed_table[row2][col2]='Χ'
            print('Ανεπιτυχές Ταίριασμα...')    
            closed.extend([[card1,row1,col1],[card2,row2,col2]]) 
            history_queue([card1,row1,col1],history)
            history_queue([card2,row2,col2],history)    
    #Καθορισμός επόμενου παίκτη
    if not game_over(mode,closed):
        if symbol(card1)==symbol(card2)=='J': #Ειδική κάρτα
            print('Συγχαρητήρια! Η τύχη σού χαμογέλασε...Ξαναπαίζεις!') 
        elif symbol(card1)==symbol(card2)=='K': #Ειδική κάρτα
            if player==players: 
                player=2
                print('Παίκτη 1 υπομονή και θα έρθει και η δική σου σειρά...Προς το παρόν την έχασες!') 
            elif player==players-1:
                player=1
                print('Παίκτη',players,'υπομονή και θα έρθει και η δική σου σειρά...Προς το παρόν την έχασες!')
            else:
                player+=2
                print('Παίκτη',player-1,'Υπομονή και θα έρθει και η δική σου σειρά...Προς το παρόν την έχασες!')    
        else:
            player+=1
            if player>players:
                player=1
if len(closed)>0:
    print('Δεν υπάρχουν άλλες ταιριαστές κάρτες...Το παιχνίδι τελείωσε')
winner(score,players) #Καθορισμός νικητή/-τών