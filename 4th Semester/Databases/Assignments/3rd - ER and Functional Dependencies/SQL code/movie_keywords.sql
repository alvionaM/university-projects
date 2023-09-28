alter table movie_keywords  
add primary key(movie_id, keyword_id)

-- 1 movie --> * keywords
-- 1 keyword --> * movies
-- Dld h sysxetish einai many to many. Opote exoume tuple key