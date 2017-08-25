package cn.hopever.platform.user.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * Created by Donghui Huo on 2016/8/19.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ExampleUserApplicationTest {
    static Logger logger = LoggerFactory.getLogger(ExampleUserApplicationTest.class);

    //    @Autowired
//    private ExampleTableService testTableService;
//
//    @Test
//    public void contextLoads() {
//        logger.debug("init finished");
//    }
//
//    @Test
//    public void  addtest() {
//        testTableService.saveOne(new ExampleTable("you know"));
//    }
//
//    @Test
//    public void  findall() {
//        testTableService.findAll();
//    }
    @Test
    public void testExample() throws Exception {
        assertEquals("1234", "1234");
    }
}
