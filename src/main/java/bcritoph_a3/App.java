package bcritoph_a3;

import java.awt.Color;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.HashMap;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        ArrayList<Product> productList = new ArrayList<Product>();
        HashMap<String, ArrayList<Integer>> keywordMap = new HashMap<>();
        int hashTableIndex = 0;

        Gui win = new Gui(Color.WHITE);
        win.setVisible(true);
        if(args.length != 0){
            EStoreSearch.readFile(productList, args[0]);
            // iterate through each product description in productList
            for(int i = 0; i < productList.size(); i++){
                Product product = productList.get(i);
                keywordMap = EStoreSearch.addKeysToHashMap(product, keywordMap, i);
                hashTableIndex++;
            }
        }
    }
}
