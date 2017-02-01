import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by пользователь on 23.12.2016.
 */
public class UpdateQueryBuilder extends QueryBuilder {


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
    private List<UpdateQueryBuilder.Insertion> insertions;

    public UpdateQueryBuilder() {
        this.insertions = new ArrayList<UpdateQueryBuilder.Insertion>();
    }

    public UpdateQueryBuilder into(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public UpdateQueryBuilder field(String fieldName, Object value, Class type) {
        insertions.add(new UpdateQueryBuilder.Insertion(fieldName, value, type));
        return this;
    }

    @Override
    public Query build() {
        sb.append("UPDATE ");
        sb.append(tableName);
        sb.append("(");
        sb.append(insertions.get(0).fieldName);
        for (int index = 1; index < insertions.size(); index++) {
            sb.append(", ");
            sb.append(insertions.get(index).fieldName);
        }
        sb.append(") VALUES (");
        sb.append(insertions.get(0).value);
        for (int index = 1; index < insertions.size(); index++) {
            sb.append(", ");
            sb.append(insertions.get(index).value);
        }
        sb.append(")");

        return super.build();
    }
}
