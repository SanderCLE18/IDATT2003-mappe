package idi.gruppe07.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**Parses JSON files into usable objects.
 * */
public class JsonParser {
  private final JSONObject jsonObject;

  /**Constructor.
   * Takes in a path to a file and creates a new JSON object if possible.
   *
   * @param is The path to the JSON file. */
  public JsonParser(InputStream is) throws IOException {
    String content = new String(is.readAllBytes());
    jsonObject = new JSONObject(content);
  }

  /**
   * Returns the entire JSON object.
   *
   * @return the stored JSON object.
   * */
  public JSONObject getJsonObject() {
    return jsonObject;
  }

  /**Returns a random news article.
   *
   * @return a random entry in a specified category.*/
  public JSONObject getRandomArticle(String category) {
    JSONArray jsonArray = jsonObject.getJSONArray(category);
    int randomIndex = (int) (Math.random() * jsonArray.length());

    return jsonArray.getJSONObject(randomIndex);
  }

}
