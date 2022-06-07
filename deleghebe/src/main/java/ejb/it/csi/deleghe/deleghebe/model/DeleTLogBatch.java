/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the dele_t_log_batch database table.
 * 
 */
@Entity
@Table(name="dele_t_log_batch")
@NamedQuery(name="DeleTLogBatch.findAll", query="SELECT d FROM DeleTLogBatch d")
public class DeleTLogBatch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@SequenceGenerator(name="DELE_T_LOG_BATCH_BATCHID_GENERATOR", sequenceName="DELE_T_LOG_BATCH_BATCH_ID_SEQ", allocationSize=1)
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_T_LOG_BATCH_BATCHID_GENERATOR")
	@Column(name="batch_id")
	private Long batchId;

	@Id
	@Column(name="batch_name")
	private String batchName;

	@Column(name="data_ora")
	private Timestamp dataOra;

	private String result;

	public DeleTLogBatch() {
	}

	public Long getBatchId() {
		return this.batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public String getBatchName() {
		return this.batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public Timestamp getDataOra() {
		return this.dataOra;
	}

	public void setDataOra(Timestamp dataOra) {
		this.dataOra = dataOra;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}