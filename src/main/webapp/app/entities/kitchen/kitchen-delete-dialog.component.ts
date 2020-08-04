import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKitchen } from 'app/shared/model/kitchen.model';
import { KitchenService } from './kitchen.service';

@Component({
  templateUrl: './kitchen-delete-dialog.component.html',
})
export class KitchenDeleteDialogComponent {
  kitchen?: IKitchen;

  constructor(protected kitchenService: KitchenService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kitchenService.delete(id).subscribe(() => {
      this.eventManager.broadcast('kitchenListModification');
      this.activeModal.close();
    });
  }
}
