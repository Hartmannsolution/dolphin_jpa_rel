/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat3.jpademo.facade;

import dat3.jpademo.entities.Person;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author tha
 */
public class PersonFacade {

    private static EntityManagerFactory emf;
    private static PersonFacade pf;

    private PersonFacade() {
    }

    public static PersonFacade getFacade(EntityManagerFactory _emf) {
        if (pf == null) {
            pf = new PersonFacade();
            emf = _emf;
        }
        return pf;
    }

    public List<Person> findAllPersons() {
        EntityManager em = emf.createEntityManager();
        try {

            TypedQuery<Person> tq = em.createNamedQuery("Person.findAll", Person.class);
            return tq.getResultList();
        } finally {
            em.close();
        }
    }

    public long countTotalStyles() {
        EntityManager em = emf.createEntityManager();
        try {

            Long count = em.createQuery("SELECT count(s) FROM Person p join p.styles s", Long.class).getSingleResult();
            return count;
        } finally {
            em.close();
        }
    }

    public long getAllPersonsBySwimstile(String styleName) {
        EntityManager em = emf.createEntityManager();
        try {

            TypedQuery<Long> q = em.createQuery("SELECT count(p) FROM Person p JOIN p.styles s WHERE s.styleName = :style", Long.class);
            q.setParameter("style", styleName);
            return q.getSingleResult();
        } finally {
            em.close();
        }
    }

    public long getSumAllFees() {
        EntityManager em = emf.createEntityManager();
        try {

            TypedQuery<Long> q = em.createQuery("SELECT sum(f.amount) FROM Fee f", Long.class);
            return q.getSingleResult();
        } finally {
            em.close();
        }
    }

    public Map<Long, Long> getSumAllFeesByPersons() {
        EntityManager em = emf.createEntityManager();
        Map<Long, Long> personFees = new HashMap();
        try {
            TypedQuery<Object[]> tq = em.createQuery("SELECT p, sum(f.amount) FROM Person p JOIN p.fees f GROUP BY p.id", Object[].class);
            for (Object[] object : tq.getResultList()) {
                Person p = (Person)object[0];
                personFees.put(p.getId(), (Long)object[1]);
            }
                return personFees;
        }finally {
            em.close();
        }
    }

    

    public List<Integer> getHighestAndLowestFee() {
        EntityManager em = emf.createEntityManager();
        try {

            TypedQuery<Object[]> q = em.createQuery("SELECT MAX(f.amount), MIN(f.amount) FROM Fee f", Object[].class);
            List<Integer> result = new ArrayList();
            for (Object object : q.getSingleResult()) {
                result.add(Integer.parseInt(String.valueOf(object)));
            }
            return result;
        } finally {
            em.close();
        }
    }

    public Person updatePerson(Person p) {
        EntityManager em = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
//          To use merge (over persist) p must allready be in db. Any changes to p from version in db will be persisted and cascading will happen to related entities.
            Person newP = em.merge(p);
            em.getTransaction().commit();
            return newP;
        } finally {
            em.close();
        }
    }
    public Person extendedUpdatePerson(Person p) {
        EntityManager em = emf.createEntityManager();
        //Check if p is allready in db
        if(em.find(Person.class, p.getId())==null)
            throw new IllegalArgumentException("No such person exists");
        try {
            em.getTransaction().begin();
//          To use merge (over persist) p must allready be in db. Any changes to p from version in db will be persisted and cascading will happen to related entities.
            Person newP = em.merge(p);
            em.getTransaction().commit();
            return newP;
        } finally {
            em.close();
        }
    }

}
