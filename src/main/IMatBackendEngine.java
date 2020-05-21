package main;

import se.chalmers.cse.dat216.project.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IMatBackendEngine {

    //TODO HÄR KAN ALLA LISTOR ÖVER ORDRAR FINNAS. HIT KALLAR MAN OM MAN BEHÖVER GETTA T.EX. VAROR ETC
    //TODO DENNA KLASSEN KAN KOMMUNICERA MED DEN EXTERNA BACKENDEN.

    private static IMatBackendEngine instance;
    private IMatBackendEngine(){}
    private List<Order> saved = new ArrayList<Order>();
    HashMap<Order, String> savedOrders = new HashMap<>();

    static public IMatBackendEngine getInstance(){
        if (instance == null){
            instance = new IMatBackendEngine();
        }
        return instance;
    }

    public void addSavedOrder(Order order){
        savedOrders.put(order, ("Inköpslista " + savedOrders.size()));
    }

    public void removeSavedOrder(Order order) {
        savedOrders.remove(order);
    }
}
