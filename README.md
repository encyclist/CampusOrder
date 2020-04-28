# 校园点餐系统

## 用到的技术
* 持久层框架 [mybatis](https://www.w3cschool.cn/mybatis/)
* 数据库 [MySQL](https://www.runoob.com/mysql/mysql-tutorial.html)
* 框架 [SpringBoot](https://baike.baidu.com/item/Spring%20Boot/20249767?fr=aladdin)
* [Ajax](https://developer.mozilla.org/zh-CN/docs/Web/Guide/AJAX)

## 简述
使用MySQL作为数据库，使用mybatis来操作mysql，整体以SpringBoot为结构。

程序一共有四层：controller、service、dao、mapper：
* controller：接受请求，作为和手机端交互的入口
* service：逻辑处理，主要的处理部分
* dao：供service层操作数据库
* mapper：里面有SQL语句，直接操作数据库

## 老师可能提的问题
* 网页怎么和后台通信：使用Ajax进行请求
* 请求的处理流程：controller收到请求，验证传来的数据是否正确，service进行处理并调用dao来操作数据库，dao和mapper是对应的，mapper里的sql语句就会执行，来完成对数据的增删改查
* 上传的图片相关：保存到硬盘上，用户就能直接访问了
* (待续)