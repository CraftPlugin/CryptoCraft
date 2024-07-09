package kr.acog.crypto;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by. Acog
 */
public class CoinConfiguration {

    public static Map<String, Coin> init(CryptoCraftPlugin plugin, List<String> coins) {
        Gson gson = new Gson();
        try (InputStream inputStream = plugin.getResource("coin_data.json");
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            Type type = new TypeToken<Map<String, JsonObject>>() {}.getType();
            Map<String, JsonObject> rawCoinData = gson.fromJson(reader, type);
            Map<String, Coin> coinData = new HashMap<>();

            for (Map.Entry<String, JsonObject> entry : rawCoinData.entrySet()) {
                JsonObject info = entry.getValue();
                String symbol = info.get("symbol").getAsString();
                String name = info.get("name").getAsString();
                if (coins.contains(symbol)) {
                    coinData.put(symbol, new Coin(symbol, name, 0, 0));
                }
            }
            return coinData;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

}
