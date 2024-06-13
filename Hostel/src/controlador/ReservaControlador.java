package controlador;

import modelo.Reserva;
import interfaces.ReservaRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ReservaControlador implements ReservaRepository {
    private final Connection connection;

    public ReservaControlador() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void addReserva(Reserva reserva) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO reservas (fecha_entrada, fecha_salida, id_huesped, numero_habitacion, nombreHuesped) VALUES (?, ?, ?, ?, ?)"
            );
            statement.setDate(1, (Date) reserva.getFecha_entrada());
            statement.setDate(2, (Date) reserva.getFecha_salida());
            statement.setInt(3, reserva.getId_huesped());
            statement.setInt(4, reserva.getNumero_habitacion());
            statement.setInt(5, reserva.getId_huesped());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Reserva insertada exitosamente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Reserva> getAllReservas() {
        List<Reserva> reservas = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM reservas");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Reserva reserva = new Reserva(
                    resultSet.getInt("id_reserva"),
                    resultSet.getDate("fecha_entrada"),
                    resultSet.getDate("fecha_salida"),
                    resultSet.getInt("id_huesped"),
                    resultSet.getInt("numero_habitacion"),
                    resultSet.getString("nombreHuesped")
                );
                reservas.add(reserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    @Override
    public Reserva getReservaById(int id) {
        Reserva reserva = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM reservas WHERE id_reserva = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                reserva = new Reserva(
                    resultSet.getInt("id_reserva"),
                    resultSet.getDate("fecha_entrada"),
                    resultSet.getDate("fecha_salida"),
                    resultSet.getInt("id_huesped"),
                    resultSet.getInt("numero_habitacion"),
                    resultSet.getString("nombreHuesped")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reserva;
    }

    @Override
    public void updateReserva(Reserva reserva) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "UPDATE reservas SET fecha_entrada = ?, fecha_salida = ?, id_huesped = ?, numero_habitacion = ?, nombreHuesped = ? WHERE id_reserva = ?"
            );
            statement.setDate(1, (Date) reserva.getFecha_entrada());
            statement.setDate(2, (Date) reserva.getFecha_salida());
            statement.setInt(3, reserva.getId_huesped());
            statement.setInt(4, reserva.getNumero_habitacion());
            statement.setInt(5, reserva.getId_huesped());
            statement.setInt(6, reserva.getId_reserva());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Reserva actualizada exitosamente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteReserva(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM reservas WHERE id_reserva = ?");
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Reserva eliminada exitosamente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Reserva buscarReservaPorDNI(int dni) {
        List<Reserva> reservas = getAllReservas();
        for (Reserva reserva : reservas) {
            if (reserva.getId_huesped() == dni) {
                return reserva; 
            }
        }
        return null; 
    }
    

}
