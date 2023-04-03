package com.johann.designPattern.dahao.command;

/** 顾客[命令模式不含此类]
 * @ClassName: Customer
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class Customer {
    private String id;
    private String name;

    private String tableId;

    public Customer() {
    }

    public Customer(String tableId) {
        this.tableId = tableId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    //重写hashcode和equals方法，以便于在map中使用
    @Override
    public int hashCode() {
        return tableId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Customer) {
            Customer customer = (Customer) obj;
            return this.tableId.equals(customer.getTableId());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", tableId='" + tableId + '\'' +
                '}';
    }
}
