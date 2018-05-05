package hometask;

import org.apache.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    final static Logger loggerRoot = Logger.getLogger(Main.class);
    public static void main(String[] args) throws Exception {

        List<Path> mySource = Files.walk(Paths.get("VerySimple"))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());

        String[] source = new String[mySource.size()];
        for (int i = 0; i < mySource.size(); i++) {
            source[i] = mySource.get(i).toString();
        }

        String[] verySimpleWords = {"starter", "ffdf", "wfrrf", "cdcd", "dc"};
//        String[] simpleWords = {"sdgffd", "ffdf", "wfrrf", "cdcd","dc"};
//        String[] mediumWords = {"starter", "ffdf", "wfrrf", "cdcd","dc"};

        Occurrence occurrence = new Occurrence();
        long start = System.currentTimeMillis();

        occurrence.getOccurrence(source, verySimpleWords, "apa.txt");
//        occurrence.getOccurrence(source, simpleWords, "apa.txt");
//        occurrence.getOccurrence(source, mediumWords, "apa.txt");

        occurrence.exitProg();
        loggerRoot.info("Occurrences completed from " + (System.currentTimeMillis() - start));
    }
}
