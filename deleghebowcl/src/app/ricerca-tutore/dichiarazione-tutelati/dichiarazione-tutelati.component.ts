import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {DefaultService} from '../../api/default.service';
import {StateService} from '../../shared/service/state.service';

/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Component ({
  selector: 'app-dichiarazione-tutelati',
  templateUrl: './dichiarazione-tutelati.component.html',
  styleUrls: ['./dichiarazione-tutelati.component.css']
})
export class DichiarazioneTutelatiComponent implements OnInit {

  constructor(
    protected  route: ActivatedRoute,
    protected  router: Router,
    protected  defaultService: DefaultService,
    protected  stateService: StateService,
  ) {
  }

  fiscalCode;
  citizen;

  onCitizenWasNotFound() {
    this.router.navigate(['ricerca', 'tutore']);
  }

  get newDichiarazione() {
    return this.stateService.data.newDichiarazione;
  }

  set newDichiarazione(newDichiarazione) {
    this.stateService.data.newDichiarazione = newDichiarazione;
  }

  onCitizenFound() {
    this.fiscalCode = this.workingCitizen.codice_fiscale;
    this.newDichiarazione = {};
    this.newDichiarazione.citizen = this.citizen;
  }

  get workingCitizen() {
    return this.stateService.data.workingCitizen;
  }

  ngOnInit() {
    if (this.workingCitizen) {
      this.onCitizenFound();
      this.citizen = this.workingCitizen;
    } else {
      this.onCitizenWasNotFound()
    }
  }


  public openConfermaDialog() {
  }
}
