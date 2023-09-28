alter table movie_cast  
add primary key(movie_id, person_id, cast_id, "character")

/* To {movie_id, person_id, cast_id, "character"} einai yperkleidi giati:
    isxyei h FD 
        {person_id} --> {name, gender}        (apo erwthma 3)
Epipleon einai elaxisto yperkleidi giati den mporei na afairethei gnwrisma. 
Ara einai kleidi */
