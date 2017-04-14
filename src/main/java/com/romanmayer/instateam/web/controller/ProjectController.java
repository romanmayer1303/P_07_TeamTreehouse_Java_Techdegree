package com.romanmayer.instateam.web.controller;

import com.romanmayer.instateam.model.Collaborator;
import com.romanmayer.instateam.model.Project;
import com.romanmayer.instateam.model.Role;
import com.romanmayer.instateam.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

/**
 * Project Controller
 */
@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Home page -- with Projects tab open
    @RequestMapping(value = {"/", "/projects"})
    public String listProjects(Model model) {
        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);
        return "project/index";
    }

    // Project Details page
    @RequestMapping("/projects/{projectId}")
    public String detailProject(@PathVariable Long projectId, Model model) {
        Project project = projectService.findById(projectId);
        List<Collaborator> collaborators = project.getCollaborators();
        List<Role> rolesNeeded = project.getRolesNeeded();
        HashMap<Role, Collaborator> rolesAssignedWithCollaborators = new HashMap<>();
        for (Role roleNeeded : rolesNeeded) {
            for (Collaborator collaborator : collaborators) {
                System.out.println("_______BJJ______!!!!!");
                if (collaborator.getRole() == roleNeeded) {
                    System.out.println("_______YOLO______!!!!!");
                    System.out.println("_______YOLO______!!!!!");
                    rolesAssignedWithCollaborators.put(roleNeeded, collaborator);
                    rolesNeeded.remove(roleNeeded);
                    collaborators.remove(collaborator);
                }
            }
        }

        System.out.println(rolesAssignedWithCollaborators.size());

        model.addAttribute("project", project);
        model.addAttribute("assignedRoles", rolesAssignedWithCollaborators);
        model.addAttribute("unassignedRoles", rolesNeeded);

/*        while (collaborators.size() > 0) {
            Collaborator currentCollaborator = collaborators.get(0);
            Role currentRole = currentCollaborator.getRole();
            if (rolesNeeded.contains(currentRole)) {
                rolesAssignedWithCollaborators.put(currentRole, currentCollaborator);
                rolesNeeded.remove(currentRole);
                collaborators.remove(currentCollaborator);
            }
        }*/

        // current collaborators? or collaborators needed?
        return "project/detail"; //TODO:RM put ID in here
    }

    //TODO:RM (add projects)-button
    @RequestMapping("/projects/add")
    public String addProject() {
        return "";
    }
}
