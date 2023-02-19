package com.example.todolist.todolist;

import com.example.todolist.User.User;
import com.example.todolist.listItem.ListItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Data
@Builder
@Entity
@Table(name="TodoList")
@AllArgsConstructor
@NoArgsConstructor
public class TodoList {
  @Id
  @SequenceGenerator(
          name = "todolist_id_sequence",
          sequenceName="todolist_id_sequence",
          allocationSize = 1
  )
  @GeneratedValue(
          strategy = GenerationType.SEQUENCE,
          generator = "todolist_id_sequence"
  )
  private Integer id;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
    private String description;
  @Column(nullable = false)
    private Date dateCreated;
  @Column(nullable = false)
    private boolean isCompleted;
  @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
  @JoinColumn(name = "list__to__task")
  private List<ListItem> listItems=new ArrayList<>();

  @ManyToOne(cascade = CascadeType.DETACH)
  @JoinColumn(name = "user__to__todolist")
  @ToString.Exclude
  @JsonIgnore
  private User user;


  public void addItem(ListItem item){
    this.listItems.add(item);
  }

  public void removeItem(ListItem item){
    this.listItems.remove(item);
  }

}
