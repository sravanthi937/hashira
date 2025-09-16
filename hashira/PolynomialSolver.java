import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class PolynomialSolver {
    static class Point {
        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("data.json"));

            JSONObject keys = (JSONObject) jsonObject.get("keys");
            int n = Integer.parseInt(keys.get("n").toString());
            int k = Integer.parseInt(keys.get("k").toString());

            List<Point> points = new ArrayList<>();

            // read first k entries
            for (int i = 1; i <= k; i++) {
                JSONObject root = (JSONObject) jsonObject.get(String.valueOf(i));
                int base = Integer.parseInt(root.get("base").toString());
                String valueStr = root.get("value").toString();

                long yDecimal = Long.parseLong(valueStr, base);
                double x = i;        // x is just the index
                double y = yDecimal; // store converted y

                points.add(new Point(x, y));
            }

            // Example: assume y = x^2 + c
            double c = calculateConstant(points);
            System.out.println("The constant c = " + c);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double calculateConstant(List<Point> points) {
        Point p1 = points.get(0);
        return p1.y - (p1.x * p1.x);
    }
}
