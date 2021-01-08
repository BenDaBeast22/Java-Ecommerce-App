package bcritoph_a3;
/**
    * A electronics product for the EStore which consists of a product ID, a description of the product,
    its price, the year and the maker of the product
    * The toString method prints out all of the electronics attributes nicely on one line each
*/
public class Electronics extends Product{
    private String maker;

    public Electronics(String productID, String description, String price, String year, String maker) throws Exception{
        super(productID, description, price, year);
        this.maker = maker;
    }

    public String getMaker(){
        return maker;
    }

    public void setMaker(String m){
        maker = m;
    }

    @Override
    public String saveOutput(){
        return "electronics\n"+productID+"\n"+description+"\n"+price+"\n"+year+
        "\n"+maker;
    }

    @Override
    public String toString(){
        return "productID: "+productID+"\ndescription: "+description+"\nprice: "+price+"\nyear: "
        +year+"\nmaker: "+maker+"\n\n";
    }
}
