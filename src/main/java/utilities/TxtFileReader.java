package utilities;

import exceptions.FileException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TxtFileReader {

    /**
     * Reads the contents of a file and returns a list of strings, where each string
     * represents a line from the file.
     *
     * @param filename The name of the file to be read.
     * @return A list of strings representing the lines of the file.
     * @throws FileException If the file does not exist, the filename is an empty string,
     *                       or there is an error reading the file.
     */
    public static List<String> readInputFile(String filename) {
        if (filename.isEmpty()) {
            throw new FileException("File does not exist or filename is an empty string");
        }

        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new FileException("Error reading file: " + filename);
        }
        return lines;
    }
}
