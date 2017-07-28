package com.lge.sdet3;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GarageStateTest {
	private GarageState garageInst; /* Singleton instance */
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		garageInst = GarageState.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		garageInst.clearState(); /* Must clearState for fresh test, because it's singleton instance */
	}
	
	@Test
	public void testInitStates() {
		/* SetUP */
		/* Exercise */
		/* Verify */
		assertEquals("SU:NG=0;XG=0;NL=0;NIR=0;XIR=0;XL=0;PO=[1=0,2=0,3=0,4=0];PL=[1=0,2=0,3=0,4=0].", garageInst.toString());
		/* Tear-down */
	}
	
	@Test
	public void testEntryGateIR() {
		/* SetUP */
		/* Exercise */
		garageInst.setCarDetectedEntry(true);
		/* Verify */
		assertEquals("SU:NG=0;XG=0;NL=0;"+"NIR=1;"+"XIR=0;XL=0;PO=[1=0,2=0,3=0,4=0];PL=[1=0,2=0,3=0,4=0].", garageInst.toString());
		/* Tear-down */
	}
	
	
}
