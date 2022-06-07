/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NuovaDelegaComponent } from './nuova-delega.component';

describe('NuovaDelegaComponent', () => {
  let component: NuovaDelegaComponent;
  let fixture: ComponentFixture<NuovaDelegaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NuovaDelegaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NuovaDelegaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
