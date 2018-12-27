## JdbcTemplate的配置

1、配置： 
         
          1、数据源（dataSource）、
          2、JdbcTemplate  
2、使用@Autowirod注入 JdbcOperations，
   JdbcOperations可以调用它（CRUD）的方法
   
3、优点： 高效、内嵌Spring框架中，支持AOP的声明
   缺点：必须使用与Spring框架结合在一起使用，不支持数据库跨平台、默认没有缓存
   
   
## MyBatis



## Hibernate
    
   1、配置： 数据源、SessionFactory、辅助参数
   
   2、翻译：（映射关系的翻译）、xml、annotation
   
   3、执行