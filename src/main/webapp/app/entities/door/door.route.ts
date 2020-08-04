import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDoor, Door } from 'app/shared/model/door.model';
import { DoorService } from './door.service';
import { DoorComponent } from './door.component';
import { DoorDetailComponent } from './door-detail.component';
import { DoorUpdateComponent } from './door-update.component';

@Injectable({ providedIn: 'root' })
export class DoorResolve implements Resolve<IDoor> {
  constructor(private service: DoorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDoor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((door: HttpResponse<Door>) => {
          if (door.body) {
            return of(door.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Door());
  }
}

export const doorRoute: Routes = [
  {
    path: '',
    component: DoorComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Doors',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DoorDetailComponent,
    resolve: {
      door: DoorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Doors',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DoorUpdateComponent,
    resolve: {
      door: DoorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Doors',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DoorUpdateComponent,
    resolve: {
      door: DoorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Doors',
    },
    canActivate: [UserRouteAccessService],
  },
];
