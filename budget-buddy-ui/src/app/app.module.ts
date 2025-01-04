import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, provideHttpClient, withFetch} from '@angular/common/http';
import { LoginComponent } from './pages/login/login.component';
import { SignUpComponent } from './pages/sign-up/sign-up.component';
import {FormsModule} from '@angular/forms';
import { ActivateAccountComponent } from './pages/activate-account/activate-account.component';
import {CodeInputModule} from 'angular-code-input';
import { HomeComponent } from './pages/home/home.component';
import { FooterComponent } from './pages/footer/footer.component';
import {NgOptimizedImage} from '@angular/common';
import { NavbarComponent } from './pages/navbar/navbar.component';
import { ErrorPageComponent } from './pages/error-page/error-page.component';
import { HeroComponent } from './pages/hero/hero.component';
import { AboutComponent } from './pages/about/about.component';
import { FeaturesComponent } from './pages/features/features.component';
import { StaticsComponent } from './pages/statics/statics.component';
import {DashboardModule} from './modules/dashboard/dashboard.module';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import { NewsLetterComponent } from './pages/news-letter/news-letter.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignUpComponent,
    ActivateAccountComponent,
    HomeComponent,
    FooterComponent,
    NavbarComponent,
    ErrorPageComponent,
    HeroComponent,
    AboutComponent,
    FeaturesComponent,
    StaticsComponent,
    NewsLetterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    CodeInputModule,
    NgOptimizedImage,
    DashboardModule,
  ],
  providers: [
    provideHttpClient(withFetch()),
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
