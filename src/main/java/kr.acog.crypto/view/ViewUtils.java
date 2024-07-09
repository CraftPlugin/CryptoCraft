package kr.acog.crypto.view;

import io.typst.bukkit.view.ClickEvent;
import io.typst.bukkit.view.ViewAction;
import io.typst.bukkit.view.ViewControl;
import io.typst.bukkit.view.item.BukkitItem;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.function.Function;

/**
 * Create by. Acog
 */
public class ViewUtils {

    public static ViewControl createViewControl(Material material, String name, String lore, Function<ClickEvent, ViewAction> action) {
        return ViewControl.of(
                BukkitItem.ofJust(material)
                        .withName(name)
                        .withLore(Arrays.asList("§7", lore, "§7"))
                        .build(),
                action
        );
    }

}
