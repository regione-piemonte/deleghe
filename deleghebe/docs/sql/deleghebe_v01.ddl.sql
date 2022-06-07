/* ---------------------------------------------------------------------- */
/* Add tables                                                             */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "dele_d_delega_servizio_stato"                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE dele_d_delega_servizio_stato (
    delstato_id SERIAL  NOT NULL,
    delstato_cod TEXT  NOT NULL,
    delstato_desc TEXT  NOT NULL,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP DEFAULT now(),
    login_operazione TEXT  NOT NULL,
    CONSTRAINT PK_dele_d_delega_servizio_stato PRIMARY KEY (delstato_id)
);

COMMENT ON COLUMN dele_d_delega_servizio_stato.delstato_cod IS 'codice stato';

COMMENT ON COLUMN dele_d_delega_servizio_stato.delstato_desc IS 'descrizione stato';

COMMENT ON COLUMN dele_d_delega_servizio_stato.data_modifica IS 'pari a creazione al primo inserimento';

/* ---------------------------------------------------------------------- */
/* Add table "dele_d_servizio"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE dele_d_servizio (
    ser_id SERIAL  NOT NULL,
    ser_cod TEXT  NOT NULL,
    ser_desc TEXT,
    ser_descestesa TEXT,
    validita_inizio TIMESTAMP DEFAULT now(),
    validita_fine TIMESTAMP DEFAULT now(),
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP DEFAULT now(),
    login_operazione TEXT  NOT NULL,
    CONSTRAINT PK_dele_d_servizio PRIMARY KEY (ser_id)
);

CREATE UNIQUE INDEX IDX_dele_d_servizio_1 ON dele_d_servizio (ser_cod,validita_inizio) where data_cancellazione IS NULL;

COMMENT ON COLUMN dele_d_servizio.ser_cod IS 'codice servizio';

COMMENT ON COLUMN dele_d_servizio.ser_desc IS 'descrizione servizio';

COMMENT ON COLUMN dele_d_servizio.ser_descestesa IS 'descrizione estesa servizio';

COMMENT ON COLUMN dele_d_servizio.validita_fine IS 'valorizzato in caso dicessazione servizio';

COMMENT ON COLUMN dele_d_servizio.data_modifica IS 'pari a creazione al primo inserimento';

/* ---------------------------------------------------------------------- */
/* Add table "dele_d_parametro"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE dele_d_parametro (
    par_id SERIAL  NOT NULL,
    par_cod TEXT  NOT NULL,
    par_desc TEXT  NOT NULL,
    par_valore TEXT  NOT NULL,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP DEFAULT now(),
    login_operazione TEXT  NOT NULL,
    CONSTRAINT PK_dele_d_parametro PRIMARY KEY (par_id)
);

COMMENT ON COLUMN dele_d_parametro.par_cod IS 'codice parametro globale';

COMMENT ON COLUMN dele_d_parametro.par_desc IS 'descrizione parametro globale';

COMMENT ON COLUMN dele_d_parametro.par_valore IS 'valore parametro globale';

COMMENT ON COLUMN dele_d_parametro.data_modifica IS 'pari a creazione al primo inserimento';

COMMENT ON COLUMN dele_d_parametro.data_cancellazione IS 'valorizzata indica cancellazione logica del record';

/* ---------------------------------------------------------------------- */
/* Add table "dele_d_errore"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE dele_d_errore (
    err_id SERIAL  NOT NULL,
    err_cod TEXT,
    err_desc TEXT,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP DEFAULT now(),
    login_operazione TEXT  NOT NULL,
    CONSTRAINT PK_dele_d_errore PRIMARY KEY (err_id)
);

COMMENT ON COLUMN dele_d_errore.err_cod IS 'codice errore';

COMMENT ON COLUMN dele_d_errore.err_desc IS 'descrizione errore';

COMMENT ON COLUMN dele_d_errore.data_modifica IS 'pari a creazione al primo inserimento';

COMMENT ON COLUMN dele_d_errore.data_cancellazione IS 'valorizzata indica cancellazione logica del record';

/* ---------------------------------------------------------------------- */
/* Add table "dele_t_servizio_parametro"                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE dele_t_servizio_parametro (
    serpar_id SERIAL  NOT NULL,
    ser_id INTEGER,
    serpar_cod TEXT  NOT NULL,
    serpar_desc TEXT  NOT NULL,
    serpar_valore TEXT  NOT NULL,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP DEFAULT now(),
    login_operazione TEXT  NOT NULL,
    CONSTRAINT PK_dele_t_servizio_parametro PRIMARY KEY (serpar_id)
);

COMMENT ON COLUMN dele_t_servizio_parametro.serpar_cod IS 'codice parametro servizio';

COMMENT ON COLUMN dele_t_servizio_parametro.serpar_desc IS 'descrizione parametro servizio';

COMMENT ON COLUMN dele_t_servizio_parametro.serpar_valore IS 'valore parametro servizio';

COMMENT ON COLUMN dele_t_servizio_parametro.validita_fine IS 'valorizzato quando il valore del parametro cambia nel tempo';

COMMENT ON COLUMN dele_t_servizio_parametro.data_modifica IS 'pari a creazione al primo inserimento';

/* ---------------------------------------------------------------------- */
/* Add table "csi_log_audit"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE csi_log_audit (
    audit_id SERIAL  NOT NULL,
    data_ora TIMESTAMP  NOT NULL,
    id_app CHARACTER VARYING(100)  NOT NULL,
    ip_address CHARACTER VARYING(40)  NOT NULL,
    utente CHARACTER VARYING(100)  NOT NULL,
    operazione CHARACTER VARYING(50)  NOT NULL,
    ogg_oper CHARACTER VARYING(500)  NOT NULL,
    key_oper CHARACTER VARYING(500),
    uuid UUID,
    idrequest INTEGER,
    PRIMARY KEY (audit_id)
);

COMMENT ON COLUMN csi_log_audit.data_ora IS 'Data e ora dell''evento';

COMMENT ON COLUMN csi_log_audit.id_app IS 'Codice identificativo dell''applicazione utilizzata dall''utente; da comporre con i valori presenti in Anagrafica Prodotti: <codice prodotto>_<codice linea cliente>_<codice ambiente>_<codice Unità di Installazione>';

COMMENT ON COLUMN csi_log_audit.ip_address IS 'Ip del client utente (se possibile)';

COMMENT ON COLUMN csi_log_audit.utente IS 'Identificativo univoco dell''utente che ha effettuato l''operazione (es. login / codice fiscale / matricola / ecc.)';

COMMENT ON COLUMN csi_log_audit.operazione IS 'Questo campo dovrà contenere l''informazione circa l''operazione effettuata; utilizzare uno dei seguenti valori: login / logout / read / insert / update / delete / merge Nei casi in cui il nome dell''operazione di business sia significativo e non riconducibile all''elenco di cui sopra, è possibile indicare il nome dell''operazione.';

COMMENT ON COLUMN csi_log_audit.ogg_oper IS 'Questa campo consentirà di identificare i dati e le informazioni trattati dall''operazione. Se la funzionalità lo permette inserire il nome delle tabelle (o in alternativa degli oggetti/entità) su cui viene eseguita l''operazione; l''indicazione della colonna è opzionale e andrà indicata nel formato tabella.colonna. Se l''applicativo prevede accessi a schemi dati esterni premettere il nome dello schema proprietario al nome della tabella.';

COMMENT ON COLUMN csi_log_audit.key_oper IS 'Questo campo dovrà contenere l''identificativo univoco dell''oggetto dell''operazione oppure nel caso di aggiornamenti multipli del valore che caratterizza l''insieme di oggetti (es: modifica di un dato in tutta una categoria merceologica)';

/* ---------------------------------------------------------------------- */
/* Add table "d_servizio"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE d_servizio (
    serv_id SERIAL  NOT NULL,
    serv_cod TEXT  NOT NULL,
    serv_desc TEXT,
    serv_descestesa TEXT,
    serv_arruolabile BOOLEAN,
    serv_delegabile BOOLEAN,
    serv_notificabile BOOLEAN,
    serv_obblarruolamento BOOLEAN,
    serv_url TEXT  NOT NULL,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP DEFAULT now(),
    login_operazione TEXT  NOT NULL,
    CONSTRAINT PK_d_servizio PRIMARY KEY (serv_id)
);

CREATE UNIQUE INDEX IDX_d_servizio_1 ON d_servizio (serv_cod,validita_inizio) where data_cancellazione IS NULL;

COMMENT ON COLUMN d_servizio.serv_cod IS 'codice servizio';

COMMENT ON COLUMN d_servizio.serv_desc IS 'descrizione servizio';

COMMENT ON COLUMN d_servizio.serv_descestesa IS 'descrizione estesa servizio ';

COMMENT ON COLUMN d_servizio.data_modifica IS 'pari a creazione al primo inserimento';

COMMENT ON COLUMN d_servizio.data_cancellazione IS 'valorizzata indica cancellazione logica del record';

/* ---------------------------------------------------------------------- */
/* Add table "dele_t_cittadino"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE dele_t_cittadino (
    cit_id SERIAL  NOT NULL,
    cit_nome TEXT  NOT NULL,
    cit_cognome TEXT  NOT NULL,
    cit_cf TEXT  NOT NULL,
    cit_nascita_data TIMESTAMP,
    cit_nascita_comune TEXT,
    cit_sesso TEXT,
    cit_auraid TEXT,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP DEFAULT now(),
    login_operazione TEXT  NOT NULL,
    audit_id INTEGER,
    CONSTRAINT PK_dele_t_cittadino PRIMARY KEY (cit_id)
);

COMMENT ON COLUMN dele_t_cittadino.cit_nome IS 'nome';

COMMENT ON COLUMN dele_t_cittadino.cit_cognome IS 'cognome';

COMMENT ON COLUMN dele_t_cittadino.cit_cf IS 'codice fiscale';

COMMENT ON COLUMN dele_t_cittadino.cit_auraid IS 'se presente denota cittadino piemontese';

COMMENT ON COLUMN dele_t_cittadino.data_modifica IS 'pari a creazione al primo inserimento';

COMMENT ON COLUMN dele_t_cittadino.data_cancellazione IS 'valorizzata indica cancellazione logica del record';

/* ---------------------------------------------------------------------- */
/* Add table "dele_t_delega"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE dele_t_delega (
    dlga_id SERIAL  NOT NULL,
    cit_id_delegante INTEGER  NOT NULL,
    cit_id_delegato INTEGER  NOT NULL,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP DEFAULT now(),
    login_operazione TEXT  NOT NULL,
    CONSTRAINT PK_dele_t_delega PRIMARY KEY (dlga_id)
);

CREATE UNIQUE INDEX IDX_dele_t_delega_1 ON dele_t_delega (cit_id_delegante,cit_id_delegato) where data_cancellazione IS NULL;

COMMENT ON COLUMN dele_t_delega.data_modifica IS 'pari a creazione al primo inserimento';

COMMENT ON COLUMN dele_t_delega.data_cancellazione IS 'valorizzata indica cancellazione logica del record';

/* ---------------------------------------------------------------------- */
/* Add table "dele_t_delega_servizio"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE dele_t_delega_servizio (
    del_id SERIAL  NOT NULL,
    dlga_id INTEGER  NOT NULL,
    ser_id INTEGER  NOT NULL,
    delstato_id INTEGER  NOT NULL,
    del_datadecorrenza TIMESTAMP DEFAULT now()  NOT NULL,
    del_datascadenza TIMESTAMP DEFAULT now()  NOT NULL,
    del_datarevoca TIMESTAMP DEFAULT now(),
    del_datarinuncia TIMESTAMP DEFAULT now(),
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP DEFAULT now(),
    login_operazione TEXT  NOT NULL,
    audit_id INTEGER,
    CONSTRAINT PK_dele_t_delega_servizio PRIMARY KEY (del_id)
);

CREATE UNIQUE INDEX IDX_dele_t_delega_servizio_1 ON dele_t_delega_servizio (dlga_id,ser_id) where data_cancellazione IS NULL;

COMMENT ON COLUMN dele_t_delega_servizio.del_datadecorrenza IS 'data inizio della delega';

COMMENT ON COLUMN dele_t_delega_servizio.del_datascadenza IS 'data fine della delega. oltre questa data la delega è scaduta';

COMMENT ON COLUMN dele_t_delega_servizio.del_datarevoca IS 'data revoca';

COMMENT ON COLUMN dele_t_delega_servizio.del_datarinuncia IS 'data rinuncia';

COMMENT ON COLUMN dele_t_delega_servizio.data_modifica IS 'pari a creazione al primo inserimento. cambia se ad esempio vengono aggiornate le date scadenza, revoca, rinuncia';

/* ---------------------------------------------------------------------- */
/* Add table "dele_t_delegato"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE dele_t_delegato (
    dlgo_id SERIAL  NOT NULL,
    dlgo_nome TEXT  NOT NULL,
    dlgo_cognome TEXT  NOT NULL,
    dlgo_cf TEXT  NOT NULL,
    dlgo_nascita_data TIMESTAMP  NOT NULL,
    dlgo_nascita_comune TEXT  NOT NULL,
    dlgo_sesso TEXT  NOT NULL,
    dlga_id INTEGER,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP DEFAULT now(),
    login_operazione TEXT  NOT NULL,
    CONSTRAINT PK_dele_t_delegato PRIMARY KEY (dlgo_id)
);

COMMENT ON COLUMN dele_t_delegato.dlgo_nome IS 'nome';

COMMENT ON COLUMN dele_t_delegato.dlgo_cognome IS 'cognome';

COMMENT ON COLUMN dele_t_delegato.dlgo_cf IS 'codice fiscale';

COMMENT ON COLUMN dele_t_delegato.dlgo_nascita_data IS 'data di nascita';

COMMENT ON COLUMN dele_t_delegato.dlgo_nascita_comune IS 'comune di nascita';

COMMENT ON COLUMN dele_t_delegato.dlgo_sesso IS 'sesso';

COMMENT ON COLUMN dele_t_delegato.data_modifica IS 'pari a creazione al primo inserimento';

COMMENT ON COLUMN dele_t_delegato.data_cancellazione IS 'valorizzata indica cancellazione logica del record';

/* ---------------------------------------------------------------------- */
/* Add table "dele_s_delega"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE dele_s_delega (
    dels_id SERIAL  NOT NULL,
    del_id INTEGER  NOT NULL,
    dlga_id INTEGER  NOT NULL,
    ser_id INTEGER  NOT NULL,
    delstato_id INTEGER  NOT NULL,
    del_datadecorrenza TIMESTAMP DEFAULT now()  NOT NULL,
    del_datascadenza TIMESTAMP DEFAULT now()  NOT NULL,
    del_datarevoca TIMESTAMP DEFAULT now(),
    del_datarinuncia TIMESTAMP DEFAULT now(),
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP DEFAULT now(),
    login_operazione TEXT  NOT NULL,
    audit_id INTEGER,
    CONSTRAINT PK_dele_s_delega PRIMARY KEY (dels_id)
);

CREATE UNIQUE INDEX IDX_dele_s_delega_1 ON dele_s_delega (dlga_id,ser_id,delstato_id,validita_inizio) where data_cancellazione IS NULL;

COMMENT ON COLUMN dele_s_delega.del_datadecorrenza IS 'data inizio della delega';

COMMENT ON COLUMN dele_s_delega.del_datascadenza IS 'data fine della delega. oltre questa data la delega è scaduta';

COMMENT ON COLUMN dele_s_delega.del_datarevoca IS 'data revoca';

COMMENT ON COLUMN dele_s_delega.del_datarinuncia IS 'data rinuncia';

COMMENT ON COLUMN dele_s_delega.validita_inizio IS 'inizio validità del record';

COMMENT ON COLUMN dele_s_delega.validita_fine IS 'fine validità del record';

COMMENT ON COLUMN dele_s_delega.data_modifica IS 'pari a creazione al primo inserimento. cambia se ad esempio vengono aggiornate le date scadenza, revoca, rinuncia';

/* ---------------------------------------------------------------------- */
/* Add foreign key constraints                                            */
/* ---------------------------------------------------------------------- */

ALTER TABLE dele_t_delega_servizio ADD CONSTRAINT dele_d_servizio_dele_t_delega_servizio 
    FOREIGN KEY (ser_id) REFERENCES dele_d_servizio (ser_id);

ALTER TABLE dele_t_delega_servizio ADD CONSTRAINT dele_t_delega_dele_t_delega_servizio 
    FOREIGN KEY (dlga_id) REFERENCES dele_t_delega (dlga_id);

ALTER TABLE dele_t_delega_servizio ADD CONSTRAINT dele_d_delega_servizio_stato_dele_t_delega_servizio 
    FOREIGN KEY (delstato_id) REFERENCES dele_d_delega_servizio_stato (delstato_id);

ALTER TABLE dele_t_delega_servizio ADD CONSTRAINT csi_log_audit_dele_t_delega_servizio 
    FOREIGN KEY (audit_id) REFERENCES csi_log_audit (audit_id);

ALTER TABLE dele_t_cittadino ADD CONSTRAINT csi_log_audit_dele_t_cittadino 
    FOREIGN KEY (audit_id) REFERENCES csi_log_audit (audit_id);

ALTER TABLE dele_t_delegato ADD CONSTRAINT dele_t_delega_dele_t_delegato 
    FOREIGN KEY (dlga_id) REFERENCES dele_t_delega (dlga_id);

ALTER TABLE dele_t_delega ADD CONSTRAINT dele_t_cittadino_dele_t_delega 
    FOREIGN KEY (cit_id_delegante) REFERENCES dele_t_cittadino (cit_id);

ALTER TABLE dele_t_delega ADD CONSTRAINT dele_t_cittadino_dele_t_delega2 
    FOREIGN KEY (cit_id_delegato) REFERENCES dele_t_cittadino (cit_id);

ALTER TABLE dele_s_delega ADD CONSTRAINT dele_t_delega_servizio_dele_s_delega 
    FOREIGN KEY (del_id) REFERENCES dele_t_delega_servizio (del_id);

ALTER TABLE dele_s_delega ADD CONSTRAINT dele_t_delega_dele_s_delega 
    FOREIGN KEY (dlga_id) REFERENCES dele_t_delega (dlga_id);

ALTER TABLE dele_s_delega ADD CONSTRAINT dele_d_delega_servizio_stato_dele_s_delega 
    FOREIGN KEY (delstato_id) REFERENCES dele_d_delega_servizio_stato (delstato_id);

ALTER TABLE dele_s_delega ADD CONSTRAINT dele_d_servizio_dele_s_delega 
    FOREIGN KEY (ser_id) REFERENCES dele_d_servizio (ser_id);

ALTER TABLE dele_s_delega ADD CONSTRAINT csi_log_audit_dele_s_delega 
    FOREIGN KEY (audit_id) REFERENCES csi_log_audit (audit_id);

ALTER TABLE dele_t_servizio_parametro ADD CONSTRAINT dele_d_servizio_dele_t_servizio_parametro 
    FOREIGN KEY (ser_id) REFERENCES dele_d_servizio (ser_id);


/* ---------------------------------------------------------------------- */
/* Add foreign key indexes                                                */
/* ---------------------------------------------------------------------- */

CREATE INDEX dele_s_delega_fk_audit_id_idx on dele_s_delega using btree ("audit_id");
CREATE INDEX dele_s_delega_fk_del_id_idx on dele_s_delega using btree ("del_id");
CREATE INDEX dele_s_delega_fk_delstato_id_idx on dele_s_delega using btree ("delstato_id");
CREATE INDEX dele_s_delega_fk_dlga_id_idx on dele_s_delega using btree ("dlga_id");
CREATE INDEX dele_s_delega_fk_ser_id_idx on dele_s_delega using btree ("ser_id");
CREATE INDEX dele_t_cittadino_fk_audit_id_idx on dele_t_cittadino using btree ("audit_id");
CREATE INDEX dele_t_delega_fk_cit_id_delegante_idx on dele_t_delega using btree ("cit_id_delegante");
CREATE INDEX dele_t_delega_fk_cit_id_delegato_idx on dele_t_delega using btree ("cit_id_delegato");
CREATE INDEX dele_t_delega_servizio_fk_audit_id_idx on dele_t_delega_servizio using btree ("audit_id");
CREATE INDEX dele_t_delega_servizio_fk_delstato_id_idx on dele_t_delega_servizio using btree ("delstato_id");
CREATE INDEX dele_t_delega_servizio_fk_dlga_id_idx on dele_t_delega_servizio using btree ("dlga_id");
CREATE INDEX dele_t_delega_servizio_fk_ser_id_idx on dele_t_delega_servizio using btree ("ser_id");
CREATE INDEX dele_t_delegato_fk_dlga_id_idx on dele_t_delegato using btree ("dlga_id");
CREATE INDEX dele_t_servizio_parametro_fk_ser_id_idx on dele_t_servizio_parametro using btree ("ser_id");