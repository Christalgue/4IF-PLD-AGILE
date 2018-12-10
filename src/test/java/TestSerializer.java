package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.exception.DijkstraException;
import main.java.exception.LoadDeliveryException;
import main.java.exception.LoadMapException;
import main.java.exception.MapNotChargedException;
import main.java.exception.NoRepositoryException;
import main.java.exception.TSPLimitTimeReachedException;
import main.java.utils.Serializer;

class TestSerializer {

	@Test
	void testSerializer() {
		try {
			CircuitManagement circuitManager = new CircuitManagement();
			circuitManager.loadMap("resources/tests/Serializer/xml/plan.xml");
			circuitManager.loadDeliveryList("resources/tests/Serializer/xml/delivery.xml");
			circuitManager.calculateCircuits(1, false);
			List<Delivery> deliveries = circuitManager.getDeliveryList();
			
			String path = "resources/tests/Serializer/sorties";
			
			Serializer.serializer(path, circuitManager);
			
			BufferedReader reader = new BufferedReader(new FileReader(path+"/Tournée_1.txt"));
			
			String line;
			String expectation;
			
			line = reader.readLine();
			expectation  = "Tournée n°1";
			assertTrue(line.contains(expectation),"Error expected : "+expectation+", got : "+line);
			
			line = reader.readLine();
			expectation  = "  Chemin de l'entrepôt à la Livraison n°1, durée : moins de 1min (14m)";
			assertTrue(line.contains(expectation),"Error expected : "+expectation+", got : "+line);
			
			line = reader.readLine();
			expectation  = "    ->Prendre Eb jusqu'à bc. (7m)";
			assertTrue(line.contains(expectation),"Error expected : "+expectation+", got : "+line);
			
			line = reader.readLine();
			expectation  = "    ->Prendre bc jusqu'à cd. (7m)";
			assertTrue(line.contains(expectation),"Error expected : "+expectation+", got : "+line);

			
			line = reader.readLine();
			expectation  = "    ->Vous êtes arrivé à destination, votre livraison doit durer environ : moins de 1min";
			assertTrue(line.contains(expectation),"Error expected : "+expectation+", got : "+line);

			line = reader.readLine();
			expectation  = "";
			assertTrue(line.contains(expectation),"Error expected : "+expectation+", got : "+line);

			line = reader.readLine();
			expectation  = "  Chemin de la Livraison n°1 à la Livraison n°2, durée : moins de 1min (8m)";
			assertTrue(line.contains(expectation),"Error expected : "+expectation+", got : "+line);

			line = reader.readLine();
			expectation  = "    ->Prendre cd jusqu'à de. (7m)";
			assertTrue(line.contains(expectation),"Error expected : "+expectation+", got : "+line);

			line = reader.readLine();
			expectation  = "    ->Prendre de jusqu'à Ee. (1m)";
			assertTrue(line.contains(expectation),"Error expected : "+expectation+", got : "+line);

			line = reader.readLine();
			expectation  = "    ->Vous êtes arrivé à destination, votre livraison doit durer environ : moins de 1min";
			assertTrue(line.contains(expectation),"Error expected : "+expectation+", got : "+line);

			line = reader.readLine();
			expectation  = "";
			assertTrue(line.contains(expectation),"Error expected : "+expectation+", got : "+line);

			line = reader.readLine();
			expectation  = "  Chemin de la Livraison n°2 à l'entrepôt, durée : moins de 1min (7m)";
			assertTrue(line.contains(expectation),"Error expected : "+expectation+", got : "+line);

			line = reader.readLine();
			expectation  = "    ->Prendre eE jusqu'à l'entrepôt. (7m)";
			assertTrue(line.contains(expectation),"Error expected : "+expectation+", got : "+line);

			line = reader.readLine();
			expectation  = "    ->Vous avez terminé votre tournée";
			assertTrue(line.contains(expectation),"Error expected : "+expectation+", got : "+line);
			
			reader.close();
		} catch (LoadMapException e) {
			fail("LoadMapException, report to TestDeserializer : "+e.getMessage());
		} catch (LoadDeliveryException e) {
			fail("LoadDeliveryException, report to TestDeserializer : "+e.getMessage());
		} catch (MapNotChargedException e) {
			fail("MapNotChargedException : "+e.getMessage());
		} catch (DijkstraException e) {
			fail("DijkstraException : "+e.getMessage());
		} catch (NoRepositoryException e) {
			fail("NoRepositoryException : "+e.getMessage());
		} catch (TSPLimitTimeReachedException e) {
			fail("TSPLimitTimeReachedException : "+e.getMessage());
		} catch (IOException e) {
			fail("IOException : "+e.getMessage());
		}
	}

}
