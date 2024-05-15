delete from utenti_ricette;
delete from ricette;
delete  from utenti;
delete  from credenziali;

ALTER SEQUENCE credenziali_seq RESTART WITH 1;
ALTER SEQUENCE utenti_seq RESTART WITH 1;
ALTER SEQUENCE ricette_seq RESTART WITH 1;


INSERT INTO credenziali (id, username, password, role) VALUES
    (nextval('credenziali_seq'), 'Lamb', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'AMMINISTRATORE_ROLE'),
    (nextval('credenziali_seq'), 'Lamb2', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'REGISTRATO_ROLE'),
    (nextval('credenziali_seq'), 'Lamb3', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'OCCASIONALE_ROLE');

SELECT * FROM credenziali;
select * from utenti;
select * from ricette;



INSERT INTO utenti (id, nome, cognome, datanascita, fotografia, credenziali) VALUES
    (nextval('utenti_seq'), 'Matteo', 'Lambertucci', CURRENT_DATE, NULL, 1),
    (nextval('utenti_seq'), 'Matteo2', 'Lambertucci2', CURRENT_DATE, '/images/utenti/51/lamb2.jpeg', 51),
    (nextval('utenti_seq'), 'Matteo3', 'Lambertucci3', CURRENT_DATE, NULL, 101);