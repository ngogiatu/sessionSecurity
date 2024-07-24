package com.ngotu.demo.repo;

import com.ngotu.demo.model.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created at 24/07/2024 by Ngo Tu
 *
 * @author: Ngo Tu
 */
@Repository
public class FaceDataUserRepo {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    List<UserLogin> list = List.of(
            UserLogin.builder().username("ngotu").password(passwordEncoder.encode("021204")).roles(new String[]{"TU"}).build(),
            UserLogin.builder().username("trang").password(passwordEncoder.encode("021204")).roles(new String[]{"TRANG"}).build()
    );

    // load user
    public UserLogin UserfindByUserName(String username){
        for (UserLogin u : list){
            if (u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }
}
