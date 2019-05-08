import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SearchPoint } from './SearchPoint';

@Injectable({
    providedIn: 'root'
})
export class PointService {

    private baseUrl = '/rest/api/v1/point';

    constructor(private http: HttpClient) { }

    createPoint(point: Object): Observable<Object> {
        return this.http.post(`${this.baseUrl}`, point);
    }

    updatePoint(id: number, value: any): Observable<Object> {
        return this.http.put(`${this.baseUrl}/${id}`, value);
    }

    deletePoint(id: number): Observable<any> {
        return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
    }

    findById(id: number): Observable<any> {
        return this.http.get(`${this.baseUrl}/${id}`);
    }

    getPointsList(): Observable<any> {
        return this.http.get(`${this.baseUrl}`);
    }

    searchForNearbyPoints(point: SearchPoint): Observable<any> {
        let params = 'coordinationX=' + point.coordinationX;
        params += '&coordinationY=' + point.coordinationY;
        params += '&maximumDistanceD=' + point.maximumDistanceD;
        return this.http.get(`${this.baseUrl}/searchForNearbyPoints?${params}`);
    }
}
