/* Query 1
Emfanish tainiwn kathe eidous me avg rating > 4.5
columns: Genre, Movie, Rating
rows: 57
*/

select g.name as Genre, m.title as Movie, middle.rating
from genre g, 
	 movie m,
	(
		select distinct mg.genre_id as gid, mr.Rating, mg.movie_id as mid
		from movie_genres mg
		join (
			select movie_id, avg(r.rating) as Rating
			from ratings r 
			group by r.movie_id
			having avg(r.rating) > 4.5
			order by r.movie_id
		) as mr
		on mg.movie_id = mr.movie_id
		order by mg.genre_id 
	) middle
where g.id = middle.gid and m.id = middle.mid



/* Query 2
Emfanish tainiwn pou periexoun ta keywords {violence, sadistic, torture, terrorism, killer, massacre, creulty}
kai vghkan th dekaetia tou 90, se aukousa seira kata hmeromhnia
columns: movie, release date, overview
rows: 267
*/

select distinct m.title as movie, m.release_date as "Release Date", m.overview
from movie_keywords mk, 
	(
		select k.keyword_id
		from keywords k 
		where k.name like '%violence%' or k.name like '%sadistic%' or k.name like '%murder%'
				or k.name in ('torture', 'terrorism', 'killer', 'massacre', 'cruelty')
	) k,
	(
		select id, title, release_date, overview 
		from movie m 
		where release_date between '1990-01-01' and '1999-12-31'
	) m
where k.keyword_id = mk.keyword_id and m.id = mk.movie_id 
order by m.release_date 



/* Query 3
Emfanish tainiwn stis opoies o skinotheths paizei kapoio rolo, taksinomhmenh kata ono skhnotheth
columns: movie, director, character
rows: 993
*/

select m.title as movie, mc.name as director, mc.character
from movie_cast mc
join movie m 
on m.id = mc.movie_id 
where exists (
		select mc2.person_id 
		from movie_crew mc2 
		where mc2.job = 'Director' and mc2.movie_id = mc.movie_id and mc2.person_id = mc.person_id
	) 
order by director 



/* Query 4
Emfanish sullogwn se auksousa taksinomhsh kata kerdos (revenue - budget) twn tainiwn ths kathe sulloghs.
Oi tainies me pedia budget=0 h revenue=0 den prosmetrontai. Emfanish arithmou tainiwn kathe sulloghs
pou prosmetrithikan.
Oi sulloges pou den eixan kamia tainia me mh mhdenika dedomena kai oses den eixan kamia tainia sth vash 
emfanizontai ston pinaka alla antistoixizontai se null.
columns: collection, # of movies, profit
rows: 1695
*/

select c.name as Collection, a."# of movies", a.profit 
from collection c 
left outer join (
        select count(*) as "# of movies", mc.collection_id, sum(m.revenue)-sum(m.budget) as profit
        from movie m
        join movie_collection mc
        on m.id = mc.movie_id 
        where m.budget <> 0 and m.revenue <> 0
        group by mc.collection_id 
	) a
on c.id = a.collection_id 
order by profit, collection



/* Query 5
Emfanish mh-agglikwn tainiwn, kathgorias 'Drama', pou bghkan meta to 2000
Taksinomhsh kata glwssa kai titlo
columns: original title, title, language, release date, overview
rows: 332
*/

select m.original_title as "original title", m.title, m.original_language as "language", m.release_date as "release date", m.overview 
from movie m
join (
		select mg.movie_id
		from movie_genres mg, genre g 
		where mg.genre_id = g.id and g.name = 'Drama'
	 ) r
on m.id = r.movie_id 
where m.original_language <> 'en' and release_date >= '2000-01-01'
order by original_language, title



/* Query 6
Emfanish etairiwn paragwghs me sunoliko budget tainiwn pou exoun paraksei > 2*10^8
se fthinousa taksinomhsh ws pros auto, kai tou arithmou twn tainiwn pou exoun paraksei.
columns: production company, total budget, # of movies produced
rows: 166
*/

select p.name as "Production Company", r.totalBudget as "Total Budget", r."# of movies produced"
from productioncompany p 
join ( 
		select mp.pc_id, sum(m.budget) as totalBudget, count(*) as "# of movies produced"
		from movie_productioncompanies mp
		join movie m 
		on mp.movie_id = m.id 
		group by mp.pc_id  
		having sum(m.budget) > 200000000
	 ) r
on p.id = r.pc_id  
order by totalBudget desc 



/* Query 7
Emfanish tainiwn mikrou mhkous ( < 40 lepta ) pou elavan megisth kritikh pano apo 3
se fthinousa taksinomhsh kata rating kai auksousa kata runtime
columns: movie, max rating, runtime, overview
rows: 6
*/

select m.title as movie, a.rating as "max rating", m.runtime, m.overview 
from movie m
join (
		select movie_id, max(r.rating) as rating
		from ratings r 
		group by r.movie_id
		having max(r.rating) > 3
		order by r.movie_id
	 ) a
on m.id = a.movie_id 
where m.runtime <= '40' and m.runtime like '__.0'
order by "max rating" desc, runtime asc



/* Query 8
Emfanish tainiwn stis opoies exoun paiksei mazi 2 sugkekrimenoi hthopoioi
columns: movie, tagline, release date
rows: 3
*/

select m.title as movie, m.tagline, m.release_date as "release date"
from movie m
join (
		select mc.movie_id 
		from movie_cast mc 
		where mc.name = 'Rupert Grint'
		intersect 
		select mc2.movie_id
		from movie_cast mc2 
		where mc2.name = 'Daniel Radcliffe'
	 ) a
on m.id = a.movie_id  


/* Query 9
Emfanish tainiwn me ton idio titlo poy einai remakes (idioi h sxedon idioi xarakthres) --> argei alla bgazei apotelesma!!!
columns: movie, release date, overview
rows: 206
*/
select distinct m1.title as movie, m1.release_date as "release date", m1.overview 
from movie m1 
join movie m2 
on m1.title = m2.title and m1.id <> m2.id
where exists (
		select mc.character 	
		from movie_cast mc 
		where m1.id = mc.movie_id 
	intersect  
		select mc2.character 	
		from movie_cast mc2 
		where m2.id = mc2.movie_id 
	)
order by m1.title, m1.release_date

