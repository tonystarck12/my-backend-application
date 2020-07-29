import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './user/user.component';
import { PmComponent } from './pm/pm.component';
import { AdminComponent } from './admin/admin.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AdduserComponent } from './admin/adduser/adduser.component';
import { ChangepasswordComponent } from './admin/changepassword/changepassword.component';
import { UserlistComponent } from './admin/userlist/userlist.component';
import { ResetpasswordComponent } from './login/resetpassword/resetpassword.component';

const routes: Routes = [
    {
        path: 'home',
        component: HomeComponent,
        children: [
            {
              path: 'user', // child route path
              component: UserComponent // child route component that the router renders
            },
            {
                path: 'dashboard', // child route path
                component: DashboardComponent // child route component that the router renders
              },
            {
              path: 'pm',
              component: PmComponent // another child route component that the router renders
            },
            {
                path: 'admin',
                component: AdminComponent // another child route component that the router renders

            },
            {
              path: 'adduser', // child route path
              component: AdduserComponent // child route component that the router renders
            },
            {
              path: 'changepassword',
              component: ChangepasswordComponent // another child route component that the router renders
            },
            {
                path: 'userlist',
                component: UserlistComponent // another child route component that the router renders
            }
          ] 
    },
    {
        path: 'login',
        component: LoginComponent,
    },
    {
      path: 'resetpassword',
      component: ResetpasswordComponent
    },
    {
        path: 'signup',
        component: RegisterComponent
    },
    {
      path: '',
      redirectTo: '/login',
      pathMatch: 'full'
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
