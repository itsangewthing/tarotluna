// package com.tarotluna.tarotluna.repository;
// private static final String SQL_INSERT_RECIPE = "insert into recipe (name, category, country, instructions, youtubeLink, user_id) values (?, ?, ?, ?, ?, ?);";
//     private static final String SQL_INSERT_INGREDIENT = "insert into ingredient (name, measurement, recipe_id) values (?, ?, ?);";
//     private static final String SQL_SELECT_RECIPE_BY_ID = "select * from recipe where recipe_id = ?;";
//     private static final String SQL_SELECT_RECIPE_BY_USER_ID = "select * from recipe where user_id = ?;";
//     private static final String SQL_SELECT_USERNAME_BY_RECIPE_ID = 
//     """
//     select u.username
//     from user as u
//     join recipe as r
//     on u.user_id = r.user_id
//     where r.recipe_id = ?;
//     """;
//     private static final String SQL_SELECT_RECIPE_BY_NAME = "select * from recipe where name like ?;";
//     private static final String SQL_SELECT_RECIPE_BY_CATEGORY = "select * from recipe where category like ?;";
//     private static final String SQL_SELECT_RECIPE_BY_AREA = "select * from recipe where country like ?;";
//     private static final String SQL_DELETE_RECIPE_BY_ID = "delete from recipe where recipe_id = ?;";
//     private static final String SQL_UPDATE_RECIPE = 
//     """
//     update recipe set name = ?, category = ?, country = ?, instructions = ?, youtubeLink = ?
//     where recipe_id = ? and user_id = ?;
//     """;

//     private static final String SQL_DELETE_INGREDIENTS_BY_RECIPEID = "delete from ingredient where recipe_id = ?;";
//     private static final String SQL_SELECT_INGREDIENTS_BY_RECIPE_ID = "select * from ingredient where recipe_id = ?;";
//     private static final String SQL_INSERT_THUMBNAIL = "insert into thumbnail (thumbnail_image, recipe_id) values (?, ?);";
//     private static final String SQL_DELETE_THUMBNAIL_BY_RECIPEID = "delete from thumbnail where recipe_id = ?;";
//     private static final String SQL_SELECT_THUMBNAIL_BY_RECIPE_ID = "select * from thumbnail where recipe_id = ?;";