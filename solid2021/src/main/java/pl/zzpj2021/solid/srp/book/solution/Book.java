package pl.zzpj2021.solid.srp.book.solution;

import java.util.HashMap;
import java.util.Map;

public class Book {

    private String libraryRoomName;
    private String rowLocator;
    private int indexOnShelf;

    public Printer printer;

    public String getTitle() {
        return "A Great Book";
    }

    public String getAuthor() {
        return "John Doe";
    }

    /**
     * Gives the library name
     *
     * @return
     */
    public String libraryRoomName() {
        return libraryRoomName;
    }

    /**
     * Gives the row location of the book.
     *
     * @return
     */
    public String getLocationRowLocator() {
        return rowLocator;
    }

    /**
     * Gives the number from shelf.
     *
     * @return
     */
    public int getIndexOnShelf() {
        return indexOnShelf;
    }
}
