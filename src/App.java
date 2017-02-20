
/*
Суть задачи в следующем. Есть класс SQL-фабрики, который представляет собой креатор объектов-запросов (типов INSERT / DELETE / UPDATE / SELECT)
далее эти объекты предоставляют методы-замыкатели на свой класс, например для инсерта:

InsertQueryBuilder iq =
SQLFactory.createInsertQuery().into("Some_Table")
.field("F_alpha", "a_value", String.class)
.field("F_Beta", false, Boolean.class)
.field("F_gamma", BigDecimal.TEN, BigDecimal.class)
.field("F_Delta", 0.01, Double.class);
каждый класс для каждого типа запроса предоставляет метод build(), возвращающий строковый эквивалент запроса, например, для описанного выше:
iq.build()
вернёт

INSERT INTO SOME_TABLE(F_ALPHA, F_BETA, F_GAMMA, F_DELTA) VALUES ('a_value', false, 10, 0.01)
UPDATE SOME_TABLE SET F_ALPHA='a_value', F_BETA=false, F_GAMMA=10, F_DELTA=0.01 WHERE F_ALPHA = 'green_elephant', F_DELTA <= 0.05 AND F_BETA = true
SELECT F_ALPHA, F_BETA, F_GAMMA, F_DELTA FROM SOME_TABLE WHERE F_ALPHA = 'green_elephant' AND F_DELTA => 0.05 AND F_BETA = true AND F_GAMMA > 10
*/


import java.math.BigDecimal;

/**
 * Created by пользователь on 23.12.2016.
 */
public class App {

    public static void main(String[] args) {
        InsertQueryBuilder insertQueryBuilder =
            new InsertQueryBuilder()
                .into("Some_Table")
                .field("F_alpha", "a_value", String.class)
                .field("F_Beta", false, Boolean.class)
                .field("F_gamma", BigDecimal.TEN, BigDecimal.class)
                .field("F_Delta", 0.01, Double.class);

        Query insertQuery = insertQueryBuilder.build();
        System.out.println(insertQuery.getSql());

        DeleteQueryBuilder deleteQueryBuilder =
            new DeleteQueryBuilder()
                .from("Some_Table")
                .whereEqual("F_alpha", "a_value", String.class)
                .whereEqual("F_Beta", false, Boolean.class)
                .whereLess("F_gamma", BigDecimal.TEN, BigDecimal.class)
                .whereGreaterOrEqual("F_Delta", 0.01, Double.class);

        Query deleteQuery = deleteQueryBuilder.build();
        System.out.println(deleteQuery.getSql());

        UpdateQueryBuilder updateQueryBuilder =
                new UpdateQueryBuilder()
                        .update("Some_Table")
                        .set("F_Beta", false, Boolean.class)
                        .set("F_alpha", "a_value", String.class)
                        .set("F_gamma", BigDecimal.TEN, BigDecimal.class)
                        .set("F_Delta", 0.01, Double.class)
                        .whereEqual("F_alpha", "green_elephant", String.class)
                        .whereEqual("F_Beta", true, Boolean.class)
                        .whereLessOrEqual("F_Delta", 0.05, Double.class);

        Query updateQuery = updateQueryBuilder.build();
        System.out.println(updateQuery.getSql());

        SelectQueryBuilder selectQueryBuilder =
                new SelectQueryBuilder()
                        .select("F_alpha", "F_BETA", "F_GAMMA", "F_DELTA")
                        .from("Some_Table")
                        .whereEqual("F_alpha", "green_elephant", String.class)
                        .whereGreaterOrEqual("F_DELTA", 0.05, Double.class)
                        .whereEqual("F_BETA", true, Boolean.class)
                        .whereGreater("F_GAMMA", BigDecimal.TEN, BigDecimal.class);

        Query selectQuery = selectQueryBuilder.build();
        System.out.println(selectQuery.getSql());
    }
}
