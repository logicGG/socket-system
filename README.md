<div align="center">

# Socket System

一个实现用户登录和鉴权认证的websocket前后端通信系统。

[![GitHub](https://img.shields.io/badge/dynamic/json?url=https%3A%2F%2Fapi.swo.moe%2Fstats%2Fgithub%2FlogicGG&query=count&color=181717&label=GitHub&labelColor=282c34&logo=github&suffix=+follows&cacheSeconds=3600)](https://github.com/logicGG)
[![qq group](https://img.shields.io/badge/Group-892890031-blue?style=flat-square&labelColor=FAFAFA&logo=data:image/png;base64,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&logoColor=000000)](http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=sp1yvuE6tyEDXvxd3rmQeeQiAsYt7JnU&authKey=0xD48RNypqDLoKuW1y3WsrezR3DFSgDJ3gvcfxDIPrWz2Txq%2B6eQyXf1gy9JWKz5&noverify=0&group_code=892890031)
[![哔哩哔哩](https://img.shields.io/badge/dynamic/json?url=https%3A%2F%2Fapi.swo.moe%2Fstats%2Fbilibili%2F8924896&query=count&color=282c34&label=%E5%93%94%E5%93%A9%E5%93%94%E5%93%A9&labelColor=FE7398&logo=data%3Aimage%2Fpng%3Bbase64%2CiVBORw0KGgoAAAANSUhEUgAAAGAAAABgCAYAAADimHc4AAAD7ElEQVR4nO2dW9WrMBCFK6ESkFAJSKiESqgEHCABCZWAhEpAAhL2ecik5dDc%2FpXLBDLfWnlqy0xmJ5BMQnq5CIIgCIIgCIIgCIIgCEIBAHQAemYfrgCunD6wAKAHsEKxALgx+bCQD8%2FS9tmgVqeDr1lLigDgZvDhXso+K9TyTBQRwRJ8AHjntl0Flh5QRAQK%2FmKxPeayWx2OXpBNBKiHvi34b7T2MC4pAvW6twR%2FRwkRKPizBN8CgEcuESj4Lwm+BwBjahEk+H8EwJRKhOaCDzW8e1JLfkUUH1NgmR3XmHffHR1l+72BSs8d7w8U+JDAnZERQMcV+CtUi7dNqFqibB4J7vtrq7xKCuAasbTMXCL4T+5aVk6+2xHUrWdhruAR6HIJcOeu2UHI8zyAe2ytWfEdWz9PVvQ8YAmIQ5dDAB9LFsMVAv8oMO2zAGrC5WNIarRiAuKR9jYEd9pY08aa6uUzIHGRdkgKd8pY0yc1WjEBAqypDYoAG0QAZkQAZkQAZkQAZk4vANQenjsSzS3I%2FwcSbXU5jQBUkRtdf4Rar90v8kSv3+I3ffCCSpk8I%2Fw+lgDkdI%2Fv2rEp2CaiWm1AsDQLlDAD+dlFXLMeAaCSeLZdaSFE5VUQNot38cKuEeBgAsSuG0flVZBmEanbXfNQAsS0fgBYIn2fIu3%2FBBMHEyBmDXlFfA8IzeHb+Ems4WAChKykrVA9ZfsQTL57jXzRg4A5wC%2FA8N4ADiZAZwm2XjW75Qh2KOTfA0p4kygPw28OJcCVgn3nDnYo2EwEYRgGH0qAMyICMCMCMCMCMCMCMCMCMCMCfP3qwHDOQ4AAUekTk8FaBRihJnZdYbvtCGC7LvmkM63GjVDINPFrQgCq5ETXfmMzI90FXzPvfqt7x4rEu%2FZaEcCUxFvgz2zO+BUn6UkoaEEAsptiMSX5e8FoRYCN7cVgb4Vq7U%2FH50Pq4JNP7Qiw8UFnJwcK+tXy+Wj6PLEvPgHSHv5UgwA1IQIwwyFAyLJin9RoxYgAzAQIkPwNmf26busC+OIx5TDqo5nDT+F%2FSS%2F9CYzwb+No49zNy2evkYv0LywGGAXUvp6eSneycqOic0w20k7CNgKE7jJunSGLACTCxF27ylmQc98T5MQUH49swd+I0HPXslLKnT0N+wnkrTKi9JZL%2FL9i1SorMmdeQ4TQQ7OFMxIMzGD45w8nUL1im7efENZLJpgPSw0pfz0cdt4U3230Td%2FTvx2R6d2FrHhEWLkq5PELOMsRPHCPnAZGv1xJteL7jbJiaW3sB2nDvPC%2FosSYvjRQz4cJ6n7KO3rYQL7M+L6nVtfDVRAEQRAEQRAEQRAEIZ5%2FSAXmdfXaoQsAAAAASUVORK5CYII%3D&suffix=+%E5%85%B3%E6%B3%A8&cacheSeconds=3600)](https://space.bilibili.com/8924896)

</div>

## 快速上手

### 1.配置文件

resources下创建application.yaml

```yaml
spring:
  #mysql
  datasource:
    url: jdbc:mysql://localhost:3306/socket?serverTimezone=Asia/Shanghai&characterEncoding=utf-8&useSSL=false
    username: root
    password: root
    hikari:
      connection-timeout: 60000
      validation-timeout: 3000
      idle-timeout: 60000
      login-timeout: 5
      max-lifetime: 60000
      maximum-pool-size: 10
      minimum-idle: 10
      read-only: false
  jpa:
    properties:
      hibernate:
        dialect: club.ovelya.socketsystem.config.MyDialect
        hbm2ddl:
          #自动创建表，模式：create create-drop update validate none
          auto: update
    open-in-view: true
  data:
    #redis
    redis:
      host: localhost
      port: 6379
      password:
  mail:
    host: smtp.qq.com
    username: xxx@qq.com
    password: 授权码
    default-encoding: UTF-8
server:
  port: 8080
base-url: http://127.0.0.1
aes-password:
```

### 2.安装MySQL和Redis

安装完成后创建数据库`CREATE DATABASE socket;`