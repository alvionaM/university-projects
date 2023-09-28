--Movie_Cast:  {person_id}  --> {name, gender}

--Emfanise ta person_id gia ta opoia, ta diaforetika name h gender pou antistoixoun se auto to memonomeno
--person_id einai perissotera apo 1. Ousiastika gia na mhn paraviazetai h FD prepei na mhn epistrefontai 
--apotelesmata

--rows:0
select person_id
from movie_cast 
group by person_id
having count(distinct name) > 1 or count(distinct gender) > 1 


--Movie_Crew:  {person_id}  --> {name, gender}

--Emfanise ta person_id gia ta opoia, ta diaforetika name h gender pou antistoixoun se auto to memonomeno
--person_id einai perissotera apo 1. Ousiastika gia na mhn paraviazetai h FD prepei na mhn epistrefontai 
--apotelesmata

--rows:0
select person_id
from movie_crew 
group by person_id
having count(distinct name) > 1 or count(distinct gender) > 1