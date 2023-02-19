package com.example.todolist.todolist;

import com.example.todolist.User.User;
import com.example.todolist.exception.ApiRequestException;
import com.example.todolist.listItem.ListItem;
import com.example.todolist.listItem.ListItemController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/todolist")
public class todolistController {
    private final ToDoListService todoListService;
public todolistController(ToDoListService todoListService){
    this.todoListService=todoListService;
}
    @GetMapping
    public TodoListFormatter getAllLists(@AuthenticationPrincipal User user){
    return new TodoListFormatter(true,this.todoListService.getAllItems(user));
    }

    @PostMapping
    public SingleTodoListFormatter addList(
            @RequestBody TodoListParam request,
            @AuthenticationPrincipal User user
            ){
    TodoList item = this.todoListService.addItem(
            request.name,
            request.description,
            user
    );

    return new SingleTodoListFormatter(
            true,
            item
            );
    }

    @DeleteMapping("{id}")
    public MessageResponse deleteById(
            @AuthenticationPrincipal User user,
            @PathVariable("id") Integer Id
    ){
    if(!todoListService.isAllowed(user,Id))
        throw new ApiRequestException("You don't have the permission to delete this list");
    this.todoListService.deleteById(Id);
    return new MessageResponse(true,"list deleted successfully");
    }

    @GetMapping("{id}")
    public SingleTodoListFormatter fetchItemsById(
            @AuthenticationPrincipal User user,
            @PathVariable("id") Integer id
    ){
        if(!todoListService.isAllowed(user,id))
            throw new ApiRequestException("You don't have the permission to view this list");
        TodoList data = todoListService.getTaskById(id);
        return new SingleTodoListFormatter(
                true,
                data
        );
    }

    @PutMapping("{id}")
    public SingleTodoListFormatter updateById(
            @AuthenticationPrincipal User user,
            @PathVariable("id") Integer Id,
            @RequestBody TodoListParam request
    ){
        if(!todoListService.isAllowed(user,Id))
            throw new ApiRequestException("You don't have the permission to update this list");
        TodoList item = this.todoListService.updateById(Id,request.name,request.description);

        return new SingleTodoListFormatter(
                true,
                item
        );
    }

    record MessageResponse(boolean status, String message){

    }



    record TodoListFormatter(boolean status,List<TodoList> items){

    }

    record TodoListParam(String name,String description){

    }

    record SingleTodoListFormatter(boolean status, TodoList item){

    }


}
