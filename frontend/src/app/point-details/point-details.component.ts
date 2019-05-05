import { Component, OnInit, Input } from '@angular/core';
import { Point } from '../point';
import { PointService } from '../point.service';
import { PointListComponent } from '../point-list/point-list.component';

@Component({
    selector: 'app-point-details',
    templateUrl: './point-details.component.html',
    styleUrls: ['./point-details.component.css']
})
export class PointDetailsComponent implements OnInit {

    @Input() point: Point;


    constructor(private pointService: PointService, private listComponent: PointListComponent) { }

    ngOnInit() {
    }

}
