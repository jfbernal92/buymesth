import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AdminSidebarComponent} from '../../components/admin/admin-sidebar/admin-sidebar.component';
import {AdminDashboardComponent} from '../../components/admin/admin-dashboard/admin-dashboard.component';
import {AdminNavbarComponent} from '../../components/admin/admin-navbar/admin-navbar.component';
import {AdminComponent} from '../../components/admin/admin.component';
import {AdminRoutingModule} from './admin-routing.module';
import {UserManagementComponent} from '../../components/admin/user-management/user-management/user-management.component';
import {OperationManagementComponent} from '../../components/admin/operation-management/operation-management/operation-management.component';
import {
    ButtonModule,
    ChartModule,
    CheckboxModule,
    DropdownModule,
    InputTextareaModule,
    InputTextModule,
    PaginatorModule
} from 'primeng/primeng';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {ProductManagementComponent} from '../../components/admin/product-management/product-management/product-management.component';
import {TableModule} from 'primeng/table';
import {DialogModule} from 'primeng/dialog';
import {ToastModule} from "primeng/toast";

@NgModule({
    declarations: [
        AdminSidebarComponent,
        AdminDashboardComponent,
        AdminNavbarComponent,
        AdminComponent,
        UserManagementComponent,
        OperationManagementComponent,
        ProductManagementComponent
    ],
    imports: [
        CommonModule,
        AdminRoutingModule,
        PaginatorModule,
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        TableModule,
        DialogModule,
        InputTextModule,
        ButtonModule,
        InputTextareaModule,
        ToastModule,
        CheckboxModule,
        DropdownModule,
        ChartModule
    ]
})
export class AdminModule {
}
