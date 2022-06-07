import {Directive, HostBinding, HostListener, OnInit} from '@angular/core';
/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
@Directive({
  selector: '[appDropdownd]'
})
export class DropdowndDirective  implements OnInit {
  @HostBinding('class.open') isOpen: boolean;

  ngOnInit() {
    this.isOpen = false; // inizializzo la scelta del colore
  }
  // Come gestire gli eventi in una Direttiva (es. movimento del mouse)
  @HostListener('click') toggleOpen() {
    this.isOpen = !this.isOpen;
  }
}
