--drop schema if exists public cascade;
--create schema if not exists public;

ALTER SEQUENCE credenziali_seq RESTART WITH 1;
ALTER SEQUENCE utenti_seq RESTART WITH 1;
ALTER SEQUENCE cuochi_seq RESTART WITH 1;
ALTER SEQUENCE ricette_seq RESTART WITH 1;
ALTER SEQUENCE ingredienti_seq RESTART WITH 1;

DELETE FROM ingredienti;
DELETE FROM ricette;
DELETE FROM cuochi;
DELETE FROM utenti;
DELETE FROM credenziali;


INSERT INTO credenziali (id, username, password, role, inserted_at) VALUES
                                                           (nextval('credenziali_seq'), 'Lamb', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'AMMINISTRATORE', CURRENT_TIMESTAMP),
                                                           (nextval('credenziali_seq'), 'Lamb2', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'REGISTRATO', CURRENT_TIMESTAMP),
                                                           (nextval('credenziali_seq'), 'Lamb3', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'REGISTRATO', CURRENT_TIMESTAMP),
                                                           (nextval('credenziali_seq'), 'Lamb4', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'REGISTRATO', CURRENT_TIMESTAMP);


INSERT INTO utenti (id, nome, cognome, datanascita, credenziali_id) VALUES
                                                            (nextval('utenti_seq'), 'Matteo', 'Lambertucci', '2002-04-02', 1),
                                                            (nextval('utenti_seq'), 'Matteo2', 'Lambertucci2', CURRENT_DATE, 51),
                                                            (nextval('utenti_seq'), 'Matteo3', 'Lambertucci3', CURRENT_DATE, 101),
                                                            (nextval('utenti_seq'), 'Matteo4', 'Lambertucci4', CURRENT_DATE - INTERVAL '2 days', 151);

INSERT INTO cuochi (id, fotografia, utente_id) VALUES
                                                   (nextval('cuochi_seq'), '/images/cuochi/1/matteo2.jpeg', 51),
                                                   (nextval('cuochi_seq'), '/images/cuochi/51/matteo3.jpeg', 101),
                                                   (nextval('cuochi_seq'), '/images/cuochi/101/matteo4.jpeg', 151);


INSERT INTO ricette (id, nome, descrizione, immagini, cuoco_id) VALUES
    (nextval('ricette_seq'), 'Pasta al sugo', 'Una bella pasta al sugo.', '{"/images/ricette/1/1.jpeg", "/images/ricette/1/2.jpeg"}', 1),
    (nextval('ricette_seq'), 'Pasta con olio', 'Una bella pasta con olio.', '{"/images/ricette/51/1.jpeg", "/images/ricette/51/2.jpeg"}', 1),
    (nextval('ricette_seq'), 'Ricetta vuota', 'Una ricetta vuota.', '{"/images/ricette/101/1.jpeg"}', 51);

INSERT INTO ingredienti (id, nome, quantita, ricetta_id) VALUES
    (nextval('ingredienti_seq'), 'Pomodoro', 2, 1),
    (nextval('ingredienti_seq'), 'Olio', 1, 51),
    (nextval('ingredienti_seq'), 'Grano', 3, 51),
    (nextval('ingredienti_seq'), 'Niente', 3, 101);

SELECT * FROM credenziali;
SELECT * FROM utenti;
SELECT * FROM cuochi;
SELECT * FROM ricette;
SELECT * FROM ingredienti;

SELECT * FROM credenziali_seq;
SELECT * FROM utenti_seq;
