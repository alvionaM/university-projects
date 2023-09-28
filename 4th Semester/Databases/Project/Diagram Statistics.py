import psycopg2
import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d.axes3d import Axes3D

# Connection information
host = "db-projectsrv2022.postgres.database.azure.com"
dbname = "postgres"
user = "examiner@db-projectsrv2022"
password = "DBexaminer"
sslmode = "require"

# Construct connection string
conn_string = "host={0} user={1} dbname={2} password={3} sslmode={4}".format(host, user, dbname, password, sslmode)
conn = psycopg2.connect(conn_string)

print("Connection established")

cursor = conn.cursor()



#QUERY 1 -------------------------------------
query1 = """select extract(year from release_date) as "Year", count(*) as "Num of Movies"
            from movie m 
            group by "Year"
            having extract(year from release_date) is not null
            order by "Year\""""

cursor.execute(query1)
rows = cursor.fetchall()

query1_data = np.array(rows).transpose()
x1 = query1_data[0]
y1 = query1_data[1]

plt.figure(1)
plt.bar(x1, y1)
plt.xlabel("Year")
plt.ylabel("Num of Movies")
plt.title("Number of Movies per Year", c = '#483C32', loc='right')
plt.grid(axis = 'y')

#plt.savefig('plot1.png')
#plt.close()



#QUERY 2 -------------------------------------
query2 = """select g."name" as "Genre", mid."Num of Movies"::numeric 
            from 	(select mg.genre_id , count(distinct movie_id) as "Num of Movies"
                    from movie_genres mg 
                    group by genre_id 
                    ) mid
            join genre g 
            on mid.genre_id = g.id
            order by "Genre\""""

cursor.execute(query2)
rows = cursor.fetchall()

query2_data = np.array(rows).transpose()
x2 = query2_data[0]
y2 = query2_data[1]

plt.figure(2, figsize = (9.4, 7))
plt.bar(x2, y2, color = '#EC8993', alpha=0.5)
plt.xlabel("Genre")
plt.ylabel("Num of Movies")
plt.title("Number of Movies per Genre", color ='#4E5B31', loc='right')
plt.grid(axis = 'y')

#plt.savefig('plot2.png')
#plt.close()



#QUERY 3 -------------------------------------
query3 = """select distinct extract(year from m.release_date)::numeric 
            as "Year", g."name" as "Genre", count(m.id) as "Num of Movies"
            from movie m 
            join movie_genres mg 
            on m.id = mg.movie_id 
            join genre g 
            on mg.genre_id = g.id 
            group by "Year", g."name"  
            having extract(year from m.release_date)::numeric is not null
            order by "Year\""""

cursor.execute(query3)
rows = cursor.fetchall()


query3_data = np.array(rows).transpose()
x3 = query3_data[0] #Year
y3 = query3_data[1] #Ganre
z3 = query3_data[2] #Num of Movies

# Initializing Figure
fig = plt.figure(3, figsize =(13,7))
ax1 = fig.add_subplot(111, projection='3d')
ax1.set_facecolor((1.0, 1.0, 1.0))

#Years dictionary
xYears = x3
i=0
xDict = {}
x = []
for year in xYears:
  if year not in xDict:
    xDict[year] = i
    x.append(i)
    i+=1
  else:
    x.append(xDict[year])


#Genres dictionary
yGenres = y3
i=0
yDict = {}
y = []
for genre in yGenres:
  if genre not in yDict:
    yDict[genre] = i
    y.append(i)
    i+=1
  else:
    y.append(yDict[genre])

# Defining the starting position of each bar
z = np.zeros(len(y))

# Defining the length/width/height of each bar.
dx = np.ones(len(y))*0.5
dy = np.ones(len(y))*0.1
dz = z3

ax1.bar3d(x, y, z, dx, dy, dz) 

# Setting scaling and axes aspect ratio
ax1.get_proj = lambda: np.dot(Axes3D.get_proj(ax1), np.diag([1.5, 1.1, 1, 1]))
ax1.set_zlim([0, max(z3)])

plt.xticks(range(len(xDict.values())), (' ' if x % 10!=0  else str(x) for x in xDict.keys()))
plt.yticks(range(len(yDict.values())), yDict.keys())

plt.xlabel("Years", color="#A50B5E")
plt.ylabel("Genres", color="#A50B5E")
plt.title("Number of Movies produced per Year and Genre", color="#A50B5E", loc = 'right')

#plt.savefig('plot3.png')
#plt.close()



#QUERY 4 -------------------------------------
query4 = """select extract(year from release_date) as "Year", max(m.budget) as "Max Budget"
            from movie m 
            group by "Year" 
            having extract(year from release_date) is not null and max(m.budget) > 0
            order by "Year" desc"""

cursor.execute(query4)
rows = cursor.fetchall()

query4_data = np.array(rows).transpose()
x4 = query4_data[0]
y4 = query4_data[1]


plt.figure(4)
plt.plot(x4, y4, color = '#835C3B')
plt.xlabel("Year")
plt.ylabel("Max Budget")
plt.title("Maximum Budget per Year", c = '#3B6182', loc = 'right')
plt.grid(axis = 'y')

#plt.savefig('plot4.png')
#plt.close()



#QUERY 5 -------------------------------------
query5 = """select extract(year from m.release_date) as "Year", sum(m.revenue) as "Total Revenue"
            from(
                select mc.movie_id 
                from movie_cast mc 
                where mc.person_id = (
                    select p.id 
                    from person p 
                    where p."name" = 'Mel Gibson')
                )mid	
            join movie m 
            on m.id = mid.movie_id
            group by "Year"
            having sum(m.revenue) > 0
            order by "Year" desc""" 

cursor.execute(query5)
rows = cursor.fetchall()


query5_data = np.array(rows).transpose()
x5 = query5_data[0]
y5 = query5_data[1]

plt.figure(5)
plt.plot(x5, y5, color = '#61823B', label = 'Mel Gibson')
plt.xlabel("Year")
plt.ylabel("Total Revenue")
plt.title("Total Revenue per Year", c = '#5C3B82', loc = 'right')
plt.grid(axis = 'y')
plt.legend()

#plt.savefig('plot5.png')
#plt.close()



#QUERY 6 -------------------------------------
query6 = """select user_id as "User", round(avg(rating)::numeric, 2) as "Average Rating"
            from ratings r 
            group by user_id 
            order by user_id """

cursor.execute(query6)
rows = cursor.fetchall()

query6_data = np.array(rows).transpose()
x6 = query6_data[0]
y6 = query6_data[1]

plt.figure(6)
plt.scatter(x6, y6, color = '#61923C')
plt.xlabel("User ID")
plt.ylabel("Average Rating")
plt.title("Average Rating per User", c = '#5C3F82', loc = 'right')
plt.grid()

#plt.savefig('plot6.png')
#plt.close()



#QUERY 7 -------------------------------------
query7 = """select user_id as "User", count(rating) as "Num of Ratings"
            from ratings r 
            group by user_id 
            order by user_id """

cursor.execute(query7)
rows = cursor.fetchall()

query7_data = np.array(rows).transpose()
x7 = query7_data[0]
y7 = query7_data[1]


plt.figure(7)
plt.scatter(x7, y7, color = 'm', marker = 'h', alpha = 0.3)
plt.xlabel("User ID")
plt.ylabel("Num of Ratings")
plt.title("Number of Ratings per User", c = '#5C5F81', loc = 'right')
plt.grid()

#plt.savefig('plot7.png')
#plt.close()



#QUERY 8 -------------------------------------

#Xrhsimopoioume ta dedomena apo ta queries 6 kai 7

plt.figure(8)
plt.scatter(y7, y6, color = '#F67280')
plt.xlabel("Num of Ratings")
plt.ylabel("Average Rating")
plt.title("Statistics per User", c = '#993792', loc = 'right')
plt.grid()

#plt.savefig('plot8.png')
#plt.close()



#QUERY 9 -------------------------------------
query9 = """select g."name" as "Genre", round(mid2."Average Rating"::numeric, 2) as "Average Rating"
            from genre g 
            join (
                    select mg.genre_id, avg(Rating) as "Average Rating"
                    from movie_genres mg
                    join (
                        select movie_id, avg(r.rating) as Rating
                        from ratings r 
                        group by r.movie_id
                    ) as mid
                    on mg.movie_id = mid.movie_id
                    group by mg.genre_id
            )mid2
            on g.id = mid2.genre_id"""

cursor.execute(query9)
rows = cursor.fetchall()

query9_data = np.array(rows).transpose()
x9 = query9_data[0]
y9 = query9_data[1]

plt.figure(9, figsize=(9.4, 7))
plt.bar(x9, y9, color = '#957DAD', alpha = 0.8)
plt.ylim((3.0, 3.7))
plt.xlabel("Genre")
plt.ylabel("Average Rating")
plt.title("Average Rating per Genre", loc = 'right')
plt.grid(axis = 'y')

#plt.savefig('plot9.png')
#plt.close()



plt.show()