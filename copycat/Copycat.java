package copycat;

import java.io.IOException;
import java.util.List;

/**
 * Copycat
 */

public class Copycat {
    String targetUrl = null;
    String analyzeLink = "https://mate09.y2mate.com/analyze/ajax";
    String audioLink = "https://mate09.y2mate.com/convert";

    Copycat(String url) {
        this.targetUrl = url;
    }

    private String audio() {
        String outputUrl = null;
        String genericId = null;
        String videoId = null;
        String[] analyzeLink = analyzeLink();
        if (analyzeLink != null) {
            genericId = analyzeLink[0];
            videoId = analyzeLink[1];
            System.out.println(genericId + " " + videoId);

            return outputUrl;
        } else {
            return null;
        }
    }

    private String[] analyzeLink() {
        String[] output = new String[2];
        String charset = "UTF-8";
        try {
            MultipartUtility multipart = new MultipartUtility(this.analyzeLink, charset);

            multipart.addHeaderField("User-Agent", "copycat");

            multipart.addFormField("url", this.targetUrl);
            multipart.addFormField("ajax", "1");

            List<String> response = multipart.finish();

            System.out.println("SERVER REPLIED:");
            String tempResponse = null;
            for (String line : response) {
                tempResponse += line;
            }

            output[0] = tempResponse.substring(tempResponse.indexOf(", _id: '") + 8,
                    tempResponse.indexOf(", v_id: '") - 1);
            output[1] = tempResponse.substring(tempResponse.indexOf(", v_id: '") + 9,
                    tempResponse.indexOf("', ajax: 1, token:"));

        } catch (IOException ex) {
            System.err.println(ex);
        }
        return output;
    }

    public static void main(String[] args) {
        Copycat cc = new Copycat("https://www.youtube.com/watch?v=rfmNAXEJbDE");
        System.out.println(cc.audio());
    }

}