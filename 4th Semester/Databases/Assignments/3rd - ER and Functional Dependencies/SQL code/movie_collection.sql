alter table movie_collection 
add primary key(movie_id, collection_id)

-- 1 movie --> * collection
-- 1 collection --> * movies
-- Dld h sysxetish einai many to many. Opote exoume tuple key