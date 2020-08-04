import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { SmartHousebackOfficeSharedModule } from 'app/shared/shared.module';
import { SmartHousebackOfficeCoreModule } from 'app/core/core.module';
import { SmartHousebackOfficeAppRoutingModule } from './app-routing.module';
import { SmartHousebackOfficeHomeModule } from './home/home.module';
import { SmartHousebackOfficeEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    SmartHousebackOfficeSharedModule,
    SmartHousebackOfficeCoreModule,
    SmartHousebackOfficeHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    SmartHousebackOfficeEntityModule,
    SmartHousebackOfficeAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class SmartHousebackOfficeAppModule {}
