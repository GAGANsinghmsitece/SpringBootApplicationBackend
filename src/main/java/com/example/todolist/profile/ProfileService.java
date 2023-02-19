package com.example.todolist.profile;

import com.example.todolist.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public Profile createProfileByUser(User user){
        Profile profile=Profile.builder().build();
        profileRepository.save(profile);
        return profile;
    }
}
