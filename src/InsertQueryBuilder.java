import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by пользователь on 23.12.2016.
 */
public class InsertQueryBuilder extends QueryBuilder {
    private class Insertion {
        String fieldName;
        Object value;
        Type type;

        public Insertion(String fieldName, Object value, Class type) {
            this.fieldName = fieldName;
            this.value = value;
            this.type = type;
        }
    }

    private String tableName;
    private List<Insertion> insertions;

    public InsertQueryBuilder() {
        this.insertions = new ArrayList<Insertion>();
    }

    public InsertQueryBuilder into(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public InsertQueryBuilder field(String fieldName, Object value, Class type) {
        insertions.add(new Insertion(fieldName, value, type));
        return this;
    }

    @Override
    public Query build() {
        sb.append("INSERT INTO ");
        sb.append(tableName);
        sb.append("(");
        sb.append(insertions.get(0).fieldName);
        for (int index = 1; index < insertions.size(); index++) {
            sb.append(", ");
            sb.append(insertions.get(index).fieldName);
        }
        sb.append(") VALUES (");
        if (insertions.get(0).type.equals(String.class))
            sb.append("'");
        sb.append(insertions.get(0).value);
        if (insertions.get(0).type.equals(String.class))
            sb.append("'");

        for (int index = 1; index < insertions.size(); index++) {
            sb.append(", ");

            if (insertions.get(index).type.equals(String.class))
                sb.append("'");
            sb.append(insertions.get(index).value);
            if (insertions.get(index).type.equals(String.class))
                sb.append("'");
        }
        sb.append(")");

        return super.build();
    }
}
