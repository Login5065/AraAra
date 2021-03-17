package pl.zzpj2021.solid.srp.book.solution;

import java.util.HashMap;
import java.util.Map;

public class Printer extends Book{

    private Map<Integer, String> pages = new HashMap<>();
    private int currentPage = 0;

    public String getCurrentPageContents() {
        return pages.get(currentPage);
    }

    public void turnPage() {
        currentPage++;
    }

    /**
     * Prints the current page.
     */
    public void printCurrentPage() {
        System.out.println(pages.get(currentPage));
    }

    /**
     * Prints all pages
     *
     * @return
     */
    public String printAllPages() {

        StringBuilder allPages = new StringBuilder();
        for (Map.Entry<Integer, String> page : pages.entrySet()) {
            allPages.append(page.getKey()).append(" ").append(page.getValue());
        }
        return allPages.toString();
    }
}
