package com.romanmayer.instateam.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Project model class, which represents a project for which a project manager is seeking collaborators.
 */
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 35)
    private String name;

    @NotNull
    @Size(min = 3, max = 1024)
    private String description; // longer description of the project

    @Size(min = 1, max = 35)
    private String status; // for example “recruiting” or “on hold” //TODO:RM (maybe make Enum...?)

    @ManyToMany(cascade = CascadeType.ALL)  //TODO:RM add: @JoinTable, cascade...
  /*  @JoinTable(name = "PROJECT_ROLE",
            joinColumns = {@JoinColumn(name = "ROLESNEEDED_ID")},
            inverseJoinColumns = {@JoinColumn(name = "PROJECT_ID")}) */
    private List<Role> rolesNeeded;

    @ManyToMany //TODO:RM add: @JoinTable, cascade...
    private List<Collaborator> collaborators;

    public Project() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Role> getRolesNeeded() {
        return rolesNeeded;
    }

    public void setRolesNeeded(List<Role> rolesNeeded) {
        this.rolesNeeded = rolesNeeded;
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }
}
