package com.johann.designPattern.designPatterns23.G_bridge;

/** DepartmentDao
 * @ClassName: DepartmentDao
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class DepartmentDao implements IDao<Department>{
    @Override
    public void insert(Department department) {
        System.out.println("插入Department: "+department.toString());
    }

    @Override
    public void get(Integer id) {
        System.out.println("查询Department: "+id);
    }

    @Override
    public void update(Department department) {
        System.out.println("更新Department: "+department.toString());
    }
}
