import hometask.Main;
import hometask.Occurrence;
import hometask.ThreadSource;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class OccurrenceTest {
    private static Logger logger = Logger.getLogger(OccurrenceTest.class);

    private Occurrence occurrence;

    @BeforeClass
    public static void beforeClass() {

    }

    @Before
    public void before() {
        this.occurrence = new Occurrence();
    }

    @Test
    public void testCountThread() throws InterruptedException {
        occurrence.countThreadAdd();
        occurrence.countThreadAdd();
        occurrence.countThreadAdd();
        Assert.assertEquals("Test count threads", 3, occurrence.countThread);
    }

    @Test
    public void testCountThreadMax() throws InterruptedException {
        occurrence.debug = true;
        for (int i = 0; i < occurrence.COUNT_THREAD + 1; i++) {
            occurrence.countThreadAdd();
            logger.info(occurrence.countThread);
        }
        Assert.assertEquals("Test count threads", occurrence.COUNT_THREAD, occurrence.countThread);
    }

    @Test(expected = NullPointerException.class)
    public void testExceptionAll() throws InterruptedException {
        occurrence.getOccurrence(null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testExceptionSources() throws InterruptedException {
        occurrence.getOccurrence(new String[]{""}, new String[]{"asd"}, "res");
    }

    @Test(expected = NullPointerException.class)
    public void testExceptionWords() throws InterruptedException {
        occurrence.getOccurrence(new String[]{"asd"}, new String[]{""}, "res");
    }

    @Test(expected = NullPointerException.class)
    public void testExceptionRes() throws InterruptedException {
        occurrence.getOccurrence(new String[]{"asd"}, new String[]{"asdf"}, "");
    }
}
