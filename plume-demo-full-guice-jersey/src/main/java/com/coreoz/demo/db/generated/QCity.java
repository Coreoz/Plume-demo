package com.coreoz.demo.db.generated;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QCity is a Querydsl query type for City
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QCity extends com.querydsl.sql.RelationalPathBase<City> {

    private static final long serialVersionUID = 1643343782;

    public static final QCity city = new QCity("demo_city");

    public final BooleanPath active = createBoolean("active");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> idFileImage = createNumber("idFileImage", Long.class);

    public final DateTimePath<java.time.Instant> lastModified = createDateTime("lastModified", java.time.Instant.class);

    public final StringPath name = createString("name");

    public final com.querydsl.sql.PrimaryKey<City> constraintD = createPrimaryKey(id);

    public final com.querydsl.sql.ForeignKey<com.coreoz.plume.file.db.querydsl.FileEntityQuerydsl> demoCityImage = createForeignKey(idFileImage, "id");

    public QCity(String variable) {
        super(City.class, forVariable(variable), "plume_demo", "demo_city");
        addMetadata();
    }

    public QCity(String variable, String schema, String table) {
        super(City.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QCity(String variable, String schema) {
        super(City.class, forVariable(variable), schema, "demo_city");
        addMetadata();
    }

    public QCity(Path<? extends City> path) {
        super(path.getType(), path.getMetadata(), "plume_demo", "demo_city");
        addMetadata();
    }

    public QCity(PathMetadata metadata) {
        super(City.class, metadata, "plume_demo", "demo_city");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(active, ColumnMetadata.named("active").withIndex(4).ofType(Types.TINYINT).withSize(3).notNull());
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(idFileImage, ColumnMetadata.named("id_file_image").withIndex(3).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(lastModified, ColumnMetadata.named("last_modified").withIndex(5).ofType(Types.TIMESTAMP).withSize(23).withDigits(10).notNull());
        addMetadata(name, ColumnMetadata.named("name").withIndex(2).ofType(Types.VARCHAR).withSize(255).notNull());
    }

}

