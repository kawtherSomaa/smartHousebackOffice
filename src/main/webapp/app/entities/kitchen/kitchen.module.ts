import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartHousebackOfficeSharedModule } from 'app/shared/shared.module';
import { KitchenComponent } from './kitchen.component';
import { KitchenDetailComponent } from './kitchen-detail.component';
import { KitchenUpdateComponent } from './kitchen-update.component';
import { KitchenDeleteDialogComponent } from './kitchen-delete-dialog.component';
import { kitchenRoute } from './kitchen.route';

@NgModule({
  imports: [SmartHousebackOfficeSharedModule, RouterModule.forChild(kitchenRoute)],
  declarations: [KitchenComponent, KitchenDetailComponent, KitchenUpdateComponent, KitchenDeleteDialogComponent],
  entryComponents: [KitchenDeleteDialogComponent],
})
export class SmartHousebackOfficeKitchenModule {}
