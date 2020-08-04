import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILivingRoom } from 'app/shared/model/living-room.model';

@Component({
  selector: 'jhi-living-room-detail',
  templateUrl: './living-room-detail.component.html',
})
export class LivingRoomDetailComponent implements OnInit {
  livingRoom: ILivingRoom | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ livingRoom }) => (this.livingRoom = livingRoom));
  }

  previousState(): void {
    window.history.back();
  }
}
