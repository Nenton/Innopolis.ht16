package hometask;

import java.io.IOException;

public interface Occurencable {
    /**
     * @param source Array of sources for parsing
     * @param words  Array of words which want to search in sources
     * @param res    Name of file in which write found offers
     * @throws Exception
     */
    void getOccurrence(String[] source, String[] words, String res) throws IOException, InterruptedException;
}