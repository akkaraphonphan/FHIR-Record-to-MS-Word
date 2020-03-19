package swing;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {
    // Based on answer by Faij Ahmad at https://community.apigee.com/questions/52082/how-to-retrive-the-json-data-using-java.html

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) {
        try {
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

            StringWriter writer = new StringWriter();
            IOUtils.copy(is, writer, "UTF-8");
            String jsonText = writer.toString();
            System.out.println(jsonText);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } catch (IOException e) {
            System.out.println("IOException json");
            System.out.println(e.getMessage());
            return null;
        } catch (JSONException e) {
            System.out.println("JSONException json");
            System.out.println(e.getMessage());
            return null;
        }
//        finally {
//            is.close();
//        }
    }
}