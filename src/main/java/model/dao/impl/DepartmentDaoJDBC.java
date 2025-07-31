package model.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement st = null;
        try {
            st= conn.prepareStatement(
                    "INSERT INTO department "
                            + "(Name) "
                            + "VALUES "
                            + "(?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(obj.getName() + " added");
                    ResultSet resultSet = st.getGeneratedKeys();
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        obj.setId(id);
                    }
            }
            else {
                throw new DbException("Unexpected error! No rows affected.");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement st = null;
        try {
            st= conn.prepareStatement(
                    "UPDATE department " +
                            "SET Name = ? " +
                            "WHERE Id = ?");

            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(obj + " updated");
            }
            else {
                throw new DbException("Unexpected error! No rows affected.");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "DELETE department "
                            + "FROM department "
                            + "WHERE department.Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                System.out.println("Department " + id + "removed.");
            }
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

@Override
public Department findById(Integer id) {
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
        st = conn.prepareStatement(
                "SELECT department.* "
                        + "FROM department "
                        + "WHERE department.Id = ?");

        st.setInt(1, id);
        rs = st.executeQuery();
        if (rs.next()) {
            Department dep = new Department();
            dep.setId(rs.getInt("Id"));
            dep.setName(rs.getString("Name"));
            Department obj = dep;
            return obj;
        }
        return null;
    } catch (SQLException e) {
        throw new DbException(e.getMessage());
    } finally {
        DB.closeStatement(st);
        DB.closeResultSet(rs);
    }
}

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT department.* "
                            + "FROM department "
                            + "ORDER BY Name");

            rs = st.executeQuery();

            List<Department> list = new ArrayList<>();

            while (rs.next()) {
                Department dep = new Department();
                dep.setId(rs.getInt("Id"));
                dep.setName(rs.getString("Name"));
                list.add(dep);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}