package com;

import java.util.Objects;

public class Post {
    private int userId;
    private int id;
    private String body;
    private String title;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return userId == post.userId && id == post.id && Objects.equals(body, post.body) && Objects.equals(title, post.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, id, body, title);
    }

    @Override
    public String toString() {
        return "Post{" +
                "userId=" + userId +
                ", id=" + id +
                ", body='" + body + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public static class Comment {
        private int postId;
        private int id;
        private String name;
        private String email;
        private String body;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Comment comment = (Comment) o;
            return postId == comment.postId && id == comment.id && Objects.equals(name, comment.name) && Objects.equals(email, comment.email) && Objects.equals(body, comment.body);
        }

        @Override
        public int hashCode() {
            return Objects.hash(postId, id, name, email, body);
        }

        @Override
        public String toString() {
            return "Comment{" +
                    "postId=" + postId +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", body='" + body + '\'' +
                    '}';
        }

        public int getPostId() {
            return postId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }
}
