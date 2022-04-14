package interview;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.net.*;
import com.google.gson.*;

//Help: https://github.com/codingwhite/Leetcode-4/blob/master/src/main/java/com/fishercoder/solutions/_99999RandomQuestions.java

public class TenorGetMovieTitle {
	
	public static void main(String[] args) throws IOException{
        Scanner in = new Scanner(System.in);
        final String fileName = System.getenv("OUTPUT_PATH");
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        String[] res;
        String _substr;
        try {
            _substr = in.nextLine();
        } catch (Exception e) {
            _substr = null;
        }
        
        res = getMovieTitles(_substr);
        for(int res_i=0; res_i < res.length; res_i++) {
            bw.write(String.valueOf(res[res_i]));
            bw.newLine();
        }
        
        bw.close();
    }
	
	public static String[] getMovieTitles(String substr) {
        final String url = "https://jsonmock.hackerrank.com/api/movies/search/?Title=";
        
        List<String> movies = new ArrayList<>();
        try {
            String response = getResponse(url + substr);
            JsonParser parser = new JsonParser();
            JsonElement rootNode = parser.parse(response);

            JsonObject details = rootNode.getAsJsonObject();

            JsonElement totalMovies = details.get("total");
            System.out.println(totalMovies.toString());

            JsonElement totalPages = details.get("total_pages");
            System.out.println(totalPages.toString());

            int currentPage = 0;
            while (currentPage++ < Integer.parseInt(totalPages.toString())) {
                nextPage(movies, currentPage, substr);
            }
            Collections.sort(movies);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] result = new String[movies.size()];
        return movies.toArray(result);
    }


static void nextPage(List<String> movies, int currentPage, String substr) throws Exception {
        final String url = "https://jsonmock.hackerrank.com/api/movies/search/?Title=";
        String response = getResponse(url + substr + "&page=" + currentPage);
        JsonParser parser = new JsonParser();
        JsonElement rootNode = parser.parse(response);

        JsonObject details = rootNode.getAsJsonObject();
        JsonElement data = details.get("data");
        JsonArray jsonArray = data.getAsJsonArray();
        for (JsonElement each : jsonArray) {
            JsonObject titleObject = each.getAsJsonObject();
            String title = titleObject.get("Title").getAsString();
            movies.add(title);
        }
    }

    static String getResponse(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

}


///--SQL
SELECT dept.name, COUNT(emp.id) COUNT_OF_EMPLOYEES_IN_THE_DEPARTMENT
FROM DEPARTMENT dept
LEFT JOIN EMPLOYEE emp 
    ON emp.DEPT_ID = dept.ID
GROUP BY dept.ID, dept.name
ORDER BY COUNT(emp.id) DESC, dept.name
