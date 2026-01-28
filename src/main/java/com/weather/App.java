package com.weather;

import java.io.IOException;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //.envファイルからapikeyを取得
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("WEATHER_API_KEY");

        String city = "Tokyo";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        System.out.println(city + "の天気を取得中...");

        try (Response response = client.newCall(request).execute()) {
            if(response.isSuccessful() && response.body() != null){
                String result = response.body().string();
                System.out.println("取得成功！");
                System.out.println("--- 取得データ ---");
                System.out.println(result);
            } else {
                System.out.println("エラーが発生しました" + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
