package com.parkinglot.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.parkinglot.entities.User;
import com.parkinglot.repositories.UserRepository;

public class UserServiceTest {
     
    @InjectMocks
    private UserService userService;
     
    @Mock
    private UserRepository userRepository;
 
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
     
    @Test
    public void getAllUsersTest()
    {
        List<User> list = new ArrayList<User>();
        User user1 = new User(1l, "Rakesh", "9028208008");
        User user2 = new User(2l, "Pankaj", "7671230891");
        User user3 = new User(3l, "Amit", "9123034455");
        
        list.add(user1);
        list.add(user2);
        list.add(user3);
         
        when(userRepository.findAll()).thenReturn(list);
         
        List<User> empList = userService.getAllUsers();
         
        assertEquals(list.size(), empList.size());
        verify(userRepository, times(1)).findAll();
    }
    
    @Test
    public void createUserTest01() throws Exception
    {
        User user1 = new User(1l, "", "9028208008");
        when(userRepository.save(user1)).thenReturn(user1);
        
        Exception exception=null;
        try {
        	userService.registerUser(user1);
        }catch(Exception ex) {
        	exception = ex;
        }
    	assertNotNull(exception);
        verify(userRepository, times(0)).save(user1);
    }
    
    @Test
    public void createUserTest02() throws Exception
    {
        User user1 = new User(1l, "Rakesh", "");
        when(userRepository.save(user1)).thenReturn(user1);
        
        Exception exception=null;
        try {
        	userService.registerUser(user1);
        }catch(Exception ex) {
        	exception = ex;
        }
        assertNotNull(exception);
        verify(userRepository, times(0)).save(user1);
    }
    
    @Test
    public void createUserTest03() throws Exception
    {
        User user1 = new User(1l, "Rakesh", "9028208008");
        when(userRepository.save(user1)).thenReturn(user1);
        
        Exception exception=null;
        try {
        	userService.registerUser(user1);
        }catch(Exception ex) {
        	exception = ex;
        }
        assertNull(exception);
        verify(userRepository, times(1)).save(user1);
    }
}