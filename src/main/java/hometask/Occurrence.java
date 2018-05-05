package hometask;

public class Occurrence implements Occurencable {
    public final int COUNT_THREAD = 8;
    public int countThread = 0;

    public boolean debug = false;

    @Override
    public void getOccurrence(String[] sources, String[] words, String res) throws InterruptedException {
        if (sources == null || words == null || res == null || res.trim().isEmpty()) {
            throw new NullPointerException();
        }

        for (String source : sources) {
            if (source.isEmpty()) {
                throw new NullPointerException();
            }
        }

        for (String word : words) {
            if (word.isEmpty()) {
                throw new NullPointerException();
            }
        }
        for (String source : sources) {
            ThreadSource thread = new ThreadSource();
            thread.initThread(res, source, words, this::countThreadCompleted);
            countThreadAdd();
            thread.start();
        }
    }

    public synchronized void countThreadAdd() throws InterruptedException {
        while (countThread >= COUNT_THREAD) {
            if (debug) {
                return;
            }
            wait();
        }
        countThread++;
    }

    public synchronized void countThreadCompleted() {
        countThread--;
        notifyAll();
    }

    public synchronized void exitProg() throws InterruptedException {
        while (countThread != 0) {
            wait();
        }
    }
}
