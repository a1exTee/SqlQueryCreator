/**
 * Created by пользователь on 27.01.2017.
 */
public class Query {
    private String queryString;

    public Query(String queryString) {
        this.queryString = queryString;
    }

    public String getSql() {
        return queryString;
    }
}
