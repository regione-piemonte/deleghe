# Componente di Prodotto

DELEGHEBE

## Descrizione del prodotto

Si tratta di API di servizi rivolti al cittadino tra il front end, realizzato tramite il prodotto [SANSOL](https://github.com/regione-piemonte/sansol);
un secondo frontend,realizzato tramite il prodotto [APISAN](https://github.com/regione-piemonte/apisan). 
Esiste Un terzo frontend di Back Office, utilizzato dalgi utenti regionali, realizzato tramite il prodotto DELEGHE pubblicato in [DELEGHEBOWEB](https://github.com/regione-piemonte/deleghe/tree/master/delegheboweb); 

## Configurazioni iniziali

I servizi si trovano al seguente link SERVIZI SOAP(mettere puntamento ai 3 wsdl).
Gli script per l'inizializzazione del database sotto la cartella SCRIPT(mettere il puntamento allo script db). 
E' necessario che il file di properties sia configurato affinchè punti a dei servizi mock generati secondo tali descrittori.

## Prerequisiti di sistema

Java: Jdk 1.8.0_121

ANT: Ant version 1.8.0

Server Web: Apache 2.4.6

Application Server: JBoss eap 6.4.5

Tipo di database: postgres  9.2.7 

## Installazione

lanciare il comando ant -Dtarget prod per generare l'ear

## Deployment

Inserire il file ear generato durante l'installazione sotto la cartella deployments del Jboss

## Versioning

Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).
deleghebe-2.1.0

## Authors
* [Maurizio Chiappo](https://github.com/maurizio-chiappo)

## Copyrights

“© Copyright Regione Piemonte – 2022”