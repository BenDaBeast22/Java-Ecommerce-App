Product EStore README

1) General Problem
- This program is an electronics and books EStore where users can add books and electronics to a
list then perform a search to find the added products.
- When books are added to the list the attributes id, description, price, year, author and publisher must
be provided
- When electronics are added to the list attributes id, description, price, year and maker must be
provided
- The user can perform a search to find the product they are looking for by providing the product id,
keywords in the description and the year of the product they are looking for
- The attributes in the search can be provided in combination with one another or none or not at all
- If no attributes are provided all of the items from both lists will be listed

(2) Assumptions and limitations of Solution
- The GUI's textfields reset every time a user selects a new command or successfully executes an "add" or "search"
command
- Couldn't figure out how to get JUnit tests to work on main
- Had trouble getting JUnit test to work for the search function in EStoreSearch
- I assumed when users skipped over a required field to keep looping and asking for input rather than
exiting the program
- When a user entered a duplicate productID I waited until all other attributes were provided and then
gave an error message that the add was unsuccessful since the item was a duplicate rather than looping
until they entered a unused id
- I assumed that the user would enter an integer for the year
- I assumed a file of size of 10 to be considered empty
- The reason I added such a large margin of error is because when I checked the file length when it was empty
sometimes it was 1 probably due to invisible characters like a newline. I wanted to be safe so I made
the file have to be greater then 10 chars since it isn't even possible to provide valid input into the file
that is less than or equal to 10 characters

(3) User build and Test Instruction (User Guide)
- First unzip the file
- Then compile the program via gradle with "gradle build" instruction
- Then run with gradle run

(4) Test Plan
- The main tests that need to be considered for this program is:
a) Making sure that program is robust with user inputs and gives appropriate error messages when required
- I will perform tests on the App.java and try entering incorrect inputs to make sure my program provides
helpful error messages
- I will test giving different types as input like Strings, Integers and Doubles when they are not expected
and see how my program handles these inputs

b) Adding products to the book list and electronics list
- I will first test under normal circumstances that adding to both lists work by adding a product to the
book list and the electronics list and providing the required attributes
- I expect to recieve no error messages
- Then I will use my search function to verify that the products were added to the lists
- Next I will need to test to make sure that user cannot add a product with the same id to the list
- I will add another book with the same id as one of the previous products and expect an error to stop
this from happening
- I will repeat this test and try the same thing except this time by adding another electronics product

c) Searching for Products
- I will add a bunch of products to the book list and electronics list
- I will test to see if search works with all attributes provided the same as the input that was added
(id, description, year) and expect only one specific item to pop up
- Then I will test the search for how it works when the year is provided in the different methods
(providing 2 years with '-' in between, 1 year, the '-' char before year and the '-' char after)
- Next I will test the description with keywords provided out of order to make sure that unordered
search works
- I will try a search with every attribute empty to see if all products come up
- I will then try a combination of different attributes together to make sure that products that are
found really do possess both of the attributes required

d) Testing the product class
- To test the product class I will create a new product and test the setters, getters and toString method
- I will perform the same tests as with the electronics and book class to make sure my setter methods
work properly with invalid input

e) Testing Hash Search
- To test this functionality I will need to account for the basic cases with one word in the description
where the element is at the not on the list, when it is at the start of the list, when it is at the end of the list,
when the element is somewhere in the middle of the list
- I will also need to perform tests with multiple words in the description to make sure that my program is
taking the intersection of the array lists in the hash map.
- Finally I will have to perform tests with multiple words in the description with the year and productID provided
to make sure that the search works all together


(5) Possible improvements
- Could have done more Junit tests and used more helper functions
- I have way to much of my code in the main file
- To improve on this I should have made many more functions and added them to the book, electronics and
product classes
- If I had done this Junit testing for input would have been much easier and my code would be much cleaner
and organized
- I didn't have time to finish making JUnit tests for my search method and file method
- My code for the yearMatch method is a mess (I rushed it, I'm sure I could have thought of a much better
way to do this)
- I also could have made some of the Junit tests I did a lot more meaningful because I copy pasted a lot for
testing the book, electronics and product class to save time
