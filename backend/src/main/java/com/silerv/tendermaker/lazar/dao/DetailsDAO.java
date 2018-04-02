package com.silerv.tendermaker.lazar.dao;

import com.silerv.tendermaker.lazar.model.Detail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by lazar on 2018. 01. 03..
 */
public class DetailsDAO {
    private Connection connection = ConnectionManager.getInstance().getConnection();
    private Statement stmt = null;

    public ArrayList<Detail> getDetailsOfTender(long id) throws SQLException {
        stmt = connection.createStatement();
        String sql = "SELECT age, name, address, phonenumber FROM Person";
        ResultSet rs = stmt.executeQuery(sql);
        ArrayList<Detail> list = new ArrayList<Detail>();
        Detail det;
        while (rs.next()) {
            det = new Detail();
            list.add(det);
        }
        rs.close();
        return list;
    }
}
