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
	final Point point = new Point("Five test JUnit Service", 33.2F, 25.9F);
	pointService.save(point);
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
    public void validCoordinationX() {
	final Point point = new Point("Sevem test JUnit Service", 33.2F, 25.9F);
	final Boolean validCoordinationXYes = pointService.validCoordinationX(point);
	assertTrue(validCoordinationXYes);
	point.setPoit_x(-25.8F);
	final Boolean validCoordinationXNo = pointService.validCoordinationX(point);
	assertFalse(validCoordinationXNo);
    }

    @Test
    @Rollback(value = true)
    public void validCoordinationY() {
	final Point point = new Point("Hight test JUnit Service", 33.2F, 25.9F);
	final Boolean validCoordinationYYes = pointService.validCoordinationY(point);
	assertTrue(validCoordinationYYes);
	point.setPoit_y(-2.48F);
	final Boolean validCoordinationYNo = pointService.validCoordinationY(point);
	assertFalse(validCoordinationYNo);

    }

    @Test
    @Rollback(value = true)
    public void validPreSave() {
	final Point point = new Point("Hight test JUnit Service", 33.2F, 25.9F);
	final Boolean validPreSaveYes = pointService.validPreSave(point);
	assertTrue(validPreSaveYes);
	point.setPoit_y(null);
	final Boolean validPreSaveNo = pointService.validCoordinationY(point);
	assertFalse(validPreSaveNo);
    }
}
