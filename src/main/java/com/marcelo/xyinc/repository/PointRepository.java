package com.marcelo.xyinc.repository;

import java.util.List;

import com.marcelo.xyinc.model.Point;

public interface PointRepository extends GenericRepository<Integer, Point> {

    public List<Point> searchForNearByPoints(final Float coordination_x, final Float coordination_y,
	    Float maximumDistance_D);
}
