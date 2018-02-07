package jp.co.ams.edu.common;

import java.sql.Connection;
/**
 * 2018.01.26
 *
 * データベースアクセスクラス
 *
 */
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

class DataBaseAccess {

    private Connection conn;
    private Statement stmt;
    private PreparedStatement preparSt;
    private ResultSet rs;
    private String driver;
    private String url;
    private String user;
    private String pass;



    /**
     * コンストラクタ
     * @param
     * @throws DB接続情報をセットします
     */
    public DataBaseAccess() {
//        -------------AWS Mysql---------------
        url = "jdbc:mysql://sampledb.ckhex5qfw02h.ap-northeast-1.rds.amazonaws.com:3306/sampledb";
        driver="com.mysql.jdbc.Driver";
        user = "dbuser";
        pass = "password";
//      -------------H2---------------
//      url = "jdbc:h2:tcp://localhost/~/book";
//      driver="org.h2.Driver";
//      user = "sa";
//      pass = "sa";


    }

    /**
     * データベースと接続します
     * @param
     * @return boolean
     * @throws ClassNotFoundException SQLException
     */
    public boolean connectDB() throws SQLException, ClassNotFoundException {
        // Mysql JDBC Driverのロード
        Class.forName(driver);
        // Mysqlに接続
        conn = DriverManager.getConnection(url, user, pass);

        return true;
    }

    /**
     * SQLt抽出処理を行います
     * @param
     * @return ResultSet
     * @throws SQLException
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        // ステートメントを作成
        stmt = conn.createStatement();
        // 問合せの実行
        rs = stmt.executeQuery(sql);
        conn.commit();
        return rs;

    }

    /**
     * SQL更新処理を行います
     * @param
     * @return boolean
     * @throws SQLException
     */
    public boolean executeUpdate(String sql) throws SQLException {
        // ステートメントを作成
        stmt = conn.createStatement();
        // 問合せの実行
        stmt.executeUpdate(sql);
        return true;

    }

    /**
     * SQL抽出処理を行います
     * @param
     * @return ResultSet
     * @throws SQLException
     */
    public ResultSet executeQuery(String sql,List<String> pramlist) throws SQLException {

        // ステートメントを作成
        preparSt = conn.prepareStatement(sql);
        //パラメータをセットします
        int i=1;
        for (String str:pramlist) {
            preparSt.setString(i, str);
            i++;
        }

        // 問合せの実行
        rs = preparSt.executeQuery();

        return rs;

    }

    /**
     * SQL更新処理を行います
     * @param
     * @return boolean
     * @throws SQLException
     */
    public boolean executeUpdate(String sql,List<String> pramlist) throws SQLException {
        // ステートメントを作成
        preparSt =  conn.prepareStatement(sql);
        // 問合せの実行
        preparSt.executeUpdate(sql);
        return true;

    }

    /**
     * データベースと接続をクローズします
     * @param
     * @return void
     * @throws SQLException
     */
    public void closeDB() throws SQLException {
        // 結果セットをクローズ
        if (!rs.equals(null)) {
            rs.close();
        }
        // ステートメントをクローズ
        stmt.close();
        // 接続をクローズ
        conn.close();

    }

}