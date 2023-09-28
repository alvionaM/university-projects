import csv
import ast

# Use the absolute path to the folder containing keywords.csv
# e.g. [PATH] = C:\\Users\\user\\Desktop...

#opening given csv file to read
file = open("[PATH]\\keywords.csv", 'r', encoding='utf8')
csvreader = csv.reader(file)
header = next(csvreader)


#creating new csv files to write 
# 1)  -- movie_keywords
mk = open("[PATH]\\movie_keywords.csv", "w", encoding='utf8')
mk_writer = csv.writer(mk, lineterminator='\n')
mk_writer.writerow(["movie_id", "keyword_id"])


# 2)  -- keywords2
k = open("[PATH]\\keywords2.csv", "w", encoding='utf8')
k_writer = csv.writer(k, lineterminator='\n')
k_writer.writerow(["keyword_id", "name"])


set_of_kw = set()  #to eliminate duplicate values
dict_of_kw = {}    #to store information (i.e. the other cols/attributes) for every (unique) keyword 

for row in csvreader:

    data = ast.literal_eval(row[1])     # row[1] is in JSON format
    
    for d in data:

        #Movie_Keywords
        #row[0], d['id']

        mk_writer.writerow([row[0], d['id']])

        #Keywords
        #d['id'], d['name']

        set_of_kw.add(d['id'])
        dict_of_kw[d['id']] = d['name']

for k_id in set_of_kw:
    k_writer.writerow([k_id, dict_of_kw[k_id]])
    
file.close()
mk.close()
k.close()