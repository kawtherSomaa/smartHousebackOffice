import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBathRoom, BathRoom } from 'app/shared/model/bath-room.model';
import { BathRoomService } from './bath-room.service';
import { BathRoomComponent } from './bath-room.component';
import { BathRoomDetailComponent } from './bath-room-detail.component';
import { BathRoomUpdateComponent } from './bath-room-update.component';

@Injectable({ providedIn: 'root' })
export class BathRoomResolve implements Resolve<IBathRoom> {
  constructor(private service: BathRoomService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBathRoom> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bathRoom: HttpResponse<BathRoom>) => {
          if (bathRoom.body) {
            return of(bathRoom.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BathRoom());
  }
}

export const bathRoomRoute: Routes = [
  {
    path: '',
    component: BathRoomComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'BathRooms',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BathRoomDetailComponent,
    resolve: {
      bathRoom: BathRoomResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BathRooms',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BathRoomUpdateComponent,
    resolve: {
      bathRoom: BathRoomResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BathRooms',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BathRoomUpdateComponent,
    resolve: {
      bathRoom: BathRoomResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BathRooms',
    },
    canActivate: [UserRouteAccessService],
  },
];
