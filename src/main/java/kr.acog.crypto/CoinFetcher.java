package kr.acog.crypto;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by. Acog
 */
public class CoinFetcher {

    private final Map<String, Coin> coins;

    private final HttpClient client;
    private final String API_URL = "https://api.bithumb.com/public/ticker/ALL_KRW";

    private CoinFetcher(Map<String, Coin> coins) {
        this.coins = coins;
        this.client = HttpClient.newHttpClient();
    }

    public static CoinFetcher of(Map<String, Coin> coins) {
        return new CoinFetcher(coins);
    }

    public Map<String, Coin> fetch() {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_URL)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return coins;
            }

            Map<String, Coin> result = new HashMap<>();
            JsonObject body = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonObject data = body.getAsJsonObject("data");

            for (Map.Entry<String, Coin> entry : coins.entrySet()) {
                JsonObject info = data.getAsJsonObject(entry.getKey().toUpperCase());
                if (info != null) {
                    double price = info.get("closing_price").getAsDouble();
                    result.put(entry.getKey(), entry.getValue().withPrice(price));
                } else {
                    result.put(entry.getKey(), entry.getValue());
                }
            }
            return result;
        } catch (IOException | InterruptedException ignored) {
            return coins;
        }
    }

}
