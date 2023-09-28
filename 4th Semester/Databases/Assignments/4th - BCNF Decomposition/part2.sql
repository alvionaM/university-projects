/* Emfanise tis tainies stis opoies o Ben Affleck exei analabei kapoion rolo, eite 
 ws hthopoios eite ws melos tou plhrwmatos, kathws epishs kai thn hmeromhnia kukloforias ths tainias
 kai ton antistoixo rolo 
       -->(xarakthras pou upoduetai an summeteixe san hthopoios 'h douleia pou eixe analabei diaforetika).
 Taksinomhse ta apotelesmata kata fthinousa seira ws pros thn hmeromhnia kukloforias twn tainiwn.

 columns: Movie, Role in movie, Release Date
 rows: 32
*/
select m.title as "Movie", actorMovies.role as "Role in movie", m.release_date as "Release Date"
from (
	select u.movie_id, u.role
	from (
		select mcast.movie_id, mcast.person_id, mcast.character as "role"  
		from movie_cast mcast
		union
		select mcrew.movie_id, mcrew.person_id, mcrew.job
		from movie_crew mcrew 
		) u
	where u.person_id = (
			select p.id
			from person p 
			where name='Ben Affleck'
			)
	) actorMovies
join movie m 
on m.id = actorMovies.movie_id
order by m.release_date desc


/* Emfanise ta onomata twn hthopoiwn pou exoun paiksei se toylaxiston 2 tainies me meso oro kritikwn =5,
 kathws kai gia kathe enan apo autous tous hthopoious to plhthos tetoiwn tainiwn pou exei sto energhtiko tou. 
 Taksinomhse ta apotelesmata kata fthinousa seira ws pros auto to plhthos.

 columns: Actors name, # of movies where avg rating is 5
 rows: 38
*/
select p.name as "Actors name", m."# of movies where avg rating is 5"
from (
	select mc.person_id, count(distinct rt.movie_id) as "# of movies where avg rating is 5"
	from (
		select r.movie_id 
		from ratings r 
		group by r.movie_id
		having avg(rating) = 5 
		) rt
	join movie_cast mc 
	on mc.movie_id = rt.movie_id
	group by mc.person_id 
	having count(distinct rt.movie_id) >= 2
	) m
join person p  
on p.id = m.person_id
order by m."# of movies where avg rating is 5" desc 



/* Emfanise tous hthopoious pou exoun toulaxistwn mia tainia me thematologia World War II
 sto energhtiko tous kai epipleon to megisto popularity metaksi autwn twn tainiwn ana hthopoio einai megalutero
 tou 20 (dld theloume na exoun paiksei se tetoias thematologias tainia kai h pio dhmofilhs tetoia tainia tous
 na einai katholika dhmofilhs).
 Gia kathe hthopoio emfanise to onoma tou kai to megisto auto popularity.

 columns: Actors name, Max popularity above 20.0
 rows: 113
*/
select p.name as "Actors name", warActors."Max popularity above 20.0"
from (
	select person_id, max(popularity) as "Max popularity above 20.0"
	from (
		select movie_id
		from (
			select k.keyword_id 
			from keywords k 
			where name like '%world war ii%'
			) middle
		join movie_keywords mk 
		on mk.keyword_id = middle.keyword_id
		) warMovies
	join movie_cast mc 
	on mc.movie_id = warMovies.movie_id
	join movie m 
	on m.id = mc.movie_id 
	group by person_id
	having max(popularity) > 20
	) warActors
join person p 
on p.id = warActors.person_id

	/*NOTE:  Me skopo na efarmostoun prakseis synathroishs (->max) epi tou popularity gia to parapanw erwthma
	 allaksame ton tupo tou column popularity se arithmhtiko (float), me tis parakatw entoles:  */  
			alter table movie 
			alter column popularity
			type float 
			using (popularity::float);