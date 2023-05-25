package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.LibraryAPIService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class LibraryAPIServiceImpl implements LibraryAPIService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibraryAPIServiceImpl.class);

    private static final int DEFAULT_ID = -1;
    @Override
    public BookImpl getBookByISBN(final String isbn) throws IOException {
        String apiUrl = "https://openlibrary.org/isbn/" + isbn + ".json";
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            // Parse JSON response
            JSONObject jsonObject = new JSONObject(content.toString());

            // Extract book information
            String title = jsonObject.optString("title", "");
            JSONArray languagesArray = jsonObject.optJSONArray("languages");
            JSONArray authorsArray = jsonObject.optJSONArray("authors");
            String language = jsonObject.optString("languages", "");

            // Create a HashMap to store book information
            Map<String, String> bookInfo = new HashMap<>();
            bookInfo.put("ISBN", isbn);
            bookInfo.put("title", title);
            bookInfo.put("author", getAuthorNames(authorsArray));
            bookInfo.put("language", getLanguages(languagesArray));

            BookImpl book = new BookImpl(DEFAULT_ID,bookInfo.get("ISBN"), bookInfo.get("author"), bookInfo.get("title"),bookInfo.get("language"));
            return book;
        } else {
            throw new IOException("Failed to get book information. Response Code: " + responseCode);
        }
    }

    private String getAuthorNames(JSONArray authorsArray) throws JSONException {
        if (authorsArray == null || authorsArray.length() == 0) {
            return "";
        }

        StringBuilder authors = new StringBuilder();
        for (int i = 0; i < authorsArray.length(); i++) {
            JSONObject authorObject = authorsArray.optJSONObject(i);
            String authorKey = authorObject.optString("key", "");
            String authorName = null;
            try {
                authorName = getAuthorName(authorKey);
            } catch (IOException | JSONException e) {
                LOGGER.error("Error retrieving info about: {}",authorKey);
            }

            if (authorName != null && !authorName.isEmpty()) {
                authors.append(authorName).append(", ");
            }
        }
        if (authors.length() > 0) {
            authors.delete(authors.length() - 2, authors.length()); // Remove trailing comma and space
        }

        return authors.toString();
    }

    private String getAuthorName(String authorKey) throws JSONException, IOException {
        String apiUrl = "https://openlibrary.org" + authorKey + ".json";
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONObject jsonObject = new JSONObject(content.toString());
            String authorName = jsonObject.optString("name", "");
            return authorName;
        } else {
            throw new JSONException("Failed to get author name. Response Code: " + responseCode);
        }
    }

    private String getLanguages(JSONArray languagesArray) throws JSONException {
        if (languagesArray == null || languagesArray.length() == 0) {
            return "";
        }


        StringBuilder languages = new StringBuilder();
        for (int i = 0; i < languagesArray.length(); i++) {
            JSONObject languageObject = languagesArray.optJSONObject(i);
            String languageKey = languageObject.optString("key", "");
            String languageName = languageKey.replace("/languages/", "");
            languages.append(languageName).append(", ");
        }
        if (languages.length() > 0) {
            languages.delete(languages.length() - 2, languages.length()); // Remove trailing comma and space
        }

        return languages.toString();
    }


    private String getLanguageName(String languageKey) throws JSONException, IOException {
        String apiUrl = "https://openlibrary.org" + languageKey + ".json";
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONObject jsonObject = new JSONObject(content.toString());
            String languageName = jsonObject.optString("name", "");
            return languageName;
        } else {
            throw new JSONException("Failed to get language name. Response Code: " + responseCode);
        }
    }


}
