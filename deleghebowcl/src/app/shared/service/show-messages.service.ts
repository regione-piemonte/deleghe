import {Injectable} from '@angular/core';
import {Message} from "primeng/api";
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Injectable()
export class ShowMessagesService {

  private msgs: Message[] = [];

  getMsgs(): Message[] {
    return this.msgs;
  }

  setMsgs(value: Message[]) {
    this.msgs = value;
    setTimeout(() => {
      this.msgs = []
    }, 10000)
  }
}
