# 快速启动
1. 环境
   1. jdk1.8
   2. maven3.8.4
   3. mysql5.7
   4. nacos 
   5. redis 5.x.0
   6. rabbitmq  3.12.0
   7. erlang 25.3.2
   8. springboot 2.6.13
   9. springcloud 2021.0.5.0
   10. springcloud-alibaba 2021.0.5.0
2. 服务端口
   1. gateway 8101
   2. user-service 8102
   3. question-service 8103
   4. judge-service 8104
   5. 远程代码沙箱地址 http://192.168.88.128:8090/executeCode
3. 代码沙箱配置
   1. 代码沙箱地址：http://192.168.88.128:8090/executeCode
   2. 选择使用远程沙箱配置 
      application.yml中配置
      ```yaml
      codesandbox:
        type: remote # example remote
        url: http://192.168.88.128:8090/executeCode # 远程沙箱地址
      ```