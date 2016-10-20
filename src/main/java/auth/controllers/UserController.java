package auth.controllers;

import auth.DAO.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * A class to test interactions with the MySQL database using the UserDao class.
 *
 * @author netgloo
 */

@Controller
public class UserController {

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/html/form";
    }

    @PostMapping("/form")
    @ResponseBody
    public String getForm(@ModelAttribute User user, Model model) {
        try {
            userDao.save(user);
        }
        catch (Exception ex) {
            return "Error creating the user: " + ex.toString();
        }
        return "User created! (id = " + user.getId() + ")";
    }
    // ------------------------
    // PUBLIC METHODS
    // ------------------------
    /**
     * /create  --> Create a new user and save it in the database.
     *
     * @param login User's login
     * @param password User's password
     * @return A string describing if the user is succesfully created or not.
     */
    @RequestMapping("/create")
    @ResponseBody
    public String create(String login, String password) {
        User user = null;
        try {
            user = new User(login, password);
            userDao.save(user);
        }
        catch (Exception ex) {
            return "Error creating the user: " + ex.toString();
        }
        return "User succesfully created! (id = " + user.getId() + ")";
    }

    /**
     * /delete  --> Delete the user having the passed id.
     *
     * @param id The id of the user to delete
     * @return A string describing if the user is succesfully deleted or not.
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(long id) {
        try {
            User user = new User(id);
            userDao.delete(user);
        }
        catch (Exception ex) {
            return "Error deleting the user: " + ex.toString();
        }
        return "User succesfully deleted!";
    }

    /**
     * /get-by-login  --> Return the id for the user having the passed login.
     *
     * @param login The login to search in the database.
     * @return The user id or a message error if the user is not found.
     */

    /**
     * /update  --> Update the login and the password for the user in the database
     * having the passed id.
     *
     * @param id The id for the user to update.
     * @param login The new login.
     * @param password The new password.
     * @return A string describing if the user is succesfully updated or not.
     */
    @RequestMapping("/update")
    @ResponseBody
    public String updateUser(long id, String login, String password) {
        try {
            User user = userDao.findOne(id);
            user.setLogin(login);
            user.setPassword(password);
            userDao.save(user);
        }
        catch (Exception ex) {
            return "Error updating the user: " + ex.toString();
        }
        return "User succesfully updated!";
    }

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    @Autowired
    private UserDao userDao;

} // class UserController
