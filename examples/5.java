import java.util.*;

public class CSVParser {

    public List<Map<String, String>> parse(String csvData, String[] headers) {
        List<Map<String, String>> data = new ArrayList<>();
        String[] rows = csvData.split("\n");

        for (String row : rows) {
            String[] values = row.split(",");
            if (values.length != headers.length) {
                throw new IllegalArgumentException("Row data does not match headers");
            }
            Map<String, String> rowData = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                rowData.put(headers[i], values[i]);
            }
            data.add(rowData);
        }

        return data;
    }
}
