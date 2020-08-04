import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartHousebackOfficeSharedModule } from 'app/shared/shared.module';
import { HouseComponent } from './house.component';
import { HouseDetailComponent } from './house-detail.component';
import { HouseUpdateComponent } from './house-update.component';
import { HouseDeleteDialogComponent } from './house-delete-dialog.component';
import { houseRoute } from './house.route';

@NgModule({
  imports: [SmartHousebackOfficeSharedModule, RouterModule.forChild(houseRoute)],
  declarations: [HouseComponent, HouseDetailComponent, HouseUpdateComponent, HouseDeleteDialogComponent],
  entryComponents: [HouseDeleteDialogComponent],
})
export class SmartHousebackOfficeHouseModule {}
