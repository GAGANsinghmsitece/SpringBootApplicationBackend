package com.example.todolist.listItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ListItemRepository extends JpaRepository<ListItem,Integer> {

}
