--Procedure/Function
create procedure Aggiungi_Parolechiave(nome_in varchar, parolechiave_in varchar)
as
$$
DECLARE
    controllo_raccolta int;
BEGIN
    parolechiave_In = ','|| parolechiave_In;
    --select che controlla l'esistenza della raccolta
    select count(nomeraccolta)
    into controllo_raccolta
    from raccolta
    where nomeraccolta ILIKE nome_In;

    --se la raccolta esiste aggiunge le parole chiavi, altrimenti no
    if controllo_raccolta > 0 then
        UPDATE raccolta
        set parolechiave = parolechiave || parolechiave_In
        where nomeraccolta ilike nome_In;
        RAISE NOTICE E'---------------------------------------------------------------------------\nInserimento avvenuto con successo nella raccolta %!\n---------------------------------------------------------------------------', nome_In;
    else
        RAISE NOTICE E'---------------------------------------------------------------------------\nInserisci una raccolta valida!\n---------------------------------------------------------------------------';
    end if;
END;
$$ language plpgsql;






create procedure Aggiungi_Saldo_Conto(soldi_in decimal, iban_in varchar)
as
$$
DECLARE
   controllo_iban int;
   controllo_saldo decimal;
   controllo_carta int;
BEGIN
 -- select controllo iban se esiste
   SELECT count(iban)
   INTO controllo_iban
   FROM contocorrente
   WHERE iban ilike iban_in;

   -- se l'iban non esiste stampa messaggi di errore, altrimenti esegue la funzione
  IF controllo_iban = 0 THEN
       RAISE NOTICE E'---------------------------------------------------------------------------\nNon esiste nessun conto con questo IBAN!\n---------------------------------------------------------------------------';

  ELSE
      --select che prende il saldo attuale del conto
      select saldo
      into controllo_saldo
      from contocorrente
      where iban = iban_in;
      
      -- controllo che carta ha associato il conto
      controllo_carta = controllo_tipocarta(iban_in);
      -- controlla se la cifra inserita sia maggiore di 0
      if soldi_in > 0 then
        
          if controllo_carta = 0 then --se è carta di debito, devo controllare che il saldo del conto non superi 10000€
           
               if controllo_saldo + soldi_in <= 10000 then
                   UPDATE contocorrente
                   SET saldo = saldo + soldi_in
                   WHERE iban ilike iban_in;
                   RAISE NOTICE E'---------------------------------------------------------------------------\nHai aggiunto %€ al conto %\n---------------------------------------------------------------------------',soldi_in, iban_in;
               else
                    RAISE NOTICE E'---------------------------------------------------------------------------\nHai ragiunto il limite massimo di fondi che puoi avere sul tuo conto!\n---------------------------------------------------------------------------';
                end if;
           
          else 
              UPDATE contocorrente
              SET saldo = saldo + soldi_in
              WHERE iban ilike iban_in;
              RAISE NOTICE E'---------------------------------------------------------------------------\nHai aggiunto %€ al conto %\n---------------------------------------------------------------------------',soldi_in, iban_in;
          end if;
      else
          RAISE NOTICE E'---------------------------------------------------------------------------\nInserisci una cifra valida.\n---------------------------------------------------------------------------';
      end if;
   END IF;
END;
$$ language plpgsql;






create function Collocazione_In_Raccolta() returns trigger
as
$$
DECLARE
    pos INT = 0;
    oldpos INT = 0;
    parolacorrente VARCHAR;
    raccolta_maggiore VARCHAR;
    stringa VARCHAR = '';
BEGIN



    -- controllo se la causale è vuota, oppure è vuota ma inizia con uno spazio
    if new.causale <> '' then
        new.causale = ltrim(new.causale);
        new.causale = rtrim(new.causale);
        stringa = new.causale || ' ';
        -- Creazione della tabella temporanea
        CREATE TEMPORARY TABLE IF NOT EXISTS Aggiornamento(NomeRaccolta VARCHAR);



        -- Loop per estrarre le parole chiave dalla causale
        LOOP
            -- Trova la posizione della prossima parola chiave
            pos = regexp_instr(stringa, '\s', oldpos + 1);
            EXIT WHEN pos = 0;

            -- Estrai la parola corrente
            parolacorrente = substr(stringa, oldpos + 1, pos - oldpos - 1);
            

            -- Esegue la query dinamica per trovare le raccolte associate alla parola chiave
            INSERT INTO Aggiornamento(NomeRaccolta)
            SELECT NomeRaccolta
            FROM Raccolta
            WHERE ParoleChiave ILIKE '%' || parolacorrente || '%';

            -- Aggiorna oldpos per la prossima iterazione
            oldpos = pos;
        END LOOP;

        -- Determina la raccolta con il maggior numero di ripetizioni
        SELECT NomeRaccolta INTO raccolta_maggiore
        FROM Aggiornamento
        GROUP BY NomeRaccolta
        ORDER BY COUNT(*) DESC
        LIMIT 1;

        -- Controlla se la raccolta è nulla
        IF raccolta_maggiore IS NULL THEN
            RAISE NOTICE 'Nessuna raccolta associata.';
            INSERT INTO Colleziona (NomeRaccolta, IdTransazione)
            VALUES ('ALTRO', NEW.idTransazione);
        ELSE
            RAISE NOTICE 'La raccolta con più ripetizioni è: %', raccolta_maggiore;
            -- Inserisci la relazione nella tabella Colleziona
            INSERT INTO Colleziona (NomeRaccolta, IdTransazione)
            VALUES (raccolta_maggiore, NEW.idTransazione);
        end if;

        -- Elimina la tabella temporanea
        DROP TABLE IF EXISTS Aggiornamento;

        
    else
        RAISE NOTICE 'Nessuna raccolta associata.';
        INSERT INTO Colleziona (NomeRaccolta, IdTransazione)
        VALUES ('ALTRO', NEW.idTransazione);
    end if;
    RETURN NEW;
END;
$$ language plpgsql;

create trigger collocazione
after insert on Transazione
for each row
execute function Collocazione_In_Raccolta();







create function Controllo_Saldo(soldi_in decimal, iban_in varchar) returns integer
as
$$
DECLARE
   saldo_contabile DECIMAL;
BEGIN
    -- controlla il saldo del conto
   select saldo
   into saldo_contabile
   from contocorrente
   where iban ilike iban_In;

    -- se il saldo è minore dei soldi da inviare mi ritorna 0, altrimenti 1
   if saldo_contabile < soldi_In then
       return 0;
   else
       return 1;
   end if;
END;
$$ language plpgsql;







create function carta(iban_in varchar) returns integer
as
$$
DECLARE
   controllo_tipocarta carta.tipocarta%TYPE;
BEGIN
   select tipocarta
   into controllo_tipocarta
   from carta
   where contocorrente_iban ilike iban_In;


   if controllo_tipocarta = 'CartaDiDebito' then
       return 0;
   else
       return 1;
   end if;
END;
$$ language plpgsql;









create function Crea_Contocorrente_Con_Carta(account_email_in character varying) returns integer
    language Plpgsql
as
$$
DECLARE
   iban_generato VARCHAR(27);
   pan_generato CHAR(16);
   pin_generato CHAR(5);
   cvv_generato CHAR(3);
BEGIN
   -- Genera un IBAN casuale utilizzando la funzione genera_iban_casuale
   iban_generato := test.genera_iban_casuale();


   -- Genera un PAN casuale di 16 cifre
   pan_generato := test.genera_pan_casuale();


   -- Genera un PIN casuale di 5 cifre
   pin_generato := test.genera_pin_casuale();


   -- Genera un CVV casuale di 3 cifre
   cvv_generato := test.genera_cvv_casuale();



    account_email_in = lower(account_email_in);
       -- Inserisce il nuovo record nella tabella ContoCorrente
       INSERT INTO test.ContoCorrente(iban, dataapertura, saldo, account_email)
       VALUES (iban_generato, CURRENT_DATE, 0, account_email_In);
       RAISE NOTICE E'---------------------------------------------------------------------------\nConto creato con successo! \n---------------------------------------------------------------------------';

        -- Inserisce anche in carta i valori generati casuali
       INSERT INTO test.Carta(Pan, Pin, Cvv, Tipocarta, maxinvio, Contocorrente_Iban)
       VALUES (pan_generato, pin_generato, cvv_generato, 'CartaDiDebito', null, iban_generato);
   return 1;

   EXCEPTION
    when foreign_key_violation then
    RAISE NOTICE E'---------------------------------------------------------------------------\nL\'email inserita non esiste!\n---------------------------------------------------------------------------';
    when not_null_violation then
    RAISE NOTICE E'---------------------------------------------------------------------------\nL\'email non può essere NULL!\n---------------------------------------------------------------------------';


END;
$$;








create procedure Crea_Nuova_Persona(codicefiscale_in varchar, nome_in varchar, cognome_in varchar, datanascita_in date, numerotelefono_in varchar, città_in varchar, via_in varchar, n_civico_in varchar, cap_in varchar)
as
$$
begin
    
    codicefiscale_in = upper(codicefiscale_in);
    --inserisce i dati in persona
   INSERT INTO test.persona (codicefiscale, nome, cognome, datanascita, numerotelefono, città, via, n_civico, cap) VALUES (codicefiscale_In, nome_In, cognome_In, datanascita_In, numerotelefono_In, città_In, via_In, n_civico_In, cap_In);
    RAISE NOTICE E'---------------------------------------------------------------------------\nDati inseriti con successo! \n---------------------------------------------------------------------------';
exception
        when not_null_violation THEN
       RAISE NOTICE 'Errore NOT NULL: %', SQLERRM;
      when unique_violation THEN
       RAISE NOTICE 'Errore Unique: %', SQLERRM;
       when check_violation then
        RAISE NOTICE 'Violazione Check: %', SQLERRM;
        
end;
$$ language plpgsql;







create procedure Crea_Nuovo_Account (email_in varchar, nomeutente_in varchar, password_in varchar, codfisc_in varchar)
as
$$
declare

begin
    
        codfisc_in = upper(codfisc_in);
        email_in = lower(email_in);
        --inserisce i dati in account
       INSERT INTO test.account(email, nomeutente, password, persona_codicefiscale) VALUES (email_In, NomeUtente_In, Password_In, CodFisc_In);
       RAISE NOTICE E'---------------------------------------------------------------------------\nAccount creato con successo! \n---------------------------------------------------------------------------';

exception
       when foreign_key_violation THEN
       RAISE NOTICE E'---------------------------------------------------------------------------\nNon esiste nessuna persona con questo codice Fiscale, per favore riporvare \n---------------------------------------------------------------------------';

      when not_null_violation THEN
       RAISE NOTICE 'Errore NOT NULL: %', SQLERRM;

      when unique_violation THEN
       RAISE NOTICE E'---------------------------------------------------------------------------\nEsiste già un account con quest\'e-mail \n---------------------------------------------------------------------------';

       when check_violation then
        RAISE NOTICE E'---------------------------------------------------------------------------\nInserisci un-email valida\n---------------------------------------------------------------------------';

end;
$$ language plpgsql;






create procedure Crea_Raccolta(nome_in varchar, parolechiavi_in varchar)
as
$$
BEGIN

    nome_in = upper(nome_in);
    --inserisce i dati in raccolta
   insert into raccolta(nomeraccolta, datacreazione, parolechiave)
       values (nome_In, current_date, parolechiavi_In);
   RAISE NOTICE E'---------------------------------------------------------------------------\nRaccolta creata con successo! \n---------------------------------------------------------------------------';


   exception
   when not_null_violation then
   RAISE NOTICE 'Errore NOT NULL: %', SQLERRM;
   when unique_violation THEN
   RAISE NOTICE E'---------------------------------------------------------------------------\nRaccolta già esistente! \n---------------------------------------------------------------------------';
END;
$$ language plpgsql;






create function Crea_Salvadanaio(iban_in character varying, nomesalvadanaio_in character varying, obiettivo_in double precision, descrizione_in character varying) returns integer
    language Plpgsql
as
$$
BEGIN
    nomesalvadanaio_in = upper(nomesalvadanaio_in);
    --inserisce i dati in salvadanaio
   INSERT INTO test.salvadanaio(nomesalvadanaio, obiettivo, saldorisparmio, saldorimanente, descrizione, contocorrente_iban, datacreazione)
    VALUES (nomesalvadanaio_In, obiettivo_In, default, obiettivo_In, descrizione_In, iban_In, current_date);
   RAISE NOTICE E'---------------------------------------------------------------------------\nSalvadanaio creato con successo!\n---------------------------------------------------------------------------';
    return 1;

   EXCEPTION
       when foreign_key_violation then
       RAISE NOTICE E'---------------------------------------------------------------------------\nL\'Iban inserito non esiste!\n---------------------------------------------------------------------------';
       when not_null_violation then
       RAISE NOTICE 'Errore NOT NULL: %', SQLERRM;
       return 0;
END;
$$;








create function Downgrade_Carta(pan_in character varying) returns integer
    language Plpgsql
as
$$
DECLARE
  controllo_esistenza int = 0;
  controllo_tipocarta int;
  controllo_iban contocorrente.iban%TYPE;
BEGIN

    -- controllo esistenza carta inserita
  select count(pan)
  into controllo_esistenza
  from test.carta
  where pan = pan_In;


  if controllo_esistenza > 0 then
      -- select dell'iban collegato alla carta, per chiamare la funzione 'controllo_tipocarta'
        select contocorrente_iban
        into controllo_iban
        from test.carta
        where pan = pan_in;

        controllo_tipocarta = test.controllo_tipocarta(controllo_iban);
         if controllo_tipocarta = 1 then
              UPDATE test.carta
              set tipocarta = 'CartaDiDebito', limitefondi = default, maxinvio = NULL
              where pan = pan_In;
              RAISE NOTICE E'---------------------------------------------------------------------------\nDowngrade effettuato! \n---------------------------------------------------------------------------';
         else

               RAISE NOTICE E'---------------------------------------------------------------------------\nNon puoi effettuare il downgrade, la carta è già di tipo Carta di Debito\n---------------------------------------------------------------------------';

         end if;
  else
      RAISE NOTICE E'---------------------------------------------------------------------------\nInserisci una carta valida!\n---------------------------------------------------------------------------';
  end if;
    return 1;
END;
$$;





create function Genera_Cvv_Casuale() returns character
as
$$
DECLARE
   cvv_generato CHAR(3) := '';
   i INTEGER;
BEGIN
   FOR i IN 1..3 LOOP
       cvv_generato := cvv_generato || TRUNC(RANDOM() * 10)::varchar;
   END LOOP;


   RETURN cvv_generato;
END;
$$ language plpgsql;


create function Genera_Iban_Casuale() returns character varying
as
$$
DECLARE
  iban_generato VARCHAR(27) := 'IT';
  i int;
BEGIN
  FOR i IN 1..25 LOOP
      iban_generato := iban_generato || (TRUNC(RANDOM() * 10))::VARCHAR;
  END LOOP;

  RETURN iban_generato;

END;
$$ language Plpgsql;





create function Genera_Pan_Casuale() returns character
as
$$
DECLARE
   pan_generato CHAR(16) := '';
   i INTEGER;
BEGIN
   FOR i IN 1..16 LOOP
       pan_generato := pan_generato || TRUNC(RANDOM() * 10)::varchar;
   END LOOP;


   RETURN pan_generato;
END;
$$ language plpgsql;



create function Genera_Pin_Casuale() returns character
as
$$
DECLARE
   pin_generato CHAR(4) := '';
   i INTEGER;
BEGIN
   FOR i IN 1..4 LOOP
       pin_generato := pin_generato || TRUNC(RANDOM() * 10)::varchar;
   END LOOP;


   RETURN pin_generato;
END;
$$ language plpgsql;







create procedure Invia_Bonifico(iban_mittente_in varchar, iban_destinatario_in varchar, soldi_in decimal, causale_in varchar)
as
$$
DECLARE
      controllo_esistenza_ibanmitt int = 0;
      controllo_esistenza_ibandest int = 0;
      controllo_saldo contocorrente.saldo%TYPE;
BEGIN

  --togliamo gli spazi laterali alla causale
  causale_in = ltrim(causale_in);
  causale_in = rtrim(causale_in);

  --select dell'iban mittente esistente
  SELECT count(iban)
  into controllo_esistenza_ibanmitt
  from contocorrente
  where iban = iban_mittente_In;

  --select dell'iban destinatario esistente
  SELECT count(iban)
  into controllo_esistenza_ibandest
  from contocorrente
  where iban = iban_destinatario_In;

  --se l'importo inserito è valido
  if soldi_in > 0 then

       -- se entrambi gli iban esistono allora entra nel primo if
       if controllo_esistenza_ibanmitt > 0 and controllo_esistenza_ibandest > 0 then
           if iban_mittente_in = iban_destinatario_in then
               RAISE NOTICE E'---------------------------------------------------------------------------\nNon puoi inviare un bonifico a te stesso!\n---------------------------------------------------------------------------';
           else
               -- funzione che controlla il saldo dell'iban mittente
              if controllo_saldo(soldi_In, iban_mittente_In) then

                -- funzione che controlla il tipo della carta dell'iban mittente (0:debito, 1:credito)
                  if (controllo_tipocarta(iban_mittente_In)) then
                      if soldi_In < 3000 then
                          -- funzione che controlla il tipo della carta dell'iban destinatario
                          if  (controllo_tipocarta(iban_destinatario_In) = 0) then
                              --select del saldo destinatario
                              select saldo
                              into controllo_saldo
                              from contocorrente
                              where iban = iban_destinatario_In;

                                --check se i soldi del conto con carta di debito + i soldi in ingresso siano minori di 10000.
                                  if (controllo_saldo + soldi_In) < 10000 then
                                      --inserisco i dati in transazione
                                      INSERT INTO transazione(importo, causale, datatransazione, orariotransazione, iban2, tipologiapagamento, tipotransazione, categoriaentrata, categoriauscita, iban1)
                                      VALUES (soldi_In, causale_In, current_date, current_time,  iban_mittente_In, 'Bonifico', 'Invia a', null, causale_In, iban_destinatario_In),
                                          (soldi_In, causale_In, current_date, current_time + interval '30 minutes', iban_destinatario_In, 'Bonifico', 'Riceve da', causale_In, null, iban_mittente_In);

                                    -- aggiorno il saldo del destinatario
                                      UPDATE contocorrente
                                      SET saldo = saldo + soldi_In
                                      WHERE iban = iban_destinatario_In;

                                    --aggiorno il saldo del mittente
                                      UPDATE contocorrente
                                      SET saldo = saldo - soldi_In
                                      WHERE iban = iban_mittente_In;
                                      RAISE NOTICE E'---------------------------------------------------------------------------\nBonifico inviato con successo!\n---------------------------------------------------------------------------';
                                  else
                                      RAISE NOTICE E'---------------------------------------------------------------------------\nNon puoi inviare altri soldi a questo conto!\n---------------------------------------------------------------------------';
                                  end if;
                          else -- caso carta di credito del destinatario
                              --inserisco i dati in transazione
                              INSERT INTO transazione(importo, causale, datatransazione, orariotransazione, iban2, tipologiapagamento, tipotransazione, categoriaentrata, categoriauscita, iban1)
                                      VALUES (soldi_In, causale_In, current_date, current_time,  iban_mittente_In, 'Bonifico', 'Invia a', null, causale_In, iban_destinatario_In),
                                          (soldi_In, causale_In, current_date, current_time + interval '30 minutes', iban_destinatario_In, 'Bonifico', 'Riceve da', causale_In, null, iban_mittente_In);

                                -- aggiorno il saldo del destinatario
                              UPDATE contocorrente
                              SET saldo = saldo + soldi_In
                              WHERE iban = iban_destinatario_In;

                                -- aggiorno il saldo del mittente
                              UPDATE contocorrente
                              SET saldo = saldo - soldi_In
                              WHERE iban = iban_mittente_In;

                              RAISE NOTICE E'---------------------------------------------------------------------------\nBonifico inviato con successo!\n---------------------------------------------------------------------------';
                          end if;
                      else
                          RAISE NOTICE E'---------------------------------------------------------------------------\nL\'importo supera il limite di soldi che è possibile inviare con una sola transazione.\n Se desideri puoi effettuare il downgrade!\n---------------------------------------------------------------------------';
                      end if;
                  else -- caso carta di credito del mittente
                      -- controllo del tipo di carta del destinatario
                      if (controllo_tipocarta(iban_destinatario_In) = 0) then
                            -- select saldo destinatario
                              select saldo
                              into controllo_saldo
                              from contocorrente
                              where iban = iban_destinatario_In;

                                --check se i soldi del conto con carta di debito + i soldi in ingresso siano minori di 10000.
                                  if (controllo_saldo + soldi_In) <= 10000 then
                                      -- inserisco i dati in transazione
                                      INSERT INTO transazione(importo, causale, datatransazione, orariotransazione, iban2, tipologiapagamento, tipotransazione, categoriaentrata, categoriauscita, iban1)
                                      VALUES (soldi_In, causale_In, current_date, current_time,  iban_mittente_In, 'Bonifico', 'Invia a', null, causale_In, iban_destinatario_In),
                                          (soldi_In, causale_In, current_date, current_time + interval '30 minutes', iban_destinatario_In, 'Bonifico', 'Riceve da', causale_In, null, iban_mittente_In);
                                      -- aggiorno saldo destinatario
                                      UPDATE contocorrente
                                      SET saldo = saldo + soldi_In
                                      WHERE iban = iban_destinatario_In;

                                        -- aggiorno saldo mittente
                                      UPDATE contocorrente
                                      SET saldo = saldo - soldi_In
                                      WHERE iban = iban_mittente_In;
                                      RAISE NOTICE E'---------------------------------------------------------------------------\nBonifico inviato con successo!\n---------------------------------------------------------------------------';
                                  else
                                      RAISE NOTICE E'---------------------------------------------------------------------------\nNon puoi inviare altri soldi a questo conto!\n---------------------------------------------------------------------------';
                                  end if;
                      else -- caso carta di credito destinatario
                          -- inserisco i dati in transazione
                              INSERT INTO transazione(importo, causale, datatransazione, orariotransazione, iban2, tipologiapagamento, tipotransazione, categoriaentrata, categoriauscita, iban1)
                                      VALUES (soldi_In, causale_In, current_date, current_time,  iban_mittente_In, 'Bonifico', 'Invia a', null, causale_In, iban_destinatario_In),
                                          (soldi_In, causale_In, current_date, current_time + interval '30 minutes', iban_destinatario_In, 'Bonifico', 'Riceve da', causale_In, null, iban_mittente_In);
                              -- aggiorno saldo destinatario
                              UPDATE contocorrente
                              SET saldo = saldo + soldi_In
                              WHERE iban = iban_destinatario_In;

                              -- aggiorno saldo mittente
                              UPDATE contocorrente
                              SET saldo = saldo - soldi_In
                              WHERE iban = iban_mittente_In;
                              RAISE NOTICE E'---------------------------------------------------------------------------\nBonifico inviato con successo!\n---------------------------------------------------------------------------';
                      end if;
                  end if;
              else
                          RAISE NOTICE E'---------------------------------------------------------------------------\nSaldo insufficiente!\n---------------------------------------------------------------------------';
              end if;

        end if;
       elseif controllo_esistenza_ibanmitt > 0 and controllo_esistenza_ibandest = 0 then
          RAISE NOTICE E'---------------------------------------------------------------------------\nIban destinatario errato!\n---------------------------------------------------------------------------';
       elseif controllo_esistenza_ibanmitt = 0 and controllo_esistenza_ibandest > 0 then
          RAISE NOTICE E'---------------------------------------------------------------------------\nIban mittente errato!\n---------------------------------------------------------------------------';
       else
          RAISE NOTICE E'---------------------------------------------------------------------------\nIban mittente e destinatario errati!\n---------------------------------------------------------------------------';
       end if;
  else
                RAISE NOTICE E'---------------------------------------------------------------------------\nInserisci una cifra valida!\n---------------------------------------------------------------------------';
  end if;
END;
$$ language plpgsql;






create procedure Invia_Bonifico_Istantaneo(iban_mittente_in varchar, iban_destinatario_in varchar, soldi_in decimal, causale_in varchar)
as
$$
DECLARE
      controllo_esistenza_ibanmitt int = 0;
      controllo_esistenza_ibandest int = 0;
      controllo_saldo contocorrente.saldo%TYPE; 
BEGIN

    --togliamo gli spazi laterali alla causale
    causale_in = ltrim(causale_in);
    causale_in = rtrim(causale_in);
  --select dell'iban mittente esistente
  SELECT count(iban)
  into controllo_esistenza_ibanmitt
  from contocorrente
  where iban = iban_mittente_In;
  --select dell'iban destinatario esistente
  SELECT count(iban)
  into controllo_esistenza_ibandest
  from contocorrente
  where iban = iban_destinatario_In;

  -- se l'importo inserito è valido
  if soldi_in > 0 then
      -- se entrambi gli iban esistono allora entra nel primo if
       if controllo_esistenza_ibanmitt > 0 and controllo_esistenza_ibandest > 0 then
           if iban_mittente_in = iban_destinatario_in then
               RAISE NOTICE E'---------------------------------------------------------------------------\nNon puoi inviare un bonifico a te stesso!\n---------------------------------------------------------------------------';
           else
               -- funzione che controlla il saldo dell'iban mittente
              if controllo_saldo(soldi_In, iban_mittente_In) then

                -- funzione che controlla il tipo della carta dell'iban mittente (0:debito, 1:credito)
                  if (controllo_tipocarta(iban_mittente_In)) then
                      if soldi_In < 3000 then
                          -- funzione che controlla il tipo della carta dell'iban destinatario
                          if  (controllo_tipocarta(iban_destinatario_In) = 0) then
                              --select del saldo destinatario
                              select saldo
                              into controllo_saldo
                              from contocorrente
                              where iban = iban_destinatario_In;

                                --check se i soldi del conto con carta di debito + i soldi in ingresso siano minori di 10000.
                                  if (controllo_saldo + soldi_In + 2) < 10000 then
                                      --inserisco i dati in transazione
                                      INSERT INTO transazione(importo, causale, datatransazione, orariotransazione, iban2, tipologiapagamento, tipotransazione, categoriaentrata, categoriauscita, iban1)
                                      VALUES (soldi_In+2, causale_In, current_date, current_time,  iban_mittente_In, 'BonificoIstantaneo', 'Invia a', null, causale_In, iban_destinatario_In),
                                          (soldi_In, causale_In, current_date, current_time, iban_destinatario_In, 'BonificoIstantaneo', 'Riceve da', causale_In, null, iban_mittente_In);

                                    -- aggiorno il saldo del destinatario
                                      UPDATE contocorrente
                                      SET saldo = saldo + soldi_In
                                      WHERE iban = iban_destinatario_In;

                                    --aggiorno il saldo del mittente
                                      UPDATE contocorrente
                                      SET saldo = saldo - soldi_In - 2
                                      WHERE iban = iban_mittente_In;
                                      RAISE NOTICE E'---------------------------------------------------------------------------\nBonifico inviato con successo!\n---------------------------------------------------------------------------';
                                  else
                                      RAISE NOTICE E'---------------------------------------------------------------------------\nNon puoi inviare altri soldi a questo conto!\n---------------------------------------------------------------------------';
                                  end if;
                          else -- caso carta di credito del destinatario
                              --inserisco i dati in transazione
                              INSERT INTO transazione(importo, causale, datatransazione, orariotransazione, iban2, tipologiapagamento, tipotransazione, categoriaentrata, categoriauscita, iban1)
                                      VALUES (soldi_In+2, causale_In, current_date, current_time,  iban_mittente_In, 'BonificoIstantaneo', 'Invia a', null, causale_In, iban_destinatario_In),
                                          (soldi_In, causale_In, current_date, current_time, iban_destinatario_In, 'BonificoIstantaneo', 'Riceve da', causale_In, null, iban_mittente_In);

                                -- aggiorno il saldo del destinatario
                              UPDATE contocorrente
                              SET saldo = saldo + soldi_In
                              WHERE iban = iban_destinatario_In;

                                -- aggiorno il saldo del mittente
                              UPDATE contocorrente
                              SET saldo = saldo - soldi_In - 2
                              WHERE iban = iban_mittente_In;

                              RAISE NOTICE E'---------------------------------------------------------------------------\nBonifico inviato con successo!\n---------------------------------------------------------------------------';
                          end if;
                      else
                          RAISE NOTICE E'---------------------------------------------------------------------------\nL\'importo supera il limite di soldi che è possibile inviare con una sola transazione.\n Se desideri puoi effettuare il downgrade!\n---------------------------------------------------------------------------';
                      end if;
                  else -- caso carta di credito del mittente
                      -- controllo del tipo di carta del destinatario
                      if (controllo_tipocarta(iban_destinatario_In) = 0) then
                            -- select saldo destinatario
                              select saldo
                              into controllo_saldo
                              from contocorrente
                              where iban = iban_destinatario_In;

                                --check se i soldi del conto con carta di debito + i soldi in ingresso siano minori di 10000.
                                  if (controllo_saldo + soldi_In + 2) <= 10000 then
                                      -- inserisco i dati in transazione
                                      INSERT INTO transazione(importo, causale, datatransazione, orariotransazione, iban2, tipologiapagamento, tipotransazione, categoriaentrata, categoriauscita, iban1)
                                      VALUES (soldi_In+2, causale_In, current_date, current_time,  iban_mittente_In, 'BonificoIstantaneo', 'Invia a', null, causale_In, iban_destinatario_In),
                                          (soldi_In, causale_In, current_date, current_time, iban_destinatario_In, 'BonificoIstantaneo', 'Riceve da', causale_In, null, iban_mittente_In);
                                      -- aggiorno saldo destinatario
                                      UPDATE contocorrente
                                      SET saldo = saldo + soldi_In
                                      WHERE iban = iban_destinatario_In;

                                        -- aggiorno saldo mittente
                                      UPDATE contocorrente
                                      SET saldo = saldo - soldi_In - 2
                                      WHERE iban = iban_mittente_In;
                                      RAISE NOTICE E'---------------------------------------------------------------------------\nBonifico inviato con successo!\n---------------------------------------------------------------------------';
                                  else
                                      RAISE NOTICE E'---------------------------------------------------------------------------\nNon puoi inviare altri soldi a questo conto!\n---------------------------------------------------------------------------';
                                  end if;
                      else -- caso carta di credito destinatario
                          -- inserisco i dati in transazione
                              INSERT INTO transazione(importo, causale, datatransazione, orariotransazione, iban2, tipologiapagamento, tipotransazione, categoriaentrata, categoriauscita, iban1)
                                      VALUES (soldi_In+2, causale_In, current_date, current_time,  iban_mittente_In, 'BonificoIstantaneo', 'Invia a', null, causale_In, iban_destinatario_In),
                                          (soldi_In, causale_In, current_date, current_time, iban_destinatario_In, 'BonificoIstantaneo', 'Riceve da', causale_In, null, iban_mittente_In);
                              -- aggiorno saldo destinatario
                              UPDATE contocorrente
                              SET saldo = saldo + soldi_In
                              WHERE iban = iban_destinatario_In;

                              -- aggiorno saldo mittente
                              UPDATE contocorrente
                              SET saldo = saldo - soldi_In - 2
                              WHERE iban = iban_mittente_In;
                              RAISE NOTICE E'---------------------------------------------------------------------------\nBonifico inviato con successo!\n---------------------------------------------------------------------------';
                      end if;
                  end if;
              else
                          RAISE NOTICE E'---------------------------------------------------------------------------\nSaldo insufficiente!\n---------------------------------------------------------------------------';
              end if;

        end if;
       elseif controllo_esistenza_ibanmitt > 0 and controllo_esistenza_ibandest = 0 then
          RAISE NOTICE E'---------------------------------------------------------------------------\nIban destinatario errato!\n---------------------------------------------------------------------------';
       elseif controllo_esistenza_ibanmitt = 0 and controllo_esistenza_ibandest > 0 then
          RAISE NOTICE E'---------------------------------------------------------------------------\nIban mittente errato!\n---------------------------------------------------------------------------';
       else
          RAISE NOTICE E'---------------------------------------------------------------------------\nIban mittente e destinatario errati!\n---------------------------------------------------------------------------';
       end if;
  else
                RAISE NOTICE E'---------------------------------------------------------------------------\nInserisci una cifra valida!\n---------------------------------------------------------------------------';
  end if;
END;
$$ language plpgsql;







create function Invia_Soldi_Al_Salvadanaio(iban_in character varying, nome_in character varying, soldi_in double precision) returns integer
    language Plpgsql
as
$$
              DECLARE
                  controllo_iban int;
                  controllo_saldo contocorrente.saldo%TYPE;
                  controllo_salvadanaio int;
                  controllo_obiettivo salvadanaio.obiettivo%TYPE;
                  controllo_saldorimanente salvadanaio.saldorimanente%TYPE;
                  controllo_saldorisparmio salvadanaio.saldorisparmio%TYPE;
              BEGIN

                -- controlla se esiste l'iban inserito
                  select count(iban)
                  into controllo_iban
                  from test.contocorrente
                  where iban = iban_In;


                  if controllo_iban > 0 then
                      -- controlla se esiste il salvadanaio inserito
                      select count(idsalvadanaio)
                      into controllo_salvadanaio
                      from test.salvadanaio
                      where contocorrente_iban = iban_In AND nomesalvadanaio ILIKE nome_In;


                      if controllo_salvadanaio > 0 then


                          select saldo
                          into controllo_saldo
                          from test.contocorrente
                          where iban = iban_In;

                        -- controlla se il saldo dal conto è sufficiente
                        if soldi_in > 0 then
                          if controllo_saldo >= soldi_In then

                            -- select che prende l'obiettivo del salvadanaio
                              select obiettivo
                              into controllo_obiettivo
                              from test.salvadanaio
                              where nomesalvadanaio ILIKE nome_In and contocorrente_iban = iban_In;

                            -- aggiorna il saldo risparmio del salvadanaio
                              UPDATE test.salvadanaio
                              SET saldorisparmio = saldorisparmio + soldi_In
                              WHERE contocorrente_iban = iban_In AND nomesalvadanaio ILIKE nome_In;

                            -- aggiorna il saldo rimanente del salvadanaio
                              UPDATE test.salvadanaio
                              SET saldorimanente = salvadanaio.obiettivo - salvadanaio.saldorisparmio
                              WHERE contocorrente_iban = iban_In AND nomesalvadanaio ILIKE nome_In;

                              -- aggiorna il saldo del contocorrente
                              UPDATE test.contocorrente
                              SET saldo = saldo-soldi_in
                              WHERE iban = iban_in;

                            -- select del saldo risparmio dopo l'update
                              select saldorisparmio
                              into controllo_saldorisparmio
                              from test.salvadanaio
                              where nomesalvadanaio ILIKE nome_In and contocorrente_iban = iban_In;

                            -- select del saldo rimanente dopo l'update
                              select saldorimanente
                              into controllo_saldorimanente
                              from test.salvadanaio
                              where nomesalvadanaio ILIKE nome_In and contocorrente_iban = iban_In;

                            -- controllo se ho raggiunto l'obiettivo
                              if  controllo_saldorisparmio >= controllo_obiettivo then
                                  RAISE NOTICE E'---------------------------------------------------------------------------\n Saldo salvadanaio attuale: %. \n Hai raggiunto il tuo obiettivo, congratulazioni!\n---------------------------------------------------------------------------', controllo_saldorisparmio;
                                  -- se l'ho raggiunto aggiorno il saldo rimanente a 0
                                  UPDATE test.salvadanaio
                                  set saldorimanente = 0
                                  WHERE contocorrente_iban = iban_In AND nomesalvadanaio ILIKE nome_In;
                              else
                                  RAISE NOTICE E'---------------------------------------------------------------------------\n Saldo salvadanaio attuale: %. \n Saldo rimanente per raggiungere il tuo obiettivo: %!\n---------------------------------------------------------------------------', controllo_saldorisparmio, controllo_saldorimanente;
                              end if;

                          else
                              RAISE NOTICE E'---------------------------------------------------------------------------\nSaldo insufficiente!\n---------------------------------------------------------------------------';
                          end if;

                        else
                            RAISE NOTICE E'---------------------------------------------------------------------------\nInserisci una cifra valida!\n---------------------------------------------------------------------------';
                        end if;

                      else
                          RAISE NOTICE E'---------------------------------------------------------------------------\nNon esiste nessun salvadanaio con questo nome!\n---------------------------------------------------------------------------';
                      end if;

                  else
                      RAISE NOTICE E'---------------------------------------------------------------------------\nNon esiste nessun conto con questo Iban!\n---------------------------------------------------------------------------';
                  end if;
              return 1;
              END;
$$;







create function Notifiche_Transazioni() returns trigger
as
$$
BEGIN
   -- Per transazioni in uscita
   IF NEW.tipotransazione = 'Riceve da' THEN
       INSERT INTO notifica (descrizionenotifica, transazione_idtransazione, datanotifica, oranotifica)
       VALUES ('Hai ricevuto ' || NEW.importo || ' EUR', NEW.idtransazione, NEW.datatransazione, NEW.orariotransazione);
   -- Per transazioni in entrata
   ELSIF NEW.tipotransazione = 'Invia a' THEN
       INSERT INTO notifica (descrizionenotifica, transazione_idtransazione, datanotifica, oranotifica)
       VALUES ('Hai inviato ' || NEW.importo || ' EUR  con successo', NEW.idtransazione, NEW.datatransazione, NEW.orariotransazione);
   END IF;
   RETURN NEW;
END;
$$ language plpgsql;

create trigger notifiche_transazioni
after insert on transazione
for each row
execute function Notifiche_Transazioni();






create function Prendi_Soldi_Dal_Salvadanaio(iban_in character varying, nome_in character varying, soldi_in double precision) returns integer
    language Plpgsql
as
$$
DECLARE
   controllo_iban int;
   controllo_salvadanaio int;
   controllo_saldorisparmio salvadanaio.saldorisparmio%TYPE;
   controllo_obiettivo salvadanaio.obiettivo%TYPE;
   controllo_saldorimanente salvadanaio.saldorimanente%TYPE;
BEGIN
    --controllo se esite l'iban inserito
   select count(iban)
   into controllo_iban
   from test.contocorrente
   where iban = iban_In;


   if controllo_iban > 0 then
       -- controllo se esiste il salvadanaio inserito
       select count(idsalvadanaio)
       into controllo_salvadanaio
       from test.salvadanaio
       where contocorrente_iban = iban_In AND nomesalvadanaio ILIKE nome_In;


       if controllo_salvadanaio > 0 then
           -- select del saldo risparmio
           select saldorisparmio
           into controllo_saldorisparmio
           from test.salvadanaio
           where nomesalvadanaio ILIKE nome_In and contocorrente_iban = iban_In;

        -- controllo se il saldo risparmio è sufficiente
            if soldi_in > 0 then
               if controllo_saldorisparmio >= soldi_In then
                   -- aggiorno il saldo risparmio
                   UPDATE test.salvadanaio
                   SET saldorisparmio = saldorisparmio - soldi_In
                   WHERE contocorrente_iban = iban_In AND nomesalvadanaio ILIKE nome_In;

                    -- aggiorno il saldo del conto corrente
                   UPDATE test.contocorrente
                   SET saldo = saldo + soldi_In
                   where iban = iban_In;

                -- select dell'obiettivo del salvadanaio
                   select obiettivo
                   into controllo_obiettivo
                   from test.salvadanaio
                   where nomesalvadanaio = nome_In and contocorrente_iban ILIKE iban_In;


                   -- vado a prendere il saldo risparmio aggiornato
                   select saldorisparmio
                   into controllo_saldorisparmio
                   from test.salvadanaio
                   where nomesalvadanaio ILIKE nome_In and contocorrente_iban = iban_In;

                    -- controllo se il salvadanaio ha raggiunto l'obiettivo
                   if  controllo_saldorisparmio >= controllo_obiettivo then
                       RAISE NOTICE E'---------------------------------------------------------------------------\n Saldo salvadanaio attuale: %.\n---------------------------------------------------------------------------', controllo_saldorisparmio;
                       -- aggiorno il saldo rimanente a 0
                       UPDATE test.salvadanaio
                       set saldorimanente = 0
                       WHERE contocorrente_iban = iban_In AND nomesalvadanaio ILIKE nome_In;
                   else
                       -- aggiorno il saldo rimanente
                       UPDATE test.salvadanaio
                       set saldorimanente = salvadanaio.obiettivo - salvadanaio.saldorisparmio
                       WHERE contocorrente_iban = iban_In AND nomesalvadanaio ILIKE nome_In;

                        -- select del saldo rimanente aggiornato
                       select saldorimanente
                       into controllo_saldorimanente
                       from test.salvadanaio
                       where nomesalvadanaio ILIKE nome_In and contocorrente_iban = iban_In;


                       RAISE NOTICE E'---------------------------------------------------------------------------\n Saldo salvadanaio attuale: %.\n Saldo rimanente per raggiungere il tuo obiettivo: %!\n---------------------------------------------------------------------------', controllo_saldorisparmio, controllo_saldorimanente;


                   end if;


                   RAISE NOTICE E'---------------------------------------------------------------------------\nSaldo ripreso con successo!\n---------------------------------------------------------------------------';


               else
                   RAISE NOTICE E'---------------------------------------------------------------------------\nSaldo salvadanaio insufficiente!\n---------------------------------------------------------------------------';
               end if;

            else
                RAISE NOTICE E'---------------------------------------------------------------------------\nInserisci una cifra valida!\n---------------------------------------------------------------------------';
            end if;

       else
           RAISE NOTICE E'---------------------------------------------------------------------------\nNon esiste nessun salvadanaio con questo nome!\n---------------------------------------------------------------------------';
       end if;

   else
       RAISE NOTICE E'---------------------------------------------------------------------------\nNon esiste nessun conto con questo Iban!\n---------------------------------------------------------------------------';
   end if;
    return 1;
END;
$$;






create procedure Rimuovi_Account(email_in varchar)
as
$$
declare
   controllo_esistenza int;
begin
    -- select che controlla l'esistenza dell'email inserita
   select count(email)
   into controllo_esistenza
   from account
   where email ilike email_In;


   if controllo_esistenza > 0 then
       DELETE FROM account where email ilike email_In;
       RAISE NOTICE E'---------------------------------------------------------------------------\nAccount rimosso con successo! \n---------------------------------------------------------------------------';
   else
        RAISE NOTICE E'---------------------------------------------------------------------------\nNon esiste nessun account con questa e-mail.\n---------------------------------------------------------------------------';
   end if;
end;
$$ language plpgsql;






create function Rimuovi_Contocorrente_Con_Carta(iban_in character varying) returns integer
    language Plpgsql
as
$$
DECLARE
   controllo_esistenza int = 0;
BEGIN
    -- select controllo esistenza iban inserito
   SELECT count(iban)
   into controllo_esistenza
   from test.contocorrente
   where iban ilike iban_In;


   if controllo_esistenza > 0 then
       DELETE FROM test.contocorrente
       WHERE iban ilike iban_In;
       RAISE NOTICE E'---------------------------------------------------------------------------\nConto chiuso con successo! \n---------------------------------------------------------------------------';
   else
       RAISE NOTICE E'---------------------------------------------------------------------------\nNon esiste nessun conto con questo IBAN.\n---------------------------------------------------------------------------';
   end if;

    return 1;
END;
$$;




create procedure Rimuovi_Persona(codicefiscale_in varchar)
as
$$
declare
   controllo_esistenza int;
begin
    -- select controllo esistenza codice fiscale inserito
   select count(codicefiscale)
   into controllo_esistenza
   from persona
   where codicefiscale ilike codicefiscale_In;

   if controllo_esistenza > 0 then
       DELETE FROM persona
        where codicefiscale ILIKE codicefiscale_In;
        RAISE NOTICE E'---------------------------------------------------------------------------\nPersona rimossa con successo! \n---------------------------------------------------------------------------';
   else
        RAISE NOTICE E'---------------------------------------------------------------------------\nNon esiste nessuna persona con questo codice fiscale.\n---------------------------------------------------------------------------';
   end if;
end;
$$ language plpgsql;




create procedure Rimuovi_Raccolta(nome_in varchar)
as
$$
DECLARE
    controllo_raccolta int;
BEGIN
    --select controllo esistenza nome raccolta inserito
    select count(nomeraccolta)
    into controllo_raccolta
    from raccolta
    where nomeraccolta ILIKE nome_In;

    if controllo_raccolta > 0 then
        DELETE from Raccolta
        where nomeraccolta ILIKE nome_In;
        RAISE NOTICE E'---------------------------------------------------------------------------\nRaccolta eliminata con successo!\n---------------------------------------------------------------------------';
    else
    RAISE NOTICE E'---------------------------------------------------------------------------\nInserisci un nome valido!\n---------------------------------------------------------------------------';
    end if;
end;
$$ language plpgsql;




create function Rimuovi_Salvadanaio(iban_in character varying, nome_in character varying) returns integer
    language Plpgsql
as
$$
DECLARE
   controllo_iban int;
   controllo_salvadanaio int;
   controllo_saldorisparmio salvadanaio.saldorisparmio%TYPE;
BEGIN
    -- select controllo esistenza iban inserito
   select count(iban)
   into controllo_iban
   from test.contocorrente
   where iban = iban_In;


   if controllo_iban > 0 then
       --select controllo esistenza salvadanaio inserito
       select count(idsalvadanaio)
       into controllo_salvadanaio
       from test.salvadanaio
       where contocorrente_iban = iban_In AND nomesalvadanaio ILIKE nome_In;


       if controllo_salvadanaio > 0 then
           --select saldo risparmio salvadanaio
           select saldorisparmio
           into controllo_saldorisparmio
           from test.salvadanaio
           where nomesalvadanaio ILIKE nome_In and contocorrente_iban = iban_In;
           --se il saldo è = 0 allora elimina il salvadanaio, altrimenti no
           if controllo_saldorisparmio = 0 then
               DELETE 
               FROM test.salvadanaio
               WHERE contocorrente_iban = iban_In AND nomesalvadanaio ILIKE nome_In;
               RAISE NOTICE E'---------------------------------------------------------------------------\nSalvadanaio eliminato con successo!\n---------------------------------------------------------------------------';
           else
               RAISE NOTICE E'---------------------------------------------------------------------------\nRimuovi prima i tuoi risparmi!\n---------------------------------------------------------------------------';
           end if;
       else
           RAISE NOTICE E'---------------------------------------------------------------------------\nNon esiste nessun salvadanaio con questo nome!\n---------------------------------------------------------------------------';
       end if;
   else
       RAISE NOTICE E'---------------------------------------------------------------------------\nNon esiste nessun conto con questo Iban!\n---------------------------------------------------------------------------';
   end if;
    return 1;
END;
$$;










create procedure Totale_Inviato_Ricevuto_Mensile(iban_in varchar, data_inizio date, data_fine date)
as
$$
DECLARE
   controllo_iban int;
   totale_inviato DECIMAL;
   totale_ricevuto DECIMAL;
   controllo_data int;
BEGIN
   -- Controllo se l'iban inserito esiste
   select count(iban)
   into controllo_iban
   from contocorrente
   where iban = iban_In;

    -- select controllo date inserite
   SELECT COUNT(*)
   INTO controllo_data
   FROM transazione
   WHERE (iban1 = iban_In OR iban2 = iban_In)
   AND datatransazione BETWEEN data_inizio AND data_fine;

   if controllo_iban > 0 and controllo_data != 0 then
       -- Calcola il totale inviato
       SELECT SUM(importo)
       INTO totale_inviato
       FROM transazione
       WHERE iban2 = iban_In
       AND tipotransazione = 'Invia a'
       AND datatransazione BETWEEN data_inizio AND data_fine;


       -- Calcola il totale ricevuto
       SELECT SUM(importo)
       INTO totale_ricevuto
       FROM transazione
       WHERE iban2 = iban_In
       AND tipotransazione = 'Riceve da'
       AND datatransazione BETWEEN data_inizio AND data_fine;

    -- controllo esistenza totale_inviato e totale_ricevuto
      if totale_inviato is null and totale_ricevuto is not null then
           RAISE NOTICE 'Per il periodo dal % al %: ', data_inizio, data_fine;
           RAISE NOTICE E'---------------------------------------------------------------------------\nHai inviato: 0€. \n Hai ricevuto: %€.\n---------------------------------------------------------------------------',totale_ricevuto;
       elseif totale_inviato is not null and totale_ricevuto is null then
           RAISE NOTICE 'Per il periodo dal % al %: ', data_inizio, data_fine;
           RAISE NOTICE E'---------------------------------------------------------------------------\nHai inviato: %€. \n Hai ricevuto: 0€.\n---------------------------------------------------------------------------',totale_inviato;
       else
           -- Stampa i risultati
           RAISE NOTICE 'Per il periodo dal % al %: ', data_inizio, data_fine;
           RAISE NOTICE E'---------------------------------------------------------------------------\nHai inviato: %€. \n Hai ricevuto: %€.\n---------------------------------------------------------------------------', totale_inviato, totale_ricevuto;
       end if;

    elseif controllo_iban = 0 and controllo_data != 0 then
    RAISE NOTICE E'---------------------------------------------------------------------------\nNon esiste nessun conto con questo Iban!\n---------------------------------------------------------------------------';
   elseif controllo_iban > 0 and controllo_data = 0 then
       RAISE NOTICE E'---------------------------------------------------------------------------\nNessuna transazione trovata per questo periodo!\n---------------------------------------------------------------------------';
   else
       RAISE NOTICE E'---------------------------------------------------------------------------\nRiprova inserendo Iban e una data corretta!\n---------------------------------------------------------------------------';
   end if;
END;
$$ language plpgsql;








create function Upgrade_Carta(pan_in character varying) returns integer
    language Plpgsql
as
$$
DECLARE
  controllo_esistenza int;
  controllo_tipocarta int;
  controllo_iban contocorrente.iban%TYPE;
BEGIN
    -- controllo esistenza carta inserita
  select count(pan)
  into controllo_esistenza
  from test.carta
  where pan = pan_In;


  if controllo_esistenza > 0 then
      -- select dell'iban collegato alla carta, per chiamare la funzione 'controllo_tipocarta'
        select contocorrente_iban
        into controllo_iban
        from test.carta
        where pan = pan_in;

        controllo_tipocarta = test.controllo_tipocarta(controllo_iban);

      if controllo_tipocarta = 0 then
          UPDATE test.carta
          set tipocarta = 'CartaDiCredito', limitefondi = NULL, maxinvio = default
          where pan = pan_In;
          RAISE NOTICE E'---------------------------------------------------------------------------\nUpgrade effettuato! \n---------------------------------------------------------------------------';
      else
          RAISE NOTICE E'---------------------------------------------------------------------------\nNon puoi effettuare l\'upgrade perchè la carta è già di tipo Carta di Credito \n---------------------------------------------------------------------------';

       end if;
   else
      RAISE NOTICE E'---------------------------------------------------------------------------\nInserisci una carta valida!\n---------------------------------------------------------------------------';
  end if;
    return 1;
END;
$$;










create function Visualizza_Conti_Persona(codicefiscale_in varchar)
    returns TABLE(iban varchar, saldo text, numerocarta varchar, tipocarta test.tipo_carta, dataapertura date)
as
$$
DECLARE
    controllo_cod int;
begin
    -- select che controlla l'esistenza del codice fiscale inserito
    select count(p.codicefiscale)
    into controllo_cod
    from persona p
    where p.codicefiscale ilike codicefiscale_In;

    if controllo_cod > 0 then
        return query
       -- Mi permette di visualizzare il conto delle persone registrate
        SELECT
            cc.IBAN,
            cc.Saldo || '€' as Saldo,
            c.pan,
            c.tipocarta,
            cc.dataapertura
        FROM
            ContoCorrente cc
        JOIN
            carta c ON Cc.Iban = C.Contocorrente_Iban
        JOIN
            Account a ON cc.Account_Email = a.Email
        JOIN
            Persona p ON a.Persona_CODICEFISCALE = p.CodiceFiscale
        WHERE p.codicefiscale ILIKE codicefiscale_In;
    else
        RAISE NOTICE E'---------------------------------------------------------------------------\nInserisci un codice fiscale valido!\n---------------------------------------------------------------------------';
    end if;
    end;
$$ language plpgsql;




create function Visualizza_Persone_Registrate()
    returns TABLE(codicefiscale varchar, nome varchar, cognome varchar, email varchar, iban varchar)
as
$$
begin
    return query
    SELECT
    p.CodiceFiscale,
    p.Nome,
    p.Cognome,
    a.Email,
    cc.IBAN
    FROM
        Persona p
    JOIN
        Account a ON p.CodiceFiscale = a.Persona_CODICEFISCALE
    JOIN
        ContoCorrente cc ON a.Email = cc.Account_Email;
end;
$$ language plpgsql;





create function Visualizza_Raccolta_Specifica(iban_in varchar, nome_in varchar)
    returns TABLE(nomeraccolta varchar, idtransazione integer, importo text, causale varchar)
as
$$
DECLARE
    controllo_iban int;
    controllo_raccolta int;
    soldi_inviati decimal;
    soldi_ricevuti decimal;
    totale_raccolta decimal;
begin
    --select che controlla esistenza dell'iban inserito
    select count(c.iban)
    into controllo_iban
    from contocorrente c
    where c.iban ilike iban_In;

    --select che controlla esistenza della raccolta inserita 
    select count(r.nomeraccolta)
    into controllo_raccolta
    from raccolta r
    where r.nomeraccolta ilike nome_In;

    if controllo_iban > 0 then
        if controllo_raccolta > 0 then
            --query che ritorna i soldi inviati per quella raccolta
            return query
            select c.nomeraccolta, c.idtransazione, '-' || t.importo || '€' as importo, t.causale
            from colleziona c join transazione t
            on c.idtransazione = t.idtransazione
            where t.iban2 ilike iban_In and c.nomeraccolta ilike nome_In and t.tipotransazione = 'Invia a' ;

            --select che prende tutti i soldi inviati di questa raccolta
            select sum(t.importo)
            into soldi_inviati
            from colleziona c join transazione t
            on c.idtransazione = t.idtransazione
            where t.iban2 ilike iban_In and c.nomeraccolta ilike nome_In and t.tipotransazione = 'Invia a' ;


            --query che ritorna i soldi ricevuti per quella raccolta
            return query
            select c.nomeraccolta, c.idtransazione, '+' || t.importo || '€' as importo, t.causale
            from colleziona c join transazione t
            on c.idtransazione = t.idtransazione
            where t.iban2 ilike iban_In and c.nomeraccolta ilike nome_In  and t.tipotransazione = 'Riceve da';

            --select che prende tutti i soldi ricevuti di questa raccolta
            select sum(t.importo)
            into soldi_ricevuti
            from colleziona c join transazione t
            on c.idtransazione = t.idtransazione
            where t.iban2 ilike iban_In and c.nomeraccolta ilike nome_In and t.tipotransazione = 'Riceve da' ;

            --nel caso non ho ricevuto alcun importo setto a 0 i soldi ricevuti
            if soldi_ricevuti is null then
                soldi_ricevuti = 0;
            end if;
            
            --nel caso non ho inviato alcun importo setto a 0 i soldi inviati
            if soldi_inviati is null then
                soldi_ricevuti = 0;
            end if;
            
            totale_raccolta = soldi_ricevuti - soldi_inviati;
            RAISE NOTICE E'---------------------------------------------------------------------------\nPer la raccolta %\nHai ricevuto: %€.\nHai inviato: %€.\nTotale: %€.\n---------------------------------------------------------------------------',nome_in, soldi_ricevuti, soldi_inviati, totale_raccolta;
        else
            RAISE NOTICE E'---------------------------------------------------------------------------\nInserisci una raccolta esistente!\n---------------------------------------------------------------------------';
        end if;
    else
       RAISE NOTICE E'---------------------------------------------------------------------------\nInserisci un iban valido!\n---------------------------------------------------------------------------';
    end if;
end;
$$ language plpgsql;






create function Visualizza_Raccolte_Per_Conto(iban_in varchar)
    returns TABLE(nomeraccolta varchar, idtransazione integer, importo text)
as
$$
DECLARE
    controllo_iban int;
begin
    --select controllo esistenza iban inserito
    select count(iban)
    into controllo_iban
    from contocorrente
    where iban = iban_In;


    if controllo_iban > 0 then
            --query che ritorna i soldi inviati della raccolta inserita
            return query
            select c.nomeraccolta, c.idtransazione, '-' || t.importo || '€' as importo
            from colleziona c join transazione t
            on c.idtransazione = t.idtransazione
            where t.iban2 = iban_In and t.tipotransazione = 'Invia a'
            order by nomeraccolta;
            --query che ritorna i soldi ricevuti della raccolta inserita
            return query
            select c.nomeraccolta, c.idtransazione, '+' || t.importo || '€' as importo
            from colleziona c join transazione t
            on c.idtransazione = t.idtransazione
            where t.iban2 = iban_In and t.tipotransazione = 'Riceve da'
            order by nomeraccolta;
    else
       RAISE NOTICE E'---------------------------------------------------------------------------\nInserisci un iban valido!\n---------------------------------------------------------------------------';
    end if;
end;
$$ language plpgsql;






create function Visualizza_Resoconto(iban_in character varying, data_inizio text, data_fine text)
    returns TABLE(importo text, causale varchar, datatransazione date, orariotransazione time without time zone, my_iban varchar, tipotransazione test.tipo_transazione, other_iban varchar)
as
$$
DECLARE
    data_inizio_convertita DATE;
    data_fine_convertita DATE;
    controllo_iban int;
    soldi_inviati decimal;
    soldi_ricevuti decimal;
    totale decimal;
BEGIN
       data_inizio_convertita := data_inizio::DATE;
       data_fine_convertita := data_fine::DATE;

  --select che controlla l'esistenza dell'iban inserito
   select count(c.iban)
    into controllo_iban
    from contocorrente c
    where c.iban = iban_in;

       if controllo_iban > 0 then
           --query che ritorna i soldi inviati dal conto
           RETURN QUERY
           SELECT '-' || t.importo || '€' as importo, t.causale, t.datatransazione, t.orariotransazione,
                  t.iban2, t.tipotransazione, t.iban1
           FROM transazione t
           WHERE t.iban2 ilike iban_In and t.tipotransazione = 'Invia a'
           AND t.datatransazione BETWEEN data_inizio_convertita AND data_fine_convertita
           ORDER BY t.datatransazione, t.orariotransazione;

            --select che prende la somma di tutti i soldi inviati dal conto
            select sum(t.importo)
            into soldi_inviati
            from transazione t
            where t.iban2 ilike iban_in and t.tipotransazione = 'Invia a';

            --query che ritorna i soldi ricevuti sul conto
           RETURN QUERY
           SELECT '+' || t.importo || '€' as importo, t.causale, t.datatransazione, t.orariotransazione,
                  t.iban2, t.tipotransazione, t.iban1
           FROM transazione t
           WHERE t.iban2 ilike iban_In and t.tipotransazione = 'Riceve da'
           AND t.datatransazione BETWEEN data_inizio_convertita AND data_fine_convertita
           ORDER BY t.datatransazione, t.orariotransazione;

           --select che prende la somma di tutti i soldi ricevuti sul conto
            select sum(t.importo)
            into soldi_ricevuti
            from transazione t
            where t.iban2 ilike iban_in and t.tipotransazione = 'Riceve da';

           --nel caso non ho ricevuto alcun importo setto a 0 i soldi ricevuti
            if soldi_ricevuti is null then
                soldi_ricevuti = 0;
            end if;

            --nel caso non ho inviato alcun importo setto a 0 i soldi inviati
            if soldi_inviati is null then
                soldi_ricevuti = 0;
            end if;

            totale = soldi_ricevuti - soldi_inviati;
            RAISE NOTICE E'---------------------------------------------------------------------------\nPer il periodo dal % al %:\nHai ricevuto: %€.\nHai inviato: %€.\nTotale: %€.\n---------------------------------------------------------------------------',data_inizio_convertita, data_fine_convertita, soldi_ricevuti, soldi_inviati, totale;
        
        else
            RAISE NOTICE E'---------------------------------------------------------------------------\nInserisci un iban valido.\n---------------------------------------------------------------------------';
        end if;

   EXCEPTION
   WHEN datetime_field_overflow THEN
       -- Gestire l'errore qui
       RAISE NOTICE 'Data fornita non valida: %, %', data_inizio, data_fine;
   END;
$$ language plpgsql;





create function Visualizza_Salvadanai_Per_Conto(iban_in varchar)
    returns TABLE(nomesalvadanaio varchar, obiettivo text, saldorisparmio text, saldorimanente text, descrizione varchar, datacreazione date)
as
$$
DECLARE
    controllo_iban int;
BEGIN
    --select che controlla esistenza dell'iban inserito
    select count(contocorrente_iban)
    into controllo_iban
    from salvadanaio s
    where contocorrente_iban ILIKE iban_in;
    
    
    if controllo_iban > 0 then
        --query che ritorna i dati del salvadanaio inserito
        RETURN QUERY
        SELECT s.nomesalvadanaio, s.obiettivo || '€' as obiettivo, s.saldorisparmio || '€' as saldorisparmio,
               s.saldorimanente || '€' as saldorimanente, s.descrizione, s.datacreazione
        FROM salvadanaio s
        WHERE s.contocorrente_iban = iban_In;
        else
        RAISE NOTICE E'---------------------------------------------------------------------------\nQuesto conto non ha nessun salvadanaio creato!\n---------------------------------------------------------------------------';
    end if;

    END;
$$ language plpgsql;








create procedure Visualizza_Ultima_Notifica(iban_in varchar)
as
$$
DECLARE
   ultima_transazione int; --transazione.idtransazione%TYPE;
   ultima_notifica varchar; --notifica.descrizionenotifica%TYPE;
   controllo_iban int;
BEGIN

    -- select che controlla l'esistenza dell'iban inserito
   select count(iban2)
   into controllo_iban
   from transazione
   where iban2 = iban_In;


   if controllo_iban > 0 then
       -- select dell'ultima transazione dell'iban inserito
       select idtransazione
       into ultima_transazione
       from transazione
       where iban2 = iban_In and orariotransazione < current_time
       order by idtransazione DESC
       limit 1;

       -- select dell'ultima notifica collegata all'ultima transazione
       select descrizionenotifica
       into ultima_notifica
       from notifica
       where transazione_idtransazione = ultima_transazione
       order by datanotifica DESC, oranotifica DESC
       limit 1;

       RAISE NOTICE E'---------------------------------------------------------------------------\nUltima notifica: % \n---------------------------------------------------------------------------', ultima_notifica;
   else
       RAISE NOTICE E'---------------------------------------------------------------------------\nNon hai nessuna notifica\n---------------------------------------------------------------------------';
   end if;


end;
$$ language plpgsql;
