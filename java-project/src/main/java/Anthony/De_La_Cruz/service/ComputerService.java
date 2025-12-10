package Anthony.De_La_Cruz.service;

import Anthony.De_La_Cruz.controller.ComputerController;
import Anthony.De_La_Cruz.model.Computer;

import java.util.List;

public class ComputerService {

    private final ComputerController controller = new ComputerController();

    public void registrar(Computer c) throws Exception {
        controller.create(c);
    }

    public void actualizar(Computer c) throws Exception {
        controller.update(c);
    }

    public List<Computer> listar() throws Exception {
        return controller.readAll();
    }

    public void eliminar(int id) throws Exception {
        controller.delete(id);
    }
}
