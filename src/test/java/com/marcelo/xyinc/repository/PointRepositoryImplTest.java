package com.marcelo.xyinc.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.marcelo.xyinc.SampleConfigTest;
import com.marcelo.xyinc.model.Point;

@RunWith(SpringJUnit4ClassRunner.class)
public class PointRepositoryImplTest extends SampleConfigTest {

    @Autowired
    @Qualifier("pointRepositoryImpl")
    private PointRepository pointRepository;

    @Before
    public void init() {
	assertNotNull(pointRepository);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void saveTest() {
	final Point point = new Point("One test JUnit", 1.2F, 2.1F);
	pointRepository.save(point);
	assertNotNull(point.getId());
	assertTrue(point.getId() > 0);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void updateTest() {

	final Point point = new Point("Two test JUnit", 3.2F, 3.1F);
	pointRepository.save(point);

	assertNotNull(point.getId());
	assertTrue(point.getId() > 0);
	point.setName(point.getName() + " Update");
	// update and valid
	final Point updatePoint = pointRepository.findById(point.getId(), Point.class);
	assertEquals("Two test JUnit Update", updatePoint.getName());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void findByIdTest() {
	final Point point = new Point("Tree test JUnit", 3.2F, 7.1F);
	pointRepository.save(point);
	assertNotNull(point.getId());
	assertTrue(point.getId() > 0);
	final Point pointById = pointRepository.findById(point.getId(), Point.class);
	assertNotNull(pointById);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void findAllTest() {
	final Point point = new Point("Tree test JUnit", 3.2F, 7.1F);
	pointRepository.save(point);
	assertNotNull(point.getId());
	assertTrue(point.getId() > 0);
	final List<Point> points = pointRepository.findAll(Point.class);
	assertTrue(CollectionUtils.isNotEmpty(points));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void deleteTest() {
	final Point point = new Point("Four test JUnit", 33.2F, 25.9F);
	pointRepository.save(point);
	assertNotNull(point.getId());
	assertTrue(point.getId() > 0);
	pointRepository.delete(point);
	final Point deletedPoint = pointRepository.findById(point.getId(), Point.class);
	assertNull(deletedPoint);
    }

}
