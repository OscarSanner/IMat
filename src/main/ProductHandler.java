package main;

import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class ProductHandler {

    private static IMatDataHandler dataHandler = IMatDataHandler.getInstance();

    //_________________________Subcategories
    //Meat and Fish
    private static ProductCategory meat[] = {ProductCategory.MEAT};
    private static ProductCategory fish [] = {ProductCategory.FISH};

    //Fruit and Vegetables
    private static ProductCategory berry [] = {ProductCategory.BERRY};
    private static ProductCategory fruit [] = {ProductCategory.CITRUS_FRUIT, ProductCategory.EXOTIC_FRUIT, ProductCategory.FRUIT, ProductCategory.MELONS};
    private static  ProductCategory vegetables [] = {ProductCategory.POD, ProductCategory.VEGETABLE_FRUIT, ProductCategory.CABBAGE, ProductCategory.ROOT_VEGETABLE};

    //Drinks
    private static ProductCategory hotDrinks[] = {ProductCategory.HOT_DRINKS};
    private static ProductCategory coldDrinks [] = {ProductCategory.COLD_DRINKS};

    //Dairy
    private static ProductCategory dairyProducts [] = {ProductCategory.DAIRIES};

    //Pantry
    private static ProductCategory flour_sugar_salt [] = {ProductCategory.FLOUR_SUGAR_SALT};
    private static ProductCategory pasta [] = {ProductCategory.PASTA};
    private static ProductCategory potato_rice [] = {ProductCategory.POTATO_RICE};

    //Spices
    private static ProductCategory herbs [] = {ProductCategory.HERB};

    //Snacks
    private static ProductCategory sweets [] = {ProductCategory.SWEET};
    private static ProductCategory nuts_seeds [] = {ProductCategory.NUTS_AND_SEEDS};

    //____________________________Categories
    private static ProductCategory meat_Fish[][] = {meat, fish};
    private static ProductCategory fruit_Vegetables [][] = {berry, fruit, vegetables};
    private static ProductCategory drinks [][] = {hotDrinks, coldDrinks};
    private static ProductCategory dairy [][] = {dairyProducts};
    private static ProductCategory pantry [][] = {flour_sugar_salt, pasta, potato_rice};
    private static ProductCategory spices [][] = {herbs};
    private static ProductCategory snacks [][] = {sweets, nuts_seeds};

    //================================================================================
    // "Collect Products" logic
    //================================================================================
    /**
     * Collects all the products from the requested category.
     */
    public static List<Product> getProductsFromCategory(String chosenCategory){
        List<Product> selectedProducts = new ArrayList<>();

        try{
            ProductCategory[][] selectedCategory = getProductCategory(chosenCategory);

            for(ProductCategory[] pcArr: selectedCategory){
                for(ProductCategory pc: pcArr){
                    selectedProducts.addAll(dataHandler.getProducts(pc));
                }
            }
        }catch (NullPointerException e){System.out.println("Not valid input for category");}
        return selectedProducts;

    }

    /**
     * Returns ProductCategory
     */
    private static ProductCategory[][] getProductCategory(String chosenCategory) {
        switch (chosenCategory){
            case "Kött & Fisk": return meat_Fish;
            case "Frukt & Grönt": return fruit_Vegetables;
            case "Dryck": return drinks;
            case "Mejeri": return dairy;
            case "Skafferi": return pantry;
            case "Snacks": return snacks;
            case "Kryddor": return spices;

            case "Kött": return new ProductCategory[][]{meat};
            case "Fisk": return new ProductCategory[][]{fish};
            case "Bär": return new ProductCategory[][]{berry};
            case "Frukt": return new ProductCategory[][]{fruit};
            case "Grönsaker": return new ProductCategory[][]{vegetables};
            case "Varma drycker": return new ProductCategory[][]{hotDrinks};
            case "Kalla drycker": return new ProductCategory[][]{coldDrinks};
            case "Mjöl, socker, salt": return new ProductCategory[][]{flour_sugar_salt};
            case "Pasta": return new ProductCategory[][]{pasta};
            case "Potatis, ris": return new ProductCategory[][]{potato_rice};
            case "Sötsaker": return new ProductCategory[][]{sweets};
            case "Nötter och frön": return new ProductCategory[][]{nuts_seeds};
            default: return new ProductCategory[][]{null};
        }
    }
    private ProductHandler(){}
}
