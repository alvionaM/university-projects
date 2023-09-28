alter table Movie_Collection
add foreign key(movie_id) references Movie(id),
add foreign key(collection_id) references Collection(id);

alter table Movie_Genres
add foreign key(movie_id) references Movie(id),
add foreign key(genre_id) references Genre(id);

alter table Movie_Productioncompanies
add foreign key(movie_id) references Movie(id),
add foreign key(pc_id) references Productioncompany(id);

alter table Movie_Keywords
add foreign key(movie_id) references Movie(id),
add foreign key(keyword_id) references Keywords(keyword_id);

alter table Movie_Cast
add foreign key(movie_id) references Movie(id);

alter table Movie_Crew
add foreign key(movie_id) references Movie(id);

alter table Ratings
add foreign key(movie_id) references Movie(id);