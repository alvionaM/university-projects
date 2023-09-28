alter table movie_productioncompanies  
add primary key(movie_id, pc_id)

-- 1 movie --> * production comp
-- 1 production comp --> * movies
-- Dld h sysxetish einai many to many. Opote exoume tuple key