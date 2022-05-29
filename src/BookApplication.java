import java.util.Scanner;

/**
 * Book Application Class - main method that prompts the user to select various options for the database
 * @author Nick
 */
public class BookApplication {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        BookDatabaseManager bookDatabaseManager = new BookDatabaseManager();

        int choice;

        while (true){
            System.out.println("\nWelcome to Nick's Library!");
            System.out.println("1) Print all the books from the database (showing the authors)");
            System.out.println("2) Print all the authors from the database (showing the books)");
            System.out.println("3) Add a book to the database for an existing author");
            System.out.println("4) Add a new author");
            System.out.println("5) Quit application");
            System.out.println();
            System.out.print("Enter choice: ");

            choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {
                bookDatabaseManager.printAllBooks();
            }
            else if (choice == 2){
                bookDatabaseManager.printAllAuthors();
            }
            else if (choice == 3){
                System.out.print("Enter ISBN (10 numbers): ");
                String isbn = input.nextLine();

                System.out.print("Enter title: ");
                String title = input.nextLine();

                System.out.print("Enter edition number: ");
                int number = input.nextInt();

                System.out.print("Enter year: ");
                String year = input.next();
                Book newBook = new Book(isbn, title, number, year);

                System.out.print("Enter author ID: ");
                int authorID = input.nextInt();
                Author author = new Author(authorID, "", "");
                newBook.getAuthorList().add(author);
                bookDatabaseManager.addNewBook(newBook);
            }
            else if (choice == 4){
                System.out.print("Enter author ID: ");
                int authorID = input.nextInt();
                input.nextLine();

                System.out.print("Enter first name: ");
                String firstName = input.nextLine();

                System.out.print("Enter last name: ");
                String lastName = input.nextLine();

                Author newAuthor = new Author(authorID, firstName, lastName);
                bookDatabaseManager.addNewAuthor(newAuthor);
            }
            else if (choice == 5){
                break;
            }
            else {
                System.out.println("Incorrect number entered");
            }
        }
    }
}
