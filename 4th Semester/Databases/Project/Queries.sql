--1. Αριθμός ταινιών ανά έτος.
--rows: 97
select extract(year from release_date) as "Year", count(*) as "Num of Movies"
from movie m 
group by "Year"
having extract(year from release_date) is not null
order by "Year"


--2. Αριθμός ταινιών ανά είδος.
--rows: 9
select g."name" as "Genre", mid."Num of Movies"::numeric
from 	(select mg.genre_id , count(distinct movie_id) as "Num of Movies"
		from movie_genres mg 
		group by genre_id 
		) mid
join genre g 
on mid.genre_id = g.id
order by "Genre"


--3. Αριθμός ταινιών ανά είδος (genre) και ανά έτος.
--rows: 674
select distinct extract(year from m.release_date)::numeric as "Year", g."name" as "Genre", count(m.id) as "Num of Movies"
from movie m 
join movie_genres mg 
on m.id = mg.movie_id 
join genre g 
on mg.genre_id = g.id 
group by "Year", g."name"  
having extract(year from m.release_date)::numeric is not null
order by "Year"


--4. Το υψηλότερο budget ταινίας ανά έτος (δεν μας ενδιαφέρει για ποια ταινία)
--rows: 90
select extract(year from release_date) as "Year", max(m.budget) as "Max Budget"
from movie m 
group by "Year" 
having extract(year from release_date) is not null and max(m.budget) > 0
order by "Year" desc


--5. Για τον αγαπημένο σας ηθοποιό, το σύνολο των εσόδων (revenue) για τις ταινίες στις οποίες έχει
--συμμετάσχει ανά έτος
--rows: 18
select extract(year from m.release_date) as "Year", sum(m.revenue) as "Total Revenue"
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
order by "Year" desc


--6.Μέση βαθμολογία (rating) ανά χρήστη (scatter plot).
--rows: 671
select user_id as "User", round(avg(rating)::numeric, 2) as "Average Rating"
from ratings r 
group by user_id 
order by user_id 


--7. Αριθμός από βαθμολογίες ανά χρήστη (scatter plot)
--rows: 671
select user_id as "User", count(rating) as "Num of Ratings"
from ratings r 
group by user_id 
order by user_id 


--9. Μέση βαθμολογία (rating) ανά είδος ταινίας.
--rows: 9
select g."name" as "Genre", round(mid2."Average Rating"::numeric, 2) as "Average Rating"
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
on g.id = mid2.genre_id