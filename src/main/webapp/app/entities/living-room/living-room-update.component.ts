import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILivingRoom, LivingRoom } from 'app/shared/model/living-room.model';
import { LivingRoomService } from './living-room.service';

@Component({
  selector: 'jhi-living-room-update',
  templateUrl: './living-room-update.component.html',
})
export class LivingRoomUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelleLivingRoom: [],
  });

  constructor(protected livingRoomService: LivingRoomService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ livingRoom }) => {
      this.updateForm(livingRoom);
    });
  }

  updateForm(livingRoom: ILivingRoom): void {
    this.editForm.patchValue({
      id: livingRoom.id,
      libelleLivingRoom: livingRoom.libelleLivingRoom,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const livingRoom = this.createFromForm();
    if (livingRoom.id !== undefined) {
      this.subscribeToSaveResponse(this.livingRoomService.update(livingRoom));
    } else {
      this.subscribeToSaveResponse(this.livingRoomService.create(livingRoom));
    }
  }

  private createFromForm(): ILivingRoom {
    return {
      ...new LivingRoom(),
      id: this.editForm.get(['id'])!.value,
      libelleLivingRoom: this.editForm.get(['libelleLivingRoom'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILivingRoom>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
