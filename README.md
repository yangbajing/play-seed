# play-seed

- 作者：杨景（羊八井)
- 邮箱：yangbajing@gmail.com
- 主页：http://www.yangbajing.me
- 微博：http://weibo.com/yangbajing

- play 2.6
- slick 3.2
- slick-pg 0.15
- scalatest 3.0
- gulp
- redisreact 3.4
- postgresql 9.6

实现了简单的用户认证和`session`控制功能。采用`redis`来保存`session`值。自定义`play action`和`play filter`来判断`session`有效性和重设`session`。`session`使用`cookie`实现。

数据库层，使用`slick`和`slick-pg`来连接`PostgreSQL`。由`typesafe config`来定义连接参数。
`[ps-util/src/main/resources/reference.conf](https://github.com/yangbajing/play-seed/blob/master/ps-util/src/main/resources/reference.conf)`。

定义play监听端口：`PlayKeys.playDefaultPort := 58082`。

修改相关配置后，执行如下命令生成数据库实例并启动服务：

```
./sbt
play-seed-root > ps-data/testOnly me.yangbajing.ps.data.record.SchemasTest
play-seed-root > project ps-web
[ps-web] $ ps-web/run
```

前端代码使用gulp管理，代码放在`static`目录。编译后的静态文件将保存在`public`

```
npm install
npm run build
```

访问：[http://localhost:58082/account/signup](http://localhost:58082/account/signup) 注册账号
