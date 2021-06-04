package jbdc;
//для взаимодествия с бд используется JDBC java DataBase Connectivity.
// Чтобы взаимодествовать нужны драйвера соотв. ( в качестве зависимости)


import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;

public class Examples {
    // Для соед. с севрером бд нужно 3 вещи:
    /* Строка подключения
     * Логин
     * Пароль
     */
    private static final String CONNECTION_STR = "jdbc:postgresql://localhost:5432/lessons";
    // строка подключения jbdс - название драйвера
    // - адрес сервера базы данных
    // - порт серверной программы
    // - название базы данных с которой будем работать


    private static final String LOGIN = "ifmo";

    private static final String PASSWORD = "ifmo21";

    private static void createTable() { // SQL принято писать в верхнем регистре
        String createString = "CREATE TABLE IF NOT EXISTS course (" +
                "id SERIAL PRIMARY KEY," +
                "title VARCHAR(255) NOT NULL," +
                "duration SMALLINT," +
                "price NUMERIC(9,2)" +
                ")";
        // Регистрация драйвера, загрузка класса
        try {
            Class.forName("org.postgresql.Driver"); // если класс не найден, выпадет exception
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("драйвер не найден"); // Выбросим Runtime
        }
        try (Connection connection = DriverManager.getConnection(CONNECTION_STR, LOGIN, PASSWORD)) {
            // getConnection обращается к драйверу, вернет соединение, всегда закрывать соединение
            // позволяет установить соединение
            try (Statement statement = connection.createStatement()) {
                //Позволяет выполнить запрос. Установка соединения,
                //через объект connection, и вернет объект,
                // .createStatement() с помощью которого выполним запрос
                System.out.println(statement.executeUpdate(createString));
                // выполняем запрос с помощью метода .executeUpdate(createString)
            }
        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос" + e.getMessage());
        }
    }

    private static void insertIntoCourse(Course course) {
        String insertSql = "INSERT INTO course(title,duration,price)" +
                "VALUES(?,?,?)"; // вместо каждого значения ставятся
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("драйвер не найден");
        }
        try (Connection connection = DriverManager.getConnection(CONNECTION_STR, LOGIN, PASSWORD)) {
            try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
                statement.setString(1, course.getTitle());
                statement.setInt(2, course.getDuration());
                statement.setDouble(3, course.getPrice());
                System.out.println(statement.executeUpdate());
            }
        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос" + e.getMessage());

        }

    }

    /* Метод Statement execute(), возвращает true false,
    тоже можно использовать для добавления

    executeQuery(). - возвращает resultSet,
    в объекте итератор устанавливается на позиции перед первой строкой, чтобы переместиться к первой нужен метод next()

    executeUpdate() - создать, удалить, обновить таблицу,
    вернет кол-во модифицированных строк (если созд или уд. таблицу метод вернет 0)
    * */

    // Select - получение данных
    // jdbc - не умеет создавать объекты
    private static HashSet<Course> selectAll() {
        String selectAll = "SELECT * FROM course";
        HashSet<Course> courses = new HashSet<>();
        try {
            Class.forName("org.postgresql.Driver"); // если класс не найден, выпадет exception
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("драйвер не найден"); // Выбросим Runtime
        }
        try (Connection connection = DriverManager.getConnection(CONNECTION_STR, LOGIN, PASSWORD)) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet result = statement.executeQuery(selectAll)) {
                    while (result.next()) {
                        int id = result.getInt("id"); // можно передать номер столбца, либо название столбца
                        String title = result.getString("title");
                        int duration = result.getInt("duration");
                        double price = result.getDouble("price");
                        Course course = new Course(title, duration, price);
                        course.setId(id);
                        courses.add(course); // кладем в hashSet
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос" + e.getMessage());
        }
        return courses;
    }




    // возваращает сет с курсами больше указанных
    // SELECT * FROM course WHERE price > ?";
        private static HashSet<Course> getByPrice(double price) {
            String getByPrice = "SELECT * FROM course WHERE price > ?";
            HashSet<Course> courses = new HashSet<>();
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("драйвер не найден");
            }
            try (Connection connection = DriverManager.getConnection(CONNECTION_STR, LOGIN, PASSWORD)) {
                try (PreparedStatement statement = connection.prepareStatement(getByPrice)) {
                    statement.setDouble(1, price); // вместо вопроса передает параметр
                    try (ResultSet result = statement.executeQuery()) {
                        while (result.next()) {
                            int id = result.getInt("id");
                            String title = result.getString("title");
                            int duration = result.getInt("duration");
                            double prices = result.getDouble("price");
                            Course course = new Course(title, duration, prices);
                            course.setId(id);
                            courses.add(course);
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("Не удалось выполнить запрос" + e.getMessage());
            }
            return courses;
        }


    private static void bufferInsert(HashSet<Course> courses) {
        String insertSql = "INSERT INTO course(title,duration,price)" +
                "VALUES(?,?,?)"; // вместо каждого значения ставятся
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("драйвер не найден");
        }
        try (Connection connection = DriverManager.getConnection(CONNECTION_STR, LOGIN, PASSWORD)) {
            try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
                for (Course course : courses) {
                    statement.setString(1, course.getTitle());
                    statement.setInt(2, course.getDuration());
                    statement.setDouble(3, course.getPrice());
                    statement.addBatch(); // накопление запросов а не перезапись
                }
                System.out.println(Arrays.toString(statement.executeBatch())); // в цикле создали запросы и выполнили друг за другом
            }
        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос" + e.getMessage());
        }
    }
 // DELETE * FROM  имя таблицы WHERE
    // если не прописать условия, удалятся все строки.
    // Запрос на обнов. данных
    // UPDATE course SET duration = 200 WHERE id = 3;







    public static void main(String[] args) {
        System.out.println("создает таблицу");
        createTable();
        /* insertIntoCourse(new Course("JAVA",3,40000));
        insertIntoCourse(new Course("C",2,50000));
        insertIntoCourse(new Course("PYTHON",4,50000));
        insertIntoCourse(new Course("C++",2,60000));*/
        System.out.println("Выведет ссылки");
        System.out.println(selectAll());
        System.out.println(getByPrice(40000));



        HashSet<Course> courses = new HashSet<>();
        courses.add(new Course("JAVA",4, 50000));
        courses.add(new Course("JAVAScript",2,20000));
        courses.add(new Course("NODE JS",3,30000));
    }
}
//  Типы данных postgres - пройтись
// Что нужно сделать чтобы выполнить запрос
// Отличие метод
// execute update
// execute Query -
//  Statement от Prepared Statement
// как получить данные из ResultSET
