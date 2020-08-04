import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBathRoom } from 'app/shared/model/bath-room.model';
import { BathRoomService } from './bath-room.service';

@Component({
  templateUrl: './bath-room-delete-dialog.component.html',
})
export class BathRoomDeleteDialogComponent {
  bathRoom?: IBathRoom;

  constructor(protected bathRoomService: BathRoomService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bathRoomService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bathRoomListModification');
      this.activeModal.close();
    });
  }
}
