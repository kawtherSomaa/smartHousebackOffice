import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartHousebackOfficeSharedModule } from 'app/shared/shared.module';
import { DoorComponent } from './door.component';
import { DoorDetailComponent } from './door-detail.component';
import { DoorUpdateComponent } from './door-update.component';
import { DoorDeleteDialogComponent } from './door-delete-dialog.component';
import { doorRoute } from './door.route';

@NgModule({
  imports: [SmartHousebackOfficeSharedModule, RouterModule.forChild(doorRoute)],
  declarations: [DoorComponent, DoorDetailComponent, DoorUpdateComponent, DoorDeleteDialogComponent],
  entryComponents: [DoorDeleteDialogComponent],
})
export class SmartHousebackOfficeDoorModule {}
