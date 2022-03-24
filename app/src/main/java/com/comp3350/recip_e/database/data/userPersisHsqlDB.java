package com.comp3350.recip_e.database.data;

import com.comp3350.recip_e.database.iUserManager;
import com.comp3350.recip_e.objects.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userPersisHsqlDB implements iUserManager {
    private final String dbpath;

    public userPersisHsqlDB(String dbpath){ this.dbpath=dbpath;}

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:"+ dbpath + ";shutdown=true", "SA", "");
    }

    private User fromResultSet(ResultSet rs) throws SQLException{
        final String email=rs.getString("userEmail");
        final String name=rs.getString("userName");
        final String password=rs.getString("userPassword");

        return new User(email,name,password);
    }
    private void sqlSetHelper(String prepSt, User usr){
        try(Connection c= connection()) {
            final PreparedStatement st=c.prepareStatement(prepSt);
            st.setString(1,usr.getUserEmail());
            st.setString(2,usr.getUserName());
            st.setString(3,usr.getUserPassword());

            st.executeUpdate();
            st.close();
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
        String prepSt= "UPDATE Users SET userName = ?,userPassword = ? WHERE UserEmail = ?";
        try(Connection c= connection()) {
            final PreparedStatement st=c.prepareStatement(prepSt);
            st.setString(1,user.getUserName());
            st.setString(2,user.getUserPassword());
            st.setString(3,user.getUserEmail());

            st.executeUpdate();
            st.close();
        }catch (final SQLException e){
            throw new hsqlDBException(e);
        }

        return user;
    }

    public User selectUser(String userEmail){
        try(Connection c=connection()){
            final PreparedStatement st=c.prepareStatement("SELECT * FROM Users WHERE UserEmail=?");
            st.setString(1,userEmail);

            final ResultSet rs=st.executeQuery();
            User usr=fromResultSet(rs);

            rs.close();
            st.close();

            return usr;
        }catch (final SQLException e){
            throw new hsqlDBException(e);
        }
    }

    public User verifyUser(String usrEmail, String password){
        User matchedUser= null;
        try(Connection c=connection()){
            final PreparedStatement st=c.prepareStatement("SELECT * FROM User WHERE userEmail =?");
            st.setString(1,usrEmail);

            final ResultSet rt=st.executeQuery();

            if(rt.next()){
                matchedUser=fromResultSet(rt);
                if(!matchedUser.getUserPassword().equals(password)){
                    matchedUser=null;
                }
            }
            rt.close();
            st.close();

            return matchedUser;

        }catch (final SQLException e){
            throw new hsqlDBException(e);
        }
    }
}
