/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleDDelegaServizioStato;
import it.csi.deleghe.deleghebe.model.DeleDServizio;
import it.csi.deleghe.deleghebe.model.DeleTDelega;
import it.csi.deleghe.deleghebe.model.DeleTDelegato;
import it.csi.deleghe.deleghebe.service.DeleDDelegaServizioStatoRepository;
import it.csi.deleghe.deleghebe.service.DeleDServizioRepository;
import it.csi.deleghe.deleghebe.service.DeleTDelegaRepository;
import it.csi.deleghe.deleghebe.service.DeleTDelegatoRepository;
import it.csi.deleghe.deleghebe.ws.model.Cittadino;
import it.csi.deleghe.deleghebe.ws.model.Delega;
import it.csi.deleghe.deleghebe.ws.model.DelegaCittadino;
import it.csi.deleghe.deleghebe.ws.model.DelegaServ;
import it.csi.deleghe.deleghebe.ws.model.DelegaServStato;
import it.csi.deleghe.deleghebe.ws.model.DelegaServizio;
import it.csi.deleghe.deleghebe.ws.model.Delegato;
import it.csi.deleghe.deleghebe.ws.model.DichiarazioneDettaglio;
import it.csi.deleghe.deleghebe.ws.model.Servizio;
import it.csi.deleghe.deleghebe.ws.model.Sesso;

public class DelegaDelegaCittadinoMapper {

	@Inject
	private DeleDDelegaServizioStatoRepository deleDDelegaServizioStatoRepository;
	@Inject
	private DeleDServizioRepository deleDServizioRepository;
	@Inject
	private DeleTDelegatoRepository deleTDelegatoRepository;
	@Inject
	private DeleTDelegaRepository deleTDelegaRepository;
	@Inject
	private DelegatoDeleTDelegatoMapper delegatoDeleTDelegatoMapper;

	public Delega estraiDelega(Cittadino cittadinoDelegante, DelegaCittadino delegaCittadino) {
		String tipoDelegaPerInserimento = null;
		Delega delega = new Delega();
		if (delegaCittadino.getUuid() != null) {
			delega.setUuid(UUID.fromString(delegaCittadino.getUuid()));
		}
		Cittadino cittadinoDelegato = new Cittadino();
		cittadinoDelegato.setCognome(delegaCittadino.getCognome());
		cittadinoDelegato.setNome(delegaCittadino.getNome());
		cittadinoDelegato.setCodiceFiscale(delegaCittadino.getCodiceFiscale());
		cittadinoDelegato.setDataNascita(delegaCittadino.getDataDiNascita());
		cittadinoDelegato.setComuneNascita(delegaCittadino.getLuogoNascita());
		cittadinoDelegato.setSesso(delegaCittadino.getSesso().value());
		cittadinoDelegato.setIdAura(delegaCittadino.getIdAura());

		delega.setDelegante(cittadinoDelegante);
		delega.setDelegato(cittadinoDelegato);
		// inserisco il delegato Input per ogni delgato cittadino
		delega.setDelegatoInput(delegaCittadino.getDelegatoInput());
		delega.setTipoDelega(delegaCittadino.getTipoDelega());	


		List<DelegaServ> listDelegaServ = new ArrayList<>();
		for (DelegaServizio delegaServizio : delegaCittadino.getDeleghe()) {
			DelegaServ delegaServ = new DelegaServ();
			if (delegaServizio.getUUID() != null) {
				delegaServ.setUuid(UUID.fromString(delegaServizio.getUUID()));
			}
			delegaServ.setDataDecorrenza(delegaServizio.getDataInizioDelega());
			delegaServ.setDataScadenza(delegaServizio.getDataFineDelega());
			Servizio servizio = new Servizio();
			servizio.setCodice(delegaServizio.getCodiceServizio());
			delegaServ.setServizio(servizio);
			DelegaServStato delegaStato = new DelegaServStato();
			delegaStato.setCodice(delegaServizio.getStatoDelega());
			delegaServ.setStato(delegaStato);
			delegaServ.setDataRevoca(delegaServizio.getDataRevoca());
			delegaServ.setDataRinuncia(delegaServizio.getDataRinuncia());
			//delegaServ.setTipoDelega(delegaServizio.getTipoDelega());
			delegaServ.setGradoDelega(delegaServizio.getGradoDelega());
			listDelegaServ.add(delegaServ);
		}

		delega.setServizi(listDelegaServ);
		return delega;
	}

	public List<DelegaCittadino> estraiDeleganti(List<Delega> deleghe) {
		List<DelegaCittadino> deleganti = new ArrayList<>();
		if (deleghe != null) {
			for (Delega d : deleghe) {
				Cittadino cittadinoDelegante = d.getDelegante();
				DelegaCittadino delegante = estraiDeleganteCittadino(d, cittadinoDelegante);
				deleganti.add(delegante);
			}
		}
		return deleganti;
	}

	public List<DelegaCittadino> estraiDelegati(List<Delega> deleghe) {
		List<DelegaCittadino> delegati = new ArrayList<>();
		if (deleghe != null) {
			for (Delega d : deleghe) {
				Cittadino cittadinoDelegato = d.getDelegato();
				DelegaCittadino delegato = estraiDelegatoCittadino(d, cittadinoDelegato);
				delegati.add(delegato);
			}
		}
		return delegati;
	}


	public DelegaCittadino estraiDelegatiByDettagli(DichiarazioneDettaglio dettaglio) {

		if (dettaglio != null) {

			return estraiDelegaCittadinoPerMinori(dettaglio);
		}
		return null;
	}

	public DelegaCittadino estraiDeleganteCittadino(Delega d, Cittadino cittadinoDelganteODelegato) {

		DelegaCittadino delegante = new DelegaCittadino();
		delegante.setCodiceFiscale(cittadinoDelganteODelegato.getCodiceFiscale());
		delegante.setCognome(cittadinoDelganteODelegato.getCognome());
		delegante.setDataDiNascita(cittadinoDelganteODelegato.getDataNascita());
		delegante.setNome(cittadinoDelganteODelegato.getNome());
		delegante.setSesso(Sesso.fromValue(cittadinoDelganteODelegato.getSesso()));
		delegante.setLuogoNascita(cittadinoDelganteODelegato.getComuneNascita());
		delegante.setIdAura(cittadinoDelganteODelegato.getIdAura());
		delegante.setUuid(d.getUuid().toString());

		List<DelegaServizio> listDelegaServizio = new ArrayList<>();
		for (DelegaServ delegaServ : d.getServizi()) {
			DelegaServizio delegaServizio = new DelegaServizio();
			delegaServizio.setCodiceServizio(delegaServ.getServizio().getCodice());
			delegaServizio.setDataInizioDelega(delegaServ.getDataDecorrenza());
			delegaServizio.setDataFineDelega(delegaServ.getDataScadenza());
			delegaServizio.setStatoDelega(delegaServ.getStato().getCodice());
			delegaServizio.setTipoDelega(delegaServ.getTipoDelega());
			delegaServizio.setGradoDelega(delegaServ.getGradoDelega());
			delegaServizio.setDataRinuncia(delegaServ.getDataRinuncia());
			delegaServizio.setDataRevoca(delegaServ.getDataRevoca());
			delegaServizio.setUUID((delegaServ.getUuid() != null)? delegaServ.getUuid().toString() : null);

			listDelegaServizio.add(delegaServizio);
		}
		delegante.setDeleghe(listDelegaServizio);
		return delegante;
	}

	public DelegaCittadino estraiDelegatoCittadino(Delega d, Cittadino cittadinoDelganteODelegato) {

		DelegaCittadino delegato = null;

		DeleTDelega deleTDelega = deleTDelegaRepository.ricercaDelegheByUUID(d.getUuid());
		DeleTDelegato deleTDelegato = deleTDelegatoRepository.ricercaDelegatoByDelega(deleTDelega.getDlgaId());

		if (deleTDelegato != null) {

			Delegato cittadinoDelegato = delegatoDeleTDelegatoMapper.from(deleTDelegato);

			delegato = new DelegaCittadino();
			delegato.setCodiceFiscale(cittadinoDelegato.getCodiceFiscale());
			delegato.setCognome(cittadinoDelegato.getCognome());
			delegato.setDataDiNascita(cittadinoDelegato.getDataNascita());
			delegato.setNome(cittadinoDelegato.getNome());
			delegato.setSesso(Sesso.fromValue(cittadinoDelegato.getSesso()));
			delegato.setLuogoNascita(cittadinoDelegato.getComuneNascita());
			delegato.setIdAura(cittadinoDelganteODelegato.getIdAura());
			delegato.setUuid(d.getUuid().toString());

			List<DelegaServizio> listDelegaServizio = new ArrayList<>();
			for (DelegaServ delegaServ : d.getServizi()) {
				DelegaServizio delegaServizio = new DelegaServizio();
				delegaServizio.setCodiceServizio(delegaServ.getServizio().getCodice());
				delegaServizio.setDataInizioDelega(delegaServ.getDataDecorrenza());
				delegaServizio.setDataFineDelega(delegaServ.getDataScadenza());
				delegaServizio.setDataRevoca(delegaServ.getDataRevoca());
				delegaServizio.setDataRinuncia(delegaServ.getDataRinuncia());
				delegaServizio.setStatoDelega(delegaServ.getStato().getCodice());
				delegaServizio.setUUID((delegaServ.getUuid() != null) ? delegaServ.getUuid().toString() : null);
				delegaServizio.setTipoDelega(delegaServ.getTipoDelega());
				delegaServizio.setGradoDelega(delegaServ.getGradoDelega());

				listDelegaServizio.add(delegaServizio);
			}
			delegato.setDeleghe(listDelegaServizio);
		}

		return delegato;
	}


	public DelegaCittadino estraiDelegaCittadinoPerMinori(DichiarazioneDettaglio dettaglio) {
		Cittadino cittadinoDelegato = dettaglio.getCittadino1();

		DelegaCittadino delegaCittadino = new DelegaCittadino();
		delegaCittadino.setCodiceFiscale(cittadinoDelegato.getCodiceFiscale());
		delegaCittadino.setCognome(cittadinoDelegato.getCognome());
		delegaCittadino.setDataDiNascita(cittadinoDelegato.getDataNascita());
		delegaCittadino.setNome(cittadinoDelegato.getNome());
		delegaCittadino.setSesso(Sesso.fromValue(cittadinoDelegato.getSesso()));
		delegaCittadino.setLuogoNascita(cittadinoDelegato.getComuneNascita());
		delegaCittadino.setIdAura(cittadinoDelegato.getIdAura());
		delegaCittadino.setTipoDelega(dettaglio.getTipoDelega());

		Delegato delegatoInput = new Delegato();
		delegatoInput.setCodiceFiscale(cittadinoDelegato.getCodiceFiscale());
		delegatoInput.setCognome(cittadinoDelegato.getCognome());
		delegatoInput.setNome(cittadinoDelegato.getNome());
		delegatoInput.setComuneNascita(cittadinoDelegato.getComuneNascita());
		delegatoInput.setDataNascita(cittadinoDelegato.getDataNascita());
		delegatoInput.setSesso(cittadinoDelegato.getSesso());

		delegaCittadino.setDelegatoInput(delegatoInput);

		List<DelegaServizio> listDelegaServizio = new ArrayList<>();

		DeleDDelegaServizioStato deleDDelegaServizioStato = deleDDelegaServizioStatoRepository.ricercaServiziStatoByDelstatoCod(DeleDDelegaServizioStatoRepository.DelstatoCodEnum.ATTIVA.name());


		Cittadino cittadinoDelegante = dettaglio.getCittadino2();
		Date dataScadenzaDelega = cittadinoDelegante.getDataNascita();
		Calendar c = Calendar.getInstance();
		c.setTime(dataScadenzaDelega);
		c.add(Calendar.YEAR, +18);
		dataScadenzaDelega = c.getTime();


		for (DeleDServizio deleDServizio : deleDServizioRepository.getAllServicesDelegabiliMinore()) {
			DelegaServizio delegaServizio = new DelegaServizio();
			delegaServizio.setCodiceServizio(deleDServizio.getSerCod());
			delegaServizio.setDataFineDelega(dataScadenzaDelega);
			delegaServizio.setStatoDelega(deleDDelegaServizioStato.getDelstatoCod());

			listDelegaServizio.add(delegaServizio);
		}
		delegaCittadino.setDeleghe(listDelegaServizio);

		return delegaCittadino;
	}
}
