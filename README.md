#Bird Admin一套简单通用的后台管理系统
前端使用vue,vuex,vue-router等技术，结合tinymce,element ui等组件。结合表格功能，可以在线编辑，也可以导入导出csv文件。

后端采用Springboot
利用spring jpa，spring cache、shiro等技术实现权限管理，菜单管理等功能。
利用aspcetJ等实现 rt拦截监控。集成cpu、内存、硬盘等监控让你随时了解你的后台运行状态，
集成操作日志记录，记录关键记录修改。


如何更改前端请求后台api的ip和端口
目录:src/api/BASE_URL.js

更改后台服务器的端口
修改application.properties里的server.port

更改上传文件的路径
application.properties bird.file.upload.path

更改mysql地址
application.properties spring.datasource.url

更改日志路径
spring-logback.xml logDir

启动项目
前端:cd bird-admin-front 
nmp install
nmp run serve
后台:更改mysql地址，创建相应的数据库。运行程序会自动生成表。sql/data/提供了示范数据。
程序运行建立好表后，导入示范数据，重启java后台

部分软件截图
![Image text](https://img-blog.csdnimg.cn/20200914173254147.png)
![Image text](https://img-blog.csdnimg.cn/20200914173254143.png)
![Image text](https://img-blog.csdnimg.cn/20200914173254169.png)
![Image text](https://img-blog.csdnimg.cn/20200914173254171.png)
![Image text](https://img-blog.csdnimg.cn/20200914173254161.png)
![Image text](https://img-blog.csdnimg.cn/20200914173254145.png)
![Image text](https://img-blog.csdnimg.cn/20200914173254142.png)
![Image text](https://img-blog.csdnimg.cn/20200914173254130.png)
欢迎加入我们
![Image text](https://img-blog.csdnimg.cn/20200914173253791.jpg)

