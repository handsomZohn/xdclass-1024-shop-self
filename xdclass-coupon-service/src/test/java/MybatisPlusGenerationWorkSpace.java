import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @ClassName MybatisPlusGenerationWorkSpace
 * @Description 代码自动化生成微服务相关类  工作空间-工具类
 * @Date 2021/12/6 16:17
 * @Version 1.0
 **/
public class MybatisPlusGenerationWorkSpace {
    public static void main(String[] args) {

        // 1.全局配置
        GlobalConfig globalConfig = new GlobalConfig();

        // 作者
        globalConfig.setAuthor("viy7664")
                // 文件输出目录
                .setOutputDir("C:\\Users\\zzohn\\Desktop\\demo\\src\\main\\java")
                // 文件覆盖
                .setFileOverride(true)
                // 主键策略
                .setIdType(IdType.AUTO)
                .setDateType(DateType.ONLY_DATE)
                .setServiceName("%sService")
                // 实体类结尾名称
                .setEntityName("%sDO")
                // 生成基本的resultMap
                .setBaseResultMap(true)
                // 不使用AR模式
                .setActiveRecord(false)
                // 生成基本的SQL片段
                .setBaseColumnList(true);

        // 2.数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();

        dataSourceConfig.setDbType(DbType.MYSQL)
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://rm-m5e7pzw9sttwm5ky7.mysql.rds.aliyuncs.com:3306/leasing_dev?allowMultiQueries=true&stringtype=unspecified")
                .setUsername("hyfl_dev")
                .setPassword("Hyfl2021");

        // 3.策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        // 全局大写命名
        strategyConfig.setCapitalMode(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setInclude("cc_person");

        // 4.包名策略配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("net.xdclass")
                .setMapper("mapper")
                .setController("controller")
                .setService("service")
                .setEntity("model")
                .setXml("mapper");

        // 5.整合配置
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig);

        // 执行
        autoGenerator.execute();

        System.out.println("======= 小滴课堂 Done 相关代码生成完毕  ========");
    }
}
