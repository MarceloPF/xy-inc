import { Component, OnInit } from '@angular/core';
import { Point } from '../point';
import { Observable } from 'rxjs';
import { PointService } from '../point.service';

@Component({
    selector: 'app-point-list',
    templateUrl: './point-list.component.html',
    styleUrls: ['./point-list.component.css']
})
export class PointListComponent implements OnInit {

    points: Observable<Point[]>;

    constructor(private pointService: PointService) { }

    ngOnInit() {
        this.reloadData();
    }

    reloadData() {
        this.points = this.pointService.getPointsList();
    }

    deletePoit(id: number) {
        this.pointService.deletePoint(id)
            .subscribe(
                data => {
                    console.log(data);
                    this.reloadData();
                },
                error => console.log(error));
    }

}
