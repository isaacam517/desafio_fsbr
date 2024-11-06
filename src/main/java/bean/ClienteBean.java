package bean;

import dao.ClienteDAO;
import model.Cliente;

import javax.enterprise.context.SessionScoped;  // CDI scoped annotation
import javax.inject.Named;  // CDI annotation for naming the bean
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import java.io.Serializable;
import java.util.List;

import javax.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;

@Named("clienteBean") 
@SessionScoped 
public class ClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Cliente cliente = new Cliente();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private List<Cliente> clientes;

    public String cadastrar() {
    	System.out.println("CLICOU");
        try {
           
            clienteDAO.salvarCliente(cliente);
            clientes = clienteDAO.listarClientes(); 
            cliente = new Cliente();  
            return "clientes"; 
        } catch (PersistenceException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Email já está em uso. Por favor, insira um email diferente.", "Erro ao Cadastrar");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Erro desconhecido", "Ocorreu um erro ao tentar cadastrar o cliente.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
            return "falha";  
        }
    }


    public List<Cliente> getClientes() {
        if (clientes == null) {
            clientes = clienteDAO.listarClientes(); 
        }
        return clientes;
    }

    public String editar(Cliente cliente) {
        this.cliente = cliente; 
        return "editarCliente"; 
    }

    public void excluir(Cliente cliente) {
        clienteDAO.removerCliente(cliente);
        clientes = null;
    }

    // Getters e Setters
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
