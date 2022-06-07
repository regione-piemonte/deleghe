/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
export interface Alert {
  type: AlertType;
  message: string;
}

export enum AlertType {
  Success = "Success",
  Error = "Error",
  Info = "Info",
  Warning = "Warning"
}
