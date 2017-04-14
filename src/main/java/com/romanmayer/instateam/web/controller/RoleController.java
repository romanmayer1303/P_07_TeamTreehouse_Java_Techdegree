package com.romanmayer.instateam.web.controller;

import com.romanmayer.instateam.model.Role;
import com.romanmayer.instateam.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 * Role Controller
 */
@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    // List all roles
    @RequestMapping("/roles")
    public String listRoles(Model model) {
        List<Role> roles = roleService.findAll();
        if(!model.containsAttribute("role")){
            model.addAttribute("role", new Role());
        }
        model.addAttribute("roles", roles);
        return "role/index";
    }

    // Add a new role
    @RequestMapping(value = "roles/add", method = RequestMethod.POST)
    public String addRole(@Valid Role role, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "redirect:/";
//        }
        roleService.save(role);
        return "redirect:/roles";
    }

    // Form for editing an existing role
    @RequestMapping("/roles/{roleId}")
    public String formEditRole(@PathVariable Long roleId, Model model) {
        model.addAttribute(roleService.findById(roleId));
        return "role/edit";
    }

    // Update an existing role
    @RequestMapping(value = "/roles/update", method = RequestMethod.POST)
    public String updateRole(@Valid Role role) {
        roleService.save(role);
        return "redirect:/roles";
    }

    // Delete an existing role
    @RequestMapping(value = "/roles/{roleId}/delete", method = RequestMethod.POST)
    public String deleteRole(@PathVariable Long roleId) {
        roleService.delete(roleService.findById(roleId));
        return "redirect:/roles";
    }

//    @RequestMapping(value = "/roles/add", method = RequestMethod.POST)
//    public String addRole(@ModelAttribute Role role) {
//        roleService.save(role);
//        return "redirect:/roles";
//    }

}
