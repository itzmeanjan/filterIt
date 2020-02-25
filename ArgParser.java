import java.io.File;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// helps in parsing command line arguments
// passed while invoking program
class ArgParser {
    private String[] args; // arguments passed while invoking program

    ArgParser(String[] args) {
        this.args = args;
    }

    // converts cmd args into a string, seperated by white space
    String join() {
        return String.join(" ", args);
    }

    // extracts those orders of which different
    // filters to be applied
    int[] getOrders(int mFrom) {
        Pattern pattern = Pattern.compile("\\s-o\\s([\\d\\s]+)");
        Matcher matcher = pattern.matcher(join());
        if (matcher.find(mFrom)) {
            return Arrays.asList(matcher.group(1).split("\\s")).stream().mapToInt(s -> Integer.parseInt(s)).toArray();
        }
        return null;
    }

    // extracts target image name, on which filters are
    // supposed to be applied
    String getTargetImage() {
        Pattern pattern = Pattern.compile("^([^\\s]+)");
        Matcher matcher = pattern.matcher(join());
        if (matcher.find())
            try {
                File file = new File(matcher.group(1));
                if (file.exists() && (file.getPath().endsWith(".jpg") || file.getPath().endsWith(".png")))
                    return file.getPath();
                return "";
            } catch (Exception e) {
                System.err.println(e);
                return "";
            }
        return "";
    }

    public static void main(String[] args) {
        ArgParser argParser = new ArgParser(args);
        String targetP = argParser.getTargetImage();
        System.out.println(targetP);
        int[] orders = argParser.getOrders(targetP.length());
        if (orders != null)
            for (int i : orders) {
                System.out.println(i);
            }
    }
}