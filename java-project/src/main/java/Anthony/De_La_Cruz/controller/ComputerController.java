package Anthony.De_La_Cruz.controller;

import Anthony.De_La_Cruz.database.DBConnection;
import Anthony.De_La_Cruz.model.Computer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ComputerController {

    public void create(Computer c) throws Exception {
        String sql = "INSERT INTO equipos " +
                "(codigo, tipo_equipo, marca, modelo, sistema, almacenamiento, ram, estado, " +
                "fecha_mantenimiento, fecha_registro, imagen) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getCodigo());
            ps.setString(2, c.getTipoEquipo());
            ps.setString(3, c.getMarca());
            ps.setString(4, c.getModelo());
            ps.setString(5, c.getSistema());
            ps.setString(6, c.getAlmacenamiento());
            ps.setString(7, c.getRam());
            ps.setString(8, c.getEstado());
            ps.setDate(9, Date.valueOf(c.getFechaMantenimiento()));
            ps.setDate(10, Date.valueOf(c.getFechaRegistro()));
            ps.setString(11, c.getImagen());

            ps.executeUpdate();
        }
    }

    public List<Computer> readAll() throws Exception {
        List<Computer> list = new ArrayList<>();
        String sql = "SELECT * FROM equipos ORDER BY id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public void update(Computer c) throws Exception {
        String sql = "UPDATE equipos SET " +
                "codigo=?, tipo_equipo=?, marca=?, modelo=?, sistema=?, " +
                "almacenamiento=?, ram=?, estado=?, fecha_mantenimiento=?, " +
                "fecha_registro=?, imagen=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getCodigo());
            ps.setString(2, c.getTipoEquipo());
            ps.setString(3, c.getMarca());
            ps.setString(4, c.getModelo());
            ps.setString(5, c.getSistema());
            ps.setString(6, c.getAlmacenamiento());
            ps.setString(7, c.getRam());
            ps.setString(8, c.getEstado());
            ps.setDate(9, Date.valueOf(c.getFechaMantenimiento()));
            ps.setDate(10, Date.valueOf(c.getFechaRegistro()));
            ps.setString(11, c.getImagen());
            ps.setInt(12, c.getId());

            ps.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM equipos WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Computer map(ResultSet rs) throws Exception {
        Computer c = new Computer();

        c.setId(rs.getInt("id"));
        c.setCodigo(rs.getString("codigo"));
        c.setTipoEquipo(rs.getString("tipo_equipo"));
        c.setMarca(rs.getString("marca"));
        c.setModelo(rs.getString("modelo"));
        c.setSistema(rs.getString("sistema"));
        c.setAlmacenamiento(rs.getString("almacenamiento"));
        c.setRam(rs.getString("ram"));
        c.setEstado(rs.getString("estado"));

        Date fm = rs.getDate("fecha_mantenimiento");
        if (fm != null) c.setFechaMantenimiento(fm.toLocalDate());

        Date fr = rs.getDate("fecha_registro");
        if (fr != null) c.setFechaRegistro(fr.toLocalDate());

        c.setImagen(rs.getString("imagen"));

        return c;
    }
}
