package fathom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Task {

    /*
     * Complete the function below.
     * Base url: https://jsonmock.hackerrank.com/api/movies/search/?Title=
     */
    public static void main(String[] args) {
        System.out.println(Arrays.equals(new String[]{"a","b"}, new String[]{"a","b"}));
//        System.out.println(Arrays.toString(getMovieTitles("spiderman")));
    }

    static String[] getMovieTitles(String substr) {
        StringBuilder result = new StringBuilder();
        getTitles(substr, result);
        int pages;
        if ((pages = Integer.parseInt(String.valueOf(
                result.toString().split("\"total_pages\":")[1].charAt(0)
        ))) > 1) {
            for (int i = 2; i <= pages; i++) {
                substr += "&page=" + i;
                getTitles(substr, result);
            }
        }
        String[] arr1 = result.toString().split("\\{\"page\"");
        String[][] arr2 = new String[arr1.length][];
        for (int i = 0; i < arr1.length; i++) {
            arr2[i] = arr1[i].split("\"Title\":\"");
        }
        List<String> res = new ArrayList<>();
        for (int i = 0; i < arr2.length; i++) {
            for (int j = 1; j < arr2[i].length; j++) {
                res.add(arr2[i][j].split("\"")[0]);
            }
        }
        Collections.sort(res);

        String[] list = new String[res.size()];
        int count = 0;
        for (String s : res) {
            list[count] = s;
            count++;
        }

        return list;
    }

    private static void getTitles(String substr, StringBuilder result) {
        URL url;
        try {
            url = new URL("https://jsonmock.hackerrank.com/api/movies/search/?Title=" + substr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
