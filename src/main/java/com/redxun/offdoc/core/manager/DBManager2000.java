package com.redxun.offdoc.core.manager;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DBManager2000 {

  public String ClassString=null;
  public String ConnectionString=null;
  public String UserName=null;
  public String PassWord=null;

  public Connection Conn;
  public Statement Stmt;


  public DBManager2000() {


    //For ODBC
    //ClassString="sun.jdbc.odbc.JdbcOdbcDriver";
    //ConnectionString=("jdbc:odbc:DBDemo");
    //UserName="dbdemo";
    //PassWord="dbdemo";


    //For Access Driver
    //ClassString="sun.jdbc.odbc.JdbcOdbcDriver";
    //ConnectionString=("jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb)};DBQ=C:\\dbdemo.mdb;ImplicitCommitSync=Yes;MaxBufferSize=512;MaxScanRows=128;PageTimeout=5;SafeTransactions=0;Threads=3;UserCommitSync=Yes;").replace('\\','/');

    //For SQLServer Driver
    //ClassString="com.microsoft.jdbc.sqlserver.SQLServerDriver";
    //ConnectionString="jdbc:microsoft:sqlserver://127.0.0.1:1433;DatabaseName=DBDemo;User=dbdemo;Password=dbdemo";
    //UserName="dbdemo";
    //PassWord="dbdemo";

    //For Oracle Driver
    //ClassString="oracle.jdbc.driver.OracleDriver";
    //ConnectionString="jdbc:oracle:thin:@hstation:1521:dbdemo";
    //UserName="system";
    //PassWord="manager";

    //For MySQL Driver
    ClassString="org.gjt.mm.mysql.Driver";
    //ConnectionString="jdbc:mysql://localhost/softforum?user=...&password=...&useUnicode=true&characterEncoding=8859_1";
    //ClassString="com.mysql.jdbc.Driver";
    ConnectionString="jdbc:mysql://localhost/dbdemo?user=root&password=123456&useUnicode=true&characterEncoding=utf-8";

    //For Sybase Driver
    //ClassString="com.sybase.jdbc.SybDriver";
    //ConnectionString="jdbc:sybase:Tds:localhost:5007/tsdata"; //tsdataΪ�����ݿ���
    //Properties sysProps = System.getProperties();
    //SysProps.put("user","userid");
    //SysProps.put("password","user_password");
    //If using Sybase then DriverManager.getConnection(ConnectionString,sysProps);
  }

  public boolean OpenConnection()
  {
   boolean mResult=true;
   try
   {
     Class.forName(ClassString);
     if ((UserName==null) && (PassWord==null))
     {
       Conn= DriverManager.getConnection(ConnectionString);
     }
     else
     {
       Conn= DriverManager.getConnection(ConnectionString,UserName,PassWord);
     }
     Conn.setAutoCommit(false);
     Stmt=Conn.createStatement();
     mResult=true;
   }
   catch(Exception e)
   {
     System.out.println(e.toString());
     mResult=false;
   }
   return (mResult);
  }

  public void CloseConnection()
  {
   try
   {
     Stmt.close();
     Conn.close();
   }
   catch(Exception e)
   {
     System.out.println(e.toString());
   }
  }

  public String GetDateTime()
  {
   Calendar cal  = Calendar.getInstance();
   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   String mDateTime=formatter.format(cal.getTime());
   return (mDateTime);
  }

  public  java.sql.Date  GetDate()
  {
    java.sql.Date mDate;
    Calendar cal  = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String mDateTime=formatter.format(cal.getTime());
    return (java.sql.Date.valueOf(mDateTime));
  }

  public int GetMaxID(String vTableName,String vFieldName)
  {
   int mResult=0;
   String mSql=new String();
   mSql = "select max("+vFieldName+")+1 as MaxID from " + vTableName;
   if (OpenConnection())
   {
     try
     {
       ResultSet result=ExecuteQuery(mSql);
       if (result.next())
       {
         mResult=result.getInt("MaxID");
       }
       result.close();
     }
     catch(Exception e)
     {
       System.out.println(e.toString());
     }
     CloseConnection();
   }
   return (mResult);
 }

  public ResultSet ExecuteQuery(String SqlString)
  {
    ResultSet result=null;
    try
    {
      result=Stmt.executeQuery(SqlString);
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
    }
    return (result);
  }

  public int ExecuteUpdate(String SqlString)
  {
    int result=0;
    try
    {
      result=Stmt.executeUpdate(SqlString);
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
    }
    return (result);
  }
}