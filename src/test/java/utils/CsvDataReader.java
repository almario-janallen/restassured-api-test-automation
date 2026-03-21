package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for reading CSV test data files from src/test/resources/testdata/.
 *
 * Usage:
 *   Object[][] data = CsvDataReader.read("testdata/create_user.csv");
 *
 * Rules:
 *   - First row is always treated as a header and skipped
 *   - Empty lines are skipped automatically
 *   - Each row is returned as a String[] inside the Object[][] array
 *   - Files must be on the classpath (i.e. inside src/test/resources/)
 */
public class CsvDataReader {

    private static final String DELIMITER = ",";

    // Private constructor — this is a static utility class, not meant to be instantiated
    private CsvDataReader() {}

    /**
     * Reads a CSV file from the classpath and returns its rows as a 2D Object array
     * compatible with TestNG @DataProvider methods.
     *
     * @param classpathPath  path relative to src/test/resources/
     *                       e.g. "testdata/create_user.csv"
     * @return Object[][] where each row is a String[] of column values
     */
    public static Object[][] read(String classpathPath) {
        List<String[]> rows = new ArrayList<>();

        try {
            InputStream inputStream = getFileFromClasspath(classpathPath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            boolean isFirstRow = true;

            while ((line = reader.readLine()) != null) {

                // Skip the header row
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }

                // Skip blank lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                rows.add(line.split(DELIMITER, -1));
            }

            reader.close();

        } catch (Exception e) {
            throw new RuntimeException(
                "Failed to read CSV file from classpath: " + classpathPath, e
            );
        }

        return toObjectArray(rows);
    }

    /**
     * Loads the file as an InputStream from the classpath.
     * Throws a clear RuntimeException if the file is not found,
     * rather than a silent NullPointerException downstream.
     */
    private static InputStream getFileFromClasspath(String path) {
        InputStream stream = CsvDataReader.class
            .getClassLoader()
            .getResourceAsStream(path);

        if (stream == null) {
            throw new RuntimeException(
                "CSV file not found on classpath: " + path +
                "\nEnsure the file exists under src/test/resources/" + path
            );
        }

        return stream;
    }

    /**
     * Converts a List of String arrays into the Object[][] format
     * that TestNG @DataProvider methods require.
     */
    private static Object[][] toObjectArray(List<String[]> rows) {
        Object[][] result = new Object[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            result[i] = rows.get(i);
        }
        return result;
    }
}