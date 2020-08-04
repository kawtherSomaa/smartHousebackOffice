import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILivingRoom } from 'app/shared/model/living-room.model';
import { LivingRoomService } from './living-room.service';

@Component({
  templateUrl: './living-room-delete-dialog.component.html',
})
export class LivingRoomDeleteDialogComponent {
  livingRoom?: ILivingRoom;

  constructor(
    protected livingRoomService: LivingRoomService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.livingRoomService.delete(id).subscribe(() => {
      this.eventManager.broadcast('livingRoomListModification');
      this.activeModal.close();
    });
  }
}
