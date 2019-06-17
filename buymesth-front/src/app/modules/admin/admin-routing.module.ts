import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AdminComponent} from "../../components/admin/admin.component";
import {AdminDashboardComponent} from "../../components/admin/admin-dashboard/admin-dashboard.component";
import {AuthGuard} from "../../guards/auth.guard";
import {UserManagementComponent} from "../../components/admin/user-management/user-management/user-management.component";
import {OperationManagementComponent} from "../../components/admin/operation-management/operation-management/operation-management.component";
import {ProductManagementComponent} from "../../components/admin/product-management/product-management/product-management.component";

const routes: Routes = [
    {
        path: 'admin',
        component: AdminComponent,
        canActivate: [AuthGuard],
        children: [
            {
                path: '',
                component: AdminDashboardComponent
            },
            {
                path: 'users',
                component: UserManagementComponent
            },
            {
                path: 'operations',
                component: OperationManagementComponent
            },
            {
                path: 'products',
                component: ProductManagementComponent
            }
        ],
    }
];


@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AdminRoutingModule {
}
