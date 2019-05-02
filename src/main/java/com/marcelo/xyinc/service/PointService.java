package com.marcelo.xyinc.service;

import java.util.List;

import com.marcelo.xyinc.model.Point;

public interface PointService extends GenericService<Point, Long> {

    public List<Point> searchForNearbyPoints(final Float coordination_x, final Float coordination_y,
	    Float maximumDistance_D);

    public Boolean validName(final Point point);

    public Boolean validCoordinationX(final Point point);

    public Boolean validCoordinationY(final Point point);
}
