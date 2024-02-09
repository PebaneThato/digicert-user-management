package com.digicert.usermanagement;

import com.digicert.usermanagement.user.User;
import com.digicert.usermanagement.user.UserController;
import com.digicert.usermanagement.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTests {
    private static final String END_POINT_PATH = "/api/v1/users";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void test_addUser_givenInvalidPayload_Returns400BadRequest() throws Exception {
        User newUser = User.builder().firstName("").lastName("").email("").build();
        String requestBody = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(END_POINT_PATH).contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());

    }

    @Test
    public void test_addUser_givenInvalidPayload_Returns201Created() throws Exception {
        User newUser = User.builder().firstName("Thabo").lastName("Pebane").email("thabo.pebane@gmail.com").build();
        String requestBody = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(requestBody))
                .andExpect(status().isCreated())
                .andDo(print());

        Mockito.verify(userRepository, Mockito.times(1)).save(newUser);
    }

    @Test
    public void test_getUser_givenNonExistentUserId_Returns404NotFoundRequest() throws Exception {
        long userId = 105l;
        Optional<User> emptyUser = null;
        String requestUri = END_POINT_PATH + "/" + userId;

        Mockito.when(userRepository.findById(userId)).then(invocation -> Optional.empty());

        mockMvc.perform(get(requestUri))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void test_getUser_givenExistingUserId_Returns200OK() throws Exception {
        long userId = 1l;
        Optional<User> user = Optional.of(User.builder()
                .id(1)
                .firstName("Thato")
                .lastName("Pebane")
                .email("thabo.pebane@gmail.com")
                .build());
        String requestUri = END_POINT_PATH + "/" + userId;

        Mockito.when(userRepository.findById(userId)).then(invocation -> user);

        mockMvc.perform(get(requestUri))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.email").exists())
                .andDo(print());
    }

    @Test
    public void test_getUsers_WhenNoRecords_Returns204NoContent() throws Exception {
        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isNoContent())
                .andDo(print());

        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void test_getUsers_WhenRecordsAvailable_Returns200OK() throws Exception {
        User user1 = User.builder().id(1).firstName("Thato").lastName("Pebane").email("thabo.pebane@gmail.com").build();
        User user2 = User.builder().id(2).firstName("Naleli").lastName("Pebane").email("naleli.pebane@gmail.com").build();
        List<User> users = List.of(user1, user2);
        Mockito.when(userRepository.findAll()).then(invocation -> (Iterable<User>) users);
        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void test_updateUser_givenInvalidPayload_Returns400BadRequest() throws Exception {
        User newUser = User.builder().firstName("").lastName("").email("").build();
        String requestBody = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(put(END_POINT_PATH).contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void test_updateUser_givenInvalidPayload_Returns201Created() throws Exception {
        User newUser = User.builder().id(1).firstName("Thabo").lastName("Pebane").email("thabo.pebane@gmail.com").build();
        String requestBody = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(put(END_POINT_PATH).contentType("application/json").content(requestBody))
                .andExpect(status().isOk())
                .andDo(print());
        Mockito.verify(userRepository, Mockito.times(1)).save(newUser);
    }

    @Test
    public void test_deleteUser_givenExistingUserId_Returns200OK() throws Exception {
        long userId = 1l;
        String requestUri = END_POINT_PATH + "/" + userId;

        mockMvc.perform(delete(requestUri))
                .andExpect(status().isOk())
                .andDo(print());

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userId);
    }

}
