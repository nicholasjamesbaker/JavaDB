import java.util.LinkedList;
import java.util.List;

/**
 * Book class - Stores ISBN, Title, Edition Number, Copyright, Author List
 * @author Nick
 */
public class Book {

    private String isbn;
    private String title;
    private int editionNumber;
    private String copyright;
    private List<Author> authorList = new LinkedList<>();

    /**
     * Constructor for Book Class
     * @param isbn book isbn
     * @param title book title
     * @param editionNumber book edition number
     * @param copyright book copyright
     */
    public Book(String isbn, String title, int editionNumber, String copyright) {
        this.isbn = isbn;
        this.title = title;
        this.editionNumber = editionNumber;
        this.copyright = copyright;
    }

    /**
     * Gets book ISBN
     * @return returns ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets book ISBN
     * @param isbn sets book ISBN
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets book title
     * @return returns book title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets book title
     * @param title sets book title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets book edition title
     * @return returns book edition title
     */
    public int getEditionNumber() {
        return editionNumber;
    }

    /**
     * Sets book edition number
     * @param editionNumber sets book edition number
     */
    public void setEditionNumber(int editionNumber) {
        this.editionNumber = editionNumber;
    }

    /**
     * Gets book copyright
     * @return returns book copyright
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * Sets book copyright
     * @param copyright returns book copyright
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /**
     * Gets author list
     * @return returns author list
     */
    public List<Author> getAuthorList() {
        return authorList;
    }

    /**
     * Sets author list
     * @param authorList sets author list
     */
    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    /**
     * Adds author to author list
     * @param author adds author
     */
    public void addAuthorList(Author author) {
        this.authorList.add(author);
    }
}
