学习使用Spring cloud

* Gateway + Zone的使用


灰度发布有很多方法, 按beta/normal划分是最简单的一种, 这里完整演示了这种最简单的例子. 网上有很多例子,不过拼起来也很费劲.

模块说明:
* eureka-server: 注册中心
* hello-remotename-core: 接口定义
* hello-remotename: remote名称服务
* hello-nameservice: 名称服务(本地, 远程, 通过网关三种方式)
* hello-gateway: 网关

