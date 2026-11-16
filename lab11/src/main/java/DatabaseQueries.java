import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class DatabaseQueries {
    public static void main(String[] args) {
        resetDatabase();
        printAllEmployees();
        printAllTasks();
        printEmployeesByDepartment(1);
        addTask("task description test", 2);
        printTasksByEmployee(2);
        deleteEmployee(3);
        printAllEmployees();
    }

    public static void printAllEmployees() {
        String query = """
                select e.employee_id, e.first_name, e.last_name, e.job_title, d.department_name
                from employee e
                join department d using (department_id)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("\nEmployees list:");

            while (resultSet.next()) {
                int employeeId = resultSet.getInt("employee_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String job = resultSet.getString("job_title");
                String department = resultSet.getString("department_name").trim();

                System.out.printf("Employee ID: %d | Full name: %s %s | Job: %s | Department: %s%n",
                        employeeId, firstName, lastName, (job != null ? job : "(not specified)"), department);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void printAllTasks() {
        String query = """
                select t.task_id, t.description, e.first_name, e.last_name
                from task t
                join employee e using (employee_id)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("\nTasks list:");

            while (resultSet.next()) {
                int taskId = resultSet.getInt("task_id");
                String description = resultSet.getString("description");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                System.out.printf("Task ID: %d | Task description: %s | Full name: %s %s%n",
                        taskId, description, firstName, lastName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void printEmployeesByDepartment(int departmentId) {
        String query = """
                select employee_id, first_name, last_name, job_title
                from employee
                where department_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, departmentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                System.out.println("\nEmployees of department ID " + departmentId + ":");
                while (resultSet.next()) {
                    String job = resultSet.getString("job_title");
                    System.out.printf("Employee ID: %d | Full name: %s %s | Job: %s%n",
                            resultSet.getInt("employee_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            (job != null ? job : "(not specified)"));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void addTask(String description, int employeeId) {
        String query = "insert into task (description, employee_id) values (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, description);
            statement.setInt(2, employeeId);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("\nTask has been added");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void printTasksByEmployee(int employeeId) {
        String query = """
                select task_id, description
                from task
                where employee_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, employeeId);

            try (ResultSet resultSet = statement.executeQuery()) {
                System.out.println("\nTasks for employee ID " + employeeId + ":");

                boolean hasRows = false;
                while (resultSet.next()) {
                    System.out.printf("Task ID: %d | Description: %s%n",
                            resultSet.getInt("task_id"),
                            resultSet.getString("description"));
                    hasRows = true;
                }
                if (!hasRows) {
                    System.out.println("No tasks found");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteEmployee(int employeeId) {
        String query = "delete from employee where employee_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, employeeId);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("\nEmployee and associated tasks have been deleted");
            } else {
                System.out.println("\nEmployee with ID does not exist");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void resetDatabase() {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("init.sql");
             Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            if (input == null) {
                throw new RuntimeException("File init.sql not found");
            }

            String queries = new String(input.readAllBytes(), StandardCharsets.UTF_8);
            statement.execute(queries);

            System.out.println("\nDatabase has been reset to initial state");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
