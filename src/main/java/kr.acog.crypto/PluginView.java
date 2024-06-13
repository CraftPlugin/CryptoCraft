package kr.acog.crypto;

import io.typst.bukkit.view.ChestView;
import io.typst.bukkit.view.ViewAction;
import io.typst.bukkit.view.ViewControl;
import io.typst.bukkit.view.item.BukkitItem;
import io.typst.bukkit.view.page.PageContext;
import io.typst.bukkit.view.page.PageViewLayout;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Create by. Acog
 */
public class PluginView {

    public static ChestView getCryptoPage() {
        CryptoCraftPlugin plugin = CryptoCraftPlugin.inst;
        List<Function<PageContext, ViewControl>> items = plugin.coins.values().stream()
                .map(coin -> (Function<PageContext, ViewControl>) ctx -> ViewControl.of(getCoinItem(coin), event -> {
                    switch (event.getClick()) {
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
                }))
                .collect(Collectors.toList());
        int row = 6;
        int controlSize = (row - 1) * 9;
        List<Integer> slots = IntStream.range(0, controlSize).boxed().collect(Collectors.toList());
        Map<Integer, Function<PageContext, ViewControl>> controls = getDefaultControls(controlSize);
        return PageViewLayout.of("암호화폐 시장", row, items, slots, controls).toView(1);
    }

    private static ItemStack getCoinItem(Coin coin) {
        return BukkitItem.ofJust(Material.GRASS_BLOCK)
                .withName(String.format("§a%s [ %s ]", coin.name(), coin.symbol().toUpperCase()))
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

    private static Map<Integer, Function<PageContext, ViewControl>> getDefaultControls(int controlSize) {
        Map<Integer, Function<PageContext, ViewControl>> controls = new HashMap<>();
        controls.put(controlSize + 3, ctx -> ViewControl.of(
                e -> BukkitItem.ofJust(Material.ACACIA_BUTTON)
                        .withAmount(Math.max(1, ctx.getPage()))
                        .withName(String.format(
                                "§e← 이전 페이지 §7( %s / %s )", ctx.getPage(), ctx.getMaxPage()
                        ))
                        .build(),
                e -> new ViewAction.Update(ctx.getLayout().toView(ctx.getPage() - 1).getContents())
        ));
        controls.put(controlSize + 4, ctx -> ViewControl.of(
                e -> BukkitItem.ofJust(Material.ACACIA_BUTTON)
                        .withAmount(Math.max(1, ctx.getPage()))
                        .withName(String.format(
                                "§a← 시장 새로고침 →", ctx.getPage(), ctx.getMaxPage()
                        ))
                        .build(),
                e -> new ViewAction.Update(getCryptoPage().getContents())
        ));
        controls.put(controlSize + 5, ctx -> ViewControl.of(
                e -> BukkitItem.ofJust(Material.ACACIA_BUTTON)
                        .withAmount(Math.max(1, ctx.getPage()))
                        .withName(String.format(
                                "§7( %s / %s ) §e다음 페이지 →", ctx.getPage(), ctx.getMaxPage()
                        ))
                        .build(),
                e -> new ViewAction.Update(ctx.getLayout().toView(ctx.getPage() + 1).getContents())
        ));
        return controls;
    }

}
