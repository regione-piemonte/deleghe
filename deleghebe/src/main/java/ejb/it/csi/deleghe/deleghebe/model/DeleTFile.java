/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the dele_t_file database table.
 * 
 */
@Entity
@Table(name="dele_t_file")
@NamedQuery(name="DeleTFile.findAll", query="SELECT d FROM DeleTFile d")
public class DeleTFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_T_FILE_FILEID_GENERATOR", sequenceName="DELE_T_FILE_FILE_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_T_FILE_FILEID_GENERATOR")
	@Column(name="file_id")
	private Integer fileId;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	private byte[] file;

	@Column(name="file_name")
	private String fileName;

	@Column(name="file_size")
	private BigDecimal fileSize;

	@Column(name="login_operazione")
	private String loginOperazione;

	@Column(name="ruoloop_id")
	private Integer ruoloopId;

	//bi-directional many-to-one association to DeleTDocumento
	@OneToMany(mappedBy="deleTFile")
	private List<DeleTDocumento> deleTDocumentos;

	public DeleTFile() {
	}

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Date getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataModifica() {
		return this.dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public byte[] getFile() {
		return this.file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public BigDecimal getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(BigDecimal fileSize) {
		this.fileSize = fileSize;
	}

	public String getLoginOperazione() {
		return this.loginOperazione;
	}

	public void setLoginOperazione(String loginOperazione) {
		this.loginOperazione = loginOperazione;
	}

	public Integer getRuoloopId() {
		return this.ruoloopId;
	}

	public void setRuoloopId(Integer ruoloopId) {
		this.ruoloopId = ruoloopId;
	}

	public List<DeleTDocumento> getDeleTDocumentos() {
		return this.deleTDocumentos;
	}

	public void setDeleTDocumentos(List<DeleTDocumento> deleTDocumentos) {
		this.deleTDocumentos = deleTDocumentos;
	}

	public DeleTDocumento addDeleTDocumento(DeleTDocumento deleTDocumento) {
		getDeleTDocumentos().add(deleTDocumento);
		deleTDocumento.setDeleTFile(this);

		return deleTDocumento;
	}

	public DeleTDocumento removeDeleTDocumento(DeleTDocumento deleTDocumento) {
		getDeleTDocumentos().remove(deleTDocumento);
		deleTDocumento.setDeleTFile(null);

		return deleTDocumento;
	}

}