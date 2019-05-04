package com.marcelo.xyinc.service;

import com.marcelo.xyinc.model.Point;
import com.marcelo.xyinc.repository.PointRepository;

public interface PointService extends GenericService<Integer, Point>, PointRepository {

    public Boolean validName(final Point point);

    public Boolean validCoordinationX(final Point point);

    public Boolean validCoordinationY(final Point point);
}
