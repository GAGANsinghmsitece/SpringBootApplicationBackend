package com.example.todolist.listItem;

import com.example.todolist.todolist.TodoList;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Lists")
public class ListItem {

    @Id
    @SequenceGenerator(
            name = "list_id_sequence",
            sequenceName = "list_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "list_id_sequence"
    )
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private boolean isCompleted;
    @Column(nullable = false)
    private Date dateCreated;
}
