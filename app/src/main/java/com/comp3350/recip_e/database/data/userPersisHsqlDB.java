package com.comp3350.recip_e.database.data;

import com.comp3350.recip_e.database.IuserManager;
import com.comp3350.recip_e.objects.Users;

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

    private Users fromResultSet(ResultSet rs) throws SQLException{
        final int id=rs.getInt("userId");
        final String name=rs.getString("userName");
        final String password=rs.getString("userPassword");

        Users userObj= new Users(id,name,password);

        return userObj;
    }
    private void sqlSetHelper(String prepSt,Users usr){
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

    public Users insertUser(Users user) {
        String prepSt = "INSERT INTO Users VALUE(?,?,?)";
        sqlSetHelper(prepSt, user);

        return user;
    }

    public Users updateUser(Users user){
        String prepSt= "UPDATE Users SET userName =?,userPassword =? where UserId=?";
        sqlSetHelper(prepSt,user);

        return user;
    }
}
