学习使用Spring Cloud

* Gateway + Zone的使用
  
**演示目的, 参考请慎重**

灰度发布有很多方法, 按beta/normal划分是最简单的一种, 这里完整演示了这种最简单的例子. 网上有很多例子,不过拼起来也很费劲.

模块说明:
* eureka-server: 注册中心
* hello-remotename-core: 接口定义
* hello-remotename: remote名称服务
* hello-nameservice: 名称服务(本地, 远程, 通过网关三种方式)
* hello-gateway: 网关 (zone-perference)
* hello-mygateway: 自定义ServiceInstanceListSupplier

* hello-pubtool 公用模块, 避免重复类 (演示目的, 参考请慎重)
* hello-nameservicebyheader: FeignClient通过Header访问不同的实例
* hello-mybalancerbyheader: 通过Header访问不同的实例

相关文章
* [Spring Cloud分区发布实践(1)](https://blog.cnscud.com/springcloud/2021/06/07/springcloud-betazonedemo-1.html)
* [Spring Cloud分区发布实践(2)](https://blog.cnscud.com/springcloud/2021/06/07/springcloud-betazonedemo-2.html)
* [Spring Cloud分区发布实践(3)](https://blog.cnscud.com/springcloud/2021/06/07/springcloud-betazonedemo-3.html)
* [Spring Cloud分区发布实践(4)](https://blog.cnscud.com/springcloud/2021/06/07/springcloud-betazonedemo-4.html)
* [Spring Cloud分区发布实践(5) 定制ServiceInstanceListSupplier](https://blog.cnscud.com/springcloud/2021/06/07/springcloud-betazonedemo-5.html)
* [Spring Cloud分区发布实践(6) 灰度服务-根据Header选择实例区域](https://blog.cnscud.com/springcloud/2021/06/07/springcloud-betazonedemo-6.html)

