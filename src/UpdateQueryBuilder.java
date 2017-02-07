import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.sun.corba.se.spi.activation.IIOP_CLEAR_TEXT.value;

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
    private List<UpdateQueryBuilder.Insertion> insertions;
    private List<UpdateQueryBuilder.Selection> selections;

    public UpdateQueryBuilder() {
        this.insertions = new ArrayList<UpdateQueryBuilder.Insertion>();
        this.selections = new ArrayList<UpdateQueryBuilder.Selection>();
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
        selections.add(new UpdateQueryBuilder.Selection("=", fieldName, value, type));
        return this;
    }

    public UpdateQueryBuilder whereNotEqual(String fieldName, Object value, Class type) {
        selections.add(new UpdateQueryBuilder.Selection("<>", fieldName, value, type));
        return this;
    }

    public UpdateQueryBuilder whereLess(String fieldName, Object value, Class type) {
        selections.add(new UpdateQueryBuilder.Selection("<", fieldName, value, type));
        return this;
    }

    public UpdateQueryBuilder whereGreater(String fieldName, Object value, Class type) {
        selections.add(new UpdateQueryBuilder.Selection(">", fieldName, value, type));
        return this;
    }

    public UpdateQueryBuilder whereLessOrEqual(String fieldName, Object value, Class type) {
        selections.add(new UpdateQueryBuilder.Selection("<=", fieldName, value, type));
        return this;
    }

    public UpdateQueryBuilder whereGreaterOrEqual(String fieldName, Object value, Class type) {
        selections.add(new UpdateQueryBuilder.Selection(">=", fieldName, value, type));
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
        sb.append(selections.get(0).fieldName);
        sb.append(selections.get(0).comparisonSign);
        if(selections.get(0).type.equals(String.class)){
            sb.append("'");
            sb.append(selections.get(0).value);
            sb.append("'");
        }
        else {
            sb.append(selections.get(0).value);
        }

        for (int index = 1; index < selections.size(); index++) {
            sb.append(" AND ");
            sb.append(selections.get(index).fieldName);
            sb.append(selections.get(index).comparisonSign);

            if(selections.get(index).type.equals(String.class)) {
                sb.append("'");
                sb.append(selections.get(index).value);
                sb.append("'");
            }
            else {
                sb.append(selections.get(index).value);
            }
        }

        return super.build();
    }
}
