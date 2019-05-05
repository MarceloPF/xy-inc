import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PointListComponent } from './point-list/point-list.component';
import { CreatePointComponent } from './create-point/create-point.component';

const routes: Routes = [
    { path: '', redirectTo: 'point', pathMatch: 'full' },
    { path: 'points', component: PointListComponent },
    { path: 'add', component: CreatePointComponent },

];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {


}
