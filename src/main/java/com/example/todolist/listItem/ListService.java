package com.example.todolist.listItem;

import com.example.todolist.User.User;
import com.example.todolist.todolist.ToDoListService;
import com.example.todolist.todolist.TodoList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListService {
    private final ListItemRepository listItemRepository;
    private final ToDoListService toDoListService;

    public boolean isAllowed(User user, Integer listId, Integer taskId){
        if(!toDoListService.isAllowed(user,listId))
            return false;
        TodoList item = toDoListService.getTaskById(listId);
        List<ListItem> tasklist = item.getListItems();
        return tasklist.stream().anyMatch(data->data.getId().equals(taskId));
    }


    public List<ListItem> getAllItems(User user){
        return this.listItemRepository.findAll();
    }

    public ListItem addItem(String name, Integer todoListId){
        TodoList main_list = this.toDoListService.getById(todoListId);
        ListItem item = ListItem
                .builder()
                .name(name)
                .isCompleted(false)
                .dateCreated(new Date())
                .build();
        this.listItemRepository.save(item);
        this.toDoListService.AddItemToList(item,todoListId);
        return item;
    }

    public ListItem updateItem(Integer Id,String name,boolean isCompleted){
        ListItem current_task = this.listItemRepository.findById(Id).get();
        ListItem updated_task = ListItem
                .builder()
                .id(current_task.getId())
                .name(name)
                .isCompleted(isCompleted)
                .dateCreated(current_task.getDateCreated())
                .build();
        this.listItemRepository.save(updated_task);
        return updated_task;
    }

    public void deleteById(Integer Id){
        this.listItemRepository.deleteById(Id);
    }
}
