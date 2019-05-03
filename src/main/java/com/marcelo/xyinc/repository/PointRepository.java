package com.marcelo.xyinc.repository;

import java.util.List;

import com.marcelo.xyinc.model.Point;

public interface PointRepository extends GenericRepository<Point, Integer> {

    public List<Point> searchForNearByPoints(final Float coordination_x, final Float coordination_y,
	    Float maximumDistance_D);
}
