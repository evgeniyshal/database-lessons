package dao;
// Класс описывающий объекты Book - класс сущность
// dao класс - описываем Логику.
public class Book {
    private int id;
    private String title;
    private int pageCount;
    // Никаких методов связанных с соединением с базой данных
    // C доб. удалением, изменением. ( Должны находиться в отдельном классе, dao класс)

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
