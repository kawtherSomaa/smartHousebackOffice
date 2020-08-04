import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBathRoom, BathRoom } from 'app/shared/model/bath-room.model';
import { BathRoomService } from './bath-room.service';

@Component({
  selector: 'jhi-bath-room-update',
  templateUrl: './bath-room-update.component.html',
})
export class BathRoomUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelleBathRoom: [],
  });

  constructor(protected bathRoomService: BathRoomService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bathRoom }) => {
      this.updateForm(bathRoom);
    });
  }

  updateForm(bathRoom: IBathRoom): void {
    this.editForm.patchValue({
      id: bathRoom.id,
      libelleBathRoom: bathRoom.libelleBathRoom,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bathRoom = this.createFromForm();
    if (bathRoom.id !== undefined) {
      this.subscribeToSaveResponse(this.bathRoomService.update(bathRoom));
    } else {
      this.subscribeToSaveResponse(this.bathRoomService.create(bathRoom));
    }
  }

  private createFromForm(): IBathRoom {
    return {
      ...new BathRoom(),
      id: this.editForm.get(['id'])!.value,
      libelleBathRoom: this.editForm.get(['libelleBathRoom'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBathRoom>>): void {
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
