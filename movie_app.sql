/*
 Navicat Premium Dump SQL

 Source Server         : MovieAppMySQL
 Source Server Type    : MySQL
 Source Server Version : 100432 (10.4.32-MariaDB)
 Source Host           : localhost:3306
 Source Schema         : movie_app

 Target Server Type    : MySQL
 Target Server Version : 100432 (10.4.32-MariaDB)
 File Encoding         : 65001

 Date: 16/05/2025 23:41:52
*/

CREATE DATABASE IF NOT EXISTS movie_app CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE movie_app;

-- 1. Người dùng
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 2. Danh sách phim yêu thích (movie_id lấy từ API như TMDB)
CREATE TABLE favorites (
    user_id INT NOT NULL,
    movie_id INT NOT NULL, -- ID phim từ API
    movie_title VARCHAR(255), -- optional
    poster_url VARCHAR(255),  -- optional

    PRIMARY KEY(user_id, movie_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 3. Lịch sử xem
CREATE TABLE watch_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    movie_id INT NOT NULL,
    watched_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 4. Bình luận
CREATE TABLE comments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    movie_id INT NOT NULL,
    content TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 5. Đánh giá sao
CREATE TABLE ratings (
    user_id INT NOT NULL,
    movie_id INT NOT NULL,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    rated_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY(user_id, movie_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
