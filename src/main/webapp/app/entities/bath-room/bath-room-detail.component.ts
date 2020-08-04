import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBathRoom } from 'app/shared/model/bath-room.model';

@Component({
  selector: 'jhi-bath-room-detail',
  templateUrl: './bath-room-detail.component.html',
})
export class BathRoomDetailComponent implements OnInit {
  bathRoom: IBathRoom | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bathRoom }) => (this.bathRoom = bathRoom));
  }

  previousState(): void {
    window.history.back();
  }
}
