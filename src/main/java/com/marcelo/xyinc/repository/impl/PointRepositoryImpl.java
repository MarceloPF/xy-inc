package com.marcelo.xyinc.repository.impl;

import org.springframework.stereotype.Repository;

import com.marcelo.xyinc.model.Point;
import com.marcelo.xyinc.repository.PointRepository;

@Repository(value="pointRepositoryImpl")
public class PointRepositoryImpl extends GenericRepositoryImpl<Point, Long> implements PointRepository  {

    
}
