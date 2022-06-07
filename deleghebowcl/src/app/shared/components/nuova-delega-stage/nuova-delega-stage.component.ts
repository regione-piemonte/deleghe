import {Component, Input, OnInit} from '@angular/core';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Component({
  selector: 'nuova-delega-stage',
  templateUrl: './nuova-delega-stage.component.html',
  styleUrls: ['./nuova-delega-stage.component.css']
})
export class NuovaDelegaStageComponent implements OnInit {

  @Input() stageNames: string[];
  @Input() numberOfActiveStage: number;

  constructor() {
  }

  ngOnInit() {
  }
}
