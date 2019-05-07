import { Component, OnInit } from '@angular/core';
import { Point } from '../point';
import { Observable } from 'rxjs';
import { PointService } from '../point.service';
import { CreatePointComponent } from '../create-point/create-point.component';
import { Router } from '@angular/router';

@Component({
    selector: 'app-point-list',
    templateUrl: './point-list.component.html',
    styleUrls: ['./point-list.component.css']
})
export class PointListComponent implements OnInit {

    points: Observable<Point[]>;

    constructor(private pointService: PointService, private router: Router) { }

    ngOnInit() {
        this.reloadData();
    }

    reloadData() {
        this.pointService.getPointsList()
            .subscribe(data => this.points = data.points
            );
    }

    deletePoint(id: number) {
        this.pointService.deletePoint(id)
            .subscribe(
                data => {
                    console.log(data);
                    this.reloadData();
                },
                error => console.log(error));
    }

    editPoint(id: number) {
        localStorage.removeItem('editPointId');
        localStorage.setItem('editPointId', id.toString());
        this.router.navigate(['edit']);

    }

}
