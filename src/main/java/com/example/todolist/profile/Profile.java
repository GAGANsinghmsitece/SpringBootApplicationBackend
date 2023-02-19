package com.example.todolist.profile;

import com.example.todolist.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue
    private Integer Id;


}
