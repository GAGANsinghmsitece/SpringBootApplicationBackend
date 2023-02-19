package com.example.todolist.todolist;

import com.example.todolist.User.User;
import com.example.todolist.User.UserRepository;
import com.example.todolist.exception.ApiRequestException;
import com.example.todolist.listItem.ListItem;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ToDoListService {
    private final TodoListRepository todoListRepository;
    private final UserRepository userRepository;
    @Transactional
    public List<TodoList> getAllItems(User user){
        return todoListRepository.findByUser(user);
    }

    public boolean isAllowed(User user,Integer id){
        TodoList item = todoListRepository.findById(id).orElseThrow(()->new ApiRequestException("List Not Found!!!"));
        return item.getUser()==user;
    }

    @Transactional
    public TodoList addItem(String name, String description, User user){
        TodoList item = TodoList
                .builder()
                .name(name)
                .description(description)
                .isCompleted(false)
                .dateCreated(new Date())
                .user(user)
                .build();
        this.todoListRepository.save(item);
        return item;
    }

    public TodoList getTaskById(Integer id){
        TodoList item = todoListRepository.findById(id).orElseThrow(()->new EntityNotFoundException("List Not Found"));
        return item;
    }

    public void deleteById(Integer Id){
        this.todoListRepository.deleteById(Id);
    }

    public TodoList getById(Integer Id){
        return this.todoListRepository.findById(Id).get();
    }

    public void AddItemToList(ListItem listItem,Integer listId){
        TodoList todoItem = this.todoListRepository.findById(listId).orElseThrow(()->new EntityNotFoundException("List Not Found!!!"));
        todoItem.addItem(listItem);
        this.todoListRepository.save(todoItem);
    }

    public TodoList updateById(Integer Id, String name, String description){
        TodoList item = this.todoListRepository.findById(Id).orElseThrow(()->new EntityNotFoundException("List Not Found"));
        TodoList updatedItem =  TodoList
                .builder()
                .id(item.getId())
                .name(name)
                .description(description)
                .dateCreated(item.getDateCreated())
                .listItems(item.getListItems())
                .isCompleted(item.isCompleted())
                .build();
        this.todoListRepository.save(updatedItem);
        return item;
    }
}
