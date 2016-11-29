package com.main.auth.controllers;

import com.main.auth.DAO.*;

import com.main.auth.exception.CustomExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@Controller
public class UserController {
    /*
    @PostMapping("/create")
    @ResponseBody
    public String create(@ModelAttribute UserHebirnate user, Model model) {
        if(userDao.countByLogin(user.getLogin()) == 1){
            throw new CustomExceptions("E888", "This is already exist");
        }
        else {
            userDao.save(user);
            return "UserHebirnate created! (id = " + user.getId() + ")";
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(long id) {
        try {
            UserHebirnate user = new UserHebirnate(id);
            userDao.delete(user);
        }
        catch (Exception ex) {
            return "Error deleting the user: " + ex.toString();
        }
        return "UserHebirnate succesfully deleted!";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateUser(long id, String login, String password) {
        try {
            UserHebirnate user = userDao.findOne(id);
            user.setLogin(login);
            user.setPassword(password);
            userDao.save(user);
        }
        catch (Exception ex) {
            return "Error updating the user: " + ex.toString();
        }
        return "UserHebirnate succesfully updated!";
    }
*/
    @Autowired
    private UserDao userDao;
}
