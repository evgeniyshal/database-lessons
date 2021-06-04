package dao;

import pool.PoolDataSource;

import java.sql.*;
import java.util.ConcurrentModificationException;
import java.util.List;

public class BookDao implements Dao<Book, Integer> {

    public void createTable(){ // метод на вход может принимать название класса Bool.class, Course.class,
        String createSql = "CREATE TABLE IF NOT EXIST tb_books (" +
                "id SERIAL PRIMARY KEY," +
                "title VARCHAR(200) NOT NULL," +
                "page_count"+")";

            Connection connection = PoolDataSource.getConnection();
            try (Statement statement = PoolDataSource.getConnection().createStatement()) {
                statement.executeUpdate(createSql);

            } catch (SQLException e) {
                System.out.println("Запрос не был выполнен" + e.getMessage());
            }



    }

    @Override
    public void add(Book entity) {
        String insertSql = "INSERT INTO tb_books(title, page_count) VALUES (?,?)";
        try(PreparedStatement statement = PoolDataSource.getConnection().prepareStatement(insertSql)) {
            statement.setString(1,entity.getTitle());
            statement.setString(2,entity.getTitle());
            statement.executeUpdate();
            try(ResultSet resultSet = statement.getGeneratedKeys()){
                if(resultSet.next()){
                    entity.setId(resultSet.getInt(1));
                }

            }
        } catch ( SQLException e) {
        System.out.println("Запрос не был выполнен..." + e.getMessage());


        }
    }

    @Override
    public Book getByPK(Integer integer) {
        String sql = "SELECT * FROM tb_books WHERE id = ?";
        Book book = new Book();
        try (PreparedStatement statement = PoolDataSource.getConnection().prepareStatement(sql)) {
            statement.setInt(1, integer);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()){
                   book.setId(result.getInt("id"));
                   book.setTitle(result.getString("title"));
                   book.setPageCount(result.getInt("page_count"));
                }
            }
        } catch ( SQLException e) {
            System.out.println("Запрос не был выполнен" + e.getMessage());
        }
        return book;
    }


    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public void update(Book entity) {
        String update = "UPDATE tb_books SET title = ?, page_count =?" +
                "WHERE ID = ?";
        try (PreparedStatement statement = PoolDataSource.getConnection().prepareStatement(update)) {
            statement.setString(1, entity.getTitle());
            statement.setInt(2,entity.getPageCount());
            statement.setInt(3,entity.getId());
            statement.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Запрос не был выполнен " + e.getMessage());
        }
    }

    @Override
    public void deleteByPk(Integer integer) {
    }

}
