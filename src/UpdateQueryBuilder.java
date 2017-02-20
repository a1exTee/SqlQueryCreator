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
    }

    private String tableName;
    private List<UpdateQueryBuilder.Insertion> insertions;
    private List<Case> cases;

    public UpdateQueryBuilder() {
        this.insertions = new ArrayList<UpdateQueryBuilder.Insertion>();
        this.cases = new ArrayList<Case>();
    }

    public UpdateQueryBuilder update(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public UpdateQueryBuilder set(String fieldName, Object value, Class type) {
        insertions.add(new UpdateQueryBuilder.Insertion(fieldName, value, type));
        return this;
    }

    public UpdateQueryBuilder whereEqual(String fieldName, Object value, Class type) {
        cases.add(new Case("=", fieldName, value, type));
        return this;
    }

    public UpdateQueryBuilder whereNotEqual(String fieldName, Object value, Class type) {
        cases.add(new Case("<>", fieldName, value, type));
        return this;
    }

    public UpdateQueryBuilder whereLess(String fieldName, Object value, Class type) {
        cases.add(new Case("<", fieldName, value, type));
        return this;
    }

    public UpdateQueryBuilder whereGreater(String fieldName, Object value, Class type) {
        cases.add(new Case(">", fieldName, value, type));
        return this;
    }

    public UpdateQueryBuilder whereLessOrEqual(String fieldName, Object value, Class type) {
        cases.add(new Case("<=", fieldName, value, type));
        return this;
    }

    public UpdateQueryBuilder whereGreaterOrEqual(String fieldName, Object value, Class type) {
        cases.add(new Case(">=", fieldName, value, type));
        return this;
    }


    @Override
    public Query build() {
        sb.append("UPDATE ");
        sb.append(tableName);
        sb.append(" SET ");

        sb.append(insertions.get(0).fieldName);
        sb.append(" = ");
        if(insertions.get(0).type.equals(String.class)) {
            sb.append("'");
            sb.append(insertions.get(0).value);
            sb.append("'");
        }
        else {
            sb.append(insertions.get(0).value);
        }

        for(int index = 1; index < insertions.size(); index++){
            sb.append(", ");
            sb.append(insertions.get(index).fieldName);
            sb.append(" = ");
            if(insertions.get(index).type.equals(String.class)) {
                sb.append("'");
                sb.append(insertions.get(index).value);
                sb.append("'");
            }
            else {
                sb.append(insertions.get(index).value);
            }
        }

        sb.append(" WHERE ");
        sb.append(cases.get(0).fieldName);
        sb.append(cases.get(0).comparisonSign);
        if(cases.get(0).type.equals(String.class)){
            sb.append("'");
            sb.append(cases.get(0).value);
            sb.append("'");
        }
        else {
            sb.append(cases.get(0).value);
        }

        for (int index = 1; index < cases.size(); index++) {
            sb.append(" AND ");
            sb.append(cases.get(index).fieldName);
            sb.append(cases.get(index).comparisonSign);

            if(cases.get(index).type.equals(String.class)) {
                sb.append("'");
                sb.append(cases.get(index).value);
                sb.append("'");
            }
            else {
                sb.append(cases.get(index).value);
            }
        }

        return super.build();
    }
}
