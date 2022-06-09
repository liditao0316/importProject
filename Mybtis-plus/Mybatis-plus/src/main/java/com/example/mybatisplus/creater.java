package com.example.mybatisplus;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Collections;

public class creater {


    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://112.74.108.226:3306/Insta-kill?characterEncoding=utf8", "dms", "12345678Ldt.")
                .globalConfig(builder -> {
                    builder.author("liditao") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("/Users/me/Desktop"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.system")
                            .moduleName("instaKill") // 设置父包模块名
                            .controller("controller")
                            .entity("pojo")
                            .service("service")
                            .serviceImpl("service")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "/Users/me/Desktop")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addTablePrefix("t_")
                            .mapperBuilder()
                            .enableMapperAnnotation()
                            .enableBaseColumnList()
                            .enableBaseResultMap()
                            .build();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();


    }
}
