import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by пользователь on 23.12.2016.
 */
public class InsertQuery extends Query {
    private class Triple {
        String fieldName;
        Object value;
        Type type;
    }

    String tableInto;
    List<Triple> triples;

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();

        sb.append("INSERT INTO ");
        sb.append(tableInto);
        sb.append("(");
        for (Triple triple : triples) {
            sb.append(triple.fieldName);
            sb.append(", ");
        }
        sb.append(") VALUES (");
        for (Triple triple : triples) {
            sb.append(triple.value);
            sb.append(", ");
        }
        sb.append(")");

        return sb.toString();
    }
}
