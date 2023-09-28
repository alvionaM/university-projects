alter table movie_genres 
add primary key(movie_id, genre_id)

-- 1 movie --> * genres
-- 1 genre --> * movies
-- Dld h sysxetish einai many to many. Opote exoume tuple key