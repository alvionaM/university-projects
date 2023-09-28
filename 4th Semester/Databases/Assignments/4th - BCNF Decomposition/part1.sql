-- Erwthma 4
    
    -- Isxuei h FD: {id} -> {name, gender} ara kleidi einai to id
    create table Person (
        id int,
        name varchar(40),
        gender int,
        primary key(id)
    )


    -- Monadiko gnwrisma to person_id pou einai kseno kleidi apo ton progono Person(id)
    create table Actor (
        person_id int,
        foreign key (person_id) references person(id),
        primary key (person_id)
    )


    -- Monadiko gnwrisma to person_id pou einai kseno kleidi apo ton progono Person(id)
    create table CrewMember (
        person_id int,
        foreign key (person_id) references person(id),
        primary key (person_id)
    )



-- Erwthma 5

    -- Inner join twn movie_cast, movie_crew sto person_id kai elegxos tou an uparxoun stis pleiades pou prokuptoun,
    -- zeugh opou to pedio movie_cast.name de sumfwnei me to pedio movie_crew.name (afou exoun to idio person_id)
    select distinct mcast.person_id, mcast.name as cast_name, mcrew.name as crew_name
    from movie_cast mcast
    join movie_crew mcrew 
    on mcast.person_id = mcrew.person_id 
    where mcast.name <> mcrew.name


    -- Inner join twn movie_cast, movie_crew sto person_id kai elegxos tou an uparxoun stis pleiades pou prokuptoun,
    -- zeugh opou to pedio movie_cast.gender de sumfwnei me to pedio movie_crew.gender (afou exoun to idio person_id)
    -- Epistrefei pleiada me id = 1785844 kai name = 'Peter Malota' opou ston ena pinaka to gender tou einai '2' kai ston allon pinaka '0'
    select distinct mcast.person_id, mcast.gender as cast_gender, mcrew.gender as crew_gender
    from movie_cast mcast
    join movie_crew mcrew 
    on mcast.person_id = mcrew.person_id 
    where mcast.gender <> mcrew.gender


        -- Update
        -- Diorthwsi tou person_id = 1785844 na exei koino gender (gender = 2) kai stous 2 pinakes 
        update movie_cast
        set gender = 2
        where person_id = 1785844;
        
        update movie_crew 
        set gender = 2
        where person_id = 1785844;



-- Erwthma 6

    -- Eisagwgh ston neo pinaka Person olwn twn diakritwn person_id pou prokuptoun apo thn enwsh (praksi union) twn pinakwn movie_cast kai movie_crew
    -- Eisagontai mono ta gnwrismata person_id, name, gender sta id, name, gender antistoixa
    -- H eisagwgh ston pinaka Person prohgeitai giati stous pinakes Actor kai Crewmember to 'primary key' einai 'foreign key' ws pros
    -- thn uperklash Person kai ara kathe eisagwgh se autous apaitei thn uparksh toy kleidiou
    insert into person(id, name, gender)
    select mcast.person_id, mcast."name", mcast.gender 
    from movie_cast mcast
    union
    select mcrew.person_id, mcrew."name", mcrew.gender
    from movie_crew mcrew


    -- Eisagwgh ston neo pinaka Actor olwn twn diakritwn person_id pou uparxoun ston pinaka movie_cast
    insert into actor(person_id)
    select distinct mc.person_id  
    from movie_cast mc


    -- Eisagwgh ston neo pinaka Crewmember olwn twn diakritwn person_id pou uparxoun ston pinaka movie_crew
    insert into crewmember(person_id)
    select distinct mc.person_id 
    from movie_crew mc



-- Erwthma 7

    -- backup movie_cast
    create table Movie_Cast2 as
    table movie_cast

    alter table movie_cast2 
    add foreign key(movie_id) references movie(id),
    add primary key(movie_id, person_id, cast_id, character);


    -- backup movie_crew
    create table Movie_Crew2 as
    table movie_crew 

    alter table movie_crew2 
    add foreign key(movie_id) references movie(id),
    add primary key(movie_id, person_id, job);



    -- Drop twn sthlwn pou aforoun ta gnwrismata gender kai name apo tous pinakes movie_cast kai movie_crew
    -- afou auta emfanizontai pleon stous neous pinakes (BCNF morfh)
    alter table movie_cast 
    drop column gender,
    drop column name;

    alter table movie_crew 
    drop column gender,
    drop column name;



    -- Tropopoihsh tou pinaka movie_cast wste to gnwrisma person_id na einai 'foreign key' tou neou pinaka Actor(person_id)
    alter table movie_cast 
    add foreign key(person_id) references actor(person_id);

    -- Tropopoihsh tou pinaka movie_crew wste to gnwrisma person_id na einai 'foreign key' tou neou pinaka Crewmember(person_id)
    alter table movie_crew 
    add foreign key(person_id) references crewmember(person_id);