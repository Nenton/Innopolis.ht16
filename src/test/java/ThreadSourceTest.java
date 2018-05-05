import hometask.Source;
import hometask.ThreadSource;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class ThreadSourceTest {

    private static Logger logger = Logger.getLogger(ThreadSourceTest.class);
    private ThreadSource threadSource;

    @BeforeClass
    public static void beforeClass() {

    }

    @Before
    public void before() {
        threadSource = new ThreadSource();
    }

    @Test
    public void testBackSignal() {
        Runnable runnable = Mockito.mock(Runnable.class);
        threadSource.initThread("res", "src/test/resources/test.txt", new String[]{"word"}, runnable);
        threadSource.run();
        Mockito.verify(runnable, Mockito.times(1)).run();
    }

    @Test
    public void testChooseFile() {
        threadSource.initThread("res", "test.txt", new String[]{"word"}, null);
        Assert.assertEquals(threadSource.choiceSource(), Source.FILE);
    }

    @Test
    public void testChooseDefault() {
        threadSource.initThread("res", "src/test/resources/test.txt", new String[]{"word"}, null);
        Assert.assertEquals(threadSource.choiceSource(), Source.DEFAULT);
    }

    @Test
    public void testChooseHttp() {
        threadSource.initThread("res", "http://hh.ru", new String[]{"word"}, null);
        Assert.assertEquals(threadSource.choiceSource(), Source.WEB);
    }

    @Test
    public void testChooseFtp() {
        threadSource.initThread("res", "ftp://ftp.idsoftware.com/idstuff/", new String[]{"word"}, null);
        Assert.assertEquals(threadSource.choiceSource(), Source.FTP);
    }

    @Test
    public void testFindOfferTrue() throws IOException {
        String word = "word";
        threadSource.initThread("res", "asd", new String[]{word}, null);
        Assert.assertTrue(threadSource.findOffer(word));
    }

    @Test
    public void testFindOfferFalse() throws IOException {
        String word = "word";
        String offer = "Word";
        threadSource.initThread("res", "asd", new String[]{word}, null);
        Assert.assertFalse(threadSource.findOffer(offer));
    }

    @Test
    public void testCheckSizeFalseSize() throws IOException {
        StringBuilder mock = new StringBuilder();
        mock.setLength(1_000_000);
        threadSource.setBuilder(mock);
        Assert.assertFalse(threadSource.checkSize(false));
    }

    @Test
    public void testCheckSizeTrueSize() throws IOException {
        threadSource.initThread("res", "asd", new String[]{"word"}, null);
        StringBuilder mock = new StringBuilder("a");
//        mock.setLength(1024 * 1024 + 1);
        for (int i = 0; i < 1024 * 1024 * 10 + 1; i++) {
            mock.append("a");
        }
        threadSource.setBuilder(mock);
        logger.info(mock.length() / (1024 * 1024));
        logger.info(10);
        Assert.assertTrue(threadSource.checkSize(false));
    }

    @Test
    public void testCheckSizeTrueExit() throws IOException {
        threadSource.initThread("res", "asd", new String[]{"word"}, null);
        StringBuilder mock = new StringBuilder("a");
        threadSource.setBuilder(mock);
        Assert.assertTrue(threadSource.checkSize(true));
    }
}
