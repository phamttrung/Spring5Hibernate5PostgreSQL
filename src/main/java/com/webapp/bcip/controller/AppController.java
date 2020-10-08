package com.webapp.bcip.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.webapp.bcip.model.User;
import com.webapp.bcip.model.UserProfile;
import com.webapp.bcip.service.UserProfileService;
import com.webapp.bcip.service.UserService;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;


@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppController {

    @Autowired
    UserService userService;

    @Autowired
    UserProfileService userProfileService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    @Autowired
    AuthenticationTrustResolver authenticationTrustResolver;

    /**
     * This method will redirect from root to login page.
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry
            .addViewController("/").setViewName("redirect:/login.home");
    }

    /**
     * This method will list all existing users.
     */
    @RequestMapping(value = { "/", "/main.home" }, method = RequestMethod.GET)
    public String main(ModelMap model) {
        //List<User> users = userService.findAllUsers();
        //model.addAttribute("users", users);
        model.addAttribute("loggedinuser", getLoggedUsername());
        model.addAttribute("loggedinpassword", getLoggedPassword());
        model.addAttribute("loggedinrole", getLoggedRole());
        if (getLoggedRole().toUpperCase().contains("ADMIN")){
            model.addAttribute("dws", "/dws?c=5ae9b7f211e23aac3df5f2b8f3b8eada&k=".concat(getLoggedPassword()).concat("&u=").concat(getLoggedUsername()).concat("&p=76a2173be6393254e72ffa4d6df1030a&r=").concat("ADMIN"));
        } else if (getLoggedRole().toUpperCase().contains("USER")){
            model.addAttribute("dws", "/dws?c=5ae9b7f211e23aac3df5f2b8f3b8eada&k=".concat(getLoggedPassword()).concat("&u=").concat(getLoggedUsername()).concat("&p=76a2173be6393254e72ffa4d6df1030a&r=").concat("USER"));
        }
        return "main";
    }

   @RequestMapping(value = { "/users" }, method = RequestMethod.GET)
    public String users(ModelMap model) {

        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("loggedinuser", getLoggedUsername());
        model.addAttribute("loggedinpassword", getLoggedPassword());
        return "users";
    }

    
    @RequestMapping(value = { "/userslist" }, method = RequestMethod.GET)
    public String userslist(ModelMap model) {

        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("loggedinuser", getLoggedUsername());
        model.addAttribute("loggedinpassword", getLoggedPassword());
        return "userslist";
    }

    /**
     * This method will provide the medium to add a new user.
     */
    @RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        model.addAttribute("loggedinuser", getLoggedUsername());
        model.addAttribute("loggedinpassword", getLoggedPassword());
        return "newuser";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * saving user in database. It also validates the user input
     */
    @RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult result, ModelMap model) {

        if (result.hasErrors()) {
            return "newuser";
        }

        /*
         * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation 
         * and applying it on field [sso] of Model class [User].
         * 
         * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
         * framework as well while still using internationalized messages.
         * 
         */
        if(!userService.isUsernameUnique(user.getId(), user.getUsername())){
            FieldError ssoError =new FieldError("user","username",messageSource.getMessage("non.unique.username", new String[]{user.getUsername()}, Locale.getDefault()));
            result.addError(ssoError);
            return "newuser";
        }

        userService.saveUser(user);

        model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " registered successfully");
        model.addAttribute("loggedinuser", getLoggedUsername());
        //return "success";
        return "newusersuccess";
    }


    /**
     * This method will provide the medium to update an existing user.
     */
    @RequestMapping(value = { "/edit-user-{username}" }, method = RequestMethod.GET)
    public String editUser(@PathVariable String username, ModelMap model) {
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        model.addAttribute("loggedinuser", getLoggedUsername());
        return "newuser";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * updating user in database. It also validates the user input
     */
    @RequestMapping(value = { "/edit-user-{username}" }, method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result, ModelMap model, @PathVariable String username) {

        if (result.hasErrors()) {
            return "newuser";
        }

        /*//Uncomment below 'if block' if you WANT TO ALLOW UPDATING SSO_ID in UI which is a unique key to a User.
        if(!userService.isUserSSOUnique(user.getId(), user.getUsername())){
                FieldError ssoError =new FieldError("user","username",messageSource.getMessage("non.unique.username", new String[]{user.getUsername()}, Locale.getDefault()));
                result.addError(ssoError);
                return "registration";
        }*/


        userService.updateUser(user);

        model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
        model.addAttribute("loggedinuser", getLoggedUsername());
        return "newusersuccess";
    }

    /**
     * This method will delete an user by it's SSOID value.
     */
    @RequestMapping(value = { "/delete-user-{username}" }, method = RequestMethod.GET)
    public String deleteUser(@PathVariable String username) {
        userService.deleteUserByUsername(username);
        return "redirect:/userslist";
    }
    

    
    /**
     * This method will provide UserProfile list to views
     */
    @ModelAttribute("roles")
    public List<UserProfile> initializeProfiles() {
        return userProfileService.findAll();
    }

    /**
     * This method handles Access-Denied redirect.
     */
    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("loggedinuser", getLoggedUsername());
        return "accessDenied";
    }

    /**
     * This method handles login GET requests.
     * If users is already logged-in and tries to goto login page again, will be redirected to list page.
     */
    @RequestMapping(value = "/login.home", method = RequestMethod.GET)
    public String loginPage() {
        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        } else {
            return "redirect:/main.home";  
        }
    }

    /**
     * This method handles logout requests.
     * Toggle the handlers if you are RememberMe functionality is useless in your app.
     */
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            //new SecurityContextLogoutHandler().logout(request, response, auth);
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login.home?logout";
    }

    
    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    private String getLoggedUsername(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    private String getLoggedPassword(){
        String password = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            User user = userService.findByUsername(((UserDetails)principal).getUsername());
            password = user.getPassword();
        } else {
            password = principal.toString();
        }
        return password;
    }


    /* This method returns the principal[user-name] of logged-in user.
     */
    private String getLoggedRole(){
        UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetail.getAuthorities().toString().toUpperCase().contains("ADMIN")){
            return "ADMIN";
        } else if (userDetail.getAuthorities().toString().toUpperCase().contains("USER")){
            return "USER";
        } else {
            return "GUEST";
        }            

    }

    
    /**
     * This method returns true if users is already authenticated [logged-in], else false.
     */
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }


}