package com.marcelo.xyinc.repository;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.marcelo.xyinc.SampleConfigTest;



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
    public void saveTest() {

    }

    @Test
    public void updateTest() {

    }

    @Test
    public void findBiIdTest() {

    }

    @Test
    public void deleteTest() {

    }

}
