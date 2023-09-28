alter table movie_crew 
add primary key(movie_id, person_id, job)

-- To {movie_id, person_id, job} einai yperkleidi giati:
    --isxyoun oi FDs 
    --  {person_id} --> {name, gender}        (apo erwthma 4)
    --  {movie_id, person_id, job} --> {department}   
                        /*(isxyei gia profaneis logous), 
                        san epivevaiwsh, to parakatw query paragei 0 apotelesmata

                        select movie_id,person_id,job
                        from movie_crew 
                        group by movie_id,person_id,job
                        having count(distinct department) > 1  
                        */


    -- opote {movie_id, person_id, job}^+ = {movie_id, person_id, job, name, gender, department}
    
-- Epipleon einai elaxisto yperkleidi giati den mporei na afairethei gnwrisma. 
-- Ara einai kleidi