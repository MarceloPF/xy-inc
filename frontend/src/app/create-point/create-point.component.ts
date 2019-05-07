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
    editCall = false;

    constructor(private pointService: PointService) {
    }

    ngOnInit() {
        const pointId: string = localStorage.getItem('editPointId');
        if (null != pointId) {
            this.edit(Number(pointId));
            localStorage.removeItem('editPointId');
        }
    }

    newPoint(): void {
        this.submitted = false;
        this.point = new Point();
    }

    edit(id: number): void {
        this.submitted = false;
        this.editCall = true;
        this.pointService.findById(id)
            .subscribe(data => this.point = data
            );
    }

    save() {
        this.pointService.createPoint(this.point)
            .subscribe(data =>
                console.log(data),
                error => console.log(error));
        this.point = new Point();
    }

    update() {
        this.pointService.updatePoint(this.point.id, this.point)
            .subscribe(data =>
                console.log(data),
                error => console.log(error));
        this.point = new Point();
    }

    onSubmit() {
        this.submitted = true;
        if (this.editCall) {
            this.update();
        } else {
            this.save();
        }
        this.editCall = false;
    }

}
