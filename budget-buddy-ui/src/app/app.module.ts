import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule, provideHttpClient, withFetch} from '@angular/common/http';
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
import { FeaturesComponent } from './pages/features/features.component';
import { StaticsComponent } from './pages/statics/statics.component';
import {DashboardModule} from './modules/dashboard/dashboard.module';
import { NewsLetterComponent } from './pages/news-letter/news-letter.component';
import {HttpTokenInterceptor} from './services/interceptor/http-token.interceptor';
import { WorkExplainComponent } from './pages/work-explain/work-explain.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { FeauturesOpenComponent } from './pages/feautures-open/feautures-open.component';
import { PrivacyComponent } from './pages/privacy/privacy.component';
import { ContactComponent } from './pages/contact/contact.component';

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
    FeaturesComponent,
    StaticsComponent,
    NewsLetterComponent,
    WorkExplainComponent,
    FeauturesOpenComponent,
    PrivacyComponent,
    ContactComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    CodeInputModule,
    NgOptimizedImage,
    DashboardModule,
    BrowserAnimationsModule,
  ],
  providers: [
    HttpClient,
    provideHttpClient(withFetch()), // This enables fetch API
    { provide: HTTP_INTERCEPTORS,
      useClass: HttpTokenInterceptor,
      multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
