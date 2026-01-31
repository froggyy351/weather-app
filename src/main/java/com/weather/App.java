package com.weather;

import java.io.IOException;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    public static void main( String[] args ){
        //.envファイルからapikeyを取得
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("WEATHER_API_KEY");

        //入力を受け付ける
        Scanner scanner = new Scanner(System.in);
        System.out.println("天気を調べる都市名を入力してください。（例：Osaka, London, Paris）");
        String city = scanner.nextLine();

        // String city = "Osaka";
        //metric = 摂氏
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        System.out.println(city + "の天気を取得中...");

        try (Response response = client.newCall(request).execute()) {
            if(response.isSuccessful() && response.body() != null){
                String result = response.body().string();

                //ここからjacksonによる解析
                ObjectMapper mapper = new ObjectMapper();
                //json文字列を木の枝(node)のような構造に変換する
                JsonNode root = mapper.readTree(result);

                //欲しい情報をpathを指定して掘り進む
                String cityName = root.path("name").asText();
                double temp = root.path("main").path("temp").asDouble();
                int humidity = root.path("main").path("humidity").asInt();
                String description = root.path("weather").get(0).path("description").asText();

                //結果を表示
                //英語で天気が出るのを日本語にできないか？？
                System.out.println("======" + cityName + "：現在の天気======");
                System.out.println("天気　　　　：　" + description);
                System.out.println("気温　　　　：　" + temp + "°C");
                System.out.println("湿度　　　　：　" + humidity + "%");
                System.out.println("アドバイス　：　" + getAdvice(temp));
                System.out.println("=============================");
            } else {
                System.out.println("エラーが発生しました" + response.code());
            }
        } catch (IOException e) {
            System.err.println("通信または解析中にエラーが発生しました。");
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    public static String getAdvice(double temp){
        if(temp < 10){
            return "コートやダウンなど上着が必須です";
        } else if(temp < 20){
            return "ジャケットがおすすめです";
        } else {
            return "半袖でも大丈夫そうです";
        }
    }
}
