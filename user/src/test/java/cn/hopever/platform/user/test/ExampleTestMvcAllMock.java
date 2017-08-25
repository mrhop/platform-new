package cn.hopever.platform.user.test;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by Donghui Huo on 2016/8/30.
 */
//@RunWith(SpringRunner.class)
//@WebMvcTest(ExampleTableController.class)
//@AutoConfigureMockMvc
public class ExampleTestMvcAllMock {

    //    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    ExampleTableService testTableService;
//    @MockBean
//    ExampleTableAssembler exampleTableAssembler;
//    @MockBean
//    ModelMapper modelMapper;
//
//
//    //
//    @Test
//    public void testSayHelloWorld() throws Exception {
//        ExampleTable et = new ExampleTable("youknow");
//        //given(this.testTableService.addOne(et)).willReturn(et);
//        this.mvc.perform(get("/example").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"));
//    }
    @Test
    public void testExample() throws Exception {
        assertEquals("1234", "1234");
    }

}
