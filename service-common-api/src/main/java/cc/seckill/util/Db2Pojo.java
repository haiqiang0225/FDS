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
        String url = "jdbc:mysql://tx_cloud:3306/fds?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String username = "fds_admin";
        String password = System.getenv("mysql_fds_admin_pass");
        System.out.println(password);
        FastAutoGenerator.create(url, username, password.trim())
                .globalConfig(builder -> {
                    builder.author("jhq") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .outputDir("/Users/hq/Desktop/pojo_output"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("cc.seckill") // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "/Users/hq/Desktop/pojo_output")); //
                    // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("tb_user", "tb_role", "tb_menu") // 设置需要生成的表名
                            .addTablePrefix("tb_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
