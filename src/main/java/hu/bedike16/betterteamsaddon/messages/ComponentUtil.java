package hu.bedike16.betterteamsaddon.messages;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@UtilityClass
public class ComponentUtil {

    private static final MiniMessage MINI = MiniMessage.miniMessage();

    @NotNull
    public static Component formatToComponent(@NotNull String message) {
        // Replace legacy color codes if needed
        message = message.replace("§", "&");
        return MINI.deserialize(message);
    }

    @NotNull
    public static Component formatToComponent(@NotNull List<String> messages) {
        return MINI.deserialize(String.join("\n", messages));
    }

    @NotNull
    public static String formatToString(@NotNull Component component) {
        return MINI.serialize(component);
    }
}
