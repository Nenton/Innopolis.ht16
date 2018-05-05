package hometask;

import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;

public class OccurrenceWriter {
    final static Logger logger = Logger.getLogger(OccurrenceWriter.class);

    /**
     * Method write all offers in file in one time
     *
     * @param res    Name of file in which write offers
     * @param offers
     * @throws IOException
     */
    public static void write(String res, String offers) throws IOException {
        if (res == null || offers == null) throw new NullPointerException();
        try (FileOutputStream stream = new FileOutputStream(res)) {
            stream.write(offers.getBytes());
        }
    }

    /**
     * Method write offer in file from one source
     *
     * @param res   Name of file in which write offers
     * @param offer
     * @throws IOException
     */
    public static synchronized void writeWithAppend(String res, String offer) throws IOException {
        if (res == null || offer == null) throw new NullPointerException();
        try (FileOutputStream stream = new FileOutputStream(res, true)) {
            stream.write(offer.getBytes());
        }
    }
}
