package com.marcelo.xyinc.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

import com.marcelo.xyinc.SampleConfigTest;
import com.marcelo.xyinc.model.Point;

@RunWith(SpringJUnit4ClassRunner.class)
public class PointServiceImplTest extends SampleConfigTest {

    @Autowired
    @Qualifier("pointServiceImpl")
    private PointService pointService;

    @Before
    public void init() {
	assertNotNull(pointService);
    }

    @Test
    @Rollback(value = true)
    public void saveTest() {
	final Point point = new Point("One test JUnit Service", 1.2F, 2.1F);
	pointService.save(point);
	assertNotNull(point.getId());
	assertTrue(point.getId() > 0);
    }

    @Test
    @Rollback(value = true)
    public void updateTest() {

	final Point point = new Point("Two test JUnit Service", 3.2F, 3.1F);
	pointService.save(point);

	assertNotNull(point.getId());
	assertTrue(point.getId() > 0);
	point.setName(point.getName() + " Update");
	pointService.update(point);
	// update and valid
	final Point updatePoint = pointService.findById(point.getId(), Point.class);
	assertEquals("Two test JUnit Service Update", updatePoint.getName());
    }

    @Test
    @Rollback(value = true)
    public void findByIdTest() {
	final Point point = new Point("Tree test JUnit Service", 3.2F, 7.1F);
	pointService.save(point);
	assertNotNull(point.getId());
	assertTrue(point.getId() > 0);
	final Point pointById = pointService.findById(point.getId(), Point.class);
	assertNotNull(pointById);
    }

    @Test
    @Rollback(value = true)
    public void findAllTest() {
	final Point point = new Point("Tree test JUnit Service", 3.2F, 7.1F);
	pointService.save(point);
	assertNotNull(point.getId());
	assertTrue(point.getId() > 0);
	final List<Point> points = pointService.findAll(Point.class);
	assertTrue(CollectionUtils.isNotEmpty(points));
    }

    @Test
    @Rollback(value = true)
    public void deleteTest() {
	final Point point = new Point("Four test JUnit Service", 33.2F, 25.9F);
	pointService.save(point);
	assertNotNull(point.getId());
	assertTrue(point.getId() > 0);
	pointService.delete(point);
	final Point deletedPoint = pointService.findById(point.getId(), Point.class);
	assertNull(deletedPoint);
    }

    @Test
    @Rollback(value = true)
    public void searchForNearbyPointsTest() {
	final List<Point> poitsAfter = pointService.searchForNearbyPoints(32F, 22F, 5F);
	final Point pointA = new Point("Five A test JUnit Service", 31.2F, 23.9F);
	pointService.save(pointA);
	final Point pointA1 = new Point("Five A1 test JUnit Service", 32.2F, 22.9F);
	pointService.save(pointA1);
	final Point pointA2 = new Point("Five A2 test JUnit Service", 33.2F, 23.09F);
	pointService.save(pointA2);
	final List<Point> poits = pointService.searchForNearbyPoints(32F, 22F, 5F);
	assertNotNull(poits);
	assertEquals(poitsAfter.size() + 3, poits.size());
	pointService.delete(pointA);
	pointService.delete(pointA1);
	pointService.delete(pointA2);
	final List<Point> hasPoits = pointService.searchForNearbyPoints(32F, 22F, 5F);
	assertTrue(poitsAfter.size() == hasPoits.size());
    }

    @Test
    @Rollback(value = true)
    public void validNameTest() {
	final Point point = new Point("Six test JUnit Service", 33.2F, 25.9F);
	final Boolean validNameYes = pointService.validName(point);
	assertTrue(validNameYes);
	point.setName(null);
	final Boolean validNameNo = pointService.validName(point);
	assertFalse(validNameNo);
    }

    @Test
    @Rollback(value = true)
    public void validCoordinationXTest() {
	final Point point = new Point("Sevem test JUnit Service", 33.2F, 25.9F);
	final Boolean validCoordinationXYes = pointService.validCoordinationX(point);
	assertTrue(validCoordinationXYes);
	point.setPoint_x(-25.8F);
	final Boolean validCoordinationXNo = pointService.validCoordinationX(point);
	assertFalse(validCoordinationXNo);
    }

    @Test
    @Rollback(value = true)
    public void validCoordinationYTest() {
	final Point point = new Point("Hight test JUnit Service", 33.2F, 25.9F);
	final Boolean validCoordinationYYes = pointService.validCoordinationY(point);
	assertTrue(validCoordinationYYes);
	point.setPoint_y(-2.48F);
	final Boolean validCoordinationYNo = pointService.validCoordinationY(point);
	assertFalse(validCoordinationYNo);

    }

    @Test
    @Rollback(value = true)
    public void validPreSaveTest() {
	final Point point = new Point("Hight test JUnit Service", 33.2F, 25.9F);
	final Boolean validPreSaveYes = pointService.validPreSave(point);
	assertTrue(validPreSaveYes);
	point.setPoint_y(null);
	final Boolean validPreSaveNo = pointService.validCoordinationY(point);
	assertFalse(validPreSaveNo);
    }
}
