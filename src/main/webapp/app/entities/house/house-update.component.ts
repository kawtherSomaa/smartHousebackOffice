import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IHouse, House } from 'app/shared/model/house.model';
import { HouseService } from './house.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ILivingRoom } from 'app/shared/model/living-room.model';
import { LivingRoomService } from 'app/entities/living-room/living-room.service';
import { IDoor } from 'app/shared/model/door.model';
import { DoorService } from 'app/entities/door/door.service';
import { IBathRoom } from 'app/shared/model/bath-room.model';
import { BathRoomService } from 'app/entities/bath-room/bath-room.service';
import { IKitchen } from 'app/shared/model/kitchen.model';
import { KitchenService } from 'app/entities/kitchen/kitchen.service';
import { IRoom } from 'app/shared/model/room.model';
import { RoomService } from 'app/entities/room/room.service';

type SelectableEntity = IUser | ILivingRoom | IDoor | IBathRoom | IKitchen | IRoom;

@Component({
  selector: 'jhi-house-update',
  templateUrl: './house-update.component.html',
})
export class HouseUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  livingrooms: ILivingRoom[] = [];
  doors: IDoor[] = [];
  bathrooms: IBathRoom[] = [];
  kitchens: IKitchen[] = [];
  rooms: IRoom[] = [];

  editForm = this.fb.group({
    id: [],
    libelleHouse: [],
    userId: [],
    livingroomsId: [],
    doorsId: [],
    bathroomsId: [],
    kitchensId: [],
    roomsId: [],
  });

  constructor(
    protected houseService: HouseService,
    protected userService: UserService,
    protected livingRoomService: LivingRoomService,
    protected doorService: DoorService,
    protected bathRoomService: BathRoomService,
    protected kitchenService: KitchenService,
    protected roomService: RoomService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ house }) => {
      this.updateForm(house);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.livingRoomService
        .query({ 'houseId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<ILivingRoom[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ILivingRoom[]) => {
          if (!house.livingroomsId) {
            this.livingrooms = resBody;
          } else {
            this.livingRoomService
              .find(house.livingroomsId)
              .pipe(
                map((subRes: HttpResponse<ILivingRoom>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ILivingRoom[]) => (this.livingrooms = concatRes));
          }
        });

      this.doorService
        .query({ 'houseId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IDoor[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDoor[]) => {
          if (!house.doorsId) {
            this.doors = resBody;
          } else {
            this.doorService
              .find(house.doorsId)
              .pipe(
                map((subRes: HttpResponse<IDoor>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDoor[]) => (this.doors = concatRes));
          }
        });

      this.bathRoomService
        .query({ 'houseId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IBathRoom[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IBathRoom[]) => {
          if (!house.bathroomsId) {
            this.bathrooms = resBody;
          } else {
            this.bathRoomService
              .find(house.bathroomsId)
              .pipe(
                map((subRes: HttpResponse<IBathRoom>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IBathRoom[]) => (this.bathrooms = concatRes));
          }
        });

      this.kitchenService
        .query({ 'houseId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IKitchen[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IKitchen[]) => {
          if (!house.kitchensId) {
            this.kitchens = resBody;
          } else {
            this.kitchenService
              .find(house.kitchensId)
              .pipe(
                map((subRes: HttpResponse<IKitchen>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IKitchen[]) => (this.kitchens = concatRes));
          }
        });

      this.roomService
        .query({ 'houseId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IRoom[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IRoom[]) => {
          if (!house.roomsId) {
            this.rooms = resBody;
          } else {
            this.roomService
              .find(house.roomsId)
              .pipe(
                map((subRes: HttpResponse<IRoom>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IRoom[]) => (this.rooms = concatRes));
          }
        });
    });
  }

  updateForm(house: IHouse): void {
    this.editForm.patchValue({
      id: house.id,
      libelleHouse: house.libelleHouse,
      userId: house.userId,
      livingroomsId: house.livingroomsId,
      doorsId: house.doorsId,
      bathroomsId: house.bathroomsId,
      kitchensId: house.kitchensId,
      roomsId: house.roomsId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const house = this.createFromForm();
    if (house.id !== undefined) {
      this.subscribeToSaveResponse(this.houseService.update(house));
    } else {
      this.subscribeToSaveResponse(this.houseService.create(house));
    }
  }

  private createFromForm(): IHouse {
    return {
      ...new House(),
      id: this.editForm.get(['id'])!.value,
      libelleHouse: this.editForm.get(['libelleHouse'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      livingroomsId: this.editForm.get(['livingroomsId'])!.value,
      doorsId: this.editForm.get(['doorsId'])!.value,
      bathroomsId: this.editForm.get(['bathroomsId'])!.value,
      kitchensId: this.editForm.get(['kitchensId'])!.value,
      roomsId: this.editForm.get(['roomsId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHouse>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
