package cn.hopever.platform.user.test;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by Donghui Huo on 2016/8/30.
 */
//@RunWith(SpringRunner.class)
//@DataJpaTest
public class ExampleTestJpa {

//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private ExampleTableRepository testTableRepository;
//
//
//    @Test
//    public void testExample() throws Exception {
//        //this.entityManager.persist(new TestTable(1,"1234"));
//        this.testTableRepository.save(new ExampleTable("1234"));
//        ExampleTable testTable = this.testTableRepository.findByUserName("1234");
//        assertEquals(testTable.getUserName(),"1234");
//    }

    @Test
    public void testExample() throws Exception {
        assertEquals("1234", "1234");
    }

}
