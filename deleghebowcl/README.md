# Componente di Prodotto

DELEGHEBOWCL

## Descrizione del prodotto

Si tratta della parte angular del back office utilizzato dagli utenti regionali di sportello, per permettere ai cittadini di 
delegare adulti e minori ai servizi del portale della Regione.

## Configurazioni iniziali

E' necessario che il file di properties sia configurato affinchè punti a dei servizi mock generati secondo tali descrittori.

## Prerequisiti di sistema

Angular: version 6.1.5

ANT: Ant version 1.8.0

Server Web: Apache 2.4.6

Application Server: JBoss eap 6.4.5

Tipo di database: postgres  9.2.7 

## Installazione

lanciare il comando ant -Dtarget prod per generare i files che verranno portati nel pacchetto ear di delegheboweb

## Deployment

Inserire il file ear  di delgeheboweb, completato con la parte angular, generato durante l'installazione sotto la cartella deployments del Jboss

## Versioning

Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).
deleghebowcl-2.1.0

## Authors

* [Maurizio Chiappo](https://github.com/maurizio-chiappo)

## Copyrights

“© Copyright Regione Piemonte – 2022”