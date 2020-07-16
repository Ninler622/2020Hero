基于Scrapy框架的爬虫系统，爬取豆瓣图书Top250，爬取了书名、作者、出版社、出版时间、价格、评分、评分人数、名句、封面等信息。
spider_xpath：自行编写的代码类，这部分的代码主要完成解析response并提取item
1. 以初始的URLRequest, 并设置回调函数, 当该requeset下载完毕并返回时, 将生成response, 并作为参数传递给回调函数. spider中初始的request是通过start_requests()来获取的。start_requests()获取start_urls中的URL, 并以parse以回调函数生成Request
　2. 在回调函数内分析返回的网页内容, 可以返回item对象或者Request，返回的Request对象之后会经过Scrapy处理, 下载相应的内容, 并调用设置的callback函数.
　3. 在回调函数, 通过xpath, css等方法获取我们想要的内容生成item
　4. 最后将item传送给pipeline处理

items：定义了书籍信息的item
middlewares：中间件，处理请求
pipelines：当Item 在Spider中被收集之后，就会被传递到Item Pipeline中进行处理。每个item pipeline组件是实现了简单的方法的python类，负责接收到item并通过它执行一些行为，同时也决定此Item是否继续通过pipeline,或者被丢弃而不再进行处理
item pipeline的主要作用：
1.清理html数据
2.验证爬取的数据
3.去重并丢弃
4.将爬取的结果保存到数据库中或文件中

