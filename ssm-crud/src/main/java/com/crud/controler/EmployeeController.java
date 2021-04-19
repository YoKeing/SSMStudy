package com.crud.controler;

import com.crud.bean.Employee;
import com.crud.bean.Msg;
import com.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {


    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "/emp/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Msg deleteEmp(@PathVariable("id")String ids){
        if(ids.contains("-")){
            List<Integer> del_ids = new ArrayList<>();
            String[] str_ids = ids.split("-");
            for (String id : str_ids){
                del_ids.add(Integer.parseInt(id));
            }
            employeeService.deleteBatch(del_ids);
        }else{
            Integer id = Integer.parseInt(ids);
            employeeService.deleteEmp(id);
        }

        return Msg.success();
    }

    @RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
    @ResponseBody
    public Msg saveEmp(Employee employee){
        System.out.println(employee);
        employeeService.updateEmp(employee);
        return Msg.success();
    }

    @RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id){

        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp", employee);
    }

////    @RequestMapping("/emps")
//    public String getEmps(@RequestParam(value = "pn", defaultValue = "1")Integer pn, Model model){
//
//        PageHelper.startPage(pn, 5);
//
//        List<Employee> emps = employeeService.getAll();
//
//        PageInfo<Employee> page = new PageInfo<>(emps, 5);
//        model.addAttribute("pageInfo", page);
//
//        return "list";
//    }

    @ResponseBody
    @RequestMapping("/checkuser")
    public Msg checkuser(@RequestParam("empName") String empName){

        String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        if (!empName.matches(regx)){
            return Msg.fail().add("va_msg", "用户名必须是2-5位中文或者6-16位英文和数字的组合");
        }

        boolean b = employeeService.checkUser(empName);
        if(b){
            return Msg.success();
        }else {
            return Msg.fail().add("va_msg", "用户名不可用");
        }
    }

    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(@Valid Employee employee, BindingResult result){

        if(result.hasErrors()){

            Map<String, Object> map = new HashMap<>();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for(FieldError fieldError : fieldErrors){
                System.out.println(fieldError.getField());
                System.out.println(fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorField", map);
        }else {
            employeeService.saveEmp(employee);
            return Msg.success();
        }

    }


    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1")Integer pn){
        PageHelper.startPage(pn, 5);

        List<Employee> emps = employeeService.getAll();

        PageInfo<Employee> page = new PageInfo<>(emps, 5);
        return Msg.success().add("pageInfo", page);
    }


}
