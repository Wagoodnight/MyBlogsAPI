package top.zhenxun.blogs.api.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author ATRI
 */
public class SystemSettingInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String EULA_FILE_PATH = "eula.txt";

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        if (!acceptEULA()) {
            return;
        }

    }

    private boolean acceptEULA() {
        Path eulaPath = Paths.get(EULA_FILE_PATH);

        ZonedDateTime now = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        String formattedDate = now.format(formatter);

        String eulaContent = "# Please agree to the End User License Agreement (EULA) to proceed.\n" +
                "# " + formattedDate + "\n" +
                "#\n" +
                "# 1. This software is for personal use only and may not be used\n" +
                "#    for commercial purposes, including but not limited to selling,\n" +
                "#    redistributing, or integrating with other commercial projects.\n" +
                "#    Unauthorized commercial use may violate copyright laws.\n" +
                "#\n" +
                "# 2. By using this software, you agree that it is solely intended\n" +
                "#    for individual, non-commercial purposes such as personal\n" +
                "#    experimentation, learning, or research.\n" +
                "eula=false\n";

        if (!Files.exists(eulaPath)) {
            try {
                Files.write(eulaPath, eulaContent.getBytes());
                System.out.println("EULA文件已生成: " + eulaPath.toAbsolutePath());
                System.out.println("请阅读并接受EULA以继续。");
                System.exit(0);
                return false;
            } catch (IOException e) {
                System.err.println("创建EULA文件失败: " + e.getMessage());
                System.exit(1);
                return false;
            }
        } else {
            try (BufferedReader reader = Files.newBufferedReader(eulaPath)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().startsWith("eula=")) {
                        if ("eula=true".equals(line.trim())) {
                            return true;
                        } else {
                            System.err.println("EULA未被接受，请同意以继续。");
                            System.exit(0);
                            return false;
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("读取EULA文件失败: " + e.getMessage());
                System.exit(1);
                return false;
            }
        }

        System.err.println("无法确定EULA状态。");
        System.exit(1);
        return false;
    }
}