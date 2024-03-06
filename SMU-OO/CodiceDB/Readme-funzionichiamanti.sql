-- FUNZIONI PER L'UTENTE

--ordine di inserimento per inserire i dati di una persona nella rispettiva tabella:
--(codice fiscale, nome, cognome, data di nascita, numero di telefono, Città, Via, numero civico, CAP)
call crea_nuova_persona('RSSMRA78R16L478X', 'Mario', 'Rossi',
                        '1978-10-16',
                        '0363899349', 'Tusa',
                        'Via Giotto',
                        '50', '98079');

/*persone alternative: ('FNICSM80T25F205M', 'Cosimo', 'Fini',
                        '1980-12-25',
                        '0274233438', 'Milano',
                        'Viale della Moscova',
                        '71', '20121');


                        ('PDVLCU96R71F839M', 'Lucia', 'Padovesi',
                        '1996-10-31',
                        '0381228022', 'Napoli',
                        'Via Santa Teresa degli Scalzi',
                        '19', '80135');
*/



--inserire il codice fiscale di una persona esistente per rimuoverla dalla rispettiva tabella
call rimuovi_persona('RSSMRA78R16L478X');


--ordine di inserimento per inserire i dati di un account nella relativa tabella
--(email, nome utente, Password, codice fiscale)
call crea_nuovo_account('mario@bdd.it', 'Mario Rossi', 'YTYVWKJR', 'RSSMRA78R16L478X');
/*email alternative: ('cosimo@bdd.it', 'Cosimo Fini', 'TOLOHER', 'FNICSM80T25F205M');
('scalzi@bdd.it', 'Lucia Padovesi', 'AFBUAOA', 'PDVLCU96R71F839M');
*/



--inserire l'email dell'account esistente per rimuoverla dalla rispettiva tabella 
call rimuovi_account('mario@bdd.it');



--inserire l'email per creare i dati di un conto corrente correlato
call crea_contocorrente_con_carta('mario@bdd.it');



--inserire l'iban del conto corrente esistente per rimuoverlo dalla rispettiva tabella 
call rimuovi_contocorrente_con_carta('IT5171562771094677876062106');



--inserire l'iban del conto sul quale si vuole modificare il saldo
call aggiungi_saldo_conto(3276.50, 'IT5171562771094677876062106');


--inserire il PAN della card di un conto corrente per renderla di tipo 'CartaDiCredito'
call upgrade_carta('1016088900391780');



--inserire il PAN della card di un conto corrente per renderla di tipo 'CartaDiDebito'
call downgrade_carta('1016088900391780');



--ordine di inserimento per inserire i dati di una transaction di tipo Bonifico nella rispettiva tabella:
--(iban mittente, iban destinatario, importo, causale)
call invia_bonifico('IT5171562771094677876062106', 'IT1632348665744204378674044', 10, '');



--ordine di inserimento per inserire i dati di una transaction di tipo Bonifico Istantaneo nella rispettiva tabella:
--(iban mittente, iban destinatario, importo, causale)
call invia_bonifico_istantaneo('IT5171562771094677876062106', 'IT6506181071340299867161434', 10, 'Biglietto cinema');



--Inserire l'iban di un conto per visualizzare l'ultimo movimento descritto da una notifica
call visualizza_ultima_notifica('IT5171562771094677876062106');



--ordine di inserimento per inserire i dati di un piggyBank virtuale nella rispettiva tabella:
--(iban personale, nome piggyBank, obiettivo monetario, descrizione piggyBank)
call crea_salvadanaio('IT5171562771094677876062106', 'Vacanza estiva 2024', 1000, 'Risparmi per la vacanza estiva del 2024');
--piggyBank alternativo: ('IT1118671947037826785928869', 'Regalo di nozze', 300, 'Regalo di nozze');



--inserire l'iban e il nome del piggyBank per rimuoverlo dalla tabella
call rimuovi_salvadanaio('IT5171562771094677876062106', 'Vacanza estiva 2024');



--inserire l'iban personale, il nome del piggyBank e la cifra che si vuole inviare ad esso
call invia_soldi_al_salvadanaio('IT5171562771094677876062106', 'Vacanza estiva 2024', 751);



--inserire l'iban personale, il nome del piggyBank e la cifra che si vuole prendere da esso
call prendi_soldi_dal_salvadanaio('IT5171562771094677876062106', 'Vacanza estiva 2024', 601);



--inserire l'iban personale, una data di inizio e una data di termine (visualizzazione del totale inviato/ricevuto in quel periodo)
call totale_inviato_ricevuto_mensile('IT5171562771094677876062106', '2024-01-01', '2024-01-31');



--Inserire il proprio codice fiscale per visualizzare quali conti aperti sono collegati
select * from visualizza_conti_persona('RSSMRA78R16L478X');



--inserire l'iban di un proprio conto per visualizzare i salvadanai creati con le loro informazioni
select * from visualizza_salvadanai_per_conto('IT5171562771094677876062106');



--inserire l'iban di un proprio conto per visualizzare le proprie transazioni in quali raccolte sono destinate
select * from visualizza_raccolte_per_conto('IT5171562771094677876062106');



--inserire l'iban di un proprio conto e il nome di una raccolta esistente per visualizzare il totale degli importi delle transazioni
select * from visualizza_raccolta_specifica('IT5171562771094677876062106', 'Altro');



--inserire l'iban personale, una data di inizio e una data di termine (visualizzazione delle transazioni con i relativi totali di quel periodo)
select * from visualizza_resoconto('IT5171562771094677876062106','2024-01-01', '2024-01-31');





--FUNZIONI PER LA GESTIONE DEL DB--

--inserire nome di una raccolta e le parole chiave per l'inserimento nella rispettiva tabella
call crea_raccolta('Cinema', 'Cinema,film,biglietto,biglietti,serietv,poltrona');


--inserire il nome della raccolta esistente e le parole chiave da aggiungere
call aggiungi_parolechiave('Cinema', 'med,modernissimo');
/*raccolte alternative:
('Altro', 'altro');
('Spese Mediche', 'Dottore,dottoressa,farmacia,medicina,medicine,ospedale');
('Spese Domestiche', 'Supermercato,macelleria,spesa');
('Bollette', 'Affitto,bolletta,bollette,luce,acqua,gas,rcauto');
('Viaggi', 'Viaggio,viaggi,treno,nave,aereo,mare,montagna');
('Sport', 'Sport,palestra,personal trainer');
*/



--inserire il nome di una raccolta esistente per rimuoverla dalla rispettiva tabella
call rimuovi_raccolta('Cinema');



--funzione che permette di visualizzare le persone registrate al sistema con i loro dati relativi
select * from visualizza_persone_registrate();



SET TIMEZONE = 'Europe/Rome';



