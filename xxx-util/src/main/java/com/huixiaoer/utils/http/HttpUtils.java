package com.huixiaoer.utils.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Created by zhanglong on 16-4-14.
 */
public class HttpUtils {
	protected static Logger log = LogManager.getLogger(HttpUtils.class);

    public static String formatHtml(String html) {
        if (html == null)
            return null;
        String result = html.replaceAll("\\>\\<", ">\n<");
        return result;
    }

    public static String postForHeader(String targetUrl, String output, String headerFieldName) {
        try {
            HttpURLConnection client = (HttpURLConnection) new URL(targetUrl).openConnection();
            client.setRequestMethod("POST");
            client.setDoOutput(true);

            OutputStream os = null;
            try {
                os = client.getOutputStream();
                os.write(output.getBytes());
                os.flush();
            } finally {
                if (os != null) {
                    os.close();
                }
            }
            if (client.getResponseCode() == 201)
                return client.getHeaderField(headerFieldName);
            return null;

        } catch (Exception ex) {
            return null;
        }
    }

    public static String post(String targetUrl, String output) {
        try {
            HttpURLConnection client = (HttpURLConnection) new URL(targetUrl).openConnection();
            client.setRequestMethod("POST");
            client.setDoOutput(true);

            OutputStream os = null;
            try {
                os = client.getOutputStream();
                os.write(output.getBytes());
                os.flush();
            } finally {
                if (os != null) {
                    os.close();
                }
            }

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                StringBuilder content = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                return content.toString();
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public static String post(String targetUrl, String output, String Encoding) {
        try {
            HttpURLConnection client = (HttpURLConnection) new URL(targetUrl).openConnection();
            client.setRequestMethod("POST");
            client.setDoOutput(true);

            OutputStream os = null;
            try {
                os = client.getOutputStream();
                os.write(output.getBytes(Encoding));
                os.flush();
            } finally {
                if (os != null) {
                    os.close();
                }
            }

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(client.getInputStream(), Encoding));
                StringBuilder content = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                return content.toString();
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public static String get(String url, String charset) {
        log.info(url);
        StringBuilder res = new StringBuilder();
        BufferedReader br = null;
        try {
            URLConnection client = new URL(url).openConnection();
            client.connect();
            br = new BufferedReader(new InputStreamReader(client.getInputStream(), charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                res.append(line.trim() + "\n");
            }
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                if (br != null)
                    br.close();
            } catch (Exception e) {
            }

            return null;
        }
        return res.toString().trim();

    }

    public static String postAObject(String targetUrl, Object obj) {
        log.info(targetUrl);
        try {
            HttpURLConnection client = (HttpURLConnection) new URL(targetUrl).openConnection();
            client.setRequestMethod("POST");
            client.setDoOutput(true);
            client.setRequestProperty("accept", "*/*");
            client.setRequestProperty("Content-type", "application/x-java-serialized-object");
            ObjectOutputStream os = null;
            try {
                os = new ObjectOutputStream(client.getOutputStream());
                os.writeObject(obj);
                os.flush();
            } finally {
                if (os != null) {
                    os.close();
                }
            }
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
                StringBuilder content = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                return content.toString();

            } finally {
                if (reader != null) {
                    reader.close();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Object getAObject(String targetUrl) {
        log.info(targetUrl);
        ObjectInputStream ois = null;
        try {
            HttpURLConnection client = (HttpURLConnection) new URL(targetUrl).openConnection();
            client.setDoInput(true);
            client.setRequestProperty("accept", "*/*");
            client.setRequestProperty("Content-type", "application/x-java-serialized-object");
            client.connect();

            ois = new ObjectInputStream(client.getInputStream());
            return ois.readObject();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return null;
    }

    public static String post(String targetUrl, String output, String Encoding, Map<String, String> properties) {
        try {
            HttpURLConnection client = (HttpURLConnection) new URL(targetUrl).openConnection();
            client.setRequestMethod("POST");
            client.setDoOutput(true);
            if (properties != null) {
                for (Map.Entry<String, String> item : properties.entrySet()) {
                    client.setRequestProperty(item.getKey(), item.getValue());
                }
            }
            OutputStream os = null;
            try {
                os = client.getOutputStream();
                os.write(output.getBytes(Encoding));
                os.flush();
            } finally {
                if (os != null) {
                    os.close();
                }
            }

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(client.getInputStream(), Encoding));
                StringBuilder content = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                String session = client.getHeaderField("Set-Cookie");
                properties.put("Set-Cookie", session);
                return content.toString();
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public static String get(String url, String charset, Map<String, String> properties) {
        log.info(url);
        StringBuilder res = new StringBuilder();
        BufferedReader br = null;
        try {
            URLConnection client = new URL(url).openConnection();
            if (properties != null) {
                for (Map.Entry<String, String> item : properties.entrySet()) {
                    client.setRequestProperty(item.getKey(), item.getValue());
                }
            }
            client.connect();
            br = new BufferedReader(new InputStreamReader(client.getInputStream(), charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                res.append(line.trim() + "\n");
            }
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                if (br != null)
                    br.close();
            } catch (Exception e) {
            }

            return null;
        }
        return res.toString().trim();

    }

    public static void main(String[] argv) {
        String server = " http://restapi.amap.com/v3/geocode/regeo?output=xml&location=116.310003,39.991957&key=b5c0ab5bef028453dfe4eff83eeb316d&radius=100&extensions=base";
        String reportUrl = server;
        String reportContent = HttpUtils.get(reportUrl, "UTf-8");
        System.out.println("\n\n" + reportContent);
    }

}

