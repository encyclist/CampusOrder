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

## 技术要求
使用Idea进行开发，基于Java8，使用Mysql作为数据库，使用tomcat运行，使用springboot作为框架

## 数据库的设计
管理员表：tb_admin：
| 名称 | 类型(长度)   | 说明 |
| ----- | --------- | ----------- |
| ID | int(11) | 用户ID |
| USERNAME | varchar(255) | 用户名 |
| PASSWORD | varchar(255) | 用户密码 |
| TEL | varchar(255) | 手机号 |
| AUTHORITY | varchar(255) | 权限 |
| STATE | varchar(255) | 状态 |
| DELETE_FLAG | varchar(255) | 是否删除 |

学生表：tb_student：
| 名称 | 类型(长度)   | 说明 |
| ----- | --------- | ----------- |
| id | int(11) | 用户ID |
| tel | varchar(255) | 用户名 |
| password | varchar(255) | 用户密码 |

餐品表：tb_product：
| 名称 | 类型(长度)   | 说明 |
| ----- | --------- | ----------- |
| id | int(11) | ID |
| name | varchar(255) | 餐品名 |
| img | varchar(255) | 图片 |
| description | varchar(255) | 描述 |
| price | decimal(10,2) | 价格（最长10位，小数点后保留两位） |

订单表：tb_order：
| 名称 | 类型(长度)   | 说明 |
| ----- | --------- | ----------- |
| id | int(11) | ID |
| table | varchar(255) | 桌号 |
| price | decimal(10,2) | 订单总价 |
| state | varchar(255) | 支付状态 |

订单与餐品关系表：tb_order_product：
| 名称 | 类型(长度)   | 说明 |
| ----- | --------- | ----------- |
| id | int(11) | ID |
| order_id | int(11) | 订单id |
| product_id | int(11) | 餐品id |
| num | int(11) | 餐品数量 |


## 老师可能提的问题
* 网页怎么和后台通信：使用Ajax进行请求
* 请求的处理流程：controller收到请求，验证传来的数据是否正确，service进行处理并调用dao来操作数据库，dao和mapper是对应的，mapper里的sql语句就会执行，来完成对数据的增删改查
* 上传的图片相关：保存到硬盘上，用户就能直接访问了
* 怎么分页的：在SQL语句后面加上`limit 20,100`意思就是从第20条开始，取之后的100条数据
* (待续)