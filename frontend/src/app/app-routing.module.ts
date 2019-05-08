import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PointListComponent } from './point-list/point-list.component';
import { CreatePointComponent } from './create-point/create-point.component';

const routes: Routes = [
    { path: '', redirectTo: 'point', pathMatch: 'full' },
    { path: 'point/list', component: PointListComponent },
    { path: 'point/add', component: CreatePointComponent },
    { path: 'point/edit/:id', component: CreatePointComponent },
    {path : '', component : PointListComponent}

];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {


}
