package hu.bedike16.betterteamsaddon.files;

import lombok.Getter;
import hu.bedike16.betterteamsaddon.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TeamData {
    private static Main m = Main.getPlugin(Main.class);
    @Getter private File cfg;
    @Getter private FileConfiguration config;


    public TeamData(String ymlFile) {
        this.cfg = new File(m.getDataFolder(), ymlFile);
        setup();
    }

    public TeamData(String folder, String file) {
        this.cfg = new File(m.getDataFolder(), folder + File.separator + file);
        setup();
    }

    public TeamData(File file) {
        this(getPath(file));
    }

    public void setup() {
        //cfg = ymlFile;
        if (!cfg.exists()) {
            try {
                cfg.getParentFile().mkdirs();
                cfg.createNewFile();

                InputStream in = m.getResource(getPath(cfg));
                FileOutputStream out = new FileOutputStream(cfg);

                if (in != null) {
                    try {
                        int n;
                        while ((n = in.read()) != -1) {
                            out.write(n);
                        }
                    } finally {
                        if (in != null) {
                            in.close();
                        }
                        if (out != null) {
                            out.close();
                        }
                    }
                }

            } catch (IOException e) {
            }
        }
        config = YamlConfiguration.loadConfiguration(cfg);
    }

    public void save() {
        try {
            config.save(cfg);
        } catch (IOException e) {
            System.out.println("Can't save language file");
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(cfg);
    }

    public static String getPath(File file) {
        return file.getPath()
                .replace(m.getDataFolder().getPath() + File.separator, "");
        // .replace("\\", File.separator);
    }
}
