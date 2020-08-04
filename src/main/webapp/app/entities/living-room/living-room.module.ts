import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartHousebackOfficeSharedModule } from 'app/shared/shared.module';
import { LivingRoomComponent } from './living-room.component';
import { LivingRoomDetailComponent } from './living-room-detail.component';
import { LivingRoomUpdateComponent } from './living-room-update.component';
import { LivingRoomDeleteDialogComponent } from './living-room-delete-dialog.component';
import { livingRoomRoute } from './living-room.route';

@NgModule({
  imports: [SmartHousebackOfficeSharedModule, RouterModule.forChild(livingRoomRoute)],
  declarations: [LivingRoomComponent, LivingRoomDetailComponent, LivingRoomUpdateComponent, LivingRoomDeleteDialogComponent],
  entryComponents: [LivingRoomDeleteDialogComponent],
})
export class SmartHousebackOfficeLivingRoomModule {}
