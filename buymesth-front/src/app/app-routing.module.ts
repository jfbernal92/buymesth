import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {ProfileComponent} from "./components/profile/profile.component";
import {EmailConfirmationComponent} from "./components/email-confirmation/email-confirmation.component";
import {PasswordRecoveryComponent} from "./components/password-recovery/password-recovery.component";
import {ShopComponent} from "./components/shop/shop.component";


const routes: Routes = [
    {
        path: 'home',
        component: HomeComponent
    },
    {
        path: 'profile',
        component: ProfileComponent,
    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'signup',
        component: RegisterComponent
    },
    {
        path: 'emailconfirmation/:token/d/:d/:id',
        component: EmailConfirmationComponent
    },
    {
        path: 'passwordrecovery',
        component: PasswordRecoveryComponent
    },
    {
        path: 'passwordrecovery/:token/d/:d/:id',
        component: PasswordRecoveryComponent
    },
    {
        path: 'shop',
        component: ShopComponent
    },
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'prefix'
    },
    {
        path: 'admin',
        redirectTo: 'admin'
    },
    {
        path: '**',
        redirectTo: 'home'
    },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}