package cn.hopever.platform.user.test;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by Donghui Huo on 2016/8/30.
 */
//@RunWith(SpringRunner.class)
//@WebAppConfiguration
//@SpringBootTest
public class ExampleTestMvc {

//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @InjectMocks
//    ExampleTableController defaultRestController;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//    }
//
//    //
//    @Test
//    public void testSayHelloWorld() throws Exception {
//        this.mockMvc.perform(get("/example").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"));
//    }

    @Test
    public void testExample() throws Exception {
        assertEquals("1234", "1234");
    }
}
