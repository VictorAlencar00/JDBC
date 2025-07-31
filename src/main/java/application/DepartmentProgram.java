package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;

public class DepartmentProgram {
    public static void main(String[] args) {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

//        INSERT
//        departmentDao.insert(new Department(null,"Houses"));

//        Find By ID with name UPDATE
//        Department department = departmentDao.findById(3);
//        department.setName("Courses");
//        departmentDao.update(department);
//        System.out.println(department);

//        DELETE by Id
//        departmentDao.deleteById(6);

//        FIND ALL
        List<Department> departmentList = departmentDao.findAll();
        System.out.println(departmentList);
    }
}
