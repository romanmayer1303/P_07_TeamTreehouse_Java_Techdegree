package com.romanmayer.instateam.web.controller;

import com.romanmayer.instateam.model.Collaborator;
import com.romanmayer.instateam.model.Project;
import com.romanmayer.instateam.model.Role;
import com.romanmayer.instateam.service.CollaboratorService;
import com.romanmayer.instateam.service.ProjectService;
import com.romanmayer.instateam.service.RoleService;
import com.romanmayer.instateam.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Role Controller
 */
@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private CollaboratorService collaboratorService;
    @Autowired
    private ProjectService projectService;

    // List all roles
    @RequestMapping("/roles")
    public String listRoles(Model model) {
        List<Role> roles = roleService.findAll();
        if (!model.containsAttribute("role")) {
            model.addAttribute("role", new Role());
        }
        model.addAttribute("roles", roles);
        return "role/index";
    }

    // Add a new role
    @RequestMapping(value = "roles/add", method = RequestMethod.POST)
    public String addRole(@Valid Role role, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.role", bindingResult);
            redirectAttributes.addFlashAttribute("role", role);
        } else {
            roleService.save(role);
        }
        return "redirect:/roles";
    }

    // Form for editing an existing role
    @RequestMapping("/roles/{roleId}")
    public String formEditRole(@PathVariable Long roleId, Model model) {
        // if the model already contains a "role", the form will be pre-populated (from redirection)
        if(!model.containsAttribute("role")) {
            model.addAttribute(roleService.findById(roleId));
        }
        return "role/edit";
    }

    // Update an existing role
    @RequestMapping(value = "/roles/update", method = RequestMethod.POST)
    public String updateRole(@Valid Role role, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // Case 1: Errors due to invalid role, e.g. name must be between 3 and 35 characters
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.role", bindingResult);
            redirectAttributes.addFlashAttribute("role", role);
            return String.format("redirect:/roles/%s", role.getId());
        }

        // Case 2: Role will be updated
        roleService.save(role);
        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage(
                        "Role has been updated.",
                        FlashMessage.Status.SUCCESS));
        return "redirect:/roles";
    }

    // Delete an existing role
    @RequestMapping(value = "/roles/{roleId}/delete", method = RequestMethod.POST)
    public String deleteRole(@PathVariable Long roleId, RedirectAttributes redirectAttributes) {
        // Case 1: Role assigned to Collaborator
        if (isRoleCurrentlyAssignedToACollaborator(roleId)) {
            redirectAttributes.addFlashAttribute("flash",
                    new FlashMessage(
                            "Role is assigned to a Collaborator, so it cannot be deleted.",
                            FlashMessage.Status.FAILURE));
            return String.format("redirect:/roles/%s", roleId);
        }
        // Case 2: Role needed in Project
        if (isRoleCurrentlyNeededInAProject(roleId)) {
            redirectAttributes.addFlashAttribute("flash",
                    new FlashMessage(
                            "Role is needed in a project, so it cannot be deleted.",
                            FlashMessage.Status.FAILURE));
            return String.format("redirect:/roles/%s", roleId);
        }
        // Case 3: Role is "free" and will be deleted
        roleService.delete(roleService.findById(roleId));
        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage(
                        "Role has been deleted.",
                        FlashMessage.Status.SUCCESS));
        return "redirect:/roles";
    }

    // helper
    private boolean isRoleCurrentlyNeededInAProject(Long roleId) {
        List<Project> projects = projectService.findAll();

        for (Project project : projects) {
            List<Role> isThisRoleNeeded = project.getRolesNeeded().stream()
                    .filter(role -> role.getId() == roleId).collect(Collectors.toList());
            if (isThisRoleNeeded.size() > 0) {
                return true;
            }
        }
        return false;
    }

    // helper
    private boolean isRoleCurrentlyAssignedToACollaborator(Long roleId) {
        List<Collaborator> collaborators = collaboratorService.findAll();
        List<Collaborator> collaboratorsWithThisRole = collaborators.stream()
                .filter(collaborator -> collaborator.getRole().getId() == roleId)
                .collect(Collectors.toList());
        return collaboratorsWithThisRole.size() > 0;
    }

}
