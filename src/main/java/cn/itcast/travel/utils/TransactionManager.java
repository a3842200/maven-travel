package cn.itcast.travel.utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 手动管理事务 。。
 */
public class TransactionManager {

    //  Connection   dao   cud   所有dao 使用同一个Connection
    final static ThreadLocal<Connection> local = new ThreadLocal<Connection>();



    //  开启事务
      public  static  void  begin(){
         Connection connection = getCurrentConnection();
          try {
              connection.setAutoCommit(false);
          } catch (SQLException e) {
              e.printStackTrace();
          }

      }

      // 回滚
    public  static void rollback(){

          Connection con = local.get();
        try {
            con.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void commit(){
        Connection connection = local.get();
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            local.remove();
        }

    }

    public static Connection getCurrentConnection(){
        Connection connection = local.get();
        if(connection==null){
            connection = C3p0Utils.getConnection();
            local.set(connection);//  connection 绑定到当前线程中
        }
        return connection;
    }



}
