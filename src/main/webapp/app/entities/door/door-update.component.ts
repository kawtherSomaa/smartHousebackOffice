import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDoor, Door } from 'app/shared/model/door.model';
import { DoorService } from './door.service';

@Component({
  selector: 'jhi-door-update',
  templateUrl: './door-update.component.html',
})
export class DoorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelleDoor: [],
  });

  constructor(protected doorService: DoorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ door }) => {
      this.updateForm(door);
    });
  }

  updateForm(door: IDoor): void {
    this.editForm.patchValue({
      id: door.id,
      libelleDoor: door.libelleDoor,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const door = this.createFromForm();
    if (door.id !== undefined) {
      this.subscribeToSaveResponse(this.doorService.update(door));
    } else {
      this.subscribeToSaveResponse(this.doorService.create(door));
    }
  }

  private createFromForm(): IDoor {
    return {
      ...new Door(),
      id: this.editForm.get(['id'])!.value,
      libelleDoor: this.editForm.get(['libelleDoor'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoor>>): void {
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
