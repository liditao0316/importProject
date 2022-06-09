package com.bookshop.common;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MybatisPlusCreator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://8.129.131.201:3306/bookshop?characterEncoding=utf8", "root", "Doplove52)")
                .globalConfig(builder -> {
                    builder.author("liditao") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir( "C:\\Users\\EDY\\Desktop\\test"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.bookshop.system")
                            .moduleName("security") // 设置父包模块名
                            .controller("controller")
                            .entity("entity")
                            .service("service")
                            .serviceImpl("service")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "C:\\Users\\EDY\\Desktop\\test")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addTablePrefix("t_")
                            .addInclude("t_users") //针对某个表生成数据
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
