package com.romanmayer.instateam.dao;

import com.romanmayer.instateam.model.Project;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Implementation of Project DAO. Repository
 */
@Repository
public class ProjectDaoImpl implements ProjectDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Project> findAll() {
        // Open a session
        Session session = sessionFactory.openSession();

        // UPDATED: Create CriteriaBuilder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // UPDATED: Create CriteriaQuery
        CriteriaQuery<Project> criteria = builder.createQuery(Project.class);

        // UPDATED: Specify criteria root
        criteria.from(Project.class);

        // UPDATED: Execute query
        List<Project> projects = session.createQuery(criteria).getResultList();

        projects.stream().forEach(project -> Hibernate.initialize(project.getRolesNeeded()));

        // Close session
        session.close();

        return projects;
    }

    @Override
    public Project findById(Long id) {
        Session session = sessionFactory.openSession();
        Project project = session.get(Project.class, id);
        Hibernate.initialize(project.getRolesNeeded());
        Hibernate.initialize(project.getCollaborators());
        session.close();
        return project;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void save(Project project) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(project);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void delete(Project project) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(project);
        session.getTransaction().commit();
        session.close();
    }
}
