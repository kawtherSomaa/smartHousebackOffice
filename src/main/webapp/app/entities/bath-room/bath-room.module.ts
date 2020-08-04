import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartHousebackOfficeSharedModule } from 'app/shared/shared.module';
import { BathRoomComponent } from './bath-room.component';
import { BathRoomDetailComponent } from './bath-room-detail.component';
import { BathRoomUpdateComponent } from './bath-room-update.component';
import { BathRoomDeleteDialogComponent } from './bath-room-delete-dialog.component';
import { bathRoomRoute } from './bath-room.route';

@NgModule({
  imports: [SmartHousebackOfficeSharedModule, RouterModule.forChild(bathRoomRoute)],
  declarations: [BathRoomComponent, BathRoomDetailComponent, BathRoomUpdateComponent, BathRoomDeleteDialogComponent],
  entryComponents: [BathRoomDeleteDialogComponent],
})
export class SmartHousebackOfficeBathRoomModule {}
