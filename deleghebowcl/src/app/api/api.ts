export * from './default.service';
import { DefaultService } from './default.service';
export * from './gestioneDeleghe.service';
import { GestioneDelegheService } from './gestioneDeleghe.service';
export * from './gestioneDichiarazioni.service';
import { GestioneDichiarazioniService } from './gestioneDichiarazioni.service';
export * from './gestioneCodifiche.service';
import { GestioneCodificheService } from './gestioneCodifiche.service';
export * from './gestioneComuni.service';
import { GestioneComuniService } from './gestioneComuni.service';
export const APIS = [DefaultService, GestioneDelegheService, GestioneDichiarazioniService, GestioneCodificheService, GestioneComuniService];
