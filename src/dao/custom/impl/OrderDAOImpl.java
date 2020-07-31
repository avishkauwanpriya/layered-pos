package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import db.DBConnection;
import entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    public  String getLastOrderId() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM `Order` ORDER BY id DESC LIMIT 1");
            if (!rst.next()){
                return null;
            }else{
                return rst.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Order> findAll() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM `Order`");
            List<Order> orders = new ArrayList<>();
            while (rst.next()) {
                orders.add(new Order(rst.getString(1),
                        rst.getDate(2),
                        rst.getString(3)));
            }
            return orders;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public Order find(String key) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM `Order` WHERE id=?",key);
            if (rst.next()) {
                return new Order(rst.getString(1),
                        rst.getDate(2),
                        rst.getString(3));
            }
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean save(Order order) {
        try {
            return CrudUtil.execute("INSERT INTO `Order` VALUES (?,?,?)", order.getId(), order.getDate(), order.getCustomerId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Order order) {
        try {
            return CrudUtil.execute("UPDATE Order SET date=?, customerId=? WHERE id=?",order.getDate(), order.getCustomerId(), order.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String key) {
        try {
            return CrudUtil.execute("DELETE FROM Order WHERE id=?",key);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
}
