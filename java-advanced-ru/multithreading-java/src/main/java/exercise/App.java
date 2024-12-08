package exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

class App {
    private static final Logger LOGGER = Logger.getLogger("AppLogger");

    // BEGIN
    public static Map<String, Integer> getMinMax(int[] numbers) {
        MaxThread maxThread = new MaxThread(numbers);
        MinThread minThread = new MinThread(numbers);

        LOGGER.info("Thread MaxThread started");
        maxThread.start();
        LOGGER.info("Thread MinThread started");
        minThread.start();

        try {
            maxThread.join();
            LOGGER.info("Thread MaxThread finished");
            minThread.join();
            LOGGER.info("Thread MinThread finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Map<String, Integer> result = new HashMap<>();
        result.put("max", maxThread.getMax());
        result.put("min", minThread.getMin());

        return result;
    }
    // END
}
