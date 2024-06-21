<div style="color:#16b0ff;font-size:50px;font-weight: 900;text-shadow: 5px 5px 10px var(--theme-color);font-family: 'Comic Sans MS';">ThreeC Cloud</div>

## 简介：

本人学习过程中搭建的一个简单的框架，供大家参考。希望能对您提供帮助！

```
common:
	common-mybatis
	common-redis
	common-tools
service:
	service-auth
	service-test
```



### common

>  项目所需要的基础项目

#### common-mybatis

- Mybatis-plus

### service

#### service-auth

- Spring Boot 3.2.5
- Spring security 6.2.4



**备注:**

**由于springcloud从2020版本开始弃用了Ribbon，所以Alibaba在2020及之后版本的nacos中删除了Ribbon的jar包， 因此无法通过lb路由到指定微服务，所以导致出现了503情况。**

```json
{
  "timestamp": "2024-06-21T08:50:37.870+00:00",
  "path": "/prod-service/prod/hot",
  "status": 503,
  "error": "Service Unavailable",
  "requestId": "1b10528c-1"
}
```



## 感谢以下大佬的项目供我参考：

参考: https://sheygithub.com/sorennx/spring-project

参考: https://github.com/liuchaocpp/spring-security-aliyun-message-login
