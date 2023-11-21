
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import Data.DbContext;

public class Main {
    public static void main(String[] arg){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("---------------------------------");
            System.out.println("Selecione a área:");
            System.out.println("1 - Cliente");
            System.out.println("2 - Estoque");
            System.out.println("3 - Gestão");
            System.out.println("4 - Sair");
            System.out.println("---------------------------------");
            
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    areaCliente(scanner);
                    break;
                case 2:
                    areaEstoque(scanner);
                    break;
                case 3:
                    areaGestao(scanner);
                    break;
                case 4:
                System.out.println("Programa finalizado.");
                System.out.println("---------------------------------");
                scanner.close();  
                return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\   Area    /////////////////////////////////////////
    // ///////////////////////////////////////  Cliente  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    
    public static void areaCliente(Scanner scanner) {
        while (true) {
            
            System.out.println("---------------------------------");
            System.out.println("Selecione a área de Cliente:");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Listar Clientes");
            System.out.println("3 - Atualizar Cliente");
            System.out.println("4 - Deletar cliente");
            System.out.println("5 - Voltar");
            System.out.println("---------------------------------");
            
            int opcliente = scanner.nextInt();

            scanner.nextLine();

            switch (opcliente) {
                case 1:
                    cadastrarCliente(scanner);
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    atualizarCliente(scanner);
                    break;
                case 4:                
                    excluirCliente(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static boolean cadastrarCliente(Scanner scanner) {
        try {
            // Solicita informações do cliente ao usuário
            System.out.print("Nome do cliente: ");
            String nome = scanner.nextLine().trim();
            System.out.print("Email do cliente: ");
            String email = scanner.nextLine();
            System.out.print("Telefone do cliente: ");
            String telefone = scanner.nextLine();

            // Conexão com o banco de dados
            DbContext database = new DbContext();

            // Query SQL para inserir cliente
            String query = "INSERT INTO clientes (nome, email, telefone) VALUES (?, ?, ?)";
            PreparedStatement stmt = database.prepareStatement(query);
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, telefone);

            // Executa a query
            int rowsAffected = stmt.executeUpdate();

            // Verifica se o cliente foi cadastrado com sucesso
            if (rowsAffected > 0) {
                System.out.println("---------------------------------");
                System.out.println("Cliente cadastrado com sucesso.");
                return true;
            } else {
                System.out.println("---------------------------------");
                System.out.println("Falha ao cadastrar o cliente.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void listarClientes() {
       
        DbContext dbContext = new DbContext();
       
        try {
            // Query SQL para selecionar todos os clientes
            String query = "SELECT id, nome, email, telefone FROM clientes";
            PreparedStatement stmt = dbContext.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            // Exibe os clientes
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String telefone = resultSet.getString("telefone");
                
                System.out.println("------------");
                System.out.println("ID: " + id);
                System.out.println("Nome: " + nome);
                System.out.println("Email: " + email);
                System.out.println("Telefone: " + telefone);
                System.out.println("------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean atualizarCliente(Scanner scanner) {
        DbContext dbContext = new DbContext();
        
        // Lista os clientes antes de solicitar informações de atualização
        listarClientes();
        System.out.print("Qual o ID do cliente: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Email do cliente: ");
        String email = scanner.nextLine();
        System.out.print("Telefone do cliente: ");
        String telefone = scanner.nextLine();

        try {
            // Query SQL para atualizar o cliente no banco de dados
            String query = "UPDATE clientes SET nome = ?, email = ?, telefone = ? WHERE id = ?";
            PreparedStatement stmt = dbContext.prepareStatement(query);
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, telefone);
            stmt.setInt(4, id);

            // Executa a atualização e verifica se foi bem-sucedida
            int rowsAffected = stmt.executeUpdate();

             if (rowsAffected > 0) {
                System.out.println("---------------------------------");
                System.out.println("Cliente atualizado com sucesso.");
                return true;
            } else {
                System.out.println("---------------------------------");
                System.out.println("Falha ao atualizar o cliente.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void excluirCliente(Scanner scanner) {
        try {
            // Lista os clientes antes de solicitar a exclusão
            listarClientes();
            System.out.print("Informe o ID do cliente que deseja excluir: ");
            int id = scanner.nextInt();
            
            DbContext database = new DbContext();
            
            // Query SQL para excluir o cliente
            String query = "DELETE FROM clientes WHERE id = ?";
            PreparedStatement stmt = database.prepareStatement(query);
            stmt.setInt(1, id);

            // Executa a exclusão e verifica se foi bem-sucedida
            int rowsAffected = stmt.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("---------------------------------");
                System.out.println("Cliente excluído com sucesso.");
            } else {
                System.out.println("---------------------------------");
                System.out.println("Falha ao excluir o cliente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();  
        }
    }

    /// As abas de Estoque e Gestão seguem a mesma lógica dos clientes. ////

    // \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\   Area    /////////////////////////////////////////
    // ///////////////////////////////////////  Estoque  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    public static void areaEstoque(Scanner scanner) {
        while (true) {

            System.out.println("---------------------------------");
            System.out.println("Selecione a área de Estoque:");
            System.out.println("1 - Cadastrar Item de Estoque");
            System.out.println("2 - Listar Itens de Estoque");
            System.out.println("3 - Atualizar Item de Estoque");
            System.out.println("4 - Deletar item de Estoque");
            System.out.println("5 -Voltar");
            System.out.println("---------------------------------");

            int opestoque = scanner.nextInt();

            switch (opestoque) {
                case 1:
                    cadastrarEstoque(scanner);
                    break;
                case 2:
                    listarEstoque();
                    break;
                case 3:
                    atualizarEstoque(scanner);
                    break;
                case 4:
                     excluirItemEstoque(scanner);
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void cadastrarEstoque(Scanner scanner) {
        DbContext dbContext = new DbContext();
        try {
            System.out.println("Digite o nome do remédio:");
            String nomeRemedio = scanner.next();

            System.out.println("Digite a quantidade em estoque:");
            int quantidade = scanner.nextInt();

            String query = "INSERT INTO estoque (nome_remedio, quantidade) VALUES (?, ?)";
            PreparedStatement stmt = dbContext.prepareStatement(query);
            stmt.setString(1, nomeRemedio);
            stmt.setInt(2, quantidade);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("---------------------------------");
                System.out.println("Remédio cadastrado");
            } else {
                System.out.println("---------------------------------");
                System.out.println("Falha ao cadastrar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listarEstoque() {
        DbContext dbContext = new DbContext();
        try {
            String query = "SELECT id, nome_remedio, quantidade FROM estoque";
            PreparedStatement stmt = dbContext.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
    
            System.out.println("Itens de Estoque:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nomeRemedio = resultSet.getString("nome_remedio");
                int quantidade = resultSet.getInt("quantidade");
                
                System.out.println("------------");
                System.out.println("ID: " + id);
                System.out.println("Nome do Remédio: " + nomeRemedio);
                System.out.println("Quantidade em Estoque: " + quantidade);
                System.out.println("------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void atualizarEstoque(Scanner scanner) {
        DbContext dbContext = new DbContext(); 
    
        try {
            
            System.out.println("\n");
            listarEstoque();
            System.out.println("\n");

            System.out.println("Digite o ID do remédio que deseja atualizar:");
            int idRemedio = scanner.nextInt();
            scanner.nextLine();  

            
            System.out.println("Digite a nova quantidade em estoque:");
            int novaQuantidade = scanner.nextInt();
    
           
            String queryAtualizar = "UPDATE estoque SET quantidade = ? WHERE id = ?";
            try (PreparedStatement stmtAtualizar = dbContext.prepareStatement(queryAtualizar)) {
                stmtAtualizar.setInt(1, novaQuantidade);
                stmtAtualizar.setInt(2, idRemedio);
    
                
                int rowsAffected = stmtAtualizar.executeUpdate();
    
                
                if (rowsAffected > 0) {
                    System.out.println("---------------------------------");
                    System.out.println("Quantidade em estoque atualizada");
                } else {
                    System.out.println("---------------------------------");
                    System.out.println("Falha ao atualizar a quantidade.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   
    public static void excluirItemEstoque(Scanner scanner) {
        DbContext database = new DbContext();
        try {
            System.out.print("Informe o ID que deseja excluir: ");
            int id = scanner.nextInt();
            scanner.nextLine();
    
            String query = "DELETE FROM estoque WHERE id = ?";
            try (PreparedStatement stmt = database.prepareStatement(query)) {
                stmt.setInt(1, id);
    
                int rowsAffected = stmt.executeUpdate();
    
                if (rowsAffected > 0) {
                    System.out.println("---------------------------------");
                    System.out.println("Item de estoque excluído com sucesso.");
                } else {
                    System.out.println("---------------------------------");
                    System.out.println("Falha ao excluir o item de estoque.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\   Area   /////////////////////////////////////////
    // ///////////////////////////////////////  Gestão  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    public static void areaGestao(Scanner scanner) {
        while (true) {

            System.out.println("---------------------------------");
            System.out.println("Selecione a área de Gestão:");
            System.out.println("1 - Cadastrar Promoção");
            System.out.println("2 - Listar Promoções");
            System.out.println("3 - Atualizar Promoção");
            System.out.println("4 - deletar Promoção");
            System.out.println("5 - Voltar");
            System.out.println("---------------------------------");
           
            int opgestao = scanner.nextInt();

            switch (opgestao) {
                case 1:
                    cadastrarPromocao(scanner);
                    break;
                case 2:
                    listarPromocoes();
                    break;
                case 3:
                    atualizarPromocao(scanner);
                    break;
                case 4:
                    excluirPromocao(scanner);
                    break;
                case 5:
                     return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void cadastrarPromocao(Scanner scanner) {
        DbContext dbContext = new DbContext();
        try {
            System.out.println("Digite o tipo de remédio da promoção:");
            String tipoRemedio = scanner.next();

            System.out.println("Digite a porcentagem de desconto da promoção (0-100):");
            int porcentagem = scanner.nextInt();

            String query = "INSERT INTO promocoes (tipo_remedio, porcentagem_desconto) VALUES (?, ?)";
            PreparedStatement stmt = dbContext.prepareStatement(query);
            stmt.setString(1, tipoRemedio);
            stmt.setInt(2, porcentagem);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("---------------------------------");
                System.out.println("Promoção cadastrada com sucesso!");
            } else {
                System.out.println("---------------------------------");
                System.out.println("Falha ao cadastrar a promoção.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listarPromocoes() {
        DbContext dbContext = new DbContext();
        try {
            String query = "SELECT id, tipo_remedio, porcentagem_desconto FROM promocoes";
            PreparedStatement stmt = dbContext.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            System.out.println("Lista de Promoções:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String tipoRemedio = resultSet.getString("tipo_remedio");
                int porcentagem = resultSet.getInt("porcentagem_desconto");
                
                System.out.println("------------");
                System.out.println("ID: " + id);
                System.out.println("Tipo de Remédio: " + tipoRemedio);
                System.out.println("Porcentagem de Desconto: " + porcentagem + "%");
                System.out.println("------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean existePromocao(int id, DbContext dbContext) {
        try {
            String query = "SELECT COUNT(*) AS count FROM promocoes WHERE id = ?";
            PreparedStatement stmt = dbContext.prepareStatement(query);
            stmt.setInt(1, id);

            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();

            int count = resultSet.getInt("count");
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void atualizarPromocao(Scanner scanner) {
        DbContext dbContext = new DbContext();
        try {

            System.out.println("Digite o ID da promoção que deseja atualizar:");
            int id = scanner.nextInt();

            if (!existePromocao(id, dbContext)) {
                System.out.println("Promoção não encontrada");
                return;
            }
            System.out.println("---------------------------------");
            System.out.println("Digite o novo tipo de remédio da promoção:");
            String novoTipoRemedio = scanner.next();
            System.out.println("---------------------------------");
            System.out.println("Digite a nova porcentagem de desconto da promoção (0-100):");
            int novaPorcentagem = scanner.nextInt();

            String query = "UPDATE promocoes SET tipo_remedio = ?, porcentagem_desconto = ? WHERE id = ?";
            PreparedStatement stmt = dbContext.prepareStatement(query);
            stmt.setString(1, novoTipoRemedio);
            stmt.setInt(2, novaPorcentagem);
            stmt.setInt(3, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("---------------------------------");
                System.out.println("Promoção atualizada ");
            } else {
                System.out.println("---------------------------------");
                System.out.println("Falha ao atualizar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     
    public static void excluirPromocao(Scanner scanner) {
        try {
            listarPromocoes();
    
            System.out.print("Informe o ID da promoção que deseja excluir: ");
            int id = scanner.nextInt();
    
            DbContext database = new DbContext();
    
            String query = "DELETE FROM promocoes WHERE id = ?";
            PreparedStatement stmt = database.prepareStatement(query);
            stmt.setInt(1, id);
    
            int rowsAffected = stmt.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("---------------------------------");
                System.out.println("Promoção excluída com sucesso.");
            } else {
                System.out.println("---------------------------------");
                System.out.println("Falha ao excluir a promoção.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
