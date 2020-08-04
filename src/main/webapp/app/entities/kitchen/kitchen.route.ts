import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IKitchen, Kitchen } from 'app/shared/model/kitchen.model';
import { KitchenService } from './kitchen.service';
import { KitchenComponent } from './kitchen.component';
import { KitchenDetailComponent } from './kitchen-detail.component';
import { KitchenUpdateComponent } from './kitchen-update.component';

@Injectable({ providedIn: 'root' })
export class KitchenResolve implements Resolve<IKitchen> {
  constructor(private service: KitchenService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKitchen> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((kitchen: HttpResponse<Kitchen>) => {
          if (kitchen.body) {
            return of(kitchen.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Kitchen());
  }
}

export const kitchenRoute: Routes = [
  {
    path: '',
    component: KitchenComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Kitchens',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KitchenDetailComponent,
    resolve: {
      kitchen: KitchenResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Kitchens',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KitchenUpdateComponent,
    resolve: {
      kitchen: KitchenResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Kitchens',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KitchenUpdateComponent,
    resolve: {
      kitchen: KitchenResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Kitchens',
    },
    canActivate: [UserRouteAccessService],
  },
];
