-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- Table: InformationSystem.InformationSystem

-- DROP TABLE "InformationSystem"."InformationSystem";

CREATE TABLE "InformationSystem"."InformationSystem"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "LocalizedName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedDescription" character varying(2000) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedAbbreviation" character varying(15) COLLATE pg_catalog."default" NOT NULL,

    "UniformResourceIdentifierId" bigint NOT NULL,
    "PurposeInformationSystemEntitySubtypeId" bigint NOT NULL,
    "TechnologyInformationSystemEntitySubtypeId" bigint NOT NULL,
    "ControllingLegalEntityId" bigint NOT NULL,

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

    CONSTRAINT "InformationSystem_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "InformationSystem_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$'),

    CONSTRAINT "InformationSystem_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),
    CONSTRAINT "InformationSystem_UQ1_LocalizedName_DeletedAtDateTimeUtc" UNIQUE ("LocalizedName", "DeletedAtDateTimeUtc"),
    CONSTRAINT "InformationSystem_UQ1_UniformResourceIdentifierId_ControllingLegalEntityId_DeletedAtDateTimeUtc" UNIQUE ("UniformResourceIdentifierId", "ControllingLegalEntityId", "DeletedAtDateTimeUtc"),

    CONSTRAINT "InformationSystem_FK_EntitySubtypeId" FOREIGN KEY ("EntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype" ("Id"),
    CONSTRAINT "InformationSystem_FK_UniformResourceIdentifierId" FOREIGN KEY ("UniformResourceIdentifierId") REFERENCES "UniformResourceIdentifier"."UniformResourceIdentifier" ("Id"),
    CONSTRAINT "InformationSystem_FK_PurposeInformationSystemEntitySubtypeId" FOREIGN KEY ("PurposeInformationSystemEntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype" ("Id"),
    CONSTRAINT "InformationSystem_FK_TechnologyInformationSystemEntitySubtypeId" FOREIGN KEY ("TechnologyInformationSystemEntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype" ("Id"),
    CONSTRAINT "InformationSystem_FK_ControllingLegalEntityId" FOREIGN KEY ("ControllingLegalEntityId") REFERENCES "LegalEntity"."LegalEntity" ("Id")
)

    TABLESPACE pg_default;

CREATE INDEX "InformationSystem_IDX_LocalizedName" ON "InformationSystem"."InformationSystem" ("LocalizedName")
