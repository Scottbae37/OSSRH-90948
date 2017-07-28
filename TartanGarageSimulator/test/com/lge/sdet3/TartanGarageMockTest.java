package com.lge.sdet3;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TartanGarageMockTest {

	private GarageState garageState;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		garageState = GarageState.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		garageState.clearState();
	}

	@Ignore("Add test-cases")
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
