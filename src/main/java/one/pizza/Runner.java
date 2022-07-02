package one.pizza;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;

public class Runner {
    private static final Logger LOG = Logger.getLogger(Runner.class.getName());
    private static final String BASE_INPUT_PATH = "src/resource/input/";
    private static final String BASE_OUTPUT_PATH = "src/resource/output/";
    private static final String DELIMITER = "#";
    private static final List<String> INPUT_FILES = List.of(
            "a_an_example.in.txt"
            ,
            "b_basic.in.txt"
            ,
            "c_coarse.in.txt"
            ,
            "d_difficult.in.txt"
            ,
            "e_elaborate.in.txt"
    );

    public static void main(String[] args) {
        Analizer analizer = new EagerAlgorithmAnalizer();
        for (String file : INPUT_FILES) {
            System.out.println("\n########### - processing the file " + BASE_INPUT_PATH + file + " - ############");
            String result = readFile(BASE_INPUT_PATH + file);
            List<Client> clients = initClientsFromFile(result);
//            System.out.println(clients);
            long startTime = System.nanoTime();
            Set<String> output = analizer.computeIngredientsOptimum(clients);
            long stopTime = System.nanoTime();
            System.out.println(output);
            long executionTime = stopTime - startTime;
            System.out.println("Method execution time: " + executionTime);
            createOutputFile(output, file);
        }
    }

    private static void createOutputFile(Set<String> output, String file) {
        try ( FileWriter myWriter = new FileWriter(BASE_OUTPUT_PATH + file)){
            File myObj = new File(BASE_OUTPUT_PATH + file);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            }
            myWriter.write(output.size() + " " + output.toString().replaceAll("\\p{Punct}", ""));
        } catch (IOException e) {
            LOG.severe(String.format("An error occurred during creating the file %s.", file));
        }
    }

    private static List<Client> initClientsFromFile(String result) {
        String[] resultList = result.split(DELIMITER);
        List<Client> clients = new LinkedList<>();
        int counter = -1;
        Set<String> likedIngredients;
        Set<String> dislikedIngredients;
        Client client = new Client();
        for (String line : resultList) {
            if (counter < 0) {
                counter++;
                continue; // skip first integer - amount of clients
            }
            if (counter % 2 == 0) { // add likedIngredients
                likedIngredients = new LinkedHashSet<>(Arrays.asList(line.substring(2).split(" "))); // remove first digit
                client.setLikedIngredients(likedIngredients);
            } else {// add dislikedIngredients
                if (line.length() != 1) { // ignore empty lines with 0
                    dislikedIngredients = new LinkedHashSet<>(Arrays.asList(line.substring(2).split(" "))); // remove first digit
                    client.setDislikedIngredients(dislikedIngredients);
                }
                clients.add(client);
                client = new Client();
            }
            counter++;
        }
        return clients;
    }

    private static String readFile(String file) {
        StringBuilder sb = new StringBuilder();
        try (Scanner myReader = new Scanner(new File(file))) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                sb.append(data).append(DELIMITER);
            }
        } catch (FileNotFoundException e) {
            LOG.severe(String.format("An error occurred during reading the file %s", file));
        }
        return sb.toString();
    }
}
