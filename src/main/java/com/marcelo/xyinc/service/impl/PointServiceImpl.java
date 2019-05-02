package com.marcelo.xyinc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcelo.xyinc.model.Point;
import com.marcelo.xyinc.repository.PointRepository;
import com.marcelo.xyinc.service.PointService;

@Service(value = "pointServiceImpl")
@Transactional
public class PointServiceImpl extends GenericServiceImpl<Point, Long> implements PointService {

    @Autowired
    public PointServiceImpl(final @Qualifier("pointRepositoryImpl") PointRepository pointRepository) {
	super(pointRepository);
    }

    /**
     * For visualize some specific method of the interface, it is not necessary to
     * do for all classes
     */
    @Override
    public PointRepository getDAO() {
	return (PointRepository) super.getDAO();
    }

    @Override
    public List<Point> searchForNearbyPoints(Float coordination_x, Float coordination_y, Float maximumDistance_D) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Boolean validName(final Point point) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Boolean validCoordinationX(final Point point) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Boolean validCoordinationY(final Point point) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean validPreSave(final Point point) {
	boolean result = false;
	if (validName(point) && validCoordinationX(point) && validCoordinationY(point)) {
	    result = true;
	}
	return result;
    }

}
