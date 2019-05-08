import { Component, OnInit, Input } from '@angular/core';
import { Point } from '../point';
import { PointService } from '../point.service';
import { Observable } from 'rxjs';
import { SearchPoint } from '../SearchPoint';

@Component({
    selector: 'app-point-details',
    templateUrl: './point-details.component.html',
    styleUrls: ['./point-details.component.css']
})
export class PointDetailsComponent implements OnInit {

    @Input() point: SearchPoint;

    points: Observable<Point[]>;

    constructor(private pointService: PointService) { }

    ngOnInit() {
        this.point = new SearchPoint();
    }


    onSubmit() {
        this.pointService.searchForNearbyPoints(this.point)
            .subscribe(data => this.points = data.points);
    }
}
