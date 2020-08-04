import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IKitchen, Kitchen } from 'app/shared/model/kitchen.model';
import { KitchenService } from './kitchen.service';

@Component({
  selector: 'jhi-kitchen-update',
  templateUrl: './kitchen-update.component.html',
})
export class KitchenUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelleKitchen: [],
  });

  constructor(protected kitchenService: KitchenService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kitchen }) => {
      this.updateForm(kitchen);
    });
  }

  updateForm(kitchen: IKitchen): void {
    this.editForm.patchValue({
      id: kitchen.id,
      libelleKitchen: kitchen.libelleKitchen,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kitchen = this.createFromForm();
    if (kitchen.id !== undefined) {
      this.subscribeToSaveResponse(this.kitchenService.update(kitchen));
    } else {
      this.subscribeToSaveResponse(this.kitchenService.create(kitchen));
    }
  }

  private createFromForm(): IKitchen {
    return {
      ...new Kitchen(),
      id: this.editForm.get(['id'])!.value,
      libelleKitchen: this.editForm.get(['libelleKitchen'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKitchen>>): void {
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
