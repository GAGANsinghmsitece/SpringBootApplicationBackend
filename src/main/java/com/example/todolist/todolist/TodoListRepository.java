package com.example.todolist.todolist;

import com.example.todolist.User.User;
import com.example.todolist.listItem.ListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoListRepository extends JpaRepository<TodoList,Integer> {
List<TodoList> findByUser(User user);

}
