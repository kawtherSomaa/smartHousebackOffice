import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDoor } from 'app/shared/model/door.model';
import { DoorService } from './door.service';

@Component({
  templateUrl: './door-delete-dialog.component.html',
})
export class DoorDeleteDialogComponent {
  door?: IDoor;

  constructor(protected doorService: DoorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.doorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('doorListModification');
      this.activeModal.close();
    });
  }
}
