import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHouse } from 'app/shared/model/house.model';
import { HouseService } from './house.service';

@Component({
  templateUrl: './house-delete-dialog.component.html',
})
export class HouseDeleteDialogComponent {
  house?: IHouse;

  constructor(protected houseService: HouseService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.houseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('houseListModification');
      this.activeModal.close();
    });
  }
}
