package org.budowa.repositories;

import org.budowa.entities.Building;
import org.budowa.entities.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;


public class BuildingsRepository {


    @PersistenceContext
    private EntityManager em;


    public BuildingsRepository() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Budowa");
        em = entityManagerFactory.createEntityManager();
    }

    public static BuildingsRepository inject() {
        return new BuildingsRepository();
    }


    /**
     * find Building by User id
     *
     * @param manager - manager to to buildings be searched with
     * @return Collection<Building> - collections of building managed by given manager
     */
    public Collection<Building> findByManager(User manager) {
        TypedQuery<Building> q = em.createQuery("SELECT b FROM Building b WHERE b.manager = :manager", Building.class);
        q.setParameter("manager", manager);
        return q.getResultList();
    }

    /**
     * Find Building by ID
     *
     * @param id
     * @return Building
     */
    public Building findById(Integer id) {
        return em.find(Building.class, id);
    }


    /**
     * Find all Building records
     *
     * @return
     */
    public Collection<Building> findAll() {
        Query query = em.createQuery("SELECT e FROM Building e");
        return (Collection<Building>) query.getResultList();
    }

    /**
     * Insert new Building record
     *
     * @param building
     * @return userId
     */
    public int insert(Building building) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        building.setCreatedAt(timestamp);
        em.getTransaction().begin();
        em.persist(building);
        em.getTransaction().commit();
        return building.getId();
    }

    /**
     * Update building record
     *
     * @param building
     */
    public void update(Building building) {
        em.getTransaction().begin();
        em.merge(building);
        em.getTransaction().commit();
    }

    /**
     * Delete user record
     *
     * @param building
     */
    public void delete(Building building) {
        em.getTransaction().begin();
        em.remove(building);
        em.getTransaction().commit();
    }


    public Building[] getWorkerBuildings(int userId) {
        Query query = em.createNativeQuery("select * from buildings right join workers_buildings wb on buildings.id = wb.building_id where wb.user_id =" + userId, Building.class);
        List<Building> list = query.getResultList();
        return list.toArray(new Building[0]);
    }
}
