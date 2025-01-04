import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import {SideBarComponent} from './components/side-bar/side-bar.component';


@NgModule({
  declarations: [
    DashboardComponent,
    SideBarComponent,
  ],
  exports: [
    SideBarComponent,
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule
  ]
})
export class DashboardModule { }
