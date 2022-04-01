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

    public userPersisHsqlDB(String dbpath){
        this.dbpath=dbpath;}

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
            st.setString(1,usr.getEmail());
            st.setString(2,usr.getUsername());
            st.setString(3,usr.getPassword());

            st.executeUpdate();
            st.close();
        }catch (final SQLException e){
            throw new hsqlDBException(e);
        }

    }

    @Override
    public User insertUser(User user) {
        String prepSt = "INSERT INTO USERS VALUE(?,?,?)";
        sqlSetHelper(prepSt, user);

        return user;
    }

    @Override
    public User updateUser(User user){
        String prepSt= "UPDATE USERS SET userName = ?,userPassword = ? WHERE UserEmail = ?";
        try(Connection c= connection()) {
            final PreparedStatement st=c.prepareStatement(prepSt);
            st.setString(1,user.getUsername());
            st.setString(2,user.getPassword());
            st.setString(3,user.getEmail());

            st.executeUpdate();
            st.close();
        }catch (final SQLException e){
            throw new hsqlDBException(e);
        }

        return user;
    }

    @Override
    public User selectUser(String userEmail){
        try(final Connection c=connection()){
            final PreparedStatement st=c.prepareStatement("SELECT * FROM USERS WHERE userEmail=?");
            st.setString(1,userEmail);

            final ResultSet rs=st.executeQuery();
            User usr = null;
            if(rs.next()) {
                usr = fromResultSet(rs);
            }

            rs.close();
            st.close();

            return usr;
        }catch (final SQLException e){
            throw new hsqlDBException(e);
        }
    }

    @Override
    public User verifyUser(String usrEmail, String password){
        User matchedUser= null;
        try(Connection c=connection()){
            final PreparedStatement st=c.prepareStatement("SELECT * FROM USERS WHERE userEmail =?");
            st.setString(1,usrEmail);

            final ResultSet rt=st.executeQuery();

            if(rt.next()){
                matchedUser=fromResultSet(rt);
                if(!matchedUser.getPassword().equals(password)){
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

    private boolean existChecker(String query,String key){
        boolean exist=false;
        try(Connection c=connection()){
            final PreparedStatement st=c.prepareStatement(query);
            st.setString(1,key);

            final ResultSet rt=st.executeQuery();
            if(rt.next()){
                exist=true;
            }
        }catch (final SQLException e){
            throw new hsqlDBException(e);
        }

        return exist;
    }

    public boolean usernameExists(String username){
       final String nameCheck="SELECT * FROM USERS WHERE userName = ?";
        return existChecker(nameCheck,username);
    }

    public boolean emailExists(String email){
        final String emailCheck="SELECT * FROM USERS WHERE UserEmail = ?";
        return existChecker(emailCheck,email);
    }


}
