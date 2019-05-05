import { Component, OnInit } from '@angular/core';
import { Point } from '../point';
import { PointService } from '../point.service';

@Component({
    selector: 'app-create-point',
    templateUrl: './create-point.component.html',
    styleUrls: ['./create-point.component.css']
})
export class CreatePointComponent implements OnInit {

    point: Point = new Point();
    submitted = false;

    constructor(private pointService: PointService) {
    }

    ngOnInit() {
    }

    newPoint(): void {
        this.submitted = false;
        this.point = new Point();
    }

    save() {
        this.pointService.createPoint(this.point)
            .subscribe(data =>
                console.log(data),
                error => console.log(error));
        this.point = new Point();
    }

    onSubmit() {
        this.submitted = true;
        this.save();
    }

}
