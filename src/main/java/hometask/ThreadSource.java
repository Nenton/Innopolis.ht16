package hometask;

import org.apache.log4j.Logger;

import java.io.*;

public class ThreadSource extends Thread {
    private final static String SOURCE_FILE = "\\w+[.]+\\w{3,4}";
    private final static String SOURCE_WEB = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w.-]*)*/?$";
    private final static String SOURCE_FTP = "((ftp):\\/\\/)+(\\w+:{0,1}\\w*@)?(\\S+)(:[0-9]+)?(\\/|\\/([\\w#!:.?+=&%@!\\-\\/]))?";
    private final static String PARSE_OFFERS = "(?<=[!?.])";
    private final static int SIZE_WRITE_MB = 10;

    private String res;
    private String source;
    private String[] words;
    private Runnable runnable;
    private StringBuilder builder = new StringBuilder();

    private static Logger logger = Logger.getLogger(ThreadSource.class);

    /**
     * Method init thread with source and words
     *
     * @param source name of source
     * @param words  array of words
     */
    public void initThread(String res, String source, String[] words, Runnable run) {
        if (source == null || words == null || source.isEmpty()) throw new NullPointerException();
        this.res = res;
        this.source = source;
        this.words = words;
        this.runnable = run;
    }


    // for testing
    public void setBuilder(StringBuilder builder) {
        this.builder = builder;
    }

    /**
     * Method work parse offers in file
     */
    @Override
    public synchronized void run() {
        try {
            startSearch();
            runnable.run();
        } catch (Exception e) {
            logger.warn("Error", e);
        }
    }

    /**
     * Method return current enum of source
     */
    public Source choiceSource() {
        if (source.matches(SOURCE_FILE)) return Source.FILE;
        if (source.matches(SOURCE_WEB))
            return Source.WEB;
        if (source.matches(SOURCE_FTP)) return Source.FTP;
        return Source.DEFAULT;
    }

    /**
     * Method start parse offers depending on source type
     */
    public void startSearch() throws Exception {
        switch (choiceSource()) {
            case FILE:
                offersInText();
//                proposalInText();
                break;
            case WEB:
                break;
            case FTP:
                break;
            case DEFAULT:
                offersInText();
//                proposalInText();
                break;
            default:
                throw new Exception("Parse source name exception");
        }
    }

    /**
     * Method parse file. Buffer 1_000_000 symbols
     */
    public void offersInText() throws IOException {
        String lastStr = "";
        byte[] array = new byte[1_000_000];
        try (FileInputStream fin = new FileInputStream(source)) {
            while (fin.available() > 0) {
                fin.read(array);
                String[] split = new String(array).split(PARSE_OFFERS);
                split[0] += lastStr;
                for (int i = 0; i < split.length - 1; i++) {
                    findOffer(split[i]);
                }
                lastStr = split[split.length - 1];
            }
        }
        findOffer(lastStr);
        checkSize(true);
    }

    /**
     * Method check offer contain words if true write offer in file
     */

    public boolean findOffer(String offer) throws IOException {
        for (String word : words) {
            if (offer.contains(word)) {
                builder.append(offer.trim());
                checkSize(false);
                return true;
            }
        }
        return false;
    }

    /**
     * Method check found offers on size. And if it more SIZE_WRITE_MB write to file
     *
     * @param exit true if last offer in file
     */
    public boolean checkSize(boolean exit) throws IOException {
        if (builder.length() / (1024 * 1024) >= SIZE_WRITE_MB || exit) {
            OccurrenceWriter.writeWithAppend(res, builder.toString());
            builder.setLength(0);
            return true;
        }
        return false;
    }
}
