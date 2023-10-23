/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat3.jpademo.facade;

import dat3.jpademo.entities.Fee;
import dat3.jpademo.entities.Person;
import dat3.jpademo.entities.SwimStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author tha
 */
public class PersonFacadeTest {
    // Pseudo code
    // 
//    Asignments:
//        Find all persons and their fees
//        Find all persons and count their number swim styles
//        Find all person that has a swimstyle named 'Crawl'
//        Find the sum of all Fees
//        Find the smallest Fee and the highest

//  Step 1: Create the static method getFacade in the facade to test
//  Step 2a: Instantiate class to be tested
    private static EntityManagerFactory emf;
    private static PersonFacade pf;
    private static Person p2;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
//      Step 2b
        emf = Persistence.createEntityManagerFactory("pu_test");
        pf = PersonFacade.getFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
//        Step 3: setup test database state before each test
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Fee.deleteAllRows").executeUpdate();
            em.createNamedQuery("SwimStyle.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            Person p1 = new Person("Henning", 1965);
            p2 = new Person("Henrietta", 2001);
            SwimStyle crawl = new SwimStyle("Crawl");
            SwimStyle butterfly = new SwimStyle("Butterfly");
            p1.addFee(new Fee(55));
            p2.addFee(new Fee(45));
            p2.addFee(new Fee(100));
            p1.addSwimStyle(crawl);
            p1.addSwimStyle(butterfly);
            p2.addSwimStyle(crawl);
            em.persist(p1);
            em.persist(p2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testFindAllPersonsAndTheirFees() {
        // Step 2c
//      Step 4: Implement the testcode (run it and see it fail)
        System.out.println("Test Find all Persons and their Fees");
        List<Person> persons = pf.findAllPersons(); // Step 5: clicking on red dot in left side here and choose to autoimplement the method - then got to the class and refactor
        int totalAmount = 0;
        for (Person person : persons) {
            for (Fee fee : person.getFees()) {
                totalAmount += fee.getAmount();
            }
        }
        Assertions.assertEquals(200, totalAmount);
    }
    @Test
    public void testCountNumberStyles(){
        System.out.println("Test Find all persons and count their swim styles");
        long expected = 3;
        Assertions.assertEquals(expected, pf.countTotalStyles());
    }
    
    @Test
    public void testFindCrawlers(){
        System.out.println("Test Find all Crawlers");
        long expected = 1;
        Assertions.assertEquals(expected, pf.getAllPersonsBySwimstile("Butterfly"));
    }
    @Test
    public void testSumOfAllFees(){
        System.out.println("Test find the sum of all fees");
        long expected = 200;
        Assertions.assertEquals(expected, pf.getSumAllFees());
    }
    
    @Test
    public void testSumOfAllFeesByPersons(){
        System.out.println("Test find the sum of all fees for each person");
        long expected = 145;
        Map<Long, Long> personFee = pf.getSumAllFeesByPersons();
        long result = personFee.get(p2.getId());
        Assertions.assertEquals(expected, result);
    }
    
    @Test
    public void testHighLowFee(){
        System.out.println("Test highest and lowest fees");
        List<Integer> expected = Arrays.asList(100,45);
        assertEquals(expected, pf.getHighestAndLowestFee());
    }
    
    @Test
    public void testUpdatePersons(){
        System.out.println("Test update person");
        boolean gotChestStroke = false;
        p2.addSwimStyle(new SwimStyle("Chest stroke"));
        Person newP = pf.updatePerson(p2);
        for (SwimStyle style : newP.getStyles()) {
            if(style.getStyleName().equals("Chest stroke")){
                gotChestStroke = true;
            }
        }
        assertTrue(gotChestStroke);
    }
    @Test
    public void testExtendedUpdatePersons(){
        System.out.println("Test update person");
        boolean gotChestStroke = false;
        p2.addSwimStyle(new SwimStyle("Back crawl"));
        Person newP = pf.updatePerson(p2);
        for (SwimStyle style : newP.getStyles()) {
            if(style.getStyleName().equals("Back crawl")){
                gotChestStroke = true;
            } else {
            }
        }
        assertTrue(gotChestStroke);
    }
    
}
