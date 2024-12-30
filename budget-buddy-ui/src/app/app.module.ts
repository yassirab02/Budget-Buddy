import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule, provideHttpClient, withFetch} from '@angular/common/http';
import { LoginComponent } from './pages/login/login.component';
import { SignUpComponent } from './pages/sign-up/sign-up.component';
import {FormsModule} from '@angular/forms';
import { ActivateAccountComponent } from './pages/activate-account/activate-account.component';
import {CodeInputModule} from 'angular-code-input';
import { HomeComponent } from './pages/home/home.component';
import { DashboardComponent } from './modules/dashboard/pages/dashboard/dashboard.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignUpComponent,
    ActivateAccountComponent,
    HomeComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    CodeInputModule,
  ],
  providers: [
    provideHttpClient(withFetch()),
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
