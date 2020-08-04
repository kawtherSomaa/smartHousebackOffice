import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'living-room',
        loadChildren: () => import('./living-room/living-room.module').then(m => m.SmartHousebackOfficeLivingRoomModule),
      },
      {
        path: 'door',
        loadChildren: () => import('./door/door.module').then(m => m.SmartHousebackOfficeDoorModule),
      },
      {
        path: 'bath-room',
        loadChildren: () => import('./bath-room/bath-room.module').then(m => m.SmartHousebackOfficeBathRoomModule),
      },
      {
        path: 'kitchen',
        loadChildren: () => import('./kitchen/kitchen.module').then(m => m.SmartHousebackOfficeKitchenModule),
      },
      {
        path: 'room',
        loadChildren: () => import('./room/room.module').then(m => m.SmartHousebackOfficeRoomModule),
      },
      {
        path: 'house',
        loadChildren: () => import('./house/house.module').then(m => m.SmartHousebackOfficeHouseModule),
      },
      {
        path: 'reservation',
        loadChildren: () => import('./reservation/reservation.module').then(m => m.SmartHousebackOfficeReservationModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SmartHousebackOfficeEntityModule {}
