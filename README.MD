# MyBlogsAPI

## 项目介绍
- 该项目是我一时心血来潮写的一个个人博客系统的后端API，集成博客接口、文件接口、后台接口，目前没有做多用户的打算，还只是一个个人博客
- [项目Demo](https://blog.zhenxuna.cn/)
- 前端项目地址(待上传)

## 环境要求
- jre 1.8
- 大于300M的可用内存

## 使用技术
- SpringBoot
- Mybatis-Plus
- Swagger3
- JWT
- SQLite

## 部署
1. 下载最新的jar包
2. 运行命令
    ```shell
    java -jar MyBlogsAPI-1.0-Release.jar
    ```
3. 返回jar包根目录，打开eula.txt，阅读并同意协议
4. 再次运行jar包
    ```shell
    java -jar MyBlogsAPI-1.0-Release.jar
    ```
   
## 使用
- 后端默认路径 `http://localhost:6060/api` 
- 集成swagger3接口文档，通过  `http://localhost:6060/api/doc.html#/` 访问
- 默认账号 `admin` 密码 `123456`
- 忘记密码可去控制台输入 `password` 命令重置密码

## TODO
- [ ] 后台增加日志省察
- [ ] 增加外置配置文件
- [ ] 增加MySQL的版本

## 许可证
这个项目采用了[CC BY-NC 4.0 许可证](LICENSE)，详情请查看 `LICENSE` 文件