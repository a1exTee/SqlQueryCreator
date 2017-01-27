/**
 * Created by пользователь on 23.12.2016.
 */
public class QueryBuilder {
    protected StringBuilder sb;

    public QueryBuilder() {
        sb = new StringBuilder();
    }

    public Query build() {
        return new Query(sb.toString());
    }
}
