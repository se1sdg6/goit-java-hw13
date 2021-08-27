package com;

import java.io.IOException;
import java.net.URI;

public class HttpUtilTest {
    static final String GET_USER_BY_ID_URL = "https://jsonplaceholder.typicode.com/users/";
    static final String GET_USER_BY_USERNAME_URL = "https://jsonplaceholder.typicode.com/users?username=";
    static final String GET_USERS_URL = "https://jsonplaceholder.typicode.com/users";
    static final String CREATE_USER_URL = "https://jsonplaceholder.typicode.com/users";
    static final String UPDATE_USER_URL = "https://jsonplaceholder.typicode.com/users/";
    static final String DELETE_USER_URL = "https://jsonplaceholder.typicode.com/users/";
    static final String USER_POSTS_URL = "https://jsonplaceholder.typicode.com/users/{id}/posts";
    static final String POST_COMMENTS_URL = "https://jsonplaceholder.typicode.com/posts/{id}/comments";
    static final String GET_TODOS_URL = "https://jsonplaceholder.typicode.com/users/{id}/todos";

    public static void main(String[] args) throws IOException, InterruptedException {
//    1. Getting user by ID
        User gettedUserByID = HttpUtil.sendGet(URI.create(String.format("%s%d", GET_USER_BY_ID_URL, 1)));
        System.out.println("Getted user by ID: " + gettedUserByID);

//    2. Getting user by username
        User user = HttpUtil.sendGet(URI.create(String.format("%s%d", GET_USER_BY_ID_URL, 3)));
        User gettedUserByUsername = HttpUtil.sendGetByUsername(URI.create(String.format("%s%s", GET_USER_BY_USERNAME_URL, user.getUsername())));
        System.out.println("Getted user by username: " + gettedUserByUsername.toString());
        System.out.println("\n\n");


//    2.  Getting all users, and print them
        HttpUtil.sendGetWithListOfUsers(URI.create(GET_USERS_URL)).forEach(System.out::println);
        System.out.println("\n\n");

//    3.  Creating user
        User user1 = getDefaultUser();
        User createdUser = HttpUtil.sendPost(URI.create(CREATE_USER_URL), user1);
        System.out.println("User1: " + user1);
        System.out.println("CreatedUser: " + createdUser);
        System.out.println("\n");

//    4.  Updating user
//       Before updating
        System.out.println("Before updating: " + gettedUserByID);

        gettedUserByID.setUsername("Leanne");
        User userPut = HttpUtil.sendPut(URI.create(String.format("%s%s", UPDATE_USER_URL, gettedUserByID.getId())), gettedUserByID);

//        After updating
        System.out.println("After updating:  " + userPut);
        System.out.println();

//    5.  Deleting user
        System.out.println("Delete user: " + HttpUtil.sendDelete(URI.create(String.format("%s%s", DELETE_USER_URL, gettedUserByID.getId())), gettedUserByID));
//    6.  Write into file all comments of last users post
        HttpUtil.sendGetAndWriteLastUsersPostComments(
                URI.create(USER_POSTS_URL.replace("{id}", String.valueOf(gettedUserByID.getId()))),
                POST_COMMENTS_URL,
//                Измените '%YOURUSERNAME%' на имя своего пользователя, и на рабочем столе будет JSON файл.
                "C:\\Users\\%YOURUSERNAME%\\Desktop");

//    7.  Writing not completed todos
        HttpUtil.sendGetAndPrintOpenedTodos(GET_TODOS_URL, gettedUserByID);
    }

    private static User getDefaultUser() {
        User user = new User();
        User.Address address =  user.new Address();
        User.Address.Geo geo = address.new Geo();
        User.Company company = user.new Company();

        address.setStreet("Kulas Light");
        address.setSuite("Apt. 556");
        address.setCity("Gwenborough");
        address.setZipcode("92998-3874");
        address.setGeo(geo);

        geo.setLat(-37.3159);
        geo.setLng(81.1496);

        company.setName("Google LLC");
        company.setCatchPhrase("Don't be evil");
        company.setBs("search service");

        user.setId(23);
        user.setName("Josh Bloch");
        user.setUsername("@joshbloch");
        user.setEmail("joshbloch@gmail.com");
        user.setAddress(address);
        user.setPhone("+380-12-345-67-89");
        user.setWebsite("google.com");
        user.setCompany(company);

        return user;
    }
}
