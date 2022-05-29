import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

/**
 * Book Database Manager Class - Loads books/authors into lists, joins relationships
 * @author Nick
 */
public class BookDatabaseManager {

    private List<Book> bookList = new LinkedList<>();
    private List<Author> authorList = new LinkedList<>();
    private HashMap<String, String> library = new HashMap<>();

    /**
     * Constructor for BookDatabaseManager Class
     * Loads books, authors and relationships when creating the object
     */
    public BookDatabaseManager() {
        loadBooks();
        loadAuthors();
        loadDatabase();
    }

    /**
     * Returns book list of database
     * @return book list
     */
    public List<Book> getBookList() {
        return bookList;
    }

    /**
     * Returns author list of database
     * @return author list
     */
    public List<Author> getAuthorList() {
        return authorList;
    }

    /**
     * Adds new book to database for an existing author
     * @param book book values to be added
     */
    public void addNewBook(Book book){
        try(
                Connection connection = getConnection();
        ) {
            String sqlQuery = "INSERT INTO " + BooksDatabaseSQL.BOOK_TABLE_NAME +
                    " VALUES (?,?,?,?)";

            String SQLAuthorISBN = "INSERT into authorISBN (authorID, isbn)" +
                    "Values (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setInt(3, book.getEditionNumber());
            preparedStatement.setString(4, book.getCopyright());
            preparedStatement.execute();

            PreparedStatement preparedStatement2 = connection.prepareStatement(SQLAuthorISBN);
            preparedStatement2.setString(2, book.getIsbn());
            for(Author author: book.getAuthorList()) {
                preparedStatement2.setInt(1, author.getAuthorID());
                preparedStatement2.execute();
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            System.out.println(book.getTitle() + " was added to the database.");
        }
    }

    /**
     * Adds new author to database
     * @param author author values to be added
     */
    public void addNewAuthor(Author author){
        try(
                Connection connection = getConnection();
        ) {
            String sqlQuery = "INSERT INTO " + AuthorsDatabaseSQL.AUTHOR_TABLE_NAME +
                    " VALUES (?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setInt(1, author.getAuthorID());
            preparedStatement.setString(2, author.getFirstName());
            preparedStatement.setString(3, author.getLastName());

            ResultSet resultSet = preparedStatement.executeQuery();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            System.out.println(author.getFirstName() + " " + author.getLastName() + " was added to the database.");
        }
    }

    /**
     * Establishes connection to database
     * @return database url, username, password
     * @throws SQLException if error occurs
     */
    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection(BooksDatabaseSQL.DATABASE_URL, BooksDatabaseSQL.USER, BooksDatabaseSQL.PASS);
    }

    /**
     * Loads database, establishes query that links relationships together
     */
    private void loadDatabase(){
        try(
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
        ) {

            String sqlQuery = "SELECT " + BooksDatabaseSQL.BOOK_TABLE_NAME + "." + BooksDatabaseSQL.BOOK_COL_NAME_TITLE + "," +
                    AuthorsDatabaseSQL.AUTHOR_TABLE_NAME + "." + AuthorsDatabaseSQL.AUTHOR_COL_NAME_AUTHORID +
                    " FROM " + BooksDatabaseSQL.BOOK_TABLE_NAME +
                    " JOIN " + AuthorISBNDatabaseSQL.AUTHOR_ISBN_TABLE_NAME +
                    " ON " + BooksDatabaseSQL.BOOK_TABLE_NAME + "." + BooksDatabaseSQL.BOOK_COL_NAME_ISBN + "=" +
                    AuthorISBNDatabaseSQL.AUTHOR_ISBN_TABLE_NAME + "." + AuthorISBNDatabaseSQL.AUTHOR_ISBN_COL_NAME_ISBN +
                    " JOIN " + AuthorsDatabaseSQL.AUTHOR_TABLE_NAME +
                    " ON "  + AuthorsDatabaseSQL.AUTHOR_TABLE_NAME + "." + AuthorsDatabaseSQL.AUTHOR_COL_NAME_AUTHORID + "=" +
                    AuthorISBNDatabaseSQL.AUTHOR_ISBN_TABLE_NAME + "." + AuthorsDatabaseSQL.AUTHOR_COL_NAME_AUTHORID;

            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                String title = resultSet.getString(1);
                String authorID = resultSet.getString(2);
                if (library.containsKey(title)) {
                    library.put(title, library.get(title) + ", " + authorID);
                } else {
                    library.put(title, authorID);
                }
            }
            for (Map.Entry<String, String> entry : library.entrySet()) {
                for (Book book : getBookList()) {
                    if (entry.getKey().equals(book.getTitle())) {
                        for (Author author : getAuthorList()) {
                            if (entry.getValue().contains(Integer.toString(author.getAuthorID()))) {
                                book.addAuthorList(author);
                                author.addBookList(book);
                            }
                        }
                    }
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Loads books from database into bookList
     */
    private void loadBooks(){
        try(
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
        ){
            String sqlQuery = "SELECT * from " + BooksDatabaseSQL.BOOK_TABLE_NAME;
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                bookList.add(
                        new Book(
                                resultSet.getString(BooksDatabaseSQL.BOOK_COL_NAME_ISBN),
                                resultSet.getString(BooksDatabaseSQL.BOOK_COL_NAME_TITLE),
                                resultSet.getInt(BooksDatabaseSQL.BOOK_COL_NAME_EDITION_NUMBER),
                                resultSet.getString(BooksDatabaseSQL.BOOK_COL_NAME_COPYRIGHT)
                        )
                );
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Loads authors from database into authorList
     */
    private void loadAuthors(){
        try(
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
        ){
            String sqlQuery = "SELECT * from " + AuthorsDatabaseSQL.AUTHOR_TABLE_NAME;
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                authorList.add(
                        new Author(
                                resultSet.getInt(AuthorsDatabaseSQL.AUTHOR_COL_NAME_AUTHORID),
                                resultSet.getString(AuthorsDatabaseSQL.AUTHOR_COL_NAME_FIRST_NAME),
                                resultSet.getString(AuthorsDatabaseSQL.AUTHOR_COL_NAME_LAST_NAME)
                        )
                );
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Prints all the books from the database (showing the authors)
     */
    public void printAllBooks(){
        for (Book book : bookList) {
            System.out.println(book.getTitle() + " Edition: " + book.getEditionNumber() + " | ISBN: " + book.getIsbn() + " - Copyright: " + book.getCopyright());
            System.out.print("Authors: ");
            for (Author author : book.getAuthorList()) {
                System.out.println(author.getFirstName() + " " + author.getLastName() + " ");
            }
            System.out.println();
        }
    }

    /**
     * Prints all the authors from the database (showing the books)
     */
    public void printAllAuthors(){
        for (Author author : authorList) {
            System.out.println();
            System.out.println("Author: " + author.getFirstName() + " " + author.getLastName());
            System.out.println("Books: ");
            for (Book book : author.getBookList()) {
                System.out.println(book.getTitle() + " Edition: " + book.getEditionNumber() + " | ISBN: " + book.getIsbn() + " - Copyright: " + book.getCopyright());
            }
        }
    }

    /**
     * Stores terms for Books SQL database
     */
    private static class BooksDatabaseSQL{
        //login info
        static final String DATABASE_URL = "jdbc:mariadb://localhost:3304/books";
        static final String USER = "root";
        static final String PASS = "Password1";

        //book table info
        public static final String BOOK_TABLE_NAME = "titles";
        public static final String BOOK_COL_NAME_ISBN = "isbn";
        public static final String BOOK_COL_NAME_TITLE = "title";
        public static final String BOOK_COL_NAME_EDITION_NUMBER = "editionNumber";
        public static final String BOOK_COL_NAME_COPYRIGHT = "copyright";
    }

    /**
     * Stores terms for Authors SQL database
     */
    private static class AuthorsDatabaseSQL{
        //login info
        static final String DATABASE_URL = "jdbc:mariadb://localhost:3304/books";
        static final String USER = "root";
        static final String PASS = "Password1";

        //author table info
        public static final String AUTHOR_TABLE_NAME = "authors";
        public static final String AUTHOR_COL_NAME_AUTHORID = "authorID";
        public static final String AUTHOR_COL_NAME_FIRST_NAME = "firstName";
        public static final String AUTHOR_COL_NAME_LAST_NAME = "lastName";
    }

    /**
     * Stores terms for AuthorISBN SQL database
     */
    private static class AuthorISBNDatabaseSQL{
        //login info
        static final String DATABASE_URL = "jdbc:mariadb://localhost:3304/books";
        static final String USER = "root";
        static final String PASS = "Password1";

        //author table info
        public static final String AUTHOR_ISBN_TABLE_NAME = "authorisbn";
        public static final String AUTHOR_ISBN_COL_NAME_AUTHORID = "authorID";
        public static final String AUTHOR_ISBN_COL_NAME_ISBN = "isbn";

    }
}
