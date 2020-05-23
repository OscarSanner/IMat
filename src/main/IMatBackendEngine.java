package main;

import javafx.scene.control.Button;
import se.chalmers.cse.dat216.project.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IMatBackendEngine {

    //TODO HÄR KAN ALLA LISTOR ÖVER ORDRAR FINNAS. HIT KALLAR MAN OM MAN BEHÖVER GETTA T.EX. VAROR ETC
    //TODO DENNA KLASSEN KAN KOMMUNICERA MED DEN EXTERNA BACKENDEN.

    private static IMatBackendEngine instance;
    private IMatBackendEngine(){}
    private List<Order> saved = new ArrayList<Order>();
    HashMap<Order, String> savedOrders = new HashMap<>();
    Button activeSubcategory;
    Button activeCategory;
    Button lastActiveCategory;

    Button activeTab;
    Button lastActiveTab;


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

    public void clearActiveSubcategory() {
        if(activeSubcategory != null){
            activeSubcategory.getStyleClass().clear();
            activeSubcategory.getStyleClass().add("button");
            activeSubcategory.getStyleClass().add("subCategoryButton");
            activeSubcategory = null;
        }
    }

    public void setActiveSubcategory(Button button) {
        activeSubcategory = button;
        button.getStyleClass().add("subCategoryButtonSelected");
    }

    public void setActiveCategory(Button button){
        activeCategory = button;
        button.getStyleClass().add("categoryButtonsSelected");
        lastActiveCategory = button;
    }

    public void clearActiveCategory() {
        if (activeCategory != null){
            activeCategory.getStyleClass().clear();
            activeCategory.getStyleClass().add("button");
            activeCategory.getStyleClass().add("categoryButtons");
            activeCategory = null;
        }
    }

    public void restoreActiveCategory() {
        if(lastActiveCategory != null){
            setActiveCategory(lastActiveCategory);
        }
    }

    public void clearActiveTab(){
        if(activeTab != null){
            activeTab.getStyleClass().clear();
            activeTab.getStyleClass().add("tabButtons");
            activeTab = null;
        }
    }
    public void setActiveTab(Button button){
        activeTab = button;
        button.getStyleClass().add("tabButtonsSelected");
    }


}
