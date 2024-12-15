package com.example.sista.BobotDosen;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcBobotDosenRepository implements BobotDosenRepository{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<BobotDosen> findAll(){
        String sql = "SELECT * FROM listBobotDosen";
        return jdbcTemplate.query(sql, this::mapRowToBobotDosen);
    }

    private BobotDosen mapRowToBobotDosen(ResultSet resultSet, int rowNum) throws SQLException {
        return new BobotDosen(
            resultSet.getInt("jenisTA"), 
            resultSet.getInt("idBobot"), 
            resultSet.getBigDecimal("bobot"), 
            resultSet.getInt("idRole"), 
            resultSet.getString("namaRole")
        );
    }

    public void updateBobot(Integer idBobot, BigDecimal bobot){
        String sql = "UPDATE bobotDosen SET bobot=? WHERE idBobot=?";
        jdbcTemplate.update(sql, bobot, idBobot);
    }
}