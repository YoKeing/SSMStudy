package com.crud.test;


import com.crud.bean.Department;
import com.crud.bean.Employee;
import com.crud.dao.DepartmentMapper;
import com.crud.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class MapperTest {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    SqlSession sqlSession;

    @Test
    public void testCRUD(){

//        ApplicationContext ioc = new ClassPathXmlApplicationContext("");
//        DepartmentMapper bean = ioc.getBean(DepartmentMapper.class);
        System.out.println(departmentMapper);


//        departmentMapper.insertSelective(new Department(null, "开发部"));
//        departmentMapper.insertSelective(new Department(null, "测试部"));

//        employeeMapper.insertSelective(new Employee(null, "Jerry", "M", "Jerry@163.com", 1));

//         for (){
//             employeeMapper.insertSelective(new Employee(null, UUID.randomUUID().toString().))
//         }

        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

        for (int i = 0; i < 1000; i++){
            String substring = UUID.randomUUID().toString().substring(0, 5) + i;
            mapper.insertSelective(new Employee(null, substring, "M", substring + "@163.com", 1));
        }
        System.out.println("批量插入完成！！");


    }

}
