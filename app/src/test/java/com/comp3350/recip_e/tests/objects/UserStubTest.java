package com.comp3350.recip_e.tests.objects;

import com.comp3350.recip_e.database.data.UserStub;
import com.comp3350.recip_e.objects.User;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserStubTest {

    String testName="Dave";
    String testEmail="evad@mock.com";
    String testPw="DaveEvad414";
    UserStub testUserStub;

    @Before
    public void setUp(){testUserStub=new UserStub();}

    @Test
    public void testCreateUser(){
        User testUser= new User(testEmail,testName,testPw);
        System.out.println("Beginning User stub database Tests\n");
        System.out.println("Testing User object creation\n");
        assertNotNull("User object did not create.",testUser);
        assertEquals("User Name does not match",testName,testUser.getUserName());
        assertEquals("User Email does not match",testEmail,testUser.getUserEmail());
        assertEquals("User Password does not match",testPw,testUser.getUserPassword());

        System.out.println("Finished testing User object Creation\n");

    }

    @Test
    public void testUserInsertion(){
        User user1=new User(testEmail,testName,testPw);
        User user2=new User("ray@lei.com","Ray","bigstep4490");
        System.out.println("Testing user insertion function.");

        assertNotNull("User object one did not create.",user1);
        assertNotNull("User object two did not create.",user2);
        assertEquals("User object one does not match",user1,testUserStub.insertUser(user1));
        assertEquals("Stub User list should have 4 users",4,testUserStub.userListSize());
        assertEquals("User object two does not match",user2,testUserStub.insertUser(user2));
        assertEquals("Stub User list should have 5 users",5,testUserStub.userListSize());

        System.out.println("Finish testing User insertion\n");
    }

    @Test
    public void testSelectUser(){
        User user1=new User(testEmail,testName,testPw);
        User user2=testUserStub.selectUser("dogge@hot.com");
        User userNull=testUserStub.selectUser("kitty@cold.com");
        User existUser=new User("dogge@hot.com","Bob","cool978988");
        System.out.println("Testing User selection");
        testUserStub.insertUser(user1);

        assertNotNull("Selection should not return null object when it dose exist",user2);
        assertNull("Selection should return a null user object when it does not exist",userNull);
        assertEquals("Select user method should return the same user1 object",user1,testUserStub.selectUser(user1.getUserEmail()));
        assertEquals("TSelect user method should return the same user2 object",existUser.getUserEmail(),testUserStub.selectUser(user2.getUserEmail()).getUserEmail());

        System.out.println("Finished testing user selection\n");
    }

    @Test
    public void testUpdateUser(){
        User user1= new User(testEmail,testName,testPw);
        System.out.println("Testing User update.");
        assertNotNull("User1 did not insert into stub database.",testUserStub.insertUser(user1));
        user1.setUserName("Vail");

        User updatedUser=testUserStub.updateUser(user1);
        assertNotNull("Return of the update user did not work.",updatedUser);
        assertEquals("The information did not update.",testUserStub.selectUser(testEmail),user1);

        System.out.println("Finished testing user update.\n");
    }

    @Test
    public void testVerifyUser(){
        User user1=new User(testEmail,testName,testPw);
        System.out.println("Testing User Verification.");

        assertNotNull("User1 did not insert into stub database.",testUserStub.insertUser(user1));
        User verifiedUser=testUserStub.verifyUser(user1.getUserEmail(),user1.getUserPassword());
        assertNotNull("The result should not be Null.",verifiedUser);
        assertEquals("Verified user should be the same",user1,verifiedUser);
        verifiedUser=testUserStub.verifyUser(user1.getUserEmail(),"wrongPassword");
        assertNull("Verified object should be null",verifiedUser);

        System.out.println("Finished testing user verification.\n");
    }
}
