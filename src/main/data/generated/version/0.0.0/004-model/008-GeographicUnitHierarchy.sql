-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- Table: GeographicUnitHierarchy.GeographicUnitHierarchy

-- DROP TABLE "GeographicUnitHierarchy"."GeographicUnitHierarchy";

CREATE TABLE "GeographicUnitHierarchy"."GeographicUnitHierarchy"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "ParentGeographicUnitId" bigint NOT NULL,
    "ChildGeographicUnitId" bigint NOT NULL,

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

    CONSTRAINT "GeographicUnitHierarchy_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "GeographicUnitHierarchy_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$'),

    CONSTRAINT "GeographicUnitHierarchy_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),

    CONSTRAINT "GeographicUnitHieracrhy_FK_EntitySubtypeId" FOREIGN KEY ("EntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype"("Id"),
    CONSTRAINT "GeographicUnitHierarchy_FK_ParentGeographicUnitId" FOREIGN KEY ("ParentGeographicUnitId") REFERENCES "GeographicUnit"."GeographicUnit"("Id"),
    CONSTRAINT "GeographicUnitHierarchy_FK_ChildGeographicUnitId" FOREIGN KEY ("ChildGeographicUnitId") REFERENCES "GeographicUnit"."GeographicUnit"("Id")
)

    TABLESPACE pg_default;

CREATE INDEX "GeographicUnitHierarchy_IDX_ParentGeographicUnitId" ON "GeographicUnitHierarchy"."GeographicUnitHierarchy" ("ParentGeographicUnitId")
CREATE INDEX "GeographicUnitHierarchy_IDX_ChildGeographicUnitId" ON "GeographicUnitHierarchy"."GeographicUnitHierarchy" ("ChildGeographicUnitId")
