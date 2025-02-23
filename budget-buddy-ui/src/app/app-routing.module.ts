import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from './pages/login/login.component';
import {SignUpComponent} from './pages/sign-up/sign-up.component';
import {ActivateAccountComponent} from './pages/activate-account/activate-account.component';
import {HomeComponent} from './pages/home/home.component';
import {AuthGuard} from './services/guard/auth.guard';
import {WorkExplainComponent} from './pages/work-explain/work-explain.component';
import {FeaturesComponent} from './pages/features/features.component';
import {FeauturesOpenComponent} from './pages/feautures-open/feautures-open.component';
import {PrivacyComponent} from './pages/privacy/privacy.component';
import {ContactComponent} from './pages/contact/contact.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'sign-up',
    component: SignUpComponent
  },
  {
    path: 'features',
    component: FeauturesOpenComponent
  },
  {
    path: 'privacy',
    component: PrivacyComponent
  },
  {
    path: 'works',
    component: WorkExplainComponent
  },
  {
    path: 'contact',
    component: ContactComponent
  },
  {
    path: 'activate-account',
    component: ActivateAccountComponent
  },
  {
    path: 'dashboard',
    loadChildren: () => import('./modules/user/dashboard/dashboard.module').then(m => m.DashboardModule),
    canActivate: [AuthGuard]

  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
