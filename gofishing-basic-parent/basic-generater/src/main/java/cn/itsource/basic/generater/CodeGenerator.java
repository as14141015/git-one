package cn.itsource.basic.generater;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CodeGenerator {

    public static void main(String[] args) {
         final ResourceBundle rb = ResourceBundle.getBundle("generate-product");
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //配置项目路径
        final String projectPath = System.getProperty("user.dir");
        //加载配置文件
        gc.setOutputDir(projectPath + rb.getString("outputDir"));
        gc.setAuthor(rb.getString("author"));
        gc.setOpen(false);
        gc.setBaseResultMap(true);
//        gc.setFileOverride(true);//是否已新的覆盖之前的，默认为false

        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(rb.getString("url"));
        // dsc.setSchemaName("public");
        dsc.setDriverName(rb.getString("driverClassName"));
        dsc.setUsername(rb.getString("username"));
        dsc.setPassword(rb.getString("password"));
        mpg.setDataSource(dsc);

        // 包配置
        final PackageConfig pc = new PackageConfig();
        pc.setParent("cn.itsource.gofishing.product");
        pc.setEntity("domain");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        //自定义mapper的输出
        String mapperTemplatePath = "/templates/mapper.xml.vm";
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(mapperTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + rb.getString("mapperOutputDir")
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        //自定义Domain的输出
        String domainTemplatePath = "/templates/entity.java.vm";
        focList.add(new FileOutConfig(domainTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + rb.getString("domainOutputDir")
                        + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });

        //自定义Query的输出
        String queryTemplatePath = "/templates/query.java.vm";
        focList.add(new FileOutConfig(queryTemplatePath) {
                        @Override
                        public String outputFile(TableInfo tableInfo) {
                            return projectPath + rb.getString("queryOutputDir")
                                    + tableInfo.getEntityName() + "Query" + StringPool.DOT_JAVA;
                        }
                    });
        //自定义Controller的输出
        String controllerTemplatePath = "/templates/controller.java.vm";
        focList.add(new FileOutConfig(controllerTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + rb.getString("controllerOutputDir")
                        + tableInfo.getEntityName() +"Controller"+ StringPool.DOT_JAVA;
            }
        });

        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        templateConfig.setEntity(null);
        templateConfig.setController(null);
        mpg.setTemplate(templateConfig);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        //生成哪些表的代码
        strategy.setInclude("t_product","t_product_ext","t_product_comment","t_sku","t_specification");
        strategy.setTablePrefix("t_");
        mpg.setStrategy(strategy);
        mpg.execute();
    }

}