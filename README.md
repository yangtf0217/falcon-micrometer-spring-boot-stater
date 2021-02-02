
#项目准备：


0.把jar包放到maven仓库，路径为com\kobe\falcon-micrometer-spring-boot-stater\2.0.1-SNAPSHOT的下面

1.需要的项目

pom新增
````
<dependency>
<groupId>com.kobe</groupId>
<artifactId>falcon-micrometer-spring-boot-stater</artifactId>
<version>2.0.1-SNAPSHOT</version>
</dependency>
````


配置文件新增
````
falcon.api.url= //根据情况定
management.metrics.export.falcon.enabled=true
management.metrics.tags.app=${spring.application.name}
management.metrics.web.server.auto-time-requests=true
management.metrics.export.falcon.step=60s
````

2.编码
````
List<Tag> tags = ImmutableList.of(Tag.of("code", code));
Metrics.counter(MetricsEnum.KPT_LOAN_MONEY.name(), tags).increment(amt.doubleValue());
````