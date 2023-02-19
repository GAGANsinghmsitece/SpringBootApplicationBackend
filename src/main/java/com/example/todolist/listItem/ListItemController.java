package com.example.todolist.listItem;

import com.example.todolist.User.User;
import com.example.todolist.exception.ApiRequestException;
import com.example.todolist.todolist.ToDoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Stack;

@RestController
@RequestMapping("api/v1/tasklist")
@RequiredArgsConstructor
public class ListItemController {
    private final ListService listService;
    private final ToDoListService toDoListService;

    @GetMapping
    public ItemGenerator getAllItems(@AuthenticationPrincipal User user){
        List<ListItem> items = this.listService.getAllItems(user);
        return new ItemGenerator(
                true,
                items
        );
    }



    @PostMapping
    public SingleItemGenerator addTask(
           @AuthenticationPrincipal User user,
           @RequestBody AddListParam request){
        if(!toDoListService.isAllowed(user,request.todolistId))
            throw new ApiRequestException("You don't have permission to add a task in this list");
        ListItem item = this.listService.addItem(request.name, request.todolistId);
        return new SingleItemGenerator(
                true,
                item
        );
    }

    @DeleteMapping("{id}")
    public MessageResponse deleteTaskById(
            @AuthenticationPrincipal User user,
            @PathVariable("id") Integer Id,
            @RequestBody Integer listId
    ){
        if(!listService.isAllowed(user,listId,Id)){
            throw new ApiRequestException("You don't have permission to delete this task");
        }
        this.listService.deleteById(Id);
        return new MessageResponse(
                true,
                "The task is deleted successfully!!!");
    }

    @PutMapping("{id}")
    public SingleItemGenerator updateTaskById(
            @AuthenticationPrincipal User user,
            @PathVariable("id") Integer Id,
            @RequestBody() UpdateParam request
    ){
        if(!listService.isAllowed(user,request.listId,Id)){
            throw new ApiRequestException("You don't have permission to delete this task");
        }
        ListItem updated_task = this.listService.updateItem(Id,request.name,request.isCompleted);
        return new SingleItemGenerator(
                true,
                updated_task
        );
    }

    record ItemGenerator(boolean status, List<ListItem> items){

    }

    record UpdateParam(String name,boolean isCompleted, Integer listId){}

    record AddListParam(String name, Integer todolistId){

    }

    record SingleItemGenerator(boolean status, ListItem items){

    }

    record MessageResponse(Boolean status, String message){

    }
}
