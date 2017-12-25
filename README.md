####内容包括：
- 一、eureka注册中心
- 二、eureka客户端（provider)
- 三、消费端
- 四、读写分离（多数据源）
- 五、jwt+redis+token的验证
- 六、swagger2文档编写
- 七、redis等封装好了很多工具包
- 八、springboot各种用户的技巧
- 九、框架各处规则（崇尚直接通过代码去规范）
- 十、未来计划：爬虫、聊天等框架



###Swageger2常用注解 [更多...](http://blog.csdn.net/u014231523/article/details/76522486)
- @Api()用于类； 
表示标识这个类是swagger的资源 。例@Api(value="用户controller",tags={"用户操作接口"})：但是tags如果有多个值，会生成多个list
- @ApiOperation()用于方法； 
表示一个http请求的操作 
- @ApiParam()用于方法，参数，字段说明； 
表示对参数的添加元数据（说明或是否必填等） 
- @ApiModel()用于类 
表示对类进行说明，用于参数用实体类接收 
- @ApiModelProperty()用于方法，字段 
表示对model属性的说明或者数据操作更改 
- @ApiIgnore()用于类，方法，方法参数 
表示这个方法或者类被忽略 
- @ApiImplicitParam() 用于方法 
表示单独的请求参数 
- @ApiImplicitParams() 用于方法，包含多个 @ApiImplicitParam

