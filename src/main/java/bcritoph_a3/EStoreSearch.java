package bcritoph_a3;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.lang.Math;
import javax.swing.*;
/**
    * The EStore contains a list to search for products
    * Has a search method that will list products based on user input of 3 attributes: productID,
    description and year
*/
public class EStoreSearch{
    private ArrayList<Product> productList;
    private HashMap<String, ArrayList<Integer>> keywordMap;

    public EStoreSearch(ArrayList<Product> productList, HashMap<String, ArrayList<Integer>> keywordMap){
        this.productList = new ArrayList<Product>(productList);
        this.keywordMap = new HashMap<String, ArrayList<Integer>>(keywordMap);
    }

    public static HashMap<String, ArrayList<Integer>> addKeysToHashMap(Product product, HashMap<String, ArrayList<Integer>> keywordMap, int index){
        String[] keywords = product.getDescription().split("[ ]+");
        // iterate through each keyword in product description
        for(int j = 0; j < keywords.length; j++){
            String word = keywords[j].toLowerCase();
            // if keyword already exists for description then do nothing
            if(keywordMap.containsKey(word)){
                // do nothing if index has already been added for keyword in description
                if(keywordMap.get(word).contains(index)){
                    ;
                }
                // otherwise add the index of the product to the arrayList
                else{
                    keywordMap.get(word).add(index);
                }
            }
            /* if keyword does not exist in hashmap then create an arrayList for keyword,
            add the index of the keyword to the arrayList and then add the arrayList with
            keyword as the key to the hashmap */
            else{
                ArrayList<Integer> indexFound = new ArrayList<>();
                indexFound.add(index);
                keywordMap.put(word, indexFound);
            }
        }
        return keywordMap;
    }
    /* Writes all products on product list to file provided as commandline argument */
    public static void saveFile(ArrayList<Product> productList, String outputFile){
        PrintWriter fileWriter = null;
        try{
            fileWriter = new PrintWriter(new FileOutputStream("src/main/java/eStoreSearch/"+outputFile));
            int max = productList.size();
            for(int i = 0; i < max; i++){
                Product product = productList.get(i);
                fileWriter.println(product.saveOutput());

            }
            fileWriter.close();
        }
        catch(Exception e){
            System.out.println("Error - failed to write to file");
        }
    }
    /* Loads file in as a commandline argument and adds all products listed on file to productList */
    public static void readFile(ArrayList<Product> productList, String inputFile){
        System.out.println("Reading text file");
        File f = new File("src/main/java/eStoreSearch/"+inputFile);
        if(f.length() <= 10){
            System.out.println("File is empty");
            return;
        }
        Scanner sc = null;
        boolean failedRead = false;
        try{
            sc = new Scanner(f);
        }
        catch(FileNotFoundException e){
            System.out.println("Error - File was not found. Failed to read file\n");
            failedRead = true;
        }
        if(!failedRead){
            while(sc.hasNextLine()){
                String type = sc.next();
                String productID = sc.next();
                sc.nextLine();
                // String description = "";
                String description = sc.nextLine();
                String price = sc.nextLine();
                String year = sc.nextLine();
                if(type.equalsIgnoreCase("book")){
                    String author = sc.nextLine();
                    String publisher = sc.nextLine();
                    try{
                        Book book = new Book(productID, description, price, year, author, publisher);
                        productList.add(book);
                        System.out.println("Loaded book to file");
                    }
                    catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                } else {
                    String maker = sc.nextLine();
                    try{
                        Electronics electronics = new Electronics(productID, description, price, year, maker);
                        productList.add(electronics);
                        System.out.println("Loaded electronics to file");
                    }
                    catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }
    /* Returns true if string is empty and false if string is not empty */
    private boolean emptyStr(String str){
        if(str.equals("")){
            return true;
        }
        return false;
    }
    /* Returns true if a products id in the list matches with the search targets id or if search
    target is empty. Otherwise returns false */
    private boolean productIdMatch(Product product, String strID){
        if(emptyStr(strID)){
            return true;
        }
        int productID = Integer.parseInt(strID);
        if(productID == product.getProductID()){
            return true;
        }
        return false;
    }
    /* Returns true if a products year in the list matches with the search target year or if search
    target is empty. Otherwise returns false */
    private boolean productYearMatch(Product product, String tp){
        if(emptyStr(tp)){
            return true;
        }
        String timePeriod = tp.replaceAll("\\s+", "");
        char firstChar = timePeriod.charAt(0);
        char lastChar = timePeriod.charAt(timePeriod.length()-1);
        if(lastChar == '-'){
            timePeriod = timePeriod.substring(0, timePeriod.length()-1);
        }
        int []years;
        if( (lastChar != '-') && (timePeriod.charAt(4) == '-') ){
            String[] strYears = timePeriod.split("[-]+");
            years = new int[strYears.length];
            years[0] = Integer.parseInt(strYears[0]);
            years[1] = Integer.parseInt(strYears[1]);
        }
        else{
            Scanner co = new Scanner(timePeriod);
            int count = 0;
            while(co.hasNextInt()){
                co.nextInt();
                count++;
            }
            Scanner sc = new Scanner(timePeriod);
            years = new int[count];
            int index = 0;
            while(sc.hasNextInt()){
                years[index] = Math.abs(sc.nextInt());
                index++;
            }
        }
        if((firstChar == '-') && (years.length == 1)){
            if(product.getYear() <= years[0]){
                return true;
            }
            return false;
        }
        else if((lastChar == '-') && (years.length == 1)){
            if(product.getYear() >= years[0]){
                return true;
            }
            return false;
        }
        else if(years.length == 2){
            if(product.getYear() >= years[0] && product.getYear() <= years[1]){
                return true;
            }
            return false;
        }
        else if(years.length == 1){
            if(product.getYear() == years[0]){
                return true;
            }
            return false;
        }
        else{
            return false;
        }
    }
    /* Returns true if a products description in the list matches with the search target description
    or if search target is empty. Otherwise returns false */
    private boolean productDescriptionMatch(Product product, String description){
        if(emptyStr(description)){
            return true;
        }
        String[] targets = description.split("[ ]+");
        String productDesc = product.getDescription();
        String[] descWords = productDesc.split("[ ]+");
        // Iterate through all of the target words being searched to make sure each word in target is in product
        for(int i = 0; i < targets.length; i++){
            // Iterate through each word in product object and compare if target description is in product
            for(int j = 0; j < descWords.length; j++){
                if(targets[i].equalsIgnoreCase(descWords[j])){
                    return true;
                }
            }
        }
        return false;
    }
    /* Uses helper functions to find matches and prints the matches with toString method of Product */
    public String search(String strID, String description, String timePeriod){
        // Iterate through productList and perform match checks for each attribute of product
        String ret = "";
        for(int i = 0; i < productList.size(); i++){
            Product product = productList.get(i);
            if(productIdMatch(product, strID) && productYearMatch(product, timePeriod) && productDescriptionMatch(product,description)){
                ret = ret + product.toString();
            }
        }
        return ret;
    }
    /* Uses HashMap to find products matching keywords description as well as the product ID and year
       Uses the normal search function if description is empty*/
    public String hashSearch(String strID, String description, String timePeriod){
        String ret = "";
        // if description is missing then do a normal search
        if(description.equals("")){
            ret = search(strID, description, timePeriod);
            return ret;
        }
        // otherwise use the hashMap for search
        String[] keyword = description.toLowerCase().split("[ ]+");
        boolean multipleLists = false;
        ArrayList <Integer> intersection = new ArrayList<>();
        ArrayList <Integer> indexList = new ArrayList<>();
        // set intersection arraylist equal to first keyword arrayList
        if(keywordMap.get(keyword[0]) == null){
            return "";
        }
        intersection = new ArrayList<Integer>(keywordMap.get(keyword[0]));
        // only add arraylist indices that intersect with all of the new arraylists
        for(int i = 1; i < keyword.length; i++){
            if(keywordMap.get(keyword[i]) == null){
                return "";
            }
            intersection.retainAll(keywordMap.get(keyword[i]));
        }
        // only search intersecting array indices and also make sure that only products with matching IDs and years are matched
        for(int i = 0; i < intersection.size(); i++){
            Product product = productList.get(intersection.get(i));
            if(productIdMatch(product, strID)){
                if(productYearMatch(product, timePeriod)){
                    ret = ret + product.toString();
                }
            }
        }
        return ret;
    }
}
