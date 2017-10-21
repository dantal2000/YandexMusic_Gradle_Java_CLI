/*
 * The MIT License
 *
 * Copyright 2017 dantal.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package dantal.utils.network;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import dantal.locale.utils.StringTaker;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 *
 * @author dantal
 */
public class MusicLoader {

    public static File loadMusic(int id) throws IOException {
        String result_1 = make_1(getU1(id));

        //System.out.println("Stage 1 competed");
        System.out.print(1 + StringTaker.getString("StageCompleted"));
        System.out.println(result_1);
        System.out.println();

        String result_2 = make_2(result_1);

        //System.out.println("Stage 2 competed");
        System.out.print(2 + StringTaker.getString("StageCompleted"));
        System.out.println(result_2);
        System.out.println();

        String result_3 = make_1(getU2(result_2));

        //System.out.println("Stage 3 competed");
        System.out.print(3 + StringTaker.getString("StageCompleted"));
        System.out.println(result_3);
        System.out.println();

        String result_4 = getU3(result_3, id);

        //System.out.println("Stage 4 competed");
        System.out.print(4 + StringTaker.getString("StageCompleted"));
        System.out.println(result_4);
        System.out.println();

        return make_3(result_4, id);
    }

    private static File make_3(String url, int id) throws IOException {
        File output = seeForExistFile(String.valueOf(id), "mp3");

        HttpClient httpClient = getHttpClient();
        RequestBuilder builder = RequestBuilder.get().setUri(url);
        HttpUriRequest request = builder.build();
        HttpResponse response = httpClient.execute(request);

        try (FileOutputStream outputStream = new FileOutputStream(output)) {
            response.getEntity().writeTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    private static File seeForExistFile(String name, String ext) {
        File file = new File(name + "." + ext);
        int count = 0;
        while (file.exists()) {
            file = new File(name + "_" + (count++) + "." + ext);
        }
        return file;
    }

    private static String make_2(String json) {
        JSONObject j = new JSONObject(json);
        return j.getString("src");
    }

    private static String make_1(String url) throws IOException {
        HttpClient httpClient = getHttpClient();
        RequestBuilder builder = RequestBuilder.get().setUri(url);
        builder = builder.addHeader("X-Retpath-Y", "https://music.yandex.ru/");
        HttpUriRequest request = builder.build();
        HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity());
    }

    private static String getU1(int id) {
        return "https://music.yandex.ru/api/v2.1/handlers/track/" + id + "/track/download/m?hq=1";
    }

    private static String getU2(String src) {
        return src + "&format=json&external-domain=music.yandex.ru&overembed=no&__t=" + System.currentTimeMillis();
    }

    private static String getU3(String json, int id) {
        JSONObject j = new JSONObject(json);

        String s, ts, path, host;
        s = j.getString("s");
        ts = j.getString("ts");
        path = j.getString("path");
        host = j.getString("host");

        String salt = "XGRlBW9FXlekgbPrRHuSiA";

        String data = salt + path.substring(1, path.length()) + s;
        String hash = DigestUtils.md5Hex(data).toLowerCase();

        return "https://" + host + "/get-mp3/" + hash + "/" + ts + path + "?track-id=" + id;
    }

    private static HttpClient httpClient = null;

    private static HttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(RequestConfig.custom()
                            .setCookieSpec(CookieSpecs.STANDARD).build())
                    .build();
        }
        return httpClient;
    }
}
