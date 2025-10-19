package hu.bedike16.betterteamsaddon.messages;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@UtilityClass
public class ComponentUtil {
    // this is the serializer/deserializer for the utility class
    private static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.builder()
            .character('&')
            .useUnusualXRepeatedCharacterHexFormat()
            .hexColors()
            .build();

    @NotNull
    public static String formatToString(@NotNull String message) {
        message = message.replace("§", "&");
        message = serialize(MiniMessage.miniMessage().deserialize(message));
        return message;
    }


    @NotNull
    public static String formatToString(@NotNull Component component) {
        return MiniMessage.miniMessage().serialize(component);
    }

    @NotNull
    public static Component formatToComponent(@NotNull String message) {
        message = message.replace("§", "&");
        message = serialize(MiniMessage.miniMessage().deserialize(message));
        return deserialize(message);
    }

    @NotNull
    public static Component formatToComponent(@NotNull List<String> messages) {
        Component empty = Component.empty();
        final boolean[] first = {true};
        messages.forEach(msg -> {
            if(first[0]) {
                empty.append(Component.newline());
                first[0] = false;
            }
            empty.append(formatToComponent(msg));
        });
        return empty;
    }

    @NotNull
    public static Component deserialize(@NotNull String message) {
        return SERIALIZER.deserialize(message);
    }

    @NotNull
    public static String serialize(@NotNull Component message) {
        return SERIALIZER.serialize(message);
    }

//    @NotNull
//    public static List<String> processStringList(@NotNull List<String> list) {
//        return list.stream()
//                .map(ComponentUtil::formatToString)
//                .collect(Collectors.toCollection(() -> new ArrayList<>(list.size())));
//    }
//
//    @NotNull
//    public static List<Component> processComponentList(@NotNull List<String> list) {
//        return list.stream()
//                .map(line -> deserialize(formatToString(line)))
//                .collect(Collectors.toCollection(() -> new ArrayList<>(list.size())));
//    }
}