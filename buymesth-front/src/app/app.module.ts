import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {HomeComponent} from './components/home/home.component';
import {ProfileComponent} from './components/profile/profile.component';
import {httpInterceptorProviders} from './services/auth/auth-interceptor';
import {EmailConfirmationComponent} from './components/email-confirmation/email-confirmation.component';
import {PanelModule} from 'primeng/panel';
import {ToastModule} from 'primeng/toast';
import {StepsModule} from 'primeng/steps';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ShopComponent} from './components/shop/shop.component';
import {ProgressSpinnerModule} from 'primeng/progressspinner';
import {ProgressBarModule} from 'primeng/progressbar';
import {PasswordRecoveryComponent} from './components/password-recovery/password-recovery.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import {AdminModule} from './modules/admin/admin.module';
import {TabMenuModule} from 'primeng/tabmenu';
import {InputSwitchModule, PaginatorModule, SliderModule} from 'primeng/primeng';

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        RegisterComponent,
        HomeComponent,
        ProfileComponent,
        EmailConfirmationComponent,
        ShopComponent,
        PasswordRecoveryComponent,
        NavbarComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        PanelModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        ToastModule,
        StepsModule,
        ProgressSpinnerModule,
        ProgressBarModule,
        AdminModule,
        TabMenuModule,
        PaginatorModule,
        InputSwitchModule,
        SliderModule

    ],
    providers: [httpInterceptorProviders],
    bootstrap: [AppComponent]
})
export class AppModule {
}
