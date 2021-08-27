package com;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.*;
import java.net.URI;
import java.net.http.*;

public class HttpUtil {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();

    public static User sendGet(URI uri) throws IOException, InterruptedException {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static User sendGetByUsername(URI uri) throws IOException, InterruptedException {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<User> users = GSON.fromJson(response.body(), new TypeToken<List<User>>() {
        }.getType());
        return users.get(0);
    }

    public static List<User> sendGetWithListOfUsers(URI uri) throws IOException, InterruptedException {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return GSON.fromJson(response.body(), new TypeToken<List<User>>() {
        }.getType());
    }

    public static User sendPut(URI uri, User user) throws IOException, InterruptedException {
        final String requestUser = GSON.toJson(user);
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .PUT(HttpRequest.BodyPublishers.ofString(requestUser))
                .header("Content-type", "application/json; charset=UTF-8")
                .build();
        final HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static User sendPost(URI uri, User user) throws IOException, InterruptedException {
        final String requestUser = GSON.toJson(user);
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(requestUser))
                .header("Content-type", "application/json; charset=UTF-8")
                .build();
        final HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static int sendDelete(URI uri, User user) throws IOException, InterruptedException {
        final String requestBody = GSON.toJson(user);
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .method("DELETE", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }

    public static List<Post> sendGetWithListOfPosts(URI uri) throws IOException, InterruptedException {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return GSON.fromJson(response.body(), new TypeToken<List<Post>>() {
                }.getType());
    }

    public static void sendGetAndWriteLastUsersPostComments(URI posts, String comments, String fileDirectory) throws IOException, InterruptedException {
        List<Post> postsList = sendGetWithListOfPosts(posts);
        postsList.sort(Comparator.comparing(Post::getId));
        Post lastPost = postsList.get(postsList.size() - 1);

        URI commentsUri = URI.create(comments.replace("{id}", String.valueOf(lastPost.getId())));
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(commentsUri)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());

        String filePath = String.format("%s%s", fileDirectory + "\\",
                String.format("%s%d%s%d%s", "user-", lastPost.getUserId(), "-post-", lastPost.getId(), "-comments.json"));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendGetAndPrintOpenedTodos(String todos, User user) throws IOException, InterruptedException {
        URI todoUri = URI.create(todos.replace("{id}", String.valueOf(user.getId())));
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(todoUri)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        List<Todo> todosList = GSON.fromJson(response.body(), new TypeToken<List<Todo>>() {
        }.getType());

        for (Todo todo : todosList) {
            if (!todo.isCompleted()) {
                System.out.println(todo);
            }
        }
    }
}
