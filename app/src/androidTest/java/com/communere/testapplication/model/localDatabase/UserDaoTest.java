package com.communere.testapplication.model.localDatabase;

import android.content.Context;

import com.communere.testapplication.LiveDataUtilAndroidTest;
import com.communere.testapplication.model.Bean.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {
    private UserDao userDao;
    private UserDatabase db;


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();

    //**************************************************************************************************
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase.class).build();
        userDao = db.getUserDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }


    //**************************************************************************************************
    //insert
    @Test
    public void insertUserAndReadInList() throws Exception {
        User user = new User("roya pashayi", "ropa", "roya@gmail.com", "Roya123", null);
        userDao.insert(user).blockingGet();


        List<User> users = LiveDataUtilAndroidTest.getOrAwaitValue(userDao.getAllUsers());
        assertThat(users).contains(user);
    }


    //**************************************************************************************************
    //update
    @Test
    public void updateUser() throws Exception {
        User user = new User("roya pashayi", "ropa", "roya@gmail.com", "Roya123", null);
        user.setId((long) 1);
        userDao.insert(user).blockingGet();

        User updatedUser = new User("roya pashayi", "roya", "roya_pashayi@gmail.com", "Roya123", null);
        updatedUser.setId((long) 1);
        userDao.update(updatedUser).blockingGet();


        User fetchedUser = LiveDataUtilAndroidTest.getOrAwaitValue(userDao.getUser(1));

        assertThat(fetchedUser).isEqualTo(updatedUser);
    }


    //**************************************************************************************************
    //delete

    @Test
    public void deleteUser() throws Exception {
        User user = new User("roya pashayi", "ropa", "roya@gmail.com", "Roya123", null);
        user.setId((long) 1);
        userDao.insert(user).blockingGet();
        userDao.delete(user).blockingGet();


        List<User> users = LiveDataUtilAndroidTest.getOrAwaitValue(userDao.getAllUsers());
        assertThat(users).doesNotContain(user);
    }

    @Test
    public void deleteAllUsers() throws Exception {
        User user1 = new User("roya pashayi", "ropa", "roya@gmail.com", "Roya123", null);
        User user2 = new User("parisa p", "parisa", "parisa@gmail.com", "Parisa123", null);
        User user3 = new User("roza", "ropa", "roza@gmail.com", "Roza123", null);

        userDao.insert(user1).blockingGet();
        userDao.insert(user2).blockingGet();
        userDao.insert(user3).blockingGet();


        userDao.deleteAllUsers().blockingAwait();

        List<User> users = LiveDataUtilAndroidTest.getOrAwaitValue(userDao.getAllUsers());
        assertThat(users).isEmpty();
    }


    //**************************************************************************************************
    //count users

    @Test
    public void getUserCount() throws Exception {
        User user1 = new User("roya pashayi", "ropa", "roya@gmail.com", "Roya123", null);
        User user2 = new User("parisa p", "parisa", "parisa@gmail.com", "Parisa123", null);
        User user3 = new User("roza", "ropa", "roza@gmail.com", "Roza123", null);

        userDao.insert(user1).blockingGet();
        userDao.insert(user2).blockingGet();
        userDao.insert(user3).blockingGet();


        int count = userDao.getUserCount("ropa").blockingGet();

        assertThat(count).isEqualTo(2);
    }



    //**************************************************************************************************
    //get user
    @Test
    public void getUserByID() throws Exception {
        User user = new User("roya pashayi", "ropa", "roya@gmail.com", "Roya123", null);
        user.setId((long) 1);


        userDao.insert(user).blockingGet();


        User fetchedUser = LiveDataUtilAndroidTest.getOrAwaitValue(userDao.getUser(1));
        assertThat(fetchedUser).isEqualTo(user);
    }


    @Test
    public void getUserByUsernameAndPassword() throws Exception {
        User user = new User("roya pashayi", "ropa", "roya@gmail.com", "Roya123", null);
        user.setId((long) 1);


        userDao.insert(user).blockingGet();


        User fetchedUser = userDao.getUser("ropa", "Roya123").blockingGet();
        assertThat(fetchedUser).isEqualTo(user);
    }



    @Test
    public void getUserByEmailAndPassword_ShouldReturnTrue() throws Exception {
        User user = new User("roya pashayi", "ropa", "roya@gmail.com", "Roya123", null);
        user.setId((long) 1);


        userDao.insert(user).blockingGet();


        User fetchedUser = userDao.getUser("roya@gmail.com", "Roya123").blockingGet();
        assertThat(fetchedUser).isEqualTo(user);
    }


}