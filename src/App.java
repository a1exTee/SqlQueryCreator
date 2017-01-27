
/*
Суть задачи в следующем. Есть класс SQL-фабрики, который представляет собой креатор объектов-запросов (типов INSERT / DELETE / UPDATE / SELECT)
далее эти объекты предоставляют методы-замыкатели на свой класс, например для инсерта:

InsertQuery iq =
SQLFactory.createInsertQuery().into("Some_Table")
.field("F_alpha", "a_value", String.class)
.field("F_Beta", false, Boolean.class)
.field("F_gamma", BigDecimal.TEN, BigDecimal.class)
.field("F_Delta", 0.01, Double.class);
каждый класс для каждого типа запроса предоставляет метод build(), возвращающий строковый эквивалент запроса, например, для описанного выше:
iq.build()
вернёт

INSERT INTO SOME_TABLE(F_ALPHA, F_BETA, F_GAMMA, F_DELTA) VALUES ('a_value', false, 10, 0.01)*/


/**
 * Created by пользователь on 23.12.2016.
 */
public class App {
}
