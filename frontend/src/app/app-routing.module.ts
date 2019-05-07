import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PointListComponent } from './point-list/point-list.component';
import { CreatePointComponent } from './create-point/create-point.component';
import { EditPointComponent } from './edit-point/edit-point.component';

const routes: Routes = [
    { path: '', redirectTo: 'point', pathMatch: 'full' },
    { path: 'list', component: PointListComponent },
    { path: 'add', component: CreatePointComponent },
    { path: 'edit', component: CreatePointComponent },
    {path : '', component : PointListComponent}

];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {


}
