import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.regex.*;

public class Extract {

    public static void main(String[] args) {
        System.out.println("Youtube video extractor :-");

        HashMap<String, String> hashMap = extractor("https://www.youtube.com/watch?v=UPOT2tgY9QQ");

        System.out.println(hashMap.keySet());
        for (String key : hashMap.keySet()) {
            System.out.println(key + " " + hashMap.get(key));
            System.out.println("======================================");
        }
    }

    public static HashMap<String, String> extractor(String url) {
        HashMap<String, String> hashMap = new HashMap<String, String>();

        System.out.println(url);
        try {

            URL newUrl = new URL(url);
            URLConnection con = newUrl.openConnection();
            con.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.1 Safari/537.36");
            InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();
            encoding = encoding == null ? "UTF-8" : encoding;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[8192];
            int len = 0;
            while ((len = in.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            String body = new String(baos.toByteArray(), encoding);

            System.out.println("=============================================\nLength of the html body:- "
                    + body.length() + "\n==============================================");

            // grab the video urls using regural expression
            Pattern pattern = Pattern.compile("\\\\u0026url=(.*?)\\\\u0026init");
            Matcher matcher = pattern.matcher(body);
            int count = 0;
            while (matcher.find()) {
                String theGroup = matcher.group().replaceAll("\\\\u0026", "&");
                String decodedGroup = new String();
                try {
                    decodedGroup = java.net.URLDecoder.decode(theGroup, "utf-8");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                // System.out.println("DECODED:- " + decodedGroup);
                // System.out.println("============================");

                Matcher qualityLabel = Pattern.compile("&quality_label=(.*?)&").matcher(decodedGroup);
                if (qualityLabel.find()) {
                    String s = qualityLabel.group().subSequence(15, qualityLabel.group().indexOf("p") + 1).toString();
                    // System.out.println(s);
                    // System.out.println("=======================================");

                    // grabbing url
                    // Matcher urlMatcher =
                    // Pattern.compile("&url=(.*?)(&lsig=(.*?)(%3D{1,2}))").matcher(decodedGroup);
                    Matcher urlMatcher = Pattern.compile("&url=(.*?)(&lsig=(.*?)&)").matcher(decodedGroup);
                    if (urlMatcher.find()) {
                        String grabbedUrl = urlMatcher.group().subSequence(5, urlMatcher.group().length() - 1)
                                .toString();
                        String validUrl = new String();
                        try {
                            validUrl = java.net.URLDecoder.decode(grabbedUrl, "utf-8");
                            // System.out.println("GRABBED:- " + validUrl);
                            // System.out.println("=======================================");

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        Matcher fileType = Pattern.compile("&type=(.*?);").matcher(decodedGroup);
                        if (fileType.find()) {
                            String fileMatcher = fileType.group().subSequence(6, fileType.group().length() - 1)
                                    .toString();
                            // System.out.println("=======================================");
                            // System.out.println("FILEMATCHED:-" + fileMatcher);
                            // System.out.println("=======================================");
                            if (fileMatcher.equals("video/mp4")) {
                                hashMap.put(s, validUrl);
                            }
                        } else {
                            hashMap.put(s, validUrl);
                        }
                    }
                } else {
                    Matcher fileType = Pattern.compile("&type=(.*?);").matcher(decodedGroup);
                    if (fileType.find()) {
                        String fileMatcher = fileType.group().subSequence(6, fileType.group().length() - 1).toString();
                        // System.out.println("=======================================");
                        // System.out.println("FILEMATCHED:-" + fileMatcher);
                        // System.out.println("=======================================");
                        if (fileMatcher.equals("audio/mp4")) {
                            Matcher urlMatcherAudio = Pattern.compile("&url=(.*?)(&lsig=(.*?)&)").matcher(decodedGroup);
                            if (urlMatcherAudio.find()) {
                                String grabbedUrlAudio = urlMatcherAudio.group()
                                        .subSequence(5, urlMatcherAudio.group().length() - 1).toString();
                                String validUrlAudio = new String();
                                try {
                                    validUrlAudio = java.net.URLDecoder.decode(grabbedUrlAudio, "utf-8");
                                    // System.out.println("GRABBED:- " + validUrlAudio);
                                    // System.out.println("=======================================");

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                hashMap.put("audio", validUrlAudio);
                            }
                        }
                    }
                }

                count++;
            }

            System.out.println("==========================================================================");

            System.out.println("total no of links :- " + count);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return hashMap;
    }
}