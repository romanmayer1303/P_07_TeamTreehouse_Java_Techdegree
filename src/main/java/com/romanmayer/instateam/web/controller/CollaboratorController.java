package com.romanmayer.instateam.web.controller;

import com.romanmayer.instateam.model.Collaborator;
import com.romanmayer.instateam.model.Role;
import com.romanmayer.instateam.service.CollaboratorService;
import com.romanmayer.instateam.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 * Collaborator Controller
 */
@Controller
public class CollaboratorController {

    @Autowired
    private CollaboratorService collaboratorService;

    @Autowired
    private RoleService roleService;

    // List all collaborators
    @RequestMapping("/collaborators")
    public String listCollaborators(Model model) {
        List<Collaborator> collaborators = collaboratorService.findAll();
        List<Role> roles = roleService.findAll();

        if (!model.containsAttribute("collaborator")) {
            model.addAttribute("collaborator", new Collaborator());
        }

        model.addAttribute("collaborators", collaborators);
        model.addAttribute("roles", roles);

        return "collaborator/index";
    }

    // Form for editing an existing collaborator
    @RequestMapping("/collaborators/{collaboratorId}")
    public String formEditCollaborator(@PathVariable Long collaboratorId, Model model) {
        Collaborator collaborator = collaboratorService.findById(collaboratorId);
        List<Role> roles = roleService.findAll();

        model.addAttribute("collaborator", collaborator);
        model.addAttribute("roles", roles);
        return "collaborator/edit";
    }

    // Update an existing collaborator
    @RequestMapping(value = "/collaborators/update", method = RequestMethod.POST)
    public String updateCollaborator(@Valid Collaborator collaborator) {
        collaboratorService.save(collaborator);
        return "redirect:/collaborators";
    }

    // Add a new role
    @RequestMapping(value = "collaborators/add", method = RequestMethod.POST)
    public String addRole(@Valid Collaborator collaborator) {
        collaboratorService.save(collaborator);
        return "redirect:/collaborators";
    }

    // Delete an existing collaborator
    @RequestMapping(value = "/collaborators/{collaboratorId}/delete", method = RequestMethod.POST)
    public String deleteRole(@PathVariable Long collaboratorId) {
        collaboratorService.delete(collaboratorService.findById(collaboratorId));
        return "redirect:/collaborators";
    }

}
