package bcritoph_a3;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.HashMap;

/**
    * The Gui class creates a window where all of the operations from the EStoreSearch class can be performed
    * User can choose a command from the "Commands" menu to add products, search for products, or to quit the program. 
*/

public class Gui extends JFrame implements ActionListener{
    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;

    private JTextArea instructions;
    private JPanel mainPanel;
    private CardLayout card;
    private JComboBox type;
    private JTextField productId;
    private JTextField description;
    private JTextField price;
    private JTextField year;
    private JTextField authors;
    private JTextField publisher;
    private JTextArea outputField;
    private JTextArea searchField;
    private JPanel publisherPanel;
    private JLabel authorLabel;
    private JTextField keywords;
    private JTextField searchProductId;
    private JTextField startYear;
    private JTextField endYear;

    private String operation = "";
    private ArrayList<Product> productList = new ArrayList<Product>();
    private HashMap<String, ArrayList<Integer>> keywordMap = new HashMap<>();
    private int hashTableIndex = 0;
    private Boolean isBook = true;

    public Gui(Color bgColor) {
        super("eStoreSearch");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(bgColor);
        setLayout(new BorderLayout());

        JMenu commandsMenu = new JMenu("Commands");

        JMenuItem add = new JMenuItem("add");
        add.addActionListener(this);
        commandsMenu.add(add);

        JMenuItem search = new JMenuItem("search");
        search.addActionListener(this);
        commandsMenu.add(search);

        JMenuItem quit = new JMenuItem("quit");
        quit.addActionListener(this);
        commandsMenu.add(quit);

        card = new CardLayout();

        mainPanel = new JPanel();
        mainPanel.setLayout(card);

        instructions = new JTextArea("\nWelcome to eStoreSearch\n\nChoose a command from the \"Commands\" menu above for adding a product, searching products, or quitting the program.", 5, 20);
        instructions.setEditable(false);
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);
        instructions.setFont(instructions.getFont().deriveFont(20.0f));
        instructions.setForeground(Color.DARK_GRAY);
        instructions.setMargin( new Insets(20,20,20,20) );

        mainPanel.add("Welcome", instructions);

        // Add Form Card
        JPanel form = new JPanel();
        form.setLayout(new BorderLayout());

        JLabel addLabel = new JLabel("Adding a product");
        form.add(addLabel,BorderLayout.NORTH);

        // Add Form info
        JPanel formInfo = new JPanel();
        formInfo.setLayout(new GridLayout(7,1));

        JPanel typePanel = new JPanel();
        String []productType = {"book" , "electronics"};
        JLabel typeLabel = new JLabel("Type:", SwingConstants.CENTER);
        type = new JComboBox(productType);
        type.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JComboBox cb = (JComboBox)e.getSource();
                String productType = (String)cb.getSelectedItem();

                if(productType.equals("book")){
                    publisher.setVisible(true);
                    publisherPanel.setVisible(true);
                    authorLabel.setText("Author:");
                    isBook = true;
                }
                else if(productType.equals("electronics")){
                    authorLabel.setText("Maker:");
                    publisher.setVisible(false);
                    publisherPanel.setVisible(false);
                    isBook = false;
                }
            }
        });
        // type.setSelectedIndex(0);
        typePanel.add(typeLabel);
        typePanel.add(type);
        formInfo.add(typePanel);

        JPanel productIdPanel = new JPanel();
        JLabel productIdLabel = new JLabel("ProductID:", SwingConstants.CENTER);
        JPanel productContainer = new JPanel();
        productId = new JTextField(20);
        productIdPanel.add(productIdLabel);
        productIdPanel.add(productId);
        formInfo.add(productIdPanel);

        JPanel descriptionPanel = new JPanel();
        JLabel descriptionLabel = new JLabel("Description:", SwingConstants.CENTER);
        description = new JTextField(20);
        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(description);
        formInfo.add(descriptionPanel);

        JPanel pricePanel = new JPanel();
        JLabel priceLabel = new JLabel("Price:", SwingConstants.CENTER);
        price = new JTextField(20);
        pricePanel.add(priceLabel);
        pricePanel.add(price);
        formInfo.add(pricePanel);

        JPanel yearPanel = new JPanel();
        JLabel yearLabel = new JLabel("Year:", SwingConstants.CENTER);
        year = new JTextField(20);
        yearPanel.add(yearLabel);
        yearPanel.add(year);
        formInfo.add(yearPanel);

        JPanel authorPanel = new JPanel();
        authorLabel = new JLabel("Author:", SwingConstants.CENTER);
        authors = new JTextField(20);
        authorPanel.add(authorLabel);
        authorPanel.add(authors);
        formInfo.add(authorPanel);

        publisherPanel = new JPanel();
        JLabel publisherLabel = new JLabel("Publisher:", SwingConstants.CENTER);
        publisher = new JTextField(20);
        publisherPanel.add(publisherLabel);
        publisherPanel.add(publisher);
        formInfo.add(publisherPanel);

        form.add(formInfo, BorderLayout.CENTER);

        // Add Form Buttons
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(2,1));

        JPanel but1Container = new JPanel();
        JButton button1 = new JButton("Reset");
        button1.addActionListener(this);
        but1Container.add(button1);
        buttons.add(but1Container);
        JPanel but2Container = new JPanel();
        JButton button2 = new JButton("Add");
        button2.addActionListener(this);
        but2Container.add(button2);
        buttons.add(but2Container);

        form.add(buttons, BorderLayout.EAST);

        //Output Field
        JPanel outputPanel = new JPanel();
        LayoutManager layout = new BoxLayout(outputPanel, BoxLayout.PAGE_AXIS);
        outputPanel.setLayout(layout);

        JPanel textPanel = new JPanel();
        textPanel.setBackground(Color.LIGHT_GRAY);

        JLabel outputLabel = new JLabel("Messages");

        outputField = new JTextArea(8, 40);
        outputField.setLineWrap(true);
        outputField.setEditable(false);
        outputField.setBackground(Color.WHITE);

        JScrollPane scrolledText = new JScrollPane(outputField);
        scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        textPanel.add(scrolledText);
        outputPanel.add(outputLabel);
        outputPanel.add(textPanel);

        form.add(outputPanel, BorderLayout.SOUTH);

        mainPanel.add("Form", form);

        add(mainPanel, BorderLayout.CENTER);

        JMenuBar bar = new JMenuBar();
        bar.add(commandsMenu);

        add(bar,BorderLayout.NORTH);

        // Search form card
        JPanel searchForm = new JPanel();
        searchForm.setLayout(new BorderLayout());

        JLabel searchLabel = new JLabel("Searching products");
        searchForm.add(searchLabel,BorderLayout.NORTH);

        // Search Form info
        JPanel searchFormInfo = new JPanel();
        searchFormInfo.setLayout(new GridLayout(4,1));

        JPanel searchProductIdPanel = new JPanel();
        JLabel searchProductIdLabel = new JLabel("ProductID:", SwingConstants.CENTER);
        searchProductId = new JTextField(20);
        searchProductIdPanel.add(searchProductIdLabel);
        searchProductIdPanel.add(searchProductId);
        searchFormInfo.add(searchProductIdPanel);

        JPanel keywordsPanel = new JPanel();
        JLabel keywordsLabel = new JLabel("<html>Description<br/>Keywords:</html>", SwingConstants.CENTER);
        keywords = new JTextField(20);
        keywordsPanel.add(keywordsLabel);
        keywordsPanel.add(keywords);
        searchFormInfo.add(keywordsPanel);

        JPanel startYearPanel = new JPanel();
        JLabel startYearLabel = new JLabel("Start Year:", SwingConstants.CENTER);
        startYear = new JTextField(20);
        startYearPanel.add(startYearLabel);
        startYearPanel.add(startYear);
        searchFormInfo.add(startYearPanel);

        JPanel endYearPanel = new JPanel();
        JLabel endYearLabel = new JLabel("End Year:", SwingConstants.CENTER);
        endYear = new JTextField(20);
        endYearPanel.add(endYearLabel);
        endYearPanel.add(endYear);
        searchFormInfo.add(endYearPanel);

        searchForm.add(searchFormInfo, BorderLayout.CENTER);

        //Search output Field
        JPanel searchOutputPanel = new JPanel();
        LayoutManager searchLayout = new BoxLayout(searchOutputPanel, BoxLayout.PAGE_AXIS);
        searchOutputPanel.setLayout(searchLayout);

        JPanel searchTextPanel = new JPanel();
        searchTextPanel.setBackground(Color.LIGHT_GRAY);

        JLabel messagesLabel = new JLabel("Search Results");

        searchField = new JTextArea(8, 40);
        searchField.setLineWrap(true);
        searchField.setEditable(false);
        searchField.setBackground(Color.WHITE);

        JScrollPane scrolledSearchText = new JScrollPane(searchField);
        scrolledSearchText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrolledSearchText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        searchTextPanel.add(scrolledSearchText);
        searchOutputPanel.add(messagesLabel);
        searchOutputPanel.add(searchTextPanel);

        searchForm.add(searchOutputPanel, BorderLayout.SOUTH);

        // Add Form Buttons
        JPanel searchButtons = new JPanel();
        searchButtons.setLayout(new GridLayout(2,1));

        JPanel sbut1Container = new JPanel();
        JButton sbutton1 = new JButton("Reset");
        sbutton1.addActionListener(this);
        sbut1Container.add(sbutton1);
        searchButtons.add(sbut1Container);
        JPanel sbut2Container = new JPanel();
        JButton sbutton2 = new JButton("Search");
        sbutton2.addActionListener(this);
        sbut2Container.add(sbutton2);
        searchButtons.add(sbut2Container);

        searchForm.add(searchButtons, BorderLayout.EAST);

        mainPanel.add("Search", searchForm);

    }

    public Gui(){
        this(Color.BLUE);
    }

    private void reset(){
        productId.setText("");
        price.setText("");
        description.setText("");
        year.setText("");
        authors.setText("");
        publisher.setText("");
        keywords.setText("");
        searchProductId.setText("");
        startYear.setText("");
        endYear.setText("");
    }

    public void actionPerformed(ActionEvent event){
        String buttonString = event.getActionCommand();

        if(buttonString.equals("add")){
            reset();
            card.show(mainPanel,"Form");
        }
        else if(buttonString.equals("search")){
            reset();
            card.last(mainPanel);
        }
        else if(buttonString.equals("quit")){
            System.exit(0);
        }

        else if(buttonString.equals("Add")){
            if(isBook){
                String ProductID = productId.getText();
                String Description = description.getText();
                String Price = price.getText();
                String Year = year.getText();
                String Author = authors.getText();
                String Publisher = publisher.getText();

                try{
                    Book book = new Book(ProductID, Description, Price, Year, Author, Publisher);
                    productList.add(book);
                    keywordMap = EStoreSearch.addKeysToHashMap(book, keywordMap, hashTableIndex);
                    hashTableIndex++;
                    System.out.println("Book was succesfully added!");
                    System.out.println(book);
                    reset();
                }
                catch(Exception e){
                    outputField.append(e.getMessage());
                }
            } else {
                String ProductID = productId.getText();
                String Description = description.getText();
                String Price = price.getText();
                String Year = year.getText();
                String Maker = authors.getText();
                try{
                    Electronics electronic = new Electronics(ProductID, Description, Price, Year, Maker);
                    productList.add(electronic);
                    keywordMap = EStoreSearch.addKeysToHashMap(electronic, keywordMap, hashTableIndex);
                    hashTableIndex++;
                    System.out.println("Electronics were succesfully added!");
                    System.out.println(electronic);
                    reset();
                }
                catch(Exception e){
                    outputField.append(e.getMessage());
                }
            }
        }
        else if(buttonString.equals("Search")){
            String idStr = searchProductId.getText();
            String description = keywords.getText();
            String startYr = startYear.getText();
            String endYr = endYear.getText();
            String timePeriod = "";
            if( (!(startYr.equals(""))) && (!(endYr.equals(""))) ){
                timePeriod = startYr+"-"+endYr;
            }
            else if( !(startYr.equals("")) ){
                timePeriod = startYr+"-";
            }
            else if( !(endYr).equals("")){
                timePeriod = "-"+endYr;
            }
            else{
                timePeriod = "";
            }
            EStoreSearch EStore = new EStoreSearch(productList, keywordMap);
            // Search function in EStore class with helper
            String ret = EStore.hashSearch(idStr, description, timePeriod);
            searchField.append(ret);
        }
        else if(buttonString.equals("Reset")){
            reset();
        }
    }

}
