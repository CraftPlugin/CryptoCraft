package kr.acog.crypto.view;

import io.typst.bukkit.view.*;
import io.typst.bukkit.view.item.BukkitItem;
import kr.acog.crypto.Coin;
import kr.acog.crypto.CryptoCraftPlugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Create by. Acog
 */
public class PluginView {

    public static ChestView getCryptoView(CryptoCraftPlugin plugin, ViewFilter filter, UUID id) {
        return ChestView.builder()
                .title("암호화폐 시장")
                .contents(getCryptoContents(plugin, filter, id))
                .row(6)
                .build();
    }

    private static ChestView getFilterView(CryptoCraftPlugin plugin, UUID id) {
        Map<Integer, ViewControl> controls = new HashMap<>();

        controls.put(0, ViewUtils.createViewControl(
                Material.GRASS_BLOCK,
                "§a모든 코인",
                "§7 서버의 모든 코인들을 확인 합니다.",
                event -> new ViewAction.Open(getCryptoView(plugin, ViewFilter.ALL, id))
        ));
        controls.put(1, ViewUtils.createViewControl(
                Material.GRASS_BLOCK,
                "§a보유하고 있는 코인",
                "§7 보유하고 있는 코인들을 확인 합니다.",
                event -> new ViewAction.Open(getCryptoView(plugin, ViewFilter.OWNER_COINS, id))
        ));
        controls.put(2, ViewUtils.createViewControl(
                Material.GRASS_BLOCK,
                "§a보유하지 않은 코인",
                "§7 보유하지 않은 코인들을 확인 합니다.",
                event -> new ViewAction.Open(getCryptoView(plugin, ViewFilter.UNOWNED_COINS, id))
        ));


        return ChestView.builder()
                .title("원하는 필터를 선택하세요.")
                .contents(ViewContents.ofControls(controls))
                .build();
    }

    private static ViewContents getCryptoContents(CryptoCraftPlugin plugin, ViewFilter filter, UUID id) {
        Map<String, Coin> coins = plugin.coins;
        Map<Coin, Integer> userCoins = plugin.players.getOrDefault(id, Collections.emptyMap());
        AtomicInteger keyGenerator = new AtomicInteger(0);

        Map<Integer, ViewControl> controls = switch (filter) {
            case ALL -> coins.values().stream()
                    .map(coin -> ViewControl.of(getCoinItem(coin), getCryptoAction(coin)))
                    .collect(Collectors.toMap(control -> keyGenerator.getAndIncrement(), control -> control));
            case OWNER_COINS -> coins.entrySet().stream()
                    .filter(entry -> userCoins.keySet().stream().anyMatch(coin -> coin.symbol().equals(entry.getKey())))
                    .map(entry -> ViewControl.of(getCoinItem(entry.getValue()), getCryptoAction(entry.getValue())))
                    .collect(Collectors.toMap(control -> keyGenerator.getAndIncrement(), control -> control));
            case UNOWNED_COINS -> coins.entrySet().stream()
                    .filter(entry -> userCoins.keySet().stream().noneMatch(coin -> coin.symbol().equals(entry.getKey())))
                    .map(entry -> ViewControl.of(getCoinItem(entry.getValue()), getCryptoAction(entry.getValue())))
                    .collect(Collectors.toMap(control -> keyGenerator.getAndIncrement(), control -> control));
        };

        controls.put(48, ViewUtils.createViewControl(
                Material.NAME_TAG,
                "§a← 상점 필터 →",
                "§7 암호시장의 코인들을 필터링 하는 페이지를 엽니다.",
                event -> new ViewAction.Open(getFilterView(plugin, id))
        ));
        controls.put(49, ViewUtils.createViewControl(
                Material.NAME_TAG,
                "§a← 보유중인 자산 →",
                "§7 .... 여러가지",
                event -> ViewAction.NOTHING
        ));
        controls.put(50, ViewUtils.createViewControl(
                Material.NAME_TAG,
                "§a← 시장 새로고침 →",
                "§7 암호시장의 코인 데이터를 새로고침 합니다.",
                event -> new ViewAction.Update(getCryptoContents(plugin, filter, id))
        ));

        return ViewContents.ofControls(controls);
    }

    private static ItemStack getCoinItem(Coin coin) {
        return BukkitItem.ofJust(Material.GRASS_BLOCK)
                .withName(String.format("§a%s [ %s ]", coin.name(), coin.symbol()))
                .withLore(Arrays.asList(
                        "§7",
                        "§7현재 가격: " + coin.getPrice(),
                        "§7전일 대비: # 개발중...",
                        "§7",
                        "§7총 거래량 : " + coin.getTradeValue(),
                        "§7",
                        "§7 좌클릭 하면 코인을 구매합니다.",
                        "§7 우클릭 하면 코인을 판매합니다.",
                        "§7 ( 쉬프트를 누르고 클릭하면 최대로 판매하거나 최대로 구매 합니다. ) ",
                        "§7"
                ))
                .build();
    }

    private static Function<ClickEvent, ViewAction> getCryptoAction(Coin coin) {
        return e -> {
            switch (e.getClick()) {
                case LEFT -> {

                }
                case SHIFT_LEFT -> {

                }
                case RIGHT -> {

                }
                case SHIFT_RIGHT -> {

                }
            }
            return ViewAction.NOTHING;
        };
    }

}
