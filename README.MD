# MyBlogsAPI

## 项目介绍
- 项目起源：本人心血来潮想要自己搭建一个属于自己的博客来记录学习成长的过程，也搜索了很多开源博客系统，例如hexo、wordpress等，但要么是静态博客，要么是PHP的博客系统，不符合我的需求，所以就有了这个项目
- 前端项目地址(待上传)

### 功能
- 文章管理
- 标签管理
- 友链管理
- 工具管理
- 基础信息管理
- 后台人机验证
- ICP、安备号填写
- 异常IP封禁
- Web缓存控制

## 环境要求
- jre 1.8
- 大于300M的可用内存

## 使用技术
- SpringBoot
- Mybatis-Plus
- Swagger3
- JWT
- SQLite
- Redis

## 部署
1. 本地运行一个Redis服务，如果需要有更改Redis配置的需要，请自行创建/config/[application.yml](src/main/resources/application.yml)文件并且重写相关Redis配置
2. 下载最新的jar包
3. 运行命令
    ```shell
    java -jar MyBlogsAPI-1.0.1-Release.jar
    ```
4. 返回jar包根目录，打开eula.txt，阅读并同意协议
5. 再次运行jar包
    ```shell
    java -jar MyBlogsAPI-1.0.1-Release.jar
    ```
   
## 使用
- 后端默认路径 `http://localhost:6060/api` 
- 集成swagger3接口文档，通过  `http://localhost:6060/api/doc.html#/` 访问
- 默认账号 `admin` 密码 `123456`
- 忘记密码可去控制台输入 `password` 命令重置密码

## Tips
- 项目默认使用SQLite数据库，数据库文件在jar包根目录下
- 如果你在使用中遇到了任何的问题或者建议，欢迎提出issue，我会尽快解决

## TODO
- [ ] 后台增加日志审查
- [ ] 增加外置配置文件
- [ ] 增加MySQL的版本

## 许可证
这个项目采用了[GPL-3.0 许可证](LICENSE)，详情请查看 `LICENSE` 文件