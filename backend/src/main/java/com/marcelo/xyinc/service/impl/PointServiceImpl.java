package com.marcelo.xyinc.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcelo.xyinc.model.Point;
import com.marcelo.xyinc.repository.PointRepository;
import com.marcelo.xyinc.service.PointService;

@Service(value = "pointServiceImpl")
@Transactional
public class PointServiceImpl extends GenericServiceImpl<Integer, Point> implements PointService {

    @Autowired
    @Qualifier("pointRepositoryImpl")
    public void setDAO(PointRepository pointRepository) {
	super.setDAO(pointRepository);
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
	return getDAO().searchForNearbyPoints(coordination_x, coordination_y, maximumDistance_D);
    }

    @Override
    public Boolean validName(final Point point) {
	return point != null && StringUtils.isNotBlank(point.getName());
    }

    @Override
    public Boolean validCoordinationX(final Point point) {
	return point != null && point.getPoint_x() != null && point.getPoint_x() > 0;
    }

    @Override
    public Boolean validCoordinationY(final Point point) {
	return point != null && point.getPoint_y() != null && point.getPoint_y() > 0;
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
