--drop schema if exists public cascade;
--create schema if not exists public;

ALTER SEQUENCE credenziali_seq RESTART WITH 1;
ALTER SEQUENCE utenti_seq RESTART WITH 1;
ALTER SEQUENCE cuochi_seq RESTART WITH 1;
ALTER SEQUENCE ricette_seq RESTART WITH 1;
ALTER SEQUENCE ingredienti_seq RESTART WITH 1;

delete from ingredienti;
delete from ricette;
delete from cuochi;
delete from utenti;
delete from credenziali;

INSERT INTO credenziali (id, username, password, role) VALUES
                                                           (nextval('credenziali_seq'), 'Lamb', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'AMMINISTRATORE'),
                                                           (nextval('credenziali_seq'), 'Lamb2', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'REGISTRATO'),
                                                           (nextval('credenziali_seq'), 'Lamb3', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'REGISTRATO');


INSERT INTO utenti (id, nome, cognome, datanascita, credenziali_id) VALUES
                                                                                    (nextval('utenti_seq'), 'Matteo', 'Lambertucci', '2002-04-02', 1),
                                                                                    (nextval('utenti_seq'), 'Matteo2', 'Lambertucci2', CURRENT_DATE, 51),
                                                                                    (nextval('utenti_seq'), 'Matteo3', 'Lambertucci3', CURRENT_DATE, 101);

INSERT INTO cuochi (id, fotografia, utente_id) VALUES (nextval('cuochi_seq'), '/images/cuochi/1/51.jpeg', 51), (nextval('cuochi_seq'), '/images/cuochi/2/101.jpeg', 101);


INSERT INTO ricette (id, nome, descrizione, immagini, cuoco_id) VALUES
    (nextval('ricette_seq'), 'Pasta al sugo', 'Una bella pasta al sugo.', '{"/images/ricette/1/1.jpeg", "/images/ricette/1/2.jpeg"}', 1),
    (nextval('ricette_seq'), 'Pasta con olio', 'Una bella pasta con olio.', '{"/images/ricette/2/1.jpeg", "/images/ricette/2/2.jpeg"}', 1),
    (nextval('ricette_seq'), 'Nuoba ricetta', 'Una nuova ricetta.', '{"/images/ricette/3/1.jpeg"}', 51);;

INSERT INTO ingredienti (id, nome, quantita, ricetta_id) VALUES
    (nextval('ingredienti_seq'), 'Pomodoro', 2, 1),
    (nextval('ingredienti_seq'), 'Olio', 1, 51),
    (nextval('ingredienti_seq'), 'Grano', 3, 51);

SELECT * FROM credenziali;
select * from utenti;
select * from cuochi;
select * from ricette;
select * from ingredienti;

select * from credenziali_seq;
select * from utenti_seq;
