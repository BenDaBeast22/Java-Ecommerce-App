package bcritoph_a3;
/**
    * A book product for the EStore which consists of a product ID, book description,
    its price, the year, the author and the publisher
    * The toString method prints out all of the books attributes nicely on one line each

*/
public class Book extends Product{
    private String author;
    private String publisher;

    public Book(String productID, String description, String price, String year, String author, String publisher) throws Exception{
        super(productID, description, price, year);
        this.author = author;
        this.publisher = publisher;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String a){
        author = a;
    }

    public String getPublisher(){
        return publisher;
    }

    public void setPublisher(String p){
        publisher = p;
    }

    @Override
    public String saveOutput(){
        return "book\n"+productID+"\n"+description+"\n"+price+"\n"+year+
        "\n"+author+"\n"+publisher;
    }

    @Override
    public String toString(){
        return "productID: "+productID+"\ndescription: "+description+"\nprice: "+price+"\nyear: "+year+
        "\nauthor: "+author+"\npublisher: "+publisher+"\n\n";
    }
}
