from pymysql import Connection
def mysql_conn():
    #连接数据库用
    #conn = Connection(host='124.70.27.180 ',user='root',password='ZYXxx444///,.99',port=3306,database='book')
    conn = MySQLdb.connect(host = '124.70.27.180',  # 远程主机的ip地址，
                       user = 'root',   # MySQL用户名
                       db = 'book',   # database名
                       passwd = 'ZYXxx444///,.99',   # 数据库密码
                       port = 3306,  #数据库监听端口，默认3306
                       charset = "utf8")  #指定utf8编码的连接
    cursor=conn.cursor()
#往名为l的表格中插入姓名和对应年龄
    sql='select *from book_category;'

#插入内容写好后要进行提交
    cursor.execute(sql)

# 数据库事务的提交
    conn.commit()

#测试是否提交成功
    print('插入成功！')

#提取表中第一个内容
# print(cursor.fetchone())
#如果提取过第一个内容，则是提取前三个
# print(cursor.fetchmany(3))
#如运行过前两个，则显示除提取后剩下的全部
# print(cursor.fetchall())

#结束关闭 cursor  connection
    cursor.close()
    conn.close()

if __name__ =='__main__':
    mysql_conn()
