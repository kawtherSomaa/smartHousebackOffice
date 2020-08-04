import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILivingRoom, LivingRoom } from 'app/shared/model/living-room.model';
import { LivingRoomService } from './living-room.service';
import { LivingRoomComponent } from './living-room.component';
import { LivingRoomDetailComponent } from './living-room-detail.component';
import { LivingRoomUpdateComponent } from './living-room-update.component';

@Injectable({ providedIn: 'root' })
export class LivingRoomResolve implements Resolve<ILivingRoom> {
  constructor(private service: LivingRoomService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILivingRoom> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((livingRoom: HttpResponse<LivingRoom>) => {
          if (livingRoom.body) {
            return of(livingRoom.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LivingRoom());
  }
}

export const livingRoomRoute: Routes = [
  {
    path: '',
    component: LivingRoomComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'LivingRooms',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LivingRoomDetailComponent,
    resolve: {
      livingRoom: LivingRoomResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'LivingRooms',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LivingRoomUpdateComponent,
    resolve: {
      livingRoom: LivingRoomResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'LivingRooms',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LivingRoomUpdateComponent,
    resolve: {
      livingRoom: LivingRoomResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'LivingRooms',
    },
    canActivate: [UserRouteAccessService],
  },
];
