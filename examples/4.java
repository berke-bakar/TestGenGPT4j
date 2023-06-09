import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebPageFetcher {
    private static final String USER_AGENT = "Mozilla/5.0";

    public String getPageContent(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
            content.append("\n");
        }

        in.close();
        connection.disconnect();

        return content.toString();
    }

    public int countLines(String content) {
        String[] lines = content.split("\n");
        return lines.length;
    }
}
