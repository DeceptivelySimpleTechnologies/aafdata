-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- Table: EntitySubtypeHierarchy.EntitySubtypeHierarchy

-- DROP TABLE "EntitySubtypeHierarchy"."EntitySubtypeHierarchy";

CREATE TABLE "EntitySubtypeHierarchy"."EntitySubtypeHierarchy"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "ParentEntitySubtypeId" bigint NOT NULL,
    "ChildEntitySubtypeId" bigint NOT NULL,

    "ResourceName" character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT '',
    "Ordinal" bigint NOT NULL DEFAULT -1,
    "IsActive" boolean NOT NULL DEFAULT true,
    "CorrelationUuid" uuid NOT NULL,
    "Digest" character varying(500) COLLATE pg_catalog."default" NOT NULL,
    "CreatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "CreatedByInformationSystemUserId" bigint NOT NULL,
    "UpdatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "UpdatedByInformationSystemUserId" bigint NOT NULL,
    "DeletedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "DeletedByInformationSystemUserId" bigint NOT NULL,

    CONSTRAINT "EntSubHir_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "EntSubHir_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$'),

    CONSTRAINT "EntSubHir_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),
    CONSTRAINT "EntSubHir_UQ1_ParentEntSubId_ChildEntSubId_DeletedAtDateTimeUtc" UNIQUE ("ParentEntitySubtypeId", "ChildEntitySubtypeId", "DeletedAtDateTimeUtc"),

    CONSTRAINT "EntSubHir_FK_EntitySubtypeId" FOREIGN KEY ("EntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype"("Id"),
    CONSTRAINT "EntSubHir_FK_ParentEntitySubtypeId" FOREIGN KEY ("ParentEntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype"("Id"),
    CONSTRAINT "EntSubHir_FK_ChildEntitySubtypeId" FOREIGN KEY ("ChildEntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype"("Id")
)

    TABLESPACE pg_default;

CREATE INDEX "EntSubHir_IDX_ParentEntitySubtypeId" ON "EntitySubtypeHierarchy"."EntitySubtypeHierarchy" ("ParentEntitySubtypeId");
CREATE INDEX "EntSubHir_IDX_ChildEntitySubtypeId" ON "EntitySubtypeHierarchy"."EntitySubtypeHierarchy" ("ChildEntitySubtypeId")
