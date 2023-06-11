package cc.seckill.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * description: Db2Pojo <br>
 * date: 2022/12/11 17:05 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
public class Db2Pojo {
    public static void main(String[] args) {
        String url = "jdbc:mysql://mysql_fds_server:3366/fds?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String username = "fds_admin";
        String password = System.getenv("mysql_fds_admin_pass");
        String outputDir = ".\\tmp";
        if (Env.isLinux() || Env.isMacOs()) {
            outputDir = "/Users/hq/Desktop/pojo_output";
        } else if (Env.isWindows()) {
            outputDir = "D:/datas/sql";
        }
        System.out.println(password);
        String finalOutputDir = outputDir;
        FastAutoGenerator.create(url, username, password.trim())
                .globalConfig(builder -> {
                    builder.author("jhq") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(finalOutputDir); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("cc.seckill") // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, finalOutputDir)); //
                    // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("tb_user", "tb_role", "tb_menu", "tb_device", "tb_log", "tb_models",
                                    "tb_statistics_fault_by_equipment_type", "tb_statistics_fault_by_type",
                                    "tb_statistics_fault_degree") // 设置需要生成的表名
                            .addTablePrefix("tb_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
