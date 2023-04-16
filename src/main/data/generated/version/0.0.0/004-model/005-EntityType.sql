-- Table: EntityType.EntityType

-- DROP TABLE "EntityType"."EntityType";

CREATE TABLE "EntityType"."EntityType"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(100) COLLATE pg_catalog."default" NOT NULL,

    "LocalizedName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedDescription" character varying(2000) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedAbbreviation" character varying(15) COLLATE pg_catalog."default" NOT NULL,

    "ResourceName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "Ordinal" bigint NOT NULL,
    "IsActive" boolean NOT NULL,
    "CorrelationUuid" uuid NOT NULL,
    "Digest" character varying(500) COLLATE pg_catalog."default" NOT NULL,
    "CreatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "CreatedByInformationSystemUserId" bigint NOT NULL,
    "UpdatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "UpdatedByInformationSystemUserId" bigint NOT NULL,
    "DeletedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "DeletedByInformationSystemUserId" bigint NOT NULL,

    CONSTRAINT "EntityType_PK" PRIMARY KEY ("Id")
)

    TABLESPACE pg_default;

ALTER TABLE "EntityType"."EntityType"
    OWNER to "AafCoreOwner";

GRANT USAGE ON SCHEMA "EntityType" TO "AafCoreClient";
GRANT SELECT ON ALL TABLES IN SCHEMA "EntityType" TO "AafCoreClient";
