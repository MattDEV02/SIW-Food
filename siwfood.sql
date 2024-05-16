--drop schema if exists public cascade;
--create schema if not exists public;

ALTER SEQUENCE credenziali_seq RESTART WITH 1;
ALTER SEQUENCE utenti_seq RESTART WITH 1;
ALTER SEQUENCE ricette_seq RESTART WITH 1;
ALTER SEQUENCE ingredienti_seq RESTART WITH 1;


INSERT INTO credenziali (id, username, password, role) VALUES
                                                           (nextval('credenziali_seq'), 'Lamb', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'AMMINISTRATORE_ROLE'),
                                                           (nextval('credenziali_seq'), 'Lamb2', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'REGISTRATO_ROLE'),
                                                           (nextval('credenziali_seq'), 'Lamb3', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'OCCASIONALE_ROLE');


INSERT INTO utenti (id, nome, cognome, datanascita, fotografia, credenziali_id) VALUES
                                                                                    (nextval('utenti_seq'), 'Matteo', 'Lambertucci', CURRENT_DATE, NULL, 1),
                                                                                    (nextval('utenti_seq'), 'Matteo2', 'Lambertucci2', CURRENT_DATE, '/images/utenti/51/lamb2.jpeg', 51),
                                                                                    (nextval('utenti_seq'), 'Matteo3', 'Lambertucci3', CURRENT_DATE, NULL, 101);

INSERT INTO ricette (id, nome, descrizione, immagini, cuoco_id) VALUES
    (nextval('ricette_seq'), 'Pasta', 'Una bella pasta al sugo.', '{"/images/ricette/1/pasta_1.jpeg", "/images/ricette/1/pasta_2.jpeg"}', 51);

INSERT INTO ingredienti (id, nome, quantita, ricetta_id) VALUES
    (nextval('ingredienti_seq'), 'Pomodoro', 2, 1);

SELECT * FROM credenziali;
select * from utenti;
select * from ricette;
select * from ingredienti;
