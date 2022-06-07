import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Comune} from '../../model/comune';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Component({
  selector: 'app-comuni',
  template: `
      <div *ngIf="show" class="state-container-dropdown text-dark">
        <div *ngFor="let comune of comuni" class="dropdown-item"
             (click)="comuneselect(comune.desc)">{{comune.desc}} ({{comune.provincia.sigla}})
        </div>
      </div>
  `,
})
export class ComuniComponent {
  @Input() comuni: Comune[] = [];
  @Input() show: false;
  @Output() selezionaComune: EventEmitter<string> = new EventEmitter<string>();

  comuneselect(value: string) {
    this.selezionaComune.emit(value);
  }

}
