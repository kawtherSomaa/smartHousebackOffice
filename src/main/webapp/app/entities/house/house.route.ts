import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHouse, House } from 'app/shared/model/house.model';
import { HouseService } from './house.service';
import { HouseComponent } from './house.component';
import { HouseDetailComponent } from './house-detail.component';
import { HouseUpdateComponent } from './house-update.component';

@Injectable({ providedIn: 'root' })
export class HouseResolve implements Resolve<IHouse> {
  constructor(private service: HouseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHouse> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((house: HttpResponse<House>) => {
          if (house.body) {
            return of(house.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new House());
  }
}

export const houseRoute: Routes = [
  {
    path: '',
    component: HouseComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Houses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HouseDetailComponent,
    resolve: {
      house: HouseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Houses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HouseUpdateComponent,
    resolve: {
      house: HouseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Houses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HouseUpdateComponent,
    resolve: {
      house: HouseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Houses',
    },
    canActivate: [UserRouteAccessService],
  },
];
