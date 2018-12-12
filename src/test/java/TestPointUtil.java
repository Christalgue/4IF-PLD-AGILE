package test.java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.entity.CircuitManagement;
import main.java.entity.Node;
import main.java.entity.Point;
import main.java.exception.ForgivableXMLException;
import main.java.exception.LoadMapException;
import main.java.utils.PointUtil;

class TestPointUtil {

	@Test
	/**
	 * Test if pointToNode return the nearest node (if in range) of the map when we send him coordinates
	 */
	void testPointToNode() {
		try {
			CircuitManagement circuitManager = new CircuitManagement();
			try {
				circuitManager.loadMap("resources/tests/PointUtil/xml/plan.xml");
			} catch (ForgivableXMLException e) {}
			
			PointUtil.range = 3;
			
			Point point = new Point(0,0);
			Node node = PointUtil.pointToNode(point, circuitManager);
			assertTrue(node!=null, "node (0,0) is null");
			assertTrue(node.getId()==(long)1, "node (0,0) is not 1, got : "+node.getId());
			
			point = new Point(0,8);
			node = PointUtil.pointToNode(point, circuitManager);
			assertTrue(node!=null, "node (8,0) is null");
			assertTrue(node.getId()==(long)2, "node (8,0) is not 2, got : "+node.getId());
			
			point = new Point(100,100);
			node = PointUtil.pointToNode(point, circuitManager);
			assertTrue(node==null, "node (100,100) is not null");
			
			point = new Point(10,1);
			node = PointUtil.pointToNode(point, circuitManager);
			assertTrue(node!=null, "node (1,10) is null");
			assertTrue(node.getId()==(long)3, "node (1,10) is not 3, got : "+node.getId());
		} catch (LoadMapException e) {
			fail("LoadMapException, report to TestDeserializer : "+e.getMessage());
		}
	}

}
