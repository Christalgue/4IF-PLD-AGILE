
import java.util.*;

/**
 * 
 */
public class CircuitManagement {

    /**
     * Default constructor
     */
    public CircuitManagement() {
    }

    /**
     * 
     */
    public Map actualMap;

    /**
     * 
     */
    public int nbDeliveryMan;

    /**
     * 
     */
    public Circuit[] circuitsList;

    /**
     * 
     */
    public List<Delivery> deliveryList;

    /**
     * 
     */
    public Serializer chargingUnit;





    /**
     * @param filename 
     * @return
     */
    public List<Delivery> loadDeliveryList(void filename) {
        // TODO implement here
        return null;
    }

    /**
     * @param filename
     */
    public void loadMap(void filename) {
        // TODO implement here
    }

    /**
     * K-means clustering algorithm
     * @return
     */
    public List<Delivery>[] cluster() {
        // TODO implement here
        return null;
    }

    /**
     * generic method to call when calculation of the circuits is asked by GUI
     * call the cluster method and create the circuits one by one after having 
     *      calculated the atomic path between each delivery
     * @param nbDeliveryman
     */
    public void calculateCircuits(void nbDeliveryman) {
        // TODO implement here
    }

}