import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by пользователь on 23.12.2016.
 */
public class DeleteQueryBuilder extends QueryBuilder {
    private class Selection {
        String fieldName;
        String comparisonSign;
        Object value;
        Type type;

        public Selection(String comparisonSign, String fieldName, Object value, Class type) {
            this.comparisonSign = comparisonSign;
            this.fieldName = fieldName;
            this.value = value;
            this.type = type;
        }
    }

    private String tableName;
    private List<DeleteQueryBuilder.Selection> selections;

    public DeleteQueryBuilder() {
        this.selections = new ArrayList<DeleteQueryBuilder.Selection>();
    }

    public DeleteQueryBuilder from(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public DeleteQueryBuilder whereEqual(String fieldName, Object value, Class type) {
        selections.add(new DeleteQueryBuilder.Selection("=", fieldName, value, type));
        return this;
    }

    public DeleteQueryBuilder whereNotEqual(String fieldName, Object value, Class type) {
        selections.add(new DeleteQueryBuilder.Selection("<>", fieldName, value, type));
        return this;
    }

    public DeleteQueryBuilder whereLess(String fieldName, Object value, Class type) {
        selections.add(new DeleteQueryBuilder.Selection("<", fieldName, value, type));
        return this;
    }

    public DeleteQueryBuilder whereGreater(String fieldName, Object value, Class type) {
        selections.add(new DeleteQueryBuilder.Selection(">", fieldName, value, type));
        return this;
    }

    public DeleteQueryBuilder whereLessOrEqual(String fieldName, Object value, Class type) {
        selections.add(new DeleteQueryBuilder.Selection("<=", fieldName, value, type));
        return this;
    }

    public DeleteQueryBuilder whereGreaterOrEqual(String fieldName, Object value, Class type) {
        selections.add(new DeleteQueryBuilder.Selection(">=", fieldName, value, type));
        return this;
    }

    @Override
    public Query build() {
        sb.append("DELETE FROM ");
        sb.append(tableName);
        sb.append(" WHERE ");
        sb.append(selections.get(0).fieldName);
        sb.append(selections.get(0).comparisonSign);

        if (selections.get(0).type.equals(String.class))
            sb.append("'");
        sb.append(selections.get(0).value);
        if (selections.get(0).type.equals(String.class))
            sb.append("'");

        for (int index = 1; index < selections.size(); index++) {
            sb.append(" AND ");

            sb.append(selections.get(index).fieldName);
            sb.append(selections.get(index).comparisonSign);

            if (selections.get(index).type.equals(String.class))
                sb.append("'");
            sb.append(selections.get(index).value);
            if (selections.get(index).type.equals(String.class))
                sb.append("'");
        }

        return super.build();
    }
}
