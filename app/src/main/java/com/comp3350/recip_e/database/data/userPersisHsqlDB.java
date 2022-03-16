package com.comp3350.recip_e.database.data;

import com.comp3350.recip_e.database.IuserManager;
import com.comp3350.recip_e.objects.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userPersisHsqlDB implements IuserManager {
    private final String dbpath;

    public userPersisHsqlDB(String dbpath){ this.dbpath=dbpath;}

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:"+ dbpath + ";shutdown=true", "SA", "");
    }

    private User fromResultSet(ResultSet rs) throws SQLException{
        final int id=rs.getInt("userId");
        final String name=rs.getString("userName");
        final String password=rs.getString("userPassword");

        User userObj= new User(id,name,password);

        return userObj;
    }
    private void sqlSetHelper(String prepSt, User usr){
        try(Connection c= connection()) {
            final PreparedStatement st=c.prepareStatement(prepSt);
            st.setInt(1,usr.getUserID());
            st.setString(2,usr.getUserName());
            st.setString(3,usr.getUserPassword());

            st.executeUpdate();
        }catch (final SQLException e){
            throw new hsqlDBException(e);
        }

    }

    public User insertUser(User user) {
        String prepSt = "INSERT INTO Users VALUE(?,?,?)";
        sqlSetHelper(prepSt, user);

        return user;
    }

    public User updateUser(User user){
        String prepSt= "UPDATE Users SET userName =?,userPassword =? where UserId=?";
        sqlSetHelper(prepSt,user);

        return user;
    }

    public User selectUser(int userId){
        try(Connection c=connection()){
            final PreparedStatement st=c.prepareStatement("SELECT * FROM Users WHERE UserId=?");
            st.setInt(1,userId);
            final ResultSet rs=st.executeQuery();

            User usr=fromResultSet(rs);

            return usr;

        }catch (final SQLException e){
            throw new hsqlDBException(e);
        }
    }

}
