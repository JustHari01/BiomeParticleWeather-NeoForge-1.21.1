/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.Scanner;
import net.neoforged.fml.loading.FMLPaths;
import org.shadowmaster435.biomeparticleweather.util.ParticleSettings;

public class ConfigFile {
    public static final int current_version = 2;

    public ConfigFile() {
    }

    public static void ensure_existance() {
        try {
            if (!ConfigFile.get_mod_config_directory().toFile().exists()) {
                ConfigFile.get_mod_config_directory().toFile().mkdir();
            }
            if (!Path.of(ConfigFile.get_mod_config_directory().toString(), "config.txt").toFile().exists()) {
                Path.of(ConfigFile.get_mod_config_directory().toString(), "config.txt").toFile().createNewFile();
                ParticleSettings.load_defaults();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void save_config() {
        ConfigFile.ensure_existance();
        String string = ParticleSettings.get_config_string();
        try {
            FileWriter file = new FileWriter(ConfigFile.get_mod_config_file().toFile());
            file.flush();
            file.write(string);
            file.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void load_config() {
        try {
            Scanner file = new Scanner(ConfigFile.get_mod_config_file().toFile());
            String mode = "int";
            int version = -1;
            while (file.hasNext()) {
                String line = file.nextLine();
                if (line.contains("=")) {
                    String[] val = ConfigFile.get_value(line);
                    switch (mode) {
                        case "int": {
                            if (line.contains("version")) {
                                version = Integer.parseInt(val[1]);
                            }
                            ParticleSettings.load_int(val);
                            break;
                        }
                        case "float": {
                            ParticleSettings.load_float(val);
                            break;
                        }
                        case "bool": {
                            ParticleSettings.load_bool(val);
                            break;
                        }
                        case "string": {
                            ParticleSettings.load_string(val);
                        }
                    }
                    continue;
                }
                mode = line;
            }
            if (version != 2) {
                ParticleSettings.load_defaults();
            }
            ConfigFile.save_config();
            file.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String[] get_value(String line) {
        return line.split("=");
    }

    public static Path get_mod_config_file() {
        return Path.of(ConfigFile.get_mod_config_directory().toString(), "config.txt");
    }

    public static Path get_mod_config_directory() {
        Path dir = FMLPaths.CONFIGDIR.get();
        return Path.of(dir.toFile().getAbsolutePath(), "biomeparticleweather");
    }
}
