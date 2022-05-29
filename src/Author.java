import java.util.LinkedList;
import java.util.List;

/**
 * Author class - Stores author ID, first name, last name, book list
 * @author Nick
 */
public class Author {

    private int authorID;
    private String firstName;
    private String lastName;
    private List<Book> bookList = new LinkedList<>();

    /**
     * Constructor for Author Class
     * @param authorID ID of author
     * @param firstName first name of author
     * @param lastName last name of author
     */
    public Author(int authorID, String firstName, String lastName) {
        this.authorID = authorID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Gets author ID
     * @return returns author ID
     */
    public int getAuthorID() {
        return authorID;
    }

    /**
     * Sets author ID
     * @param authorID sets author ID
     */
    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    /**
     * Gets author's first name
     * @return returns first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets author's first name
     * @param firstName sets first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets author's last name
     * @return returns last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets author's last name
     * @param lastName sets last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets author's booklist
     * @return returns booklist
     */
    public List<Book> getBookList() {
        return bookList;
    }

    /**
     * Sets author's booklist
     * @param bookList returns booklist
     */
    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    /**
     * Adds book to booklist
     * @param book adds book
     */
    public void addBookList(Book book) {
        this.bookList.add(book);
    }
}
