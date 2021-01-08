package bcritoph_a3;
/**
    * A parent class for the Electronics and Book classes which consists of a product ID, a description of the product,
    its price and the year
    * The toString method prints out all of the products attributes that both electronics and book classes possess nicely on one line each
*/
abstract class Product{
    protected int productID;
    protected String description;
    protected double price;
    protected int year;

    public Product(String productID, String description, String price, String year) throws Exception{
        if(validProductID(productID)){
            try{
                this.productID = Integer.parseInt(productID);
            }
            catch(Exception e){
                throw new Exception("Product ID must be an integer\n");
            }
        } else {
            throw new Exception("Product ID must be 6 digits(0-9)\n");
        }
        if(!description.equals("")){
            this.description = description;
        } else {
            throw new Exception("Description must not be left empty\n");
        }
        if(price.equals("")){
            this.price = 0;
        }
        else{
            try{
                this.price = Double.parseDouble(price);
            }
            catch(Exception e){
                throw new Exception("Price must be a double or an integer\n");
            }
        }
        int validYear;
        if( !(year.equals("")) ){
            try{
                validYear = Integer.parseInt(year);
            }
            catch(Exception e){
                throw new Exception("Year must be an integer\n");
            }
            if(validYear >= 1000 && validYear <= 9999){
                this.year = validYear;
            } else {
                throw new Exception("Year must be between 1000 and 9999 inclusive\n");
            }
        }
        else{
            throw new Exception("Year must not be left empty\n");
        }
    }

    public int getProductID(){
        return productID;
    }

    public static boolean validProductID(String productID){
        if(productID.length() != 6 || !(productID.matches("[0-9]+"))){
            return false;
        }
        return true;
    }

    public boolean setProductID(String id){
        if(id.equals("")){
            return false;
        }
        else if(id.length() != 6 || !(id.matches("[0-9]+"))){
            return false;
        }
        productID = Integer.parseInt(id);
        return true;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String d){
        description = d;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double p){
        price = p;
    }

    public int getYear(){
        return year;
    }

    public boolean setYear(int y){
        if(y < 1000 || y > 9999){
            return false;
        }
        year = y;
        return true;
    }

    public abstract String saveOutput();

    public abstract String toString();
}
