package com.example.sista.Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DuplicateKeyException;

@Repository
public class JdbcUserRepository implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String login(String email, String passwords) {
        String sql = "SELECT email, passwords FROM listUser WHERE email=? AND passwords=?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[] { email, passwords }, this::mapRowToUserLogin);
            return user.getEmail();
        } catch (EmptyResultDataAccessException e) { // user tidak ditemukan
            return null;
        }
    }

    private User mapRowToUserLogin(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
                resultSet.getString("email"),
                resultSet.getString("passwords"));
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM listUser";
        return jdbcTemplate.query(sql, this::mapRowToUser);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        String email = resultSet.getString("email");
        return new User(
                resultSet.getString("noInduk"),
                resultSet.getString("nama"),
                email,
                resultSet.getString("passwords"),
                role(email));
    }

    @Override
    public List<User> findAllDosen() {
        String sql = "SELECT nama FROM dosen";
        return jdbcTemplate.query(sql, (dosenList, rowNum) -> new User(dosenList.getString("nama")));
    }

    private String role(String email) {
        if (email.endsWith("@student.edu")) {
            return "mahasiswa";
        } else if (email.endsWith("@dosen.edu")) {
            boolean statusKoordinator = getStatusKoordinator(email);
            if (statusKoordinator)
                return "koordinator";
            else
                return "dosen";
        } else {
            return "admin";
        }
    }

    @Override
    public User getUserByID(String noInduk) {
        String sql = "SELECT * FROM listUser WHERE noInduk=?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[] { noInduk }, this::mapRowToUser);
            return user;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean getStatusKoordinator(String email) {
        String sql = "SELECT statusKoordinator FROM dosen WHERE email=?";
        return jdbcTemplate.queryForObject(sql, boolean.class, email);
    }

    @Override
    public boolean register(User user) {
        String role = user.getRole();
        String sql = "";
        int rowsEffected = 0;
        try {
            if (role.equalsIgnoreCase("Mahasiswa")) {
                sql = "INSERT INTO Mahasiswa (NPM, nama, email, passwords) VALUES (?,?,?,?)";
                rowsEffected = jdbcTemplate.update(sql, user.getNoInduk(), user.getNama(), user.getEmail(),
                        user.getPasswords());
            } else if (role.equalsIgnoreCase("Dosen")) {
                sql = "INSERT INTO Dosen (NIP, nama, email, passwords, statusKoordinator) VALUES (?,?,?,?,?)";
                rowsEffected = jdbcTemplate.update(sql, user.getNoInduk(), user.getNama(), user.getEmail(),
                        user.getPasswords(), false);
            } else if (role.equalsIgnoreCase("Koordinator")) {
                jdbcTemplate.update("UPDATE dosen SET statusKoordinator = false WHERE statusKoordinator = true");
                sql = "INSERT INTO Dosen (NIP, nama, email, passwords, statusKoordinator) VALUES (?,?,?,?,?)";
                rowsEffected = jdbcTemplate.update(sql, user.getNoInduk(), user.getNama(), user.getEmail(),
                        user.getPasswords(), true);
            }
            return rowsEffected > 0;
        } catch (DuplicateKeyException e) {
            return false;
        }
    }

    @Override
    public boolean editRoleDosen(String noInduk, String newRole) {
        String sql;
        int rowsEffected = 0;
        try {
            if (newRole.equalsIgnoreCase("dosen")) { // koordinator --> dosen
                sql = "UPDATE dosen SET statusKoordinator = false WHERE nip = ?";
                rowsEffected = jdbcTemplate.update(sql, noInduk);
            } else { // dosen --> koordinator
                sql = "UPDATE dosen SET statusKoordinator = false WHERE statusKoordinator = true";
                jdbcTemplate.update(sql);
                sql = "UPDATE dosen SET statusKoordinator = true WHERE nip = ?";
                rowsEffected = jdbcTemplate.update(sql, noInduk);
            }
            return rowsEffected > 0;
        } catch (Exception e) {
            return false;
        }
    }
}