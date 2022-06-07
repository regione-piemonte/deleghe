/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

import { Input } from "@angular/core";

export enum Servizi {
  PTW = 'Pagamento Ticket (PWT)',
  RE = 'Ritiro referti',
  FP = 'Fruizione promemoria',
  ER = 'Esenzione da reddito',
  EP = 'Esenzione per patologia',
  ROL = 'Arruolamento fascicolo',
}

export class ServiziInfo {
  nome?: string;
  descrizione?: string;
  service_descrizione?: string;
  cod_serpadre?: string;
  frase_debole?: string;
  frase_forte?: string;
  numero_giorni_delegabili?: number;
  delegabile?: boolean;
}
