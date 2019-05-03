package com.marcelo.xyinc.repository.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.marcelo.xyinc.model.Point;
import com.marcelo.xyinc.repository.PointRepository;

@Repository
public class PointRepositoryImpl extends GenericRepositoryImpl<Point, Integer> implements PointRepository {

    /**
     * Find all points next coordination (x,y) for system
     * 
     * @param: Float coordination x
     * @param: Float coordination y
     * @param: Float circle ray for find
     * 
     * @return: List<Point> find in next point
     * 
     * 
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Point> searchForNearByPoints(Float coordination_x, Float coordination_y, Float maximumDistance_D) {
	final DetachedCriteria criteria = DetachedCriteria.forClass(Point.class);
	// define value for x
	final Float ray_in_x_minimal = coordination_x - maximumDistance_D;
	final Float ray_in_x_maximal = coordination_x + maximumDistance_D;

	criteria.add(Restrictions.between("point_x", ray_in_x_minimal, ray_in_x_maximal));

	// define value for y
	final Float ray_in_y_minimal = coordination_y - maximumDistance_D;
	final Float ray_in_y_maximal = coordination_y + maximumDistance_D;

	criteria.add(Restrictions.between("point_y", ray_in_y_minimal, ray_in_y_maximal));
	return criteria.getExecutableCriteria(getSession()).list();
    }

}
