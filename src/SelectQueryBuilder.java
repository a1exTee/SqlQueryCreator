import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by пользователь on 23.12.2016.
 */
public class SelectQueryBuilder extends QueryBuilder {

    private class Case {
        String fieldName;
        String comparisonSign;
        Object value;
        Type type;

        public Case(String comparisonSign, String fieldName, Object value, Class type) {
            this.comparisonSign = comparisonSign;
            this.fieldName = fieldName;
            this.value = value;
            this.type = type;
        }

        public Case(String fieldName) {
            this.fieldName = fieldName;
        }
    }

    private String tableName;
    private List<Case> cases;
    private List<String> fieldsNames;

    public SelectQueryBuilder() {
        this.cases = new ArrayList<Case>();
        this.fieldsNames = new ArrayList<String>();
    }

    public SelectQueryBuilder from(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public SelectQueryBuilder select(String... fieldNames) {
        for (String fieldName: fieldNames) {
            fieldsNames.add(fieldName);
        }
        return this;
    }

    public SelectQueryBuilder whereEqual(String fieldName, Object value, Class type) {
        cases.add(new Case("=", fieldName, value, type));
        return this;
    }

    public SelectQueryBuilder whereNotEqual(String fieldName, Object value, Class type) {
        cases.add(new Case("<>", fieldName, value, type));
        return this;
    }

    public SelectQueryBuilder whereLess(String fieldName, Object value, Class type) {
        cases.add(new Case("<", fieldName, value, type));
        return this;
    }

    public SelectQueryBuilder whereGreater(String fieldName, Object value, Class type) {
        cases.add(new Case(">", fieldName, value, type));
        return this;
    }

    public SelectQueryBuilder whereLessOrEqual(String fieldName, Object value, Class type) {
        cases.add(new Case("<=", fieldName, value, type));
        return this;
    }

    public SelectQueryBuilder whereGreaterOrEqual(String fieldName, Object value, Class type) {
        cases.add(new Case(">=", fieldName, value, type));
        return this;
    }

    @Override
    public Query build() {
        sb.append("SELECT ");

        sb.append("'");
        sb.append(fieldsNames.get(0));
        sb.append("'");

        for(int index = 1; index < fieldsNames.size(); index++){
            sb.append(", ");
            sb.append("'");
            sb.append(fieldsNames.get(index));
            sb.append("'");
        }

        sb.append(" FROM ");
        sb.append(tableName);
        sb.append(" WHERE ");
        sb.append(cases.get(0).fieldName);
        sb.append(cases.get(0).comparisonSign);
        if (cases.get(0).type.equals(String.class)) {
            sb.append("'");
            sb.append(cases.get(0).value);
            sb.append("'");
        } else {
            sb.append(cases.get(0).value);
        }

        for (int index = 1; index < cases.size(); index++) {
            sb.append(" AND ");
            sb.append(cases.get(index).fieldName);
            sb.append(cases.get(index).comparisonSign);

            if (cases.get(index).type.equals(String.class)) {
                sb.append("'");
                sb.append(cases.get(index).value);
                sb.append("'");
            } else {
                sb.append(cases.get(index).value);
            }
        }

        return super.build();
    }
}
